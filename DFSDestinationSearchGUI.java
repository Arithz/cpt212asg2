import DFS.Graph;
import DFS.Neighbour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ArrayList;

public class DFSDestinationSearchGUI extends JFrame {
    private JComboBox<String> sourceComboBox;
    private JComboBox<String> destinationComboBox;
    private final Graph graph;
    private final HashMap<String, Point> locationPoints;
    private final MapPanel mapPanel; // Added

public DFSDestinationSearchGUI(Graph graph, HashMap<String, Point> locationPoints) {
    this.graph = graph;
    this.locationPoints = locationPoints;
    this.mapPanel = new MapPanel(graph, locationPoints);

    setTitle("DFS Destination Search");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout());

    JLabel sourceLabel = new JLabel("Source:");
    sourceComboBox = new JComboBox<>();
    JLabel destinationLabel = new JLabel("Destination:");
    destinationComboBox = new JComboBox<>();
    JButton searchButton = new JButton("Search");

    JTextArea outputArea = new JTextArea(5, 30);
    outputArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(outputArea);
    scrollPane.setOpaque(false); // Make scroll pane transparent
    scrollPane.getViewport().setOpaque(false); // Make viewport transparent

    inputPanel.add(sourceLabel);
    inputPanel.add(sourceComboBox);
    inputPanel.add(destinationLabel);
    inputPanel.add(destinationComboBox);
    inputPanel.add(searchButton);

    JPanel topPanel = new JPanel(new BorderLayout());

    topPanel.add(inputPanel, BorderLayout.NORTH); // Add input panel to the center of top panel
    topPanel.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the top of top panel

    add(topPanel, BorderLayout.NORTH); // Add top panel to the top
    add(mapPanel, BorderLayout.CENTER);

    searchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String source = (String) sourceComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();

            outputArea.setText("");
            graph.dfsSearch(source, destination, outputArea);
            mapPanel.setSource(source);
            mapPanel.setDestination(destination);
            mapPanel.setPaths(outputArea.getText());
            mapPanel.repaint();
        }
    });
}




    public JComboBox<String> getSourceComboBox() {
        return sourceComboBox;
    }

    public JComboBox<String> getDestinationComboBox() {
        return destinationComboBox;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        String filePath = "locations.txt";
        HashMap<String, ArrayList<Neighbour>> locations = graph.initializeGraphFromFile(filePath);
        HashMap<String, Point> locationPoints = calculateLocationCoordinates(locations);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame gui = new DFSDestinationSearchGUI(graph, locationPoints);
                gui.setSize(800, 600);
                gui.setVisible(true);

                DFSDestinationSearchGUI dfsGui = (DFSDestinationSearchGUI) gui;
                for (String location : locations.keySet()) {
                    dfsGui.getSourceComboBox().addItem(location);
                    dfsGui.getDestinationComboBox().addItem(location);
                }
            }
        });
    }

    private static HashMap<String, Point> calculateLocationCoordinates(HashMap<String, ArrayList<Neighbour>> locations) {
        HashMap<String, Point> locationPoints = new HashMap<>();
        int panelWidth = 600;
        int panelHeight = 550;
        int centerX = panelWidth / 2 ;
        int centerY = panelHeight / 2 -50;
        double angleIncrement = 2 * Math.PI / locations.size();
        double currentAngle = 0;

        for (String location : locations.keySet()) {
            int x = (int) (centerX + Math.cos(currentAngle) * panelWidth / 3);
            int y = (int) (centerY + Math.sin(currentAngle) * panelHeight / 3);
            locationPoints.put(location, new Point(x, y));
            currentAngle += angleIncrement;
        }

        return locationPoints;
    }
}
