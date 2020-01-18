package a.path.finding.control;

import a.path.finding.boundary.Frame;
import a.path.finding.entity.Node;
import a.path.finding.entity.Style;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class Drawer {

    Frame frame;
    int width, height;

    public Drawer(Frame frame) {
        this.frame = frame;
        this.height = frame.getHeight();
        this.width = frame.getWidth();
    }

    public void drawObstacles(Graphics g, List<Node> obstacles) {
        Node obstacle;
        for (int i = 0; i < obstacles.size(); i++) {
            obstacle = obstacles.get(i);
            g.setColor(Color.black);
            g.fillRect(obstacle.getX() + 1, obstacle.getY() + 1,
                    SIZE - 1, SIZE - 1);
        }
    }

    public void drawOpenNodes(Graphics g, List<Node> openNodes) {
        for (int i = 0; i < openNodes.size(); i++) {
            Node current = openNodes.get(i);
            g.setColor(Style.greenHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
            drawInfo(current, g);
        }
    }

    public void drawGrid(Graphics g) {
        for (int j = 0; j < height; j += SIZE) {
            for (int i = 0; i < width; i += SIZE) {
                g.setColor(Color.lightGray);
                g.drawRect(i, j, SIZE, SIZE);
            }
        }
    }

    public void drawPath(Graphics g, PathConnector pathConnector) {
        ArrayList<Node> pathNodes = pathConnector.getPath();
        for (int i = 0; i < pathNodes.size(); i++) {
            Node current = pathNodes.get(i);
            if (i != pathNodes.size() - 1) {
                Node next = pathNodes.get(i + 1);
                drawArc(g, current, next);
            }
            g.setColor(Style.blueHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
            drawInfo(current, g);
        }
    }

    private void drawArc(Graphics g, Node current, Node next) {
        int offset = SIZE / 2;
        int startX = current.getX() + offset;
        int startY = current.getY() + offset;
        int endX = next.getX() + offset;
        int endY = next.getY() + offset;
        g.setColor(Style.darkText);
        g.drawLine(startX, startY, endX, endY);
    }

    public void drawClosedNodes(Graphics g, List<Node> closedNodes) {
        for (int i = 0; i < closedNodes.size(); i++) {
            Node current = closedNodes.get(i);
            g.setColor(Style.redHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
            drawInfo(current, g);
        }
    }

    public void drawInfo(Node current, Graphics g) {
        if (SIZE > 49) {
            g.setFont(Style.smallText);
            g.setColor(Color.black);
            int top = current.getY() + 16;
            int bot = current.getY() + SIZE - 7;
            int left = current.getX() + 2;
            int right = current.getX() + SIZE - 20;
            g.drawString(Integer.toString(current.getF()), left, top);
            g.drawString(Integer.toString(current.getResolution()), right, top);
            g.drawString(Integer.toString(current.getG()), left, bot);
            g.drawString(Integer.toString(current.getH()), right, bot);
        }
    }

    public void drawEndPoint(Graphics g, Node node) {
        g.setColor(Color.red);
        drawPoint(g, node);
    }

    public void drawStartPoint(Graphics g, Node node) {
        g.setColor(Color.blue);
        drawPoint(g, node);
    }

    private void drawPoint(Graphics g, Node node) {
        if (node != null) {
            g.fillRect(node.getX() + 1, node.getY() + 1, SIZE - 1, SIZE - 1);
        }
    }

    public void drawControlPanel(Graphics g) {
        g.setColor(Style.btnPanel);
        int panelHeight = 120;
        g.fillRect(5, height - panelHeight, 320, panelHeight);
    }
}
