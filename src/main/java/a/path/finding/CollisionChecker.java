package a.path.finding;

import java.util.List;

import static a.path.finding.GlobalConstants.SIZE;

public class CollisionChecker {

    public static boolean checkForwardCollisions(Node node, int targetX, int targetY, List<Node> obstacles) {
        int nodeY = node.getY();
        int yDiff = targetY - nodeY;
        int inc = yDiff / SIZE;
        for (int i = inc; i <= yDiff; i += inc) {
            if (searchBorder(targetX, nodeY + i, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTurnLeftCollisions(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = 1; i <= 3; i++) {
            int stepSize = i * SIZE;
            if (searchBorder(nodeX + stepSize, nodeY + stepSize, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTurnRightCollisions(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = 0; i <= 3; i++) {
            int stepSize = i * SIZE;
            if (searchBorder(nodeX - stepSize, nodeY + stepSize, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    public static int searchBorder(int xSearch, int ySearch, List<Node> obstacles) {
        int location = -1;

        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getX() == xSearch && obstacles.get(i).getY() == ySearch) {
                location = i;
                break;
            }
        }
        return location;
    }
}
