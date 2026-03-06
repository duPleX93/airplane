package com.airplane.controller;

import com.airplane.dto.CityExtremesDto;
import com.airplane.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST végpontok a városokkal kapcsolatos lekérdezésekhez. */
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /** Visszaadja a legkisebb és legnagyobb várost lakosság szerint (GET /api/cities/extremes). */
    @GetMapping("/extremes")
    public CityExtremesDto getExtremes() {
        return cityService.getExtremes();
    }
}
