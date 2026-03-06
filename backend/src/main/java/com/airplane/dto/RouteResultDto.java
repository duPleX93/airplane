package com.airplane.dto;

import java.util.List;

public class RouteResultDto {
    private String message; // "Nincs útvonal" or null when route exists
    private String airlineId;
    private String airlineName;
    private String fromCityName;
    private String toCityName;
    private Integer totalDistanceKm;
    private String totalDurationFormatted;
    private Integer totalElapsedMinutes;
    private Integer transfers;
    private List<RouteSegmentDto> segments;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getAirlineId() { return airlineId; }
    public void setAirlineId(String airlineId) { this.airlineId = airlineId; }
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
    public String getFromCityName() { return fromCityName; }
    public void setFromCityName(String fromCityName) { this.fromCityName = fromCityName; }
    public String getToCityName() { return toCityName; }
    public void setToCityName(String toCityName) { this.toCityName = toCityName; }
    public Integer getTotalDistanceKm() { return totalDistanceKm; }
    public void setTotalDistanceKm(Integer totalDistanceKm) { this.totalDistanceKm = totalDistanceKm; }
    public String getTotalDurationFormatted() { return totalDurationFormatted; }
    public void setTotalDurationFormatted(String totalDurationFormatted) { this.totalDurationFormatted = totalDurationFormatted; }
    public Integer getTotalElapsedMinutes() { return totalElapsedMinutes; }
    public void setTotalElapsedMinutes(Integer totalElapsedMinutes) { this.totalElapsedMinutes = totalElapsedMinutes; }
    public Integer getTransfers() { return transfers; }
    public void setTransfers(Integer transfers) { this.transfers = transfers; }
    public List<RouteSegmentDto> getSegments() { return segments; }
    public void setSegments(List<RouteSegmentDto> segments) { this.segments = segments; }
}
