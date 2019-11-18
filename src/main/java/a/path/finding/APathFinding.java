package a.path.finding;

import a.path.finding.boundary.Frame;
import a.path.finding.control.PathConnector;
import a.path.finding.control.Sort;
import a.path.finding.entity.Node;
import a.path.finding.orientation.*;

public class APathFinding {

    private Frame frame;
    private Node startNode, endNode, parent;
    private boolean running, complete;
    private PathConnector pathConnector;
    private OrientationDown orientationDown = new OrientationDown();
    private OrientationLeft orientationLeft = new OrientationLeft();
    private OrientationUp orientationUp = new OrientationUp();
    private OrientationRight orientationRight = new OrientationRight();
    boolean hasPossibleMovements = true;
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

    public APathFinding(Frame frame, Node startNode, Node endNode, PathConnector pathConnector) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.frame = frame;
        this.pathConnector = pathConnector;
    }

    public void start(Node s, Node e) {
        Astar.addClosed(s);
        running = true;
        startNode = s;
        startNode.setG(0);
        parent = startNode;
        endNode = e;
        findPath(startNode);
    }

    public void reset() {
        Astar.getOpenNodes().clear();
        Astar.getClosedList().clear();
        pathConnector.getPath().clear();
        running = false;
        complete = false;
    }

    public void addBorder(Node node) {
        if (Astar.getObstacles().isEmpty()) {
            Astar.getObstacles().add(node);
        } else if (!Astar.checkForDuplicates(node, Astar.getObstacles())) {
            Astar.getObstacles().add(node);
        }
    }

    public void removeBorder(int location) {
        Astar.getObstacles().remove(location);
    }

    public void clearBorder() {
        Astar.getObstacles().clear();
    }

    public void removeOpen(Node node) {
        for (int i = 0; i < Astar.getOpenNodes().size(); i++) {
            if (node.getX() == Astar.getOpenNodes().get(i).getX() && node.getY() == Astar.getOpenNodes().get(i).getY()) {
                Astar.getOpenNodes().remove(i);
            }
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
//        if (hasPossibleMovements) {
        Astar.addClosed(parent);
//        }
        this.parent = parent;
    }

    private void checkPossibilities(Node parent) {
        hasPossibleMovements = false;
        if (parent.getOrientation() == Orientation.DOWN) {
            checkOrientationDownMovePossibilities();
        } else if (parent.getOrientation() == Orientation.RIGHT) {
            checkRightOrientationMovePossibilities();
        } else if (parent.getOrientation() == Orientation.UP) {
            checkUpOrientationMovePossibilities();
        } else if (parent.getOrientation() == Orientation.LEFT) {
            checkLeftOrientationMovePossibilities();
        }
    }

    private void checkOrientationDownMovePossibilities() {
        hasPossibleMovements |= orientationDown.checkForwardMoveWhenOrientationDown(parent, endNode);
        hasPossibleMovements |= orientationDown.checkLeftTurnWhenOrientationDown(parent, endNode);
        hasPossibleMovements |= orientationDown.checkRightTurnWhenOrientationDown(parent, endNode);
    }

    private void checkRightOrientationMovePossibilities() {
        hasPossibleMovements |= orientationRight.checkForwardMoveWhenOrientationRight(parent, endNode);
        hasPossibleMovements |= orientationRight.checkRightTurnWhenOrientationRight(parent, endNode);
        hasPossibleMovements |= orientationRight.checkLeftTurnWhenOrientationRight(parent, endNode);
    }

    private void checkUpOrientationMovePossibilities() {
        hasPossibleMovements |= orientationUp.checkForwardMoveWhenOrientationUp(parent, endNode);
        hasPossibleMovements |= orientationUp.checkLeftTurnWhenOrientationUp(parent, endNode);
        hasPossibleMovements |= orientationUp.checkRightTurnWhenOrientationUp(parent, endNode);
    }

    private void checkLeftOrientationMovePossibilities() {
        hasPossibleMovements |= orientationLeft.checkForwardMoveWhenOrientationLeft(parent, endNode);
        hasPossibleMovements |= orientationLeft.checkLeftTurnWhenOrientationLeft(parent, endNode);
        hasPossibleMovements |= orientationLeft.checkRightTurnWhenOrientationLeft(parent, endNode);
    }

    public Node lowestFCostFromOpenNodes() {
        if (Astar.getOpenNodes().size() > 0) {
            sort.bubbleSort(Astar.getOpenNodes());
            return Astar.getOpenNodes().get(0);
        }
        return null;
    }

    private void success() {
        endNode.setParent(parent.getParent());
        pathConnector.connectPath(startNode, endNode, Astar.getClosedList());
        running = false;
        complete = true;
        frame.repaint();
    }
}
