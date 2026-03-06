package com.airplane.controller;

import com.airplane.dto.FlightDto;
import com.airplane.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST végpontok a járatok listázásához és szűréséhez. */
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /** Visszaadja az összes járatot (GET /api/flights). */
    @GetMapping
    public List<FlightDto> getAll() {
        return flightService.getAllFlights();
    }

    /** Járatok szűrése indulási és/vagy célváros szerint (GET /api/flights/search?from=&to=). Üres param = nincs szűrő. */
    @GetMapping("/search")
    public List<FlightDto> search(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return flightService.search(from, to);
    }
}
