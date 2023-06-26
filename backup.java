// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.*;
// import java.util.List;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class DFSDestinationSearchGUI extends JFrame {
//     private static HashMap<String, Boolean> visited;
//     private static HashMap<String, ArrayList<Neighbour>> graph;
//     private JComboBox<String> sourceComboBox;
//     private JComboBox<String> destinationComboBox;

//     private static class Neighbour {
//         private final String name;
//         private final int distance;

//         public Neighbour(String name, int distance) {
//             this.name = name;
//             this.distance = distance;
//         }

//         public String getName() {
//             return name;
//         }

//         public int getDistance() {
//             return distance;
//         }
//     }

//     private static HashMap<String, ArrayList<Neighbour>> initializeGraphFromFile(String filePath) {
//         HashMap<String, ArrayList<Neighbour>> graph = new HashMap<>();

//         try {
//             List<String> lines = Files.readAllLines(Path.of(filePath));
//             for (String line : lines) {
//                 String[] parts = line.split(":");
//                 String vertex = parts[0];
//                 String[] neighbours = parts[1].split(",");

//                 ArrayList<Neighbour> neighbourList = new ArrayList<>();
//                 for (String neighbour : neighbours) {
//                     String[] neighbourParts = neighbour.split("\\(");
//                     String neighbourName = neighbourParts[0];
//                     int distance = Integer.parseInt(neighbourParts[1].replace(")", ""));
//                     neighbourList.add(new Neighbour(neighbourName, distance));
//                 }

//                 graph.put(vertex, neighbourList);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         return graph;
//     }

//     private static void dfsWithDestination(String vertex, String destination, int totalDistance, String path, JTextArea outputArea) {
//         visited.put(vertex, true);

//         if (vertex != null && vertex.equals(destination)) {
//             outputArea.append("Destination reached! Total Distance: " + totalDistance + "\n");
//             outputArea.append("Path: " + path + "\n");
//             return;
//         }

//         ArrayList<Neighbour> neighbours = graph.getOrDefault(vertex, new ArrayList<>());
//         for (Neighbour neighbour : neighbours) {
//             if (!visited.containsKey(neighbour.getName())) {
//                 dfsWithDestination(neighbour.getName(), destination, totalDistance + neighbour.getDistance(),
//                         path + " -> " + neighbour.getName() + " (" + neighbour.getDistance() + ")", outputArea);
//             }
//         }
//     }

//     private static void dfsSearch(String source, String destination, JTextArea outputArea) {
//         visited = new HashMap<>();
//         dfsWithDestination(source, destination, 0, source, outputArea);

//         if (!visited.containsKey(destination)) {
//             outputArea.append("Destination not reachable.\n");
//         }
//     }

//     public DFSDestinationSearchGUI() {
//         setTitle("DFS Destination Search");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());

//         JPanel inputPanel = new JPanel();
//         inputPanel.setLayout(new FlowLayout());

//         JLabel sourceLabel = new JLabel("Source:");
//         sourceComboBox = new JComboBox<>();
//         JLabel destinationLabel = new JLabel("Destination:");
//         destinationComboBox = new JComboBox<>();
//         JButton searchButton = new JButton("Search");

//         JTextArea outputArea = new JTextArea(10, 30);
//         outputArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(outputArea);

//         inputPanel.add(sourceLabel);
//         inputPanel.add(sourceComboBox);
//         inputPanel.add(destinationLabel);
//         inputPanel.add(destinationComboBox);
//         inputPanel.add(searchButton);

//         add(inputPanel, BorderLayout.NORTH);
//         add(scrollPane, BorderLayout.CENTER);

//         searchButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 String source = (String) sourceComboBox.getSelectedItem();
//                 String destination = (String) destinationComboBox.getSelectedItem();

//                 outputArea.setText("");
//                 dfsSearch(source, destination, outputArea);
//             }
//         });
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//                 DFSDestinationSearchGUI gui = new DFSDestinationSearchGUI();
//                 gui.setSize(800, 600);
//                 gui.setVisible(true);

//                 String filePath = "locations.txt";
//                 graph = initializeGraphFromFile(filePath);
//                 Set<String> locations = graph.keySet();

//                 // Populate source and destination combo boxes
//                 for (String location : locations) {
//                     gui.sourceComboBox.addItem(location);
//                     gui.destinationComboBox.addItem(location);
//                 }
//             }
//         });
//     }
// }

