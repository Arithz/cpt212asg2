package DFS;

public class Neighbour {
    private final String name;
    private final int distance;

    public Neighbour(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }
}
