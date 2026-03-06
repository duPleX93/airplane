package com.airplane.controller;

import com.airplane.dto.RouteResultDto;
import com.airplane.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/per-airline")
    public List<RouteResultDto> perAirline() {
        return routeService.perAirlineRoutes();
    }

    @GetMapping("/best")
    public RouteResultDto best() {
        return routeService.bestRoute();
    }
}
