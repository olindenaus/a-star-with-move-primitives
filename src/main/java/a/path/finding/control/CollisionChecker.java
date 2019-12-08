package a.path.finding.control;

import a.path.finding.entity.Node;
import a.path.finding.orientation.Orientation;

import java.util.List;

import static a.path.finding.entity.GlobalConstants.ORIGINAL_STEP_SIZE;
import static a.path.finding.entity.GlobalConstants.SIZE;

public class CollisionChecker {

    public static boolean checkTurnLeftCollisions(Node node, List<Node> obstacles, Orientation orientation, int resolution) {
        switch (orientation) {
            case DOWN:
                return checkBottomRightCollision(node, obstacles, resolution);
            case LEFT:
                return checkBottomLeftCollision(node, obstacles, resolution);
            case UP:
                return checkTopLeftCollision(node, obstacles, resolution);
            case RIGHT:
                return checkTopRightCollision(node, obstacles, resolution);
            default:
                return false;
        }
    }

    private static boolean checkTopRightCollision(Node node, List<Node> obstacles, int resolution) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = 0; i < ORIGINAL_STEP_SIZE / resolution; i++) {
            if (searchBorder(nodeX + i * SIZE, nodeY - i * SIZE, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkBottomLeftCollision(Node node, List<Node> obstacles, int resolution) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = 0; i < ORIGINAL_STEP_SIZE / resolution; i++) {
            if (searchBorder(nodeX - i * SIZE, nodeY + i * SIZE, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkBottomRightCollision(Node node, List<Node> obstacles, int resolution) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = 0; i < ORIGINAL_STEP_SIZE / resolution; i++) {
            if (searchBorder(nodeX + i * SIZE, nodeY + i * SIZE, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkTopLeftCollision(Node node, List<Node> obstacles, int resolution) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = 0; i < ORIGINAL_STEP_SIZE / resolution; i++) {
            if (searchBorder(nodeX - i * SIZE, nodeY - i * SIZE, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTurnRightCollisions(Node node, List<Node> obstacles, Orientation orientation, int resolution) {
        switch (orientation) {
            case DOWN:
                return checkBottomLeftCollision(node, obstacles, resolution);
            case LEFT:
                return checkTopLeftCollision(node, obstacles, resolution);
            case UP:
                return checkTopRightCollision(node, obstacles, resolution);
            case RIGHT:
                return checkBottomRightCollision(node, obstacles, resolution);
            default:
                return false;
        }
    }

    public static boolean checkForwardCollisionsWhenOrientationDown(Node node, int targetX, int targetY, List<Node> obstacles) {
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

    public static boolean checkForwardCollisionsWhenOrientationUp(Node node, int targetX, int targetY, List<Node> obstacles) {
        int nodeY = node.getY();
        int yDiff = Math.abs(targetY - nodeY);
        int inc = yDiff / SIZE;
        for (int i = inc; i <= yDiff; i += inc) {
            if (searchBorder(targetX, nodeY - i, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForwardCollisionsWhenOrientationLeft(Node node, int targetX, int targetY, List<Node> obstacles) {
        int nodeX = node.getX();
        int xDiff = Math.abs(targetX - nodeX);
        int inc = xDiff / SIZE;
        for (int i = inc; i <= xDiff; i += inc) {
            if (searchBorder(nodeX - i, targetY, obstacles) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForwardCollisionsWhenOrientationRight(Node node, int targetX, int targetY, List<Node> obstacles) {
        int nodeX = node.getX();
        int xDiff = targetX - nodeX;
        int inc = xDiff / SIZE;
        for (int i = inc; i <= xDiff; i += inc) {
            if (searchBorder(nodeX + i, targetY, obstacles) != -1) {
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
