package a.path.finding;

import java.util.List;

import static a.path.finding.GlobalConstants.SIZE;

public class CollisionChecker {





//    This seems correct when orientation is UP or DOWN ?

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

//    public static boolean checkTurnLeftCollisions(Node node, List<Node> obstacles) {
//        int nodeX = node.getX();
//        int nodeY = node.getY();
//        for (int i = 1; i <= 3; i++) {
//            int stepSize = i * SIZE;
//            if (searchBorder(nodeX + stepSize, nodeY + stepSize, obstacles) != -1) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public static boolean checkTurnRightCollisions(Node node, List<Node> obstacles) {
//        int nodeX = node.getX();
//        int nodeY = node.getY();
//        for (int i = 0; i <= 3; i++) {
//            int stepSize = i * SIZE;
//            if (searchBorder(nodeX - stepSize, nodeY + stepSize, obstacles) != -1) {
//                return false;
//            }
//        }
//        return true;
//    }

//    check turn left collisions

    public static boolean checkTurnLeftCollisionWhenOrientationDown(Node node, List<Node> obstacles) {
            int nodeX = node.getX();
            int nodeY = node.getY();
            if (searchBorder(nodeX, nodeY + SIZE, obstacles) != -1) {
                return false;
            }
            if (searchBorder(nodeX, nodeY + 2*SIZE, obstacles) != -1) {
                return false;
            }
            if (searchBorder(nodeX + SIZE, nodeY + 3*SIZE, obstacles) != -1) {
                return false;
            }
            if (searchBorder(nodeX + 2*SIZE, nodeY + 4*SIZE, obstacles) != -1) {
                return false;
            }
            if (searchBorder(nodeX + 3*SIZE, nodeY + 4*SIZE, obstacles) != -1) {
                return false;
            }
            return true;
    }

    public static boolean checkTurnLeftCollisionWhenOrientationRight(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX  + 2*SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3*SIZE, nodeY + -1*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4*SIZE, nodeY + -2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4*SIZE, nodeY + -3*SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    public static boolean checkTurnLeftCollisionWhenOrientationUp(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + -1*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + -2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -1*SIZE, nodeY + -3*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2*SIZE, nodeY + -4*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3*SIZE, nodeY + -4*SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    public static boolean checkTurnLeftCollisionWhenOrientationLeft(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + -1*SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2*SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3*SIZE, nodeY + 1*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4*SIZE, nodeY + 2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4*SIZE, nodeY + 3*SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    //    check turn right collisions

    public static boolean checkTurnRightCollisionWhenOrientationDown(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + 2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -1*SIZE, nodeY + 3*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2*SIZE, nodeY + 4*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3*SIZE, nodeY + 4*SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    public static boolean checkTurnRightCollisionWhenOrientationRight(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX  + 2*SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3*SIZE, nodeY + 1*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4*SIZE, nodeY + 2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 4*SIZE, nodeY + 3*SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    public static boolean checkTurnRightCollisionWhenOrientationUp(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX, nodeY + -1*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX, nodeY + -2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 11*SIZE, nodeY + -3*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 2*SIZE, nodeY + -4*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + 3*SIZE, nodeY + -4*SIZE, obstacles) != -1) {
            return false;
        }
        return true;
    }

    public static boolean checkTurnRightCollisionWhenOrientationLeft(Node node, List<Node> obstacles) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        if (searchBorder(nodeX + -1*SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -2*SIZE, nodeY, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -3*SIZE, nodeY + -1*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4*SIZE, nodeY + -2*SIZE, obstacles) != -1) {
            return false;
        }
        if (searchBorder(nodeX + -4*SIZE, nodeY + -3*SIZE, obstacles) != -1) {
            return false;
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
