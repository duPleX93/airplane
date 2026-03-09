package com.airplane.service;

import com.airplane.domain.City;
import com.airplane.dto.*;
import com.airplane.data.InMemoryDataLoader;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/** Városokkal kapcsolatos lekérdezések (pl. szélsőértékek lakosság szerint). */
@Service
public class CityService {

    private final InMemoryDataLoader data;

    public CityService(InMemoryDataLoader data) {
        this.data = data;
    }

    /** Visszaadja a legkisebb és legnagyobb várost lakosság szerint DTO-ként; üres listánál üres DTO. */
    public CityExtremesDto getExtremes() {
        List<City> cities = data.getCities();
        if (cities.isEmpty()) {
            return new CityExtremesDto();
        }
        City smallest = cities.stream().min(Comparator.comparingInt(City::getPopulation)).orElse(null);
        City largest = cities.stream().max(Comparator.comparingInt(City::getPopulation)).orElse(null);
        return new CityExtremesDto(
                smallest != null ? toDto(smallest) : null,
                largest != null ? toDto(largest) : null
        );
    }

    /** City domain → CityDto (id, név, lakosság). */
    private static CityDto toDto(City c) {
        return new CityDto(c.getId(), c.getName(), c.getPopulation());
    }
}
