package org.example;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String code;
    private List<Flight> flights;

    public Airport(String code) {
        this.code = code;
        this.flights = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }
}

