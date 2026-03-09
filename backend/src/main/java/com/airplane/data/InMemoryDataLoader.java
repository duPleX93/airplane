package com.airplane.data;

import com.airplane.domain.Airline;
import com.airplane.domain.City;
import com.airplane.domain.Flight;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.*;

/**
 * Betölti a városok, légitársaságok és járatok mintaadatait memóriába induláskor.
 * Nincs külső fájl vagy adatbázis – minden adat itt van definiálva.
 */
@Component
public class InMemoryDataLoader {

    private final List<City> cities = new ArrayList<>();
    private final List<Airline> airlines = new ArrayList<>();
    private final List<Flight> flights = new ArrayList<>();
    private final Map<String, City> cityById = new HashMap<>();
    private final Map<String, Airline> airlineById = new HashMap<>();

    /** Induláskor meghívódik: betölti a városokat, légitársaságokat és járatokat a memóriába. */
    @PostConstruct
    public void loadData() {
        loadCities();
        loadAirlines();
        loadFlights();
    }

    /** Városok betöltése (id, név, lakosság); listába és cityById map-be. */
    private void loadCities() {
        // id, név, lakosság (a legkisebb: Keszthely, a legnagyobb: Budapest)
        var data = List.of(
                cityRow("KESZ", "Keszthely", 19_500),
                cityRow("EGER", "Eger", 54_532),
                cityRow("SOP", "Sopron", 62_250),
                cityRow("NYIR", "Nyíregyháza", 116_874),
                cityRow("GYOR", "Győr", 132_035),
                cityRow("PEC", "Pécs", 142_873),
                cityRow("MISC", "Miskolc", 154_304),
                cityRow("SZEG", "Szeged", 160_766),
                cityRow("DEB", "Debrecen", 201_432),
                cityRow("BUD", "Budapest", 1_752_286)
        );
        for (CityRow r : data) {
            City c = new City(r.id, r.name, r.pop);
            cities.add(c);
            cityById.put(c.getId(), c);
        }
    }

    /** Légitársaságok betöltése (id, név); listába és airlineById map-be. */
    private void loadAirlines() {
        var data = List.of(
                airlineRow("W6", "Wizz Air"),
                airlineRow("FR", "Ryanair"),
                airlineRow("MA", "Malev")
        );
        for (AirlineRow r : data) {
            Airline a = new Airline(r.id, r.name);
            airlines.add(a);
            airlineById.put(a.getId(), a);
        }
    }

    /**
     * Járatok: id, indulás város id, érkezés város id, légitársaság id, km, perc.
     * Útvonal: legkisebb város (Keszthely) → legnagyobb (Budapest) több légitársasággal is elérhető.
     */
    private void loadFlights() {
        var data = List.of(
                flight("F1", "KESZ", "GYOR", "W6", 80, 45),
                flight("F2", "GYOR", "BUD", "W6", 120, 55),
                flight("F3", "BUD", "DEB", "W6", 180, 70),
                flight("F4", "DEB", "BUD", "W6", 170, 65),
                flight("F5", "SOP", "GYOR", "FR", 115, 50),
                flight("F6", "GYOR", "SOP", "FR", 115, 50),
                flight("F7", "KESZ", "SOP", "FR", 90, 45),
                flight("F8", "SOP", "BUD", "FR", 120, 55),
                flight("F9", "BUD", "MISC", "MA", 200, 75),
                flight("F10", "MISC", "BUD", "MA", 130, 60),
                flight("F11", "KESZ", "BUD", "MA", 140, 65),
                flight("F12", "PEC", "BUD", "W6", 100, 50),
                flight("F13", "SZEG", "BUD", "FR", 165, 70),
                flight("F14", "EGER", "BUD", "MA", 140, 60)
        );
        for (FlightRow f : data) {
            flights.add(new Flight(f.id, f.km, f.min,
                    city(f.from), city(f.to), airline(f.airline)));
        }
    }

    private static CityRow cityRow(String id, String name, int pop) {
        CityRow r = new CityRow();
        r.id = id;
        r.name = name;
        r.pop = pop;
        return r;
    }

    private static AirlineRow airlineRow(String id, String name) {
        AirlineRow r = new AirlineRow();
        r.id = id;
        r.name = name;
        return r;
    }

    private static FlightRow flight(String id, String from, String to, String airline, int km, int min) {
        FlightRow f = new FlightRow();
        f.id = id;
        f.from = from;
        f.to = to;
        f.airline = airline;
        f.km = km;
        f.min = min;
        return f;
    }

    /** Id alapján város lekérése; loadFlights-ban használt. Nincs ilyen id → NPE. */
    private City city(String id) {
        return Objects.requireNonNull(cityById.get(id), "city " + id);
    }

    /** Id alapján légitársaság lekérése; loadFlights-ban használt. Nincs null – hibára dob. */
    private Airline airline(String id) {
        return Objects.requireNonNull(airlineById.get(id), "airline " + id);
    }

    /** Városok listája (változtathatatlan); a service réteg ezt hívja. */
    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    /** Légitársaságok listája (változtathatatlan). */
    public List<Airline> getAirlines() {
        return Collections.unmodifiableList(airlines);
    }

    /** Járatok listája (változtathatatlan). */
    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    private static class CityRow {
        String id;
        String name;
        int pop;
    }

    private static class AirlineRow {
        String id;
        String name;
    }

    private static class FlightRow {
        String id;
        String from;
        String to;
        String airline;
        int km;
        int min;
    }
}
