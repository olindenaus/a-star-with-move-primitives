package a.path.finding;

import a.path.finding.entity.Node;

import java.util.ArrayList;
import java.util.List;

public class Astar {

    private static ArrayList<Node> obstacles = new ArrayList<>(), open = new ArrayList<>(), closed = new ArrayList<>();

    public static List<Node> getObstacles() {
        return obstacles;
    }

    public static List<Node> getOpenNodes() {
        return open;
    }

    public static List<Node> getClosedList() {
        return closed;
    }

    public static void addOpen(Node node) {
        if (open.size() == 0) {
            open.add(node);
        } else if (!checkForDuplicates(node, open)) {
            open.add(node);
        }
    }

    public static void addClosed(Node node) {
        if (closed.size() == 0) {
            closed.add(node);
        } else if (!checkForDuplicates(node, closed)) {
            closed.add(node);
        }
    }

    public static boolean checkForDuplicates(Node node, List<Node> nodes) {
        for (Node value : nodes) {
            if (node.getX() == value.getX() && node.getY() == value.getY()) {
                return true;
            }
        }
        return false;
    }
}
