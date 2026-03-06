package com.airplane.service;

import com.airplane.domain.Flight;
import com.airplane.dto.FlightDto;
import com.airplane.data.InMemoryDataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/** Járatok listázása és szűrése indulási/célváros szerint; Flight → FlightDto átalakítás. */
@Service
public class FlightService {

    private final InMemoryDataLoader data;

    public FlightService(InMemoryDataLoader data) {
        this.data = data;
    }

    /** Visszaadja az összes járatot DTO-ként. */
    public List<FlightDto> getAllFlights() {
        return data.getFlights().stream().map(this::toDto).toList();
    }

    /** Járatok szűrése fromCityId és/vagy toCityId szerint; null/üres param = nincs szűrő. */
    public List<FlightDto> search(String fromCityId, String toCityId) {
        Stream<Flight> stream = data.getFlights().stream();
        if (fromCityId != null && !fromCityId.isBlank()) {
            stream = stream.filter(f -> f.getFromCity().getId().equals(fromCityId));
        }
        if (toCityId != null && !toCityId.isBlank()) {
            stream = stream.filter(f -> f.getToCity().getId().equals(toCityId));
        }
        return stream.map(this::toDto).toList();
    }

    /** Flight domain → FlightDto (id, távolság, idő, városok, légitársaság). */
    private FlightDto toDto(Flight f) {
        FlightDto dto = new FlightDto();
        dto.setId(f.getId());
        dto.setDistanceKm(f.getDistanceKm());
        dto.setDurationMinutes(f.getDurationMinutes());
        dto.setFromCityId(f.getFromCity().getId());
        dto.setFromCityName(f.getFromCity().getName());
        dto.setToCityId(f.getToCity().getId());
        dto.setToCityName(f.getToCity().getName());
        dto.setAirlineId(f.getAirline().getId());
        dto.setAirlineName(f.getAirline().getName());
        return dto;
    }
}
