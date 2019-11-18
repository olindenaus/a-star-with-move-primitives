package a.path.finding;

import java.util.ArrayList;
import java.util.List;

import static a.path.finding.GlobalConstants.SIZE;

public class APathFinding {
    private Frame frame;
    private Node startNode, endNode, parent;
    private boolean running, complete;
    private ArrayList<Node> obstacles = new ArrayList<>(), open = new ArrayList<>(), closed = new ArrayList<>();
    private Orientation orientation = Orientation.DOWN;
    private PathConnector pathConnector;
    private Sort sort = new Sort();

    public boolean isRunning() {
        return running;
    }

    public boolean isComplete() {
        return complete;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getObstacles() {
        return obstacles;
    }

    public List<Node> getOpenNodes() {
        return open;
    }

    public List<Node> getClosedList() {
        return closed;
    }

    public APathFinding(Frame frame, Node startNode, Node endNode, PathConnector pathConnector) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.frame = frame;
        this.pathConnector = pathConnector;
    }

    public void start(Node s, Node e) {
        addClosed(s);
        running = true;
        startNode = s;
        startNode.setG(0);
        parent = startNode;
        endNode = e;
        findPath(startNode);
    }

    public void reset() {
        open.clear();
        closed.clear();
        pathConnector.path.clear();
        running = false;
        complete = false;
    }

    public void addBorder(Node node) {
        if (obstacles.isEmpty()) {
            obstacles.add(node);
        } else if (!checkForDuplicates(node, obstacles)) {
            obstacles.add(node);
        }
    }

    public void addClosed(Node node) {
        if (closed.size() == 0) {
            closed.add(node);
        } else if (!checkForDuplicates(node, closed)) {
            closed.add(node);
        }
    }

    private boolean checkForDuplicates(Node node, List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            if (node.getX() == nodes.get(i).getX() && node.getY() == nodes.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public void removeBorder(int location) {
        obstacles.remove(location);
    }

    public void removeOpen(Node node) {
        for (int i = 0; i < open.size(); i++) {
            if (node.getX() == open.get(i).getX() && node.getY() == open.get(i).getY()) {
                open.remove(i);
            }
        }
    }

    public void addOpen(Node node) {
        if (open.size() == 0) {
            open.add(node);
        } else if (!checkForDuplicates(node, open)) {
            open.add(node);
        }
    }

    public void findPath(Node parent) {
        checkPossibilities(parent);
        parent = lowestFCostFromOpenNodes();
        if (parent == null) {
            throw new RuntimeException("No parent -> no path!");
        }
        if (Node.isInVicinity(parent, endNode)) {
            this.parent = parent;
            success();
            return;
        }
        removeOpen(parent);
        addClosed(parent);
        this.parent = parent;
    }

    private void checkPossibilities(Node parent) {
        if (orientation == Orientation.DOWN) {
            checkDownOrientationMovePossibilities();
        } else if (orientation == Orientation.RIGHT) {
            checkRightOrientationMovePossibilities();
        } else if (orientation == Orientation.UP) {
            checkUpOrientationMovePossibilities();
        } else if (orientation == Orientation.LEFT) {
            checkLeftOrientationMovePossibilities();
        }
    }

    private void checkDownOrientationMovePossibilities() {
        checkForwardMoveWhenOrientationDown(parent);
        checkLeftTurnWhenOrientationDown(parent);
        checkRightTurnWhenOrientationDown(parent);
    }

    private void checkForwardMoveWhenOrientationDown(Node node) {
        int move = SIZE * 5;
        int possibleX = node.getX();
        int possibleY = node.getY() + move;
        if (CollisionChecker.checkForwardCollisions(node, possibleX, possibleY, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkLeftTurnWhenOrientationDown(Node node) {
        int possibleX = node.getX() + 3 * SIZE;
        int possibleY = node.getY() + 4 * SIZE;
        if (CollisionChecker.checkTurnLeftCollisionWhenOrientationDown(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkRightTurnWhenOrientationDown(Node node) {
        int possibleX = node.getX() - 3 * SIZE;
        int possibleY = node.getY() + 4 * SIZE;
        if (CollisionChecker.checkTurnRightCollisionWhenOrientationDown(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkRightOrientationMovePossibilities() {
        checkForwardMoveWhenOrientationRight(parent);
        checkRightTurnWhenOrientationRight(parent);
        checkLeftTurnWhenOrientationRight(parent);
    }

    private void checkForwardMoveWhenOrientationRight(Node node) {
        int move = SIZE * 5;
        int possibleX = node.getX() - move;
        int possibleY = node.getY();
        if (CollisionChecker.checkForwardCollisions(node, possibleX, possibleY, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

// Y HAS TO BE +3 ??

    private void checkRightTurnWhenOrientationRight(Node node) {
        int possibleX = node.getX() - 4 * SIZE;
        int possibleY = node.getY() + 3 * SIZE;
        if (CollisionChecker.checkTurnRightCollisionWhenOrientationRight(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkLeftTurnWhenOrientationRight(Node node) {
        int possibleX = node.getX() + 4 * SIZE;
        int possibleY = node.getY() - 3 * SIZE;
        if (CollisionChecker.checkTurnLeftCollisionWhenOrientationRight(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkUpOrientationMovePossibilities() {
        checkForwardMoveWhenOrientationUp(parent);
        checkLeftTurnWhenOrientationUp(parent);
        checkRightTurnWhenOrientationUp(parent);
    }

    private void checkForwardMoveWhenOrientationUp(Node node) {
        int move = SIZE * 5;
        int possibleX = node.getX();
        int possibleY = node.getY() - move;
        if (CollisionChecker.checkForwardCollisions(node, possibleX, possibleY, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkLeftTurnWhenOrientationUp(Node node) {
        int possibleX = node.getX() - 3 * SIZE;
        int possibleY = node.getY() - 4 * SIZE;
        if (CollisionChecker.checkTurnLeftCollisionWhenOrientationUp(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkRightTurnWhenOrientationUp(Node node) {
        int possibleX = node.getX() + 3 * SIZE;
        int possibleY = node.getY() - 4 * SIZE;
        if (CollisionChecker.checkTurnRightCollisionWhenOrientationUp(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkLeftOrientationMovePossibilities() {
        checkForwardMoveWhenOrientationLeft(parent);
        checkLeftTurnWhenOrientationLeft(parent);
        checkRightTurnWhenOrientationLeft(parent);
    }

    private void checkForwardMoveWhenOrientationLeft(Node node) {
        int move = SIZE * 5;
        int possibleX = node.getX() + move;
        int possibleY = node.getY();
        if (CollisionChecker.checkForwardCollisions(node, possibleX, possibleY, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void checkLeftTurnWhenOrientationLeft(Node node) {
        int possibleX = node.getX() - 4 * SIZE;
        int possibleY = node.getY() + 3 * SIZE;
        if (CollisionChecker.checkTurnLeftCollisionWhenOrientationLeft(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

//     IT HAS TO BE -4,-3 ?

    private void checkRightTurnWhenOrientationLeft(Node node) {
        int possibleX = node.getX() - 4 * SIZE;
        int possibleY = node.getY() - 3 * SIZE;
        if (CollisionChecker.checkTurnRightCollisionWhenOrientationLeft(node, obstacles)) {
            calculateNodeValues(possibleX, possibleY, node);
        }
    }

    private void calculateNodeValues(int possibleX, int possibleY, Node parent) {
        Node openNode = new Node(possibleX, possibleY);
        openNode.setParent(parent);
        openNode.setG(calculateGCost(openNode));
        openNode.setH(calculateHCost(openNode));
        addOpen(openNode);
    }

    private int calculateGCost(Node openNode) {
        int gCost = parent.getG();
        gCost += SIZE * 4;
        return gCost;
    }

    private int calculateHCost(Node openNode) {
        int hXDiff = Math.abs(endNode.getX() - openNode.getX());
        int hYDiff = Math.abs(endNode.getY() - openNode.getY());
        return hXDiff + hYDiff;
    }

    public Node lowestFCostFromOpenNodes() {
        if (open.size() > 0) {
            sort.bubbleSort(open);
            return open.get(0);
        }
        return null;
    }

    private void success() {
        endNode.setParent(parent.getParent());
        pathConnector.connectPath(startNode, endNode, getClosedList());
        running = false;
        complete = true;
        frame.repaint();
    }
}
