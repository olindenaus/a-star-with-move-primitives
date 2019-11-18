package a.path.finding;

import java.util.ArrayList;
import java.util.List;

public class PathConnector {

    ArrayList<Node> path = new ArrayList<>();

    public ArrayList<Node> getPath() {
        return path;
    }

    public void connectPath(Node startNode, Node endNode, List<Node> closedList) {
        Node parentNode = endNode.getParent();

        while (!Node.isEqual(parentNode, startNode)) {
            addPath(parentNode);
            for (int i = 0; i < closedList.size(); i++) {
                Node current = closedList.get(i);
                if (Node.isEqual(current, parentNode)) {
                    parentNode = current.getParent();
                    break;
                }
            }
        }
    }

    public void addPath(Node node) {
        path.add(node);
    }
}
