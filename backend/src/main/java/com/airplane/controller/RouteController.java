package com.airplane.controller;

import com.airplane.dto.RouteResultDto;
import com.airplane.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** REST végpontok az útvonalak lekérdezéséhez (legkisebb → legnagyobb város). */
@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /** Légitársaságonkénti legrövidebb útvonal (GET /api/routes/per-airline). */
    @GetMapping("/per-airline")
    public List<RouteResultDto> perAirline() {
        return routeService.perAirlineRoutes();
    }

    /** Egyetlen legjobb útvonal tetszőleges légitársasággal (GET /api/routes/best). */
    @GetMapping("/best")
    public RouteResultDto best() {
        return routeService.bestRoute();
    }
}
