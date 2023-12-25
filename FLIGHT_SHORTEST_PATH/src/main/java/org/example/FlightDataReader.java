package org.example;

import org.example.Flight;

import java.io.*;
import java.util.*;

public class FlightDataReader {
    public static void main(String[] args) {
        String csvFile = "src/main/java/org/example/flights_data_NEW.csv";
        String line = "";
        String csvSplitBy = ",";

        Map<String, List<Flight>> airportMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] flightData = line.split(csvSplitBy);

                if (flightData[0].equals("SOURCE") && flightData[1].equals("DESTINATION")) {
                    continue; // Skip the header row.
                }

                String source = flightData[0];
                String destination = flightData[1];
                double weight = Double.parseDouble(flightData[2]);

                // Create a new Flight
                Flight flight = new Flight(destination, weight);

                // Add the Flight to the list of flights for the source airport
                airportMap.computeIfAbsent(source, k -> new ArrayList<>()).add(flight);

                // Ensure the graph is undirected by adding a return flight
                Flight returnFlight = new Flight(source, weight);
                airportMap.computeIfAbsent(destination, k -> new ArrayList<>()).add(returnFlight);
            }

            // Take user input for source and destination
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the source airport: ");
            String sourceAirport = scanner.nextLine();
            System.out.print("Enter the destination airport: ");
            String destinationAirport = scanner.nextLine();

            // Find the shortest path
            List<String> shortestPath = DijkstraShortestPath.findShortestPath(airportMap, sourceAirport, destinationAirport);

            // Print the shortest path and total weight
            if (shortestPath.isEmpty()) {
                System.out.println("No path found.");
            } else {
                System.out.println("Shortest path: " + String.join(" --> ", shortestPath));
                double totalWeight = DijkstraShortestPath.calculateTotalWeight(airportMap, shortestPath);
                System.out.println("Total weight: " + totalWeight);

                writeOutputToFile("src/main/java/org/example/output.txt", shortestPath, totalWeight);
                callPythonScript();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeOutputToFile(String fileName, List<String> shortestPath, double totalWeight) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(String.join(" ", shortestPath));
//            writer.println("Total weight: " + totalWeight);
//            writer.println(totalWeight);
            System.out.println("Output written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void callPythonScript() {
        try {
            // Replace this path with the actual path to your Python script
            String pythonScriptPath = "src/main/java/org/example/OutputGraph.py";

            // Use ProcessBuilder to run the Python script
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
            Process process = processBuilder.start();

            // Wait for the script to finish
            int exitCode = process.waitFor();

            // Check the exit code to see if the script ran successfully
            if (exitCode == 0) {
                System.out.println("Python script executed successfully");
            } else {
                System.err.println("Error executing Python script. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

