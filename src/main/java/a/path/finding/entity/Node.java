package a.path.finding.entity;

import a.path.finding.orientation.Orientation;

import java.util.Objects;

import static a.path.finding.entity.GlobalConstants.RESOLUTION_PENALTY;
import static a.path.finding.entity.GlobalConstants.SIZE;

public class Node {
    private int x, y, g, h, f;
    private Node parent;
    private Orientation orientation;
    private int resolution;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.orientation = Orientation.DOWN;
        this.resolution = 1;
    }

    public Node(int x, int y, int resolution) {
        this.x = x;
        this.y = y;
        this.orientation = Orientation.DOWN;
        this.resolution = resolution;
    }

    public Node(int x, int y, Orientation orientation, int resolution) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.resolution = Math.min(resolution, 3);
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
        return f;
    }

    public int getResolution() {
        return resolution;
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

    public void setF() {
        this.f = h + g + resolution*RESOLUTION_PENALTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y &&
                resolution == node.resolution &&
                orientation == node.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, orientation, resolution);
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public static boolean isEqual(Node s, Node e) {
        return s.getX() == e.getX() && s.getY() == e.getY();
    }

    public static boolean isInVicinity(Node current, Node target) {
        return (Math.abs(current.getX() - target.getX()) <= SIZE) &&
                (Math.abs(current.getY() - target.getY()) <= SIZE);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", g=" + g +
                ", h=" + h +
                ", f=" + f +
                ", or=" + orientation +
                ", res=" + resolution +
                '}';
    }
}
