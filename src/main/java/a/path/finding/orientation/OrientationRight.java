package a.path.finding.orientation;

import a.path.finding.Astar;
import a.path.finding.control.CollisionChecker;
import a.path.finding.control.NodeValueCalculator;
import a.path.finding.entity.Node;

import static a.path.finding.entity.GlobalConstants.ORIGINAL_STEP_SIZE;
import static a.path.finding.entity.GlobalConstants.SIZE;

public class OrientationRight {

    private NodeValueCalculator nodeValueCalculator = new NodeValueCalculator();
    private Astar astar = Astar.getInstance();

    public boolean checkForwardMoveWhenOrientationRight(Node node, Node endNode, int resolution) {
        int move = SIZE * ORIGINAL_STEP_SIZE / resolution;
        int possibleX = node.getX() + move;
        int possibleY = node.getY();
        if (CollisionChecker.checkForwardCollisionsWhenOrientationRight(node, possibleX, possibleY, astar.getObstacles())) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.RIGHT);
            return true;
        }
        return false;
    }

    public boolean checkRightTurnWhenOrientationRight(Node node, Node endNode, int resolution) {
        int possibleX = node.getX() + ORIGINAL_STEP_SIZE / resolution * SIZE;
        int possibleY = node.getY() + ORIGINAL_STEP_SIZE / resolution * SIZE;
        if (CollisionChecker.checkTurnRightCollisions(node, astar.getObstacles(), Orientation.RIGHT, resolution)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.DOWN);
            return true;
        }
        return false;
    }

    public boolean checkLeftTurnWhenOrientationRight(Node node, Node endNode, int resolution) {
        int possibleX = node.getX() + ORIGINAL_STEP_SIZE / resolution * SIZE;
        int possibleY = node.getY() - ORIGINAL_STEP_SIZE / resolution * SIZE;
        if (CollisionChecker.checkTurnLeftCollisions(node, astar.getObstacles(), Orientation.RIGHT, resolution)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.UP);
            return true;
        }
        return false;
    }
}
