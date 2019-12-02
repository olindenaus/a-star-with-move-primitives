package a.path.finding;

import a.path.finding.entity.Node;

import java.util.ArrayList;
import java.util.List;

public class Astar {

    private static Astar instance = new Astar();

    private ArrayList<Node> obstacles = new ArrayList<>(), open = new ArrayList<>(), closed = new ArrayList<>();

    public List<Node> getObstacles() {
        return obstacles;
    }

    public List<Node> getOpenNodes() {
        return open;
    }

    private Astar() {}

    public static Astar getInstance() {
        return instance;
    }

    public List<Node> getClosedList() {
        return closed;
    }

    public void addOpen(Node node) {
        if (open.size() == 0) {
            open.add(node);
        } else if (!checkForDuplicates(node, open)) {
            open.add(node);
        }
    }

    public void addClosed(Node node) {
        if (closed.size() == 0) {
            closed.add(node);
        } else if (!checkForDuplicates(node, closed)) {
            closed.add(node);
        }
    }

    public boolean checkForDuplicates(Node node, List<Node> nodes) {
        for (Node value : nodes) {
            if (node.getX() == value.getX() && node.getY() == value.getY()) {
                return true;
            }
        }
        return false;
    }
}
