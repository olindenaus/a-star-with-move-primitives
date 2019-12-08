package a.path.finding.control;

import a.path.finding.Astar;
import a.path.finding.entity.Node;
import a.path.finding.orientation.Orientation;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class NodeValueCalculator {

    private Astar astar = Astar.getInstance();

    public void calculateNodeValues(int possibleX, int possibleY, Node parent, Node endNode, Orientation orientation) {
        Node openNode = new Node(possibleX, possibleY, orientation, parent.getResolution());
        openNode.setParent(parent);
        openNode.setG(calculateGCost(openNode, parent));
        openNode.setH(calculateHCost(openNode, endNode));
//        openNode.setF();
        astar.addOpen(openNode);
    }

    private int calculateGCost(Node openNode, Node parent) {
        int gCost = parent.getG();
        gCost += SIZE * 4;
        return gCost;
    }

    private int calculateHCost(Node openNode, Node endNode) {
        int hXDiff = Math.abs(endNode.getX() - openNode.getX());
        int hYDiff = Math.abs(endNode.getY() - openNode.getY());
        return hXDiff + hYDiff;
    }
}
