package a.path.finding.orientation;

import a.path.finding.Astar;
import a.path.finding.control.CollisionChecker;
import a.path.finding.control.NodeValueCalculator;
import a.path.finding.entity.Node;
import com.sun.org.apache.xpath.internal.operations.Or;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class OrientationRight {

    private NodeValueCalculator nodeValueCalculator = new NodeValueCalculator();

    public boolean checkForwardMoveWhenOrientationRight(Node node, Node endNode) {
        int move = SIZE * 5;
        int possibleX = node.getX() + move;
        int possibleY = node.getY();
        if (CollisionChecker.checkForwardCollisionsWhenOrientationRight(node, possibleX, possibleY, Astar.getObstacles())) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.RIGHT);
            return true;
        }
        return false;
    }

    public boolean checkRightTurnWhenOrientationRight(Node node, Node endNode) {
        int possibleX = node.getX() + 4 * SIZE;
        int possibleY = node.getY() + 3 * SIZE;
        if (CollisionChecker.checkTurnRightCollisions(node, Astar.getObstacles(), Orientation.RIGHT)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.DOWN);
            return true;
        }
        return false;
    }

    public boolean checkLeftTurnWhenOrientationRight(Node node, Node endNode) {
        int possibleX = node.getX() + 4 * SIZE;
        int possibleY = node.getY() - 3 * SIZE;
        if (CollisionChecker.checkTurnLeftCollisions(node, Astar.getObstacles(), Orientation.RIGHT)) {
            nodeValueCalculator.calculateNodeValues(possibleX, possibleY, node, endNode, Orientation.UP);
            return true;
        }
        return false;
    }
}
