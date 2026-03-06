package com.airplane.service;

import com.airplane.domain.Airline;
import com.airplane.dto.AirlineDto;
import com.airplane.data.InMemoryDataLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {

    private final InMemoryDataLoader data;

    public AirlineService(InMemoryDataLoader data) {
        this.data = data;
    }

    public List<AirlineDto> getAllAirlines() {
        return data.getAirlines().stream()
                .map(a -> new AirlineDto(a.getId(), a.getName()))
                .toList();
    }
}
