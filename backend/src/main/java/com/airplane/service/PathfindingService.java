package com.airplane.service;

import com.airplane.domain.Flight;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Dijkstra shortest path by distance (km). Uses flight list as graph edges.
 * All flights depart at the start of every hour; waiting time between connections is included.
 */
@Service
public class PathfindingService {

    /**
     * Result of Dijkstra: path as list of flights, and for each step we track
     * arrival minute (from start of day 0) so we can compute waiting time for next departure.
     */
    public static class PathResult {
        private final List<Flight> flights;
        private final int totalDistanceKm;
        private final int totalElapsedMinutes; // including waiting

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
     * Find shortest path by distance (km) from fromCityId to toCityId using only the given flights.
     * Flights depart at the start of every hour. Waiting time is rounded up to next full hour.
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

        // Build adjacency: cityId -> list of (neighbor city, flight, distance)
        Map<String, List<Edge>> graph = new HashMap<>();
        for (Flight f : flights) {
            graph
                    .computeIfAbsent(f.getFromCity().getId(), k -> new ArrayList<>())
                    .add(new Edge(f.getToCity().getId(), f, f.getDistanceKm()));
        }

        // Dijkstra: dist[cityId] = best known distance; we also need arrival time at each city
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
                // Departure from u: next full hour after arrival
                int arrMin = arrivalMinute.get(u);
                int depMin = nextHourStart(arrMin);
                int flightDuration = e.flight.getDurationMinutes();
                int arrAtV = depMin + flightDuration;
                int elapsed = arrAtV; // from minute 0

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

        // Reconstruct path
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

    private static int nextHourStart(int minute) {
        if (minute == 0) return 0;
        return ((minute / 60) + 1) * 60;
    }

    private record Edge(String toCityId, Flight flight, int distanceKm) {}

    private record State(String cityId, int distanceKm, int elapsedMinutes) {}
}
