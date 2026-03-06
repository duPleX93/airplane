package com.airplane.dto;

public class CityDto {
    private String id;
    private String name;
    private int population;

    public CityDto() {}

    public CityDto(String id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }
}
