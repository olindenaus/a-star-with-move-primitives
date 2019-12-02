package a.path.finding.control;

import a.path.finding.orientation.Orientation;
import a.path.finding.entity.Node;

import java.util.List;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class CollisionChecker {

    public static boolean checkTurnLeftCollisions(Node node, List<Node> obstacles, Orientation orientation) {
        switch (orientation) {
            case DOWN:
                return checkTurnLeftCollisionWhenOrientationDown(node, obstacles);
            case LEFT:
                return checkTurnLeftCollisionWhenOrientationLeft(node, obstacles);
            case UP:
                return checkTurnLeftCollisionWhenOrientationUp(node, obstacles);
            case RIGHT:
                return checkTurnLeftCollisionWhenOrientationRight(node, obstacles);
            default:
                return false;
        }
    }

    private static boolean checkTurnLeftCollisionWhenOrientationDown(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + 2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + SIZE, nodeY + 3 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 2 * SIZE, nodeY + 4 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3 * SIZE, nodeY + 4 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    private static boolean checkTurnLeftCollisionWhenOrientationRight(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 2 * SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3 * SIZE, nodeY + -1 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4 * SIZE, nodeY + -2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4 * SIZE, nodeY + -3 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    private static boolean checkTurnLeftCollisionWhenOrientationUp(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + -1 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + -2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -1 * SIZE, nodeY + -3 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2 * SIZE, nodeY + -4 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3 * SIZE, nodeY + -4 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    private static boolean checkTurnLeftCollisionWhenOrientationLeft(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + -1 * SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2 * SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3 * SIZE, nodeY + SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4 * SIZE, nodeY + 2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4 * SIZE, nodeY + 3 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    public static boolean checkTurnRightCollisions(Node node, List<Node> obstacles, Orientation orientation) {
        switch (orientation) {
            case DOWN:
                return checkTurnRightCollisionWhenOrientationDown(node, obstacles);
            case LEFT:
                return checkTurnRightCollisionWhenOrientationLeft(node, obstacles);
            case UP:
                return checkTurnRightCollisionWhenOrientationUp(node, obstacles);
            case RIGHT:
                return checkTurnRightCollisionWhenOrientationRight(node, obstacles);
            default:
                return false;
        }
    }

    private static boolean checkTurnRightCollisionWhenOrientationDown(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + 2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -1 * SIZE, nodeY + 3 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2 * SIZE, nodeY + 4 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3 * SIZE, nodeY + 4 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    private static boolean checkTurnRightCollisionWhenOrientationRight(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 2 * SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3 * SIZE, nodeY + SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4 * SIZE, nodeY + 2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4 * SIZE, nodeY + 3 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    private static boolean checkTurnRightCollisionWhenOrientationUp(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + -1 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + -2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + SIZE, nodeY + -3 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 2 * SIZE, nodeY + -4 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3 * SIZE, nodeY + -4 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    private static boolean checkTurnRightCollisionWhenOrientationLeft(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + -1 * SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2 * SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3 * SIZE, nodeY + -1 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4 * SIZE, nodeY + -2 * SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4 * SIZE, nodeY + -3 * SIZE, obstacles) != -1) {
            return false;
        }
        return true;
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
