package com.airplane.service;

import com.airplane.domain.Airline;
import com.airplane.domain.City;
import com.airplane.domain.Flight;
import com.airplane.dto.*;
import com.airplane.data.InMemoryDataLoader;
import org.springframework.stereotype.Service;

import java.util.*;

/** Útvonalak számítása: legkisebb → legnagyobb város; légitársaságonkénti és „legjobb” útvonal; PathResult → DTO. */
@Service
public class RouteService {

    private static final String NO_ROUTE = "Nincs útvonal";

    private final InMemoryDataLoader data;
    private final PathfindingService pathfinding;

    public RouteService(InMemoryDataLoader data, PathfindingService pathfinding) {
        this.data = data;
        this.pathfinding = pathfinding;
    }

    /** Minden légitársaságra kiszámolja a legrövidebb útvonalat (km) a legkisebb és legnagyobb város között; üres lista ha nincs város vagy egyeznek. */
    public List<RouteResultDto> perAirlineRoutes() {
        City smallest = data.getCities().stream().min(Comparator.comparingInt(City::getPopulation)).orElse(null);
        City largest = data.getCities().stream().max(Comparator.comparingInt(City::getPopulation)).orElse(null);
        if (smallest == null || largest == null || smallest.equals(largest)) {
            return List.of();
        }
        String fromId = smallest.getId();
        String toId = largest.getId();

        List<RouteResultDto> results = new ArrayList<>();
        for (Airline airline : data.getAirlines()) {
            List<Flight> airlineFlights = data.getFlights().stream()
                    .filter(f -> f.getAirline().getId().equals(airline.getId()))
                    .toList();
            PathfindingService.PathResult path = pathfinding.shortestPathByDistance(fromId, toId, airlineFlights);
            RouteResultDto dto = toRouteResult(path, airline, smallest, largest);
            results.add(dto);
        }
        return results;
    }

    /** Egyetlen legjobb útvonal (összes járat használható) a legkisebb és legnagyobb város között; ha nincs útvonal, message = "Nincs útvonal". */
    public RouteResultDto bestRoute() {
        City smallest = data.getCities().stream().min(Comparator.comparingInt(City::getPopulation)).orElse(null);
        City largest = data.getCities().stream().max(Comparator.comparingInt(City::getPopulation)).orElse(null);
        if (smallest == null || largest == null || smallest.equals(largest)) {
            RouteResultDto dto = new RouteResultDto();
            dto.setMessage(NO_ROUTE);
            return dto;
        }
        PathfindingService.PathResult path = pathfinding.shortestPathByDistance(
                smallest.getId(), largest.getId(), data.getFlights());
        return toRouteResult(path, null, smallest, largest);
    }

    /** PathResult + indulás/cél város (és opcionálisan légitársaság) → RouteResultDto: távolság, idő, átszállások, szegmensek. */
    private RouteResultDto toRouteResult(PathfindingService.PathResult path, Airline airline, City from, City to) {
        RouteResultDto dto = new RouteResultDto();
        dto.setFromCityName(from.getName());
        dto.setToCityName(to.getName());
        if (airline != null) {
            dto.setAirlineId(airline.getId());
            dto.setAirlineName(airline.getName());
        }
        if (!path.isReachable()) {
            dto.setMessage(NO_ROUTE);
            return dto;
        }
        dto.setTotalDistanceKm(path.getTotalDistanceKm());
        dto.setTotalDurationFormatted(DurationFormatter.formatMinutes(path.getTotalElapsedMinutes()));
        dto.setTotalElapsedMinutes(path.getTotalElapsedMinutes());
        dto.setTransfers(Math.max(0, path.getFlights().size() - 1));
        dto.setSegments(buildSegments(path.getFlights(), path.getTotalElapsedMinutes()));
        return dto;
    }

    /** Az útvonal járatlistájából szegmenseket készít: indulás/érkezés idő, várakozás, formázott idők. */
    private List<RouteSegmentDto> buildSegments(List<Flight> pathFlights, int totalElapsedMinutes) {
        List<RouteSegmentDto> segments = new ArrayList<>();
        int currentMinute = 0;
        for (int i = 0; i < pathFlights.size(); i++) {
            Flight f = pathFlights.get(i);
            int depMin = nextHourStart(currentMinute);
            Integer waitMin = (i == 0) ? null : (depMin - currentMinute);
            int arrMin = depMin + f.getDurationMinutes();
            currentMinute = arrMin;

            RouteSegmentDto seg = new RouteSegmentDto();
            seg.setFlightId(f.getId());
            seg.setFromCityName(f.getFromCity().getName());
            seg.setToCityName(f.getToCity().getName());
            seg.setAirlineName(f.getAirline().getName());
            seg.setDistanceKm(f.getDistanceKm());
            seg.setDurationMinutes(f.getDurationMinutes());
            seg.setDurationFormatted(DurationFormatter.formatMinutes(f.getDurationMinutes()));
            seg.setDepartureTimeFormatted(formatTime(depMin));
            seg.setArrivalTimeFormatted(formatTime(arrMin));
            seg.setWaitMinutes(waitMin);
            segments.add(seg);
        }
        return segments;
    }

    /** Következő óra eleje (percben, 0-tól); induláskor 0, utána mindig következő óra. */
    private static int nextHourStart(int minute) {
        if (minute == 0) return 0;
        return ((minute / 60) + 1) * 60;
    }

    /** Perc érték (0-tól) → "óra:perc" formátum pl. 125 → "2:05". */
    private static String formatTime(int minute) {
        int h = minute / 60;
        int m = minute % 60;
        return String.format("%d:%02d", h, m);
    }
}
