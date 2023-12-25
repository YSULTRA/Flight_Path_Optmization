package org.example;

public class Flight {
    private String destination;
    private double weight;

    public Flight(String destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public String getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}
