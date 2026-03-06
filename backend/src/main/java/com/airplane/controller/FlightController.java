package com.airplane.controller;

import com.airplane.dto.FlightDto;
import com.airplane.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightDto> getAll() {
        return flightService.getAllFlights();
    }

    @GetMapping("/search")
    public List<FlightDto> search(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return flightService.search(from, to);
    }
}
