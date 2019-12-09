package a.path.finding;

import a.path.finding.boundary.Frame;
import a.path.finding.control.NodeValueCalculator;
import a.path.finding.control.PathConnector;
import a.path.finding.entity.Node;
import a.path.finding.orientation.Orientation;
import a.path.finding.orientation.OrientationDown;
import a.path.finding.orientation.OrientationLeft;
import a.path.finding.orientation.OrientationRight;
import a.path.finding.orientation.OrientationUp;

public class APathFinding {

    private Frame frame;
    private Node startNode, endNode, parent;
    private boolean running, complete;
    private PathConnector pathConnector;
    private NodeValueCalculator nodeValueCalculator = new NodeValueCalculator();
    private OrientationDown orientationDown = new OrientationDown();
    private OrientationLeft orientationLeft = new OrientationLeft();
    private OrientationUp orientationUp = new OrientationUp();
    private OrientationRight orientationRight = new OrientationRight();
    boolean hasPossibleMovements = true;
    private Astar astar = Astar.getInstance();

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
        astar.addClosed(s);
        running = true;
        startNode = s;
        startNode.setG(0);
        startNode.setH(nodeValueCalculator.calculateHCost(startNode, endNode));
        parent = startNode;
        endNode = e;
        findPath(startNode);
    }

    public void reset() {
        astar.reset();
        pathConnector.getPath().clear();
        running = false;
        complete = false;
    }

    public void addBorder(Node node) {
        if (astar.getObstacles().isEmpty()) {
            astar.getObstacles().add(node);
        } else if (!astar.checkForDuplicates(node, astar.getObstacles())) {
            astar.getObstacles().add(node);
        }
    }

    public void removeBorder(int location) {
        astar.getObstacles().remove(location);
    }

    public void clearBorder() {
        astar.getObstacles().clear();
    }

    public void removeOpen(Node node) {
        for (int i = 0; i < astar.getOpenNodes().size(); i++) {
            if (node.getX() == astar.getOpenNodes().get(i).getX() && node.getY() == astar.getOpenNodes().get(i).getY()) {
                astar.getOpenNodes().remove(i);
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
            astar.addClosed(parent);
            success();
            return;
        }
        removeOpen(parent);
//        if (hasPossibleMovements) {
        astar.addClosed(parent);
//        }
        this.parent = parent;
    }

    private void checkPossibilities(Node parent) {
        hasPossibleMovements = false;
        if (parent.getOrientation() == Orientation.DOWN) {
            checkOrientationDownMovePossibilities(parent);
        } else if (parent.getOrientation() == Orientation.RIGHT) {
            checkRightOrientationMovePossibilities(parent);
        } else if (parent.getOrientation() == Orientation.UP) {
            checkUpOrientationMovePossibilities(parent);
        } else if (parent.getOrientation() == Orientation.LEFT) {
            checkLeftOrientationMovePossibilities(parent);
        }
        Node higherResolutionNode = new Node(parent.getX(), parent.getY(), parent.getOrientation(), parent.getResolution() + 1);
        higherResolutionNode.setH(parent.getH());
        higherResolutionNode.setG(parent.getG());
        astar.addOpen(higherResolutionNode);
    }

    private void checkOrientationDownMovePossibilities(Node parent) {
        int resolution = parent.getResolution();
        hasPossibleMovements |= orientationDown.checkForwardMoveWhenOrientationDown(parent, endNode, resolution);
        hasPossibleMovements |= orientationDown.checkLeftTurnWhenOrientationDown(parent, endNode, resolution);
        hasPossibleMovements |= orientationDown.checkRightTurnWhenOrientationDown(parent, endNode, resolution);
    }

    private void checkRightOrientationMovePossibilities(Node parent) {
        int resolution = parent.getResolution();
        hasPossibleMovements |= orientationRight.checkForwardMoveWhenOrientationRight(parent, endNode, resolution);
        hasPossibleMovements |= orientationRight.checkRightTurnWhenOrientationRight(parent, endNode, resolution);
        hasPossibleMovements |= orientationRight.checkLeftTurnWhenOrientationRight(parent, endNode, resolution);
    }

    private void checkUpOrientationMovePossibilities(Node parent) {
        int resolution = parent.getResolution();
        hasPossibleMovements |= orientationUp.checkForwardMoveWhenOrientationUp(parent, endNode, resolution);
        hasPossibleMovements |= orientationUp.checkLeftTurnWhenOrientationUp(parent, endNode, resolution);
        hasPossibleMovements |= orientationUp.checkRightTurnWhenOrientationUp(parent, endNode, resolution);
    }

    private void checkLeftOrientationMovePossibilities(Node parent) {
        int resolution = parent.getResolution();
        hasPossibleMovements |= orientationLeft.checkForwardMoveWhenOrientationLeft(parent, endNode, resolution);
        hasPossibleMovements |= orientationLeft.checkLeftTurnWhenOrientationLeft(parent, endNode, resolution);
        hasPossibleMovements |= orientationLeft.checkRightTurnWhenOrientationLeft(parent, endNode, resolution);
    }

    public Node lowestFCostFromOpenNodes() {
        if (!astar.getOpenNodes().isEmpty()) {
            astar.sortOpen();
            return astar.getOpenNodes().get(0);
        }
        return null;
    }

    private void success() {
        endNode.setParent(parent);
        pathConnector.connectPath(startNode, endNode, astar.getClosedList());
        running = false;
        complete = true;
        frame.repaint();
    }
}
