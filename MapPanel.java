import DFS.Graph;
import DFS.Neighbour;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class MapPanel extends JPanel {
    private final Graph graph;
    private final HashMap<String, Point> locationPoints;
    private String source;
    private String destination;
    private String[] paths;

    public MapPanel(Graph graph, HashMap<String, Point> locationPoints) {
        this.graph = graph;
        this.locationPoints = locationPoints;
        this.source = null;
        this.destination = null;
        this.paths = null;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPaths(String paths) {
        this.paths = paths.split("\nPath: ");
        for (int i = 1; i < this.paths.length; i++) {
            this.paths[i] = this.paths[i].trim();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Map.Entry<String, Point> entry : locationPoints.entrySet()) {
            String location = entry.getKey();
            Point point = entry.getValue();

            // Draw circle point for each location
            int pointSize = 10;
            int x = point.x - pointSize/2;
            int y = point.y - pointSize/2;
            g.setColor(Color.BLACK);
            g.fillOval(x, y, pointSize, pointSize);

            // Draw location name on top of the point
            g.setColor(Color.BLACK);
            g.drawString(location, x - 10, y - 10);

            ArrayList<Neighbour> neighbours = graph.getGraph().get(location);
            for (Neighbour neighbour : neighbours) {
                Point endPoint = locationPoints.get(neighbour.getName());

                //use paths property to highlight the path in red
                if (paths != null) {
                    for (String path : paths) {
                        if (path.contains(location) && path.contains(neighbour.getName())) {
                            g.setColor(Color.BLUE);
                            // set the location point to blue   
                            g.fillOval(x, y, pointSize, pointSize);
                        }else {
                            g.setColor(Color.GRAY);
                        }
                    }
                }else {
                    g.setColor(Color.GRAY);
                }
                g.drawLine(point.x, point.y, endPoint.x, endPoint.y);

            }
        }

        // Highlight source and destination points if set
        if (source != null && destination != null) {
            Point sourcePoint = locationPoints.get(source);
            Point destinationPoint = locationPoints.get(destination);

            // Highlight source point in red
            g.setColor(Color.RED);
            g.fillOval(sourcePoint.x - 5, sourcePoint.y - 5, 10, 10);
            

            // Highlight destination point in green
            g.setColor(new Color(0, 255, 0)); // Green color (RGB: 0, 255, 0)
            g.fillOval(destinationPoint.x - 5, destinationPoint.y - 5, 10, 10);
        }
    }
}
