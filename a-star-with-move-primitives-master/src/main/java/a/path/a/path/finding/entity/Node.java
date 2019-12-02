package a.path.finding.entity;

import a.path.finding.orientation.Orientation;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class Node {
    private int x, y, g, h, f;
    private Node parent;
    private Orientation orientation;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.orientation = Orientation.DOWN;
    }

    public Node(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return getG() + getH();
    }

    public Node getNode() {
        return parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public static boolean isEqual(Node s, Node e) {
        return s.getX() == e.getX() && s.getY() == e.getY();
    }

    public static boolean isInVicinity(Node current, Node target) {
        return (Math.abs(current.getX() - target.getX()) <= SIZE) &&
                (Math.abs(current.getY() - target.getY()) <= SIZE);
    }
}
