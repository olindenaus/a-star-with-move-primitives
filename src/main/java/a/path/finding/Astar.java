package a.path.finding;

import a.path.finding.entity.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Astar {

    private Comparator<Node> byFCost = Comparator.comparing(Node::getF, Integer::compareTo);

    private static Astar instance = new Astar();

    private List<Node> obstacles = new ArrayList<>(), open = new ArrayList<>(), closed = new ArrayList<>();

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
        node.setF();
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

    public void sortOpen() {
        this.open = open.stream()
                .sorted(byFCost)
                .collect(Collectors.toList());
    }
}
