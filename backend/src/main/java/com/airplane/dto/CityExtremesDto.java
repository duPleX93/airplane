package com.airplane.dto;

public class CityExtremesDto {
    private CityDto smallest;
    private CityDto largest;

    public CityExtremesDto() {}

    public CityExtremesDto(CityDto smallest, CityDto largest) {
        this.smallest = smallest;
        this.largest = largest;
    }

    public CityDto getSmallest() { return smallest; }
    public void setSmallest(CityDto smallest) { this.smallest = smallest; }
    public CityDto getLargest() { return largest; }
    public void setLargest(CityDto largest) { this.largest = largest; }
}
