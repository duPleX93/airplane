package com.airplane.service;

import com.airplane.domain.Airline;
import com.airplane.domain.City;
import com.airplane.domain.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathfindingServiceTest {

    private PathfindingService pathfinding;
    private City a, b, c;
    private Airline airline;

    @BeforeEach
    void setUp() {
        pathfinding = new PathfindingService();
        a = new City("A", "CityA", 100);
        b = new City("B", "CityB", 200);
        c = new City("C", "CityC", 300);
        airline = new Airline("X", "TestAirline");
    }

    @Test
    void sameCity_returnsEmptyPath() {
        PathfindingService.PathResult result = pathfinding.shortestPathByDistance("A", "A", List.of());
        assertTrue(result.getFlights().isEmpty());
        assertEquals(0, result.getTotalDistanceKm());
    }

    @Test
    void directFlight_returnsOneSegment() {
        Flight f = new Flight("F1", 100, 60, a, b, airline);
        PathfindingService.PathResult result = pathfinding.shortestPathByDistance("A", "B", List.of(f));
        assertEquals(1, result.getFlights().size());
        assertEquals(100, result.getTotalDistanceKm());
        assertEquals(60, result.getTotalElapsedMinutes());
    }

    @Test
    void twoSegments_includesWaitingTime() {
        Flight f1 = new Flight("F1", 50, 45, a, b, airline);
        Flight f2 = new Flight("F2", 50, 45, b, c, airline);
        PathfindingService.PathResult result = pathfinding.shortestPathByDistance("A", "C", List.of(f1, f2));
        assertEquals(2, result.getFlights().size());
        assertEquals(100, result.getTotalDistanceKm());
        // Arrive B at 45, next departure at 60, arrive C at 60+45=105
        assertEquals(105, result.getTotalElapsedMinutes());
    }

    @Test
    void unreachable_returnsEmpty() {
        Flight f = new Flight("F1", 100, 60, a, b, airline);
        PathfindingService.PathResult result = pathfinding.shortestPathByDistance("A", "C", List.of(f));
        assertFalse(result.isReachable());
        assertTrue(result.getFlights().isEmpty());
    }
}
