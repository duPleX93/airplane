package com.airplane.service;

import com.airplane.domain.Flight;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Dijkstra legrövidebb útvonal távolság (km) szerint. A járatlista a gráf éleit adja.
 * Minden járat minden óra elején indul; a csatlakozások közötti várakozási idő beleszámít.
 */
@Service
public class PathfindingService {

    /**
     * Dijkstra eredménye: útvonal járatok listájában; minden lépésnél az érkezés percét (a nap 0. percétől)
     * tároljuk, hogy a következő indulás várakozási idejét ki tudjuk számolni.
     */
    public static class PathResult {
        private final List<Flight> flights;
        private final int totalDistanceKm;
        private final int totalElapsedMinutes; // várakozás is beleszámít

        public PathResult(List<Flight> flights, int totalDistanceKm, int totalElapsedMinutes) {
            this.flights = flights == null ? List.of() : List.copyOf(flights);
            this.totalDistanceKm = totalDistanceKm;
            this.totalElapsedMinutes = totalElapsedMinutes;
        }

        public List<Flight> getFlights() {
            return flights;
        }

        public int getTotalDistanceKm() {
            return totalDistanceKm;
        }

        public int getTotalElapsedMinutes() {
            return totalElapsedMinutes;
        }

        public boolean isReachable() {
            return !flights.isEmpty();
        }
    }

    /**
     * Legrövidebb útvonal távolság (km) szerint fromCityId-ből toCityId-be, csak a megadott járatokkal.
     * A járatok minden óra elején indulnak. A várakozást a következő teljes órára kerekítjük.
     */
    public PathResult shortestPathByDistance(
            String fromCityId,
            String toCityId,
            List<Flight> flights
    ) {
        if (fromCityId == null || toCityId == null || flights == null || flights.isEmpty()) {
            return new PathResult(List.of(), 0, 0);
        }
        if (fromCityId.equals(toCityId)) {
            return new PathResult(List.of(), 0, 0);
        }

        // Szomszédsági lista: cityId -> (szomszéd város, járat, távolság) listája
        Map<String, List<Edge>> graph = new HashMap<>();
        for (Flight f : flights) {
            graph
                    .computeIfAbsent(f.getFromCity().getId(), k -> new ArrayList<>())
                    .add(new Edge(f.getToCity().getId(), f, f.getDistanceKm()));
        }

        // Dijkstra: dist[cityId] = eddig ismert legjobb távolság; városonként az érkezés percét is tároljuk
        Map<String, Integer> dist = new HashMap<>();
        Map<String, Flight> prevFlight = new HashMap<>();
        Map<String, Integer> arrivalMinute = new HashMap<>();
        dist.put(fromCityId, 0);
        arrivalMinute.put(fromCityId, 0);

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.distanceKm));
        pq.add(new State(fromCityId, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            String u = cur.cityId;
            if (cur.distanceKm > dist.getOrDefault(u, Integer.MAX_VALUE)) {
                continue;
            }
            if (u.equals(toCityId)) {
                break;
            }
            for (Edge e : graph.getOrDefault(u, List.of())) {
                String v = e.toCityId;
                int altDist = dist.get(u) + e.distanceKm;
                // u-ból indulás: érkezés utáni következő teljes óra
                int arrMin = arrivalMinute.get(u);
                int depMin = nextHourStart(arrMin);
                int flightDuration = e.flight.getDurationMinutes();
                int arrAtV = depMin + flightDuration;
                int elapsed = arrAtV; // a 0. perctől

                if (altDist < dist.getOrDefault(v, Integer.MAX_VALUE)) {
                    dist.put(v, altDist);
                    prevFlight.put(v, e.flight);
                    arrivalMinute.put(v, arrAtV);
                    pq.add(new State(v, altDist, elapsed));
                }
            }
        }

        if (!dist.containsKey(toCityId)) {
            return new PathResult(List.of(), 0, 0);
        }

        // Útvonal visszaépítése
        List<Flight> path = new ArrayList<>();
        String at = toCityId;
        while (at != null && prevFlight.containsKey(at)) {
            Flight f = prevFlight.get(at);
            path.add(0, f);
            at = f.getFromCity().getId();
        }
        int totalElapsed = arrivalMinute.get(toCityId);
        return new PathResult(path, dist.get(toCityId), totalElapsed);
    }

    /** Következő óra eleje percben (0 = 0, egyébként (perc/60+1)*60). */
    private static int nextHourStart(int minute) {
        if (minute == 0) return 0;
        return ((minute / 60) + 1) * 60;
    }

    private record Edge(String toCityId, Flight flight, int distanceKm) {}

    private record State(String cityId, int distanceKm, int elapsedMinutes) {}
}
