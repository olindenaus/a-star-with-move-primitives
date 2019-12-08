package a.path.finding.orientation;

import a.path.finding.Astar;
import a.path.finding.control.CollisionChecker;
import a.path.finding.control.NodeValueCalculator;
import a.path.finding.entity.Node;

import static a.path.finding.entity.GlobalConstants.ORIGINAL_STEP_SIZE;
import static a.path.finding.entity.GlobalConstants.SIZE;

public class OrientationLeft {

    private NodeValueCalculator nodeValueCalculator = new NodeValueCalculator();
    private Astar astar = Astar.getInstance();

    public boolean checkForwardMoveWhenOrientationLeft(Node node, Node endNode, int resolution) {
        int move = SIZE * ORIGINAL_STEP_SIZE/resolution;
        int possibleX = node.getX() - move;
        int possibleY = node.getY();
        if (CollisionChecker.checkForwardCollisionsWhenOrientationLeft(node, possibleX, possibleY, astar.getObstacles())) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.LEFT);
            return true;
        }
        return false;
    }

    public boolean checkLeftTurnWhenOrientationLeft(Node node, Node endNode, int resolution) {
        int possibleX = node.getX() - ORIGINAL_STEP_SIZE/resolution * SIZE;
        int possibleY = node.getY() + ORIGINAL_STEP_SIZE/resolution * SIZE;
        if (CollisionChecker.checkTurnLeftCollisions(node, astar.getObstacles(), Orientation.LEFT, resolution)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.DOWN);
            return true;
        }
        return false;
    }

    public boolean checkRightTurnWhenOrientationLeft(Node node, Node endNode, int resolution) {
        int possibleX = node.getX() - ORIGINAL_STEP_SIZE/resolution * SIZE;
        int possibleY = node.getY() - ORIGINAL_STEP_SIZE/resolution * SIZE;
        if (CollisionChecker.checkTurnRightCollisions(node, astar.getObstacles(), Orientation.LEFT, resolution)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.UP);
            return true;
        }
        return false;
    }
}
