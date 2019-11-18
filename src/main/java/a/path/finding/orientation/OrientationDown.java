package a.path.finding.orientation;

import a.path.finding.Astar;
import a.path.finding.control.CollisionChecker;
import a.path.finding.control.NodeValueCalculator;
import a.path.finding.entity.Node;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class OrientationDown {

    private NodeValueCalculator nodeValueCalculator = new NodeValueCalculator();

    public boolean checkForwardMoveWhenOrientationDown(Node node, Node endNode) {
        int move = SIZE * 5;
        int possibleX = node.getX();
        int possibleY = node.getY() + move;
        if (CollisionChecker.checkForwardCollisionsWhenOrientationDown(node, possibleX, possibleY, Astar.getObstacles())) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.DOWN);
            return true;
        }
        return false;
    }

    public boolean checkLeftTurnWhenOrientationDown(Node node, Node endNode) {
        int possibleX = node.getX() + 3 * SIZE;
        int possibleY = node.getY() + 4 * SIZE;
        if (CollisionChecker.checkTurnLeftCollisions(node, Astar.getObstacles(), Orientation.DOWN)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.RIGHT);
            return true;
        }
        return false;
    }

    public boolean checkRightTurnWhenOrientationDown(Node node, Node endNode) {
        int possibleX = node.getX() - 3 * SIZE;
        int possibleY = node.getY() + 4 * SIZE;
        if (CollisionChecker.checkTurnRightCollisions(node, Astar.getObstacles(), Orientation.DOWN)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.LEFT);
            return true;
        }
        return false;
    }
}
