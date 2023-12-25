package org.example;

import java.util.*;

public class DijkstraShortestPath {
    public static List<String> findShortestPath(Map<String, List<Flight>> graph, String source, String destination) {
        Map<String, Double> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<QueueNode> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.distance));

        for (String airport : graph.keySet()) {
            if (airport.equals(source)) {
                distance.put(airport, 0.0);
            } else {
                distance.put(airport, Double.MAX_VALUE);
            }
            queue.add(new QueueNode(airport, distance.get(airport)));
        }

        while (!queue.isEmpty()) {
            String currentAirport = queue.poll().airport;
            if (currentAirport.equals(destination)) {
                break;  // Found the shortest path to the destination
            }

            List<Flight> flights = graph.get(currentAirport);
            if (flights != null) {
                for (Flight flight : flights) {
                    double alt = distance.get(currentAirport) + flight.getWeight();
                    if (alt < distance.get(flight.getDestination())) {
                        distance.put(flight.getDestination(), alt);
                        previous.put(flight.getDestination(), currentAirport);
                        queue.add(new QueueNode(flight.getDestination(), alt));
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<String> shortestPath = new ArrayList<>();
        String current = destination;
        while (current != null) {
            shortestPath.add(current);
            current = previous.get(current);
        }
        Collections.reverse(shortestPath);

        return shortestPath.size() > 1 ? shortestPath : new ArrayList<>();  // Return an empty list if no path was found
    }
    public static double calculateTotalWeight(Map<String, List<Flight>> graph, List<String> path) {
        double totalWeight = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            String currentVertex = path.get(i);
            String nextVertex = path.get(i + 1);
            List<Flight> flights = graph.get(currentVertex);
            for (Flight flight : flights) {
                if (flight.getDestination().equals(nextVertex)) {
                    totalWeight += flight.getWeight();
                    break;
                }
            }
        }
        return totalWeight;
    }
}
