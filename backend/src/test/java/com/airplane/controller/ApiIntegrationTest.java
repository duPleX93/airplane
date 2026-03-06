package com.airplane.controller;

import com.airplane.dto.AirlineDto;
import com.airplane.dto.CityExtremesDto;
import com.airplane.dto.FlightDto;
import com.airplane.dto.RouteResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAirlines_returnsList() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/airlines"))
                .andExpect(status().isOk())
                .andReturn();
        List<AirlineDto> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, AirlineDto.class));
        assertFalse(body.isEmpty());
    }

    @Test
    void getFlights_returnsList() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andReturn();
        List<FlightDto> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, FlightDto.class));
        assertFalse(body.isEmpty());
    }

    @Test
    void getCitiesExtremes_returnsSmallestAndLargest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cities/extremes"))
                .andExpect(status().isOk())
                .andReturn();
        CityExtremesDto body = objectMapper.readValue(result.getResponse().getContentAsString(), CityExtremesDto.class);
        assertNotNull(body.getSmallest());
        assertNotNull(body.getLargest());
        assertTrue(body.getSmallest().getPopulation() < body.getLargest().getPopulation());
    }

    @Test
    void getRoutesPerAirline_returnsResults() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/routes/per-airline"))
                .andExpect(status().isOk())
                .andReturn();
        List<RouteResultDto> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, RouteResultDto.class));
        assertFalse(body.isEmpty());
    }

    @Test
    void getBestRoute_returnsResult() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/routes/best"))
                .andExpect(status().isOk())
                .andReturn();
        RouteResultDto body = objectMapper.readValue(result.getResponse().getContentAsString(), RouteResultDto.class);
        assertNotNull(body.getFromCityName());
        assertNotNull(body.getToCityName());
    }

    @Test
    void searchFlights_withNoParams_returnsAll() throws Exception {
        mockMvc.perform(get("/api/flights/search"))
                .andExpect(status().isOk());
    }

    @Test
    void searchFlights_withFrom_returnsFiltered() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/flights/search").param("from", "BUD"))
                .andExpect(status().isOk())
                .andReturn();
        List<FlightDto> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, FlightDto.class));
        body.forEach(f -> assertEquals("BUD", f.getFromCityId()));
    }
}
