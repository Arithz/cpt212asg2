package DFS;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Graph {
    private HashMap<String, Boolean> visited;
    private HashMap<String, ArrayList<Neighbour>> graph;
    private ArrayList<String> paths;

    public Graph() {
        visited = new HashMap<>();
        graph = new HashMap<>();
        paths = new ArrayList<>();
    }

    public HashMap<String, ArrayList<Neighbour>> getGraph() {
        return graph;
    }

    public HashMap<String, ArrayList<Neighbour>> initializeGraphFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            for (String line : lines) {
                String[] parts = line.split(":");
                String vertex = parts[0];
                String[] neighbours = parts[1].split(",");

                ArrayList<Neighbour> neighbourList = new ArrayList<>();
                for (String neighbour : neighbours) {
                    String[] neighbourParts = neighbour.split("\\(");
                    String neighbourName = neighbourParts[0];
                    int distance = Integer.parseInt(neighbourParts[1].replace(")", ""));
                    neighbourList.add(new Neighbour(neighbourName, distance));
                }

                graph.put(vertex, neighbourList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }

    public void dfsWithDestination(String vertex, String destination, int totalDistance, String path, JTextArea outputArea) {
        visited.put(vertex, true);

        if (vertex.equals(destination)) {
            outputArea.append("Destination reached! Total Distance: " + totalDistance + "\n");
            outputArea.append("Path: " + path + "\n");
            paths.add(path);
            return;
        }

        ArrayList<Neighbour> neighbours = graph.getOrDefault(vertex, new ArrayList<>());
        for (Neighbour neighbour : neighbours) {
            if (!visited.containsKey(neighbour.getName())) {
                dfsWithDestination(neighbour.getName(), destination, totalDistance + neighbour.getDistance(),
                        path + " -> " + neighbour.getName() + " (" + neighbour.getDistance() + ")", outputArea);
            }
        }
    }

    public ArrayList<String> dfsSearch(String source, String destination, JTextArea outputArea) {
        visited = new HashMap<>();
        paths = new ArrayList<>();
        dfsWithDestination(source, destination, 0, source, outputArea);

        if (!visited.containsKey(destination)) {
            outputArea.append("Destination not reachable.\n");
        }
        return paths;
    }
}
