package a.path.finding.boundary;

import a.path.finding.APathFinding;
import a.path.finding.Astar;
import a.path.finding.control.CollisionChecker;
import a.path.finding.ControlHandler;
import a.path.finding.control.PathConnector;
import a.path.finding.entity.Node;
import a.path.finding.entity.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    /*
    * path drawing fails when there was a unpassable, exclude node with all failed possible movements
     * make Astar class singleton
     * draw movement lines on the grid
     * try refactoring orientation related code in generic manner
     * make cotrolHandler smaller
     * draw information on nodes, like, F and H cost, orientation in the node
     * */

    ControlHandler controlHandler;
    JFrame window;
    APathFinding aPathFinding;
    char currentKey = (char) 0;
    Node startNode, endNode;
    String stage;
    PathConnector pathConnector = new PathConnector();

    Timer timer = new Timer(100, this);

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        startNode = new Node(5 * SIZE, SIZE);
        endNode = new Node(5 * SIZE, 5 * SIZE);
        controlHandler = new ControlHandler(this);
        stage = "Map Creation";
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        aPathFinding = new APathFinding(this, startNode, endNode, pathConnector);
        setupWindow();

        controlHandler.addAllComponents();

        this.revalidate();
        this.repaint();
    }

    private void setupWindow() {
        window = new JFrame();
        window.setContentPane(this);
        window.setTitle("A* with move primitives");
        window.getContentPane().setPreferredSize(new Dimension(700, 600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawObstacles(g);
        drawOpenNodes(g);
        drawClosedNodes(g);
        drawPath(g);
        drawPoint(g, startNode, true);
        drawPoint(g, endNode, false);
        drawControlPanel(g);
        controlHandler.getLabelByName("modeText").setText("Mode: " + stage);
        controlHandler.positionElements();
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.lightGray);
        for (int j = 0; j < this.getHeight(); j += SIZE) {
            for (int i = 0; i < this.getWidth(); i += SIZE) {
                g.drawRect(i, j, SIZE, SIZE);
            }
        }
    }

    private void drawObstacles(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < Astar.getObstacles().size(); i++) {
            g.fillRect(Astar.getObstacles().get(i).getX() + 1, Astar.getObstacles().get(i).getY() + 1,
                    SIZE - 1, SIZE - 1);
        }
    }

    private void drawOpenNodes(Graphics g) {
        for (int i = 0; i < Astar.getOpenNodes().size(); i++) {
            Node current = Astar.getOpenNodes().get(i);
            g.setColor(Style.greenHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
        }
    }

    private void drawPath(Graphics g) {
        ArrayList<Node> pathNodes = pathConnector.getPath();
        for (int i = 0; i < pathNodes.size(); i++) {
            Node current = pathNodes.get(i);
//            if (i != pathNodes.size() - 1) {
//                Node next = pathNodes.get(i+1);
//                drawArc(g, current, next);
//            }
            g.setColor(Style.blueHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
        }
    }

    private void drawArc(Graphics g, Node current, Node next) {
        int startX = current.getX();
        int startY = current.getY();
        int endX = next.getX();
        int endY = next.getY();
        int height = endY - startY;
        int width = endX - startX;
        int angle = (int) Math.atan2(height, width);
        g.setColor(Style.darkText);
        g.fillArc(startX, startY, 100, 100, angle, angle);
    }

    private void drawClosedNodes(Graphics g) {
        for (int i = 0; i < Astar.getClosedList().size(); i++) {
            Node current = Astar.getClosedList().get(i);
            g.setColor(Style.redHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
        }
    }

    private void drawPoint(Graphics g, Node node, boolean isStart) {
        if (node != null) {
            g.setColor(Color.blue);
            if (!isStart) {
                g.setColor(Color.red);
            }
            g.fillRect(node.getX() + 1, node.getY() + 1, SIZE - 1, SIZE - 1);
        }
    }

    private void drawControlPanel(Graphics g) {
        g.setColor(Style.btnPanel);
        g.fillRect(10, getHeight() - 96, 322, 90);
    }

    public void actionPerformed(ActionEvent e) {
        if (aPathFinding.isRunning()) {
            aPathFinding.findPath(aPathFinding.getParent());
        } else if (aPathFinding.isComplete()) {
            stage = "Finished";
            controlHandler.getButtonByName("run").setText("clear");
        }
        handleButtonClick(e);
    }

    private void handleButtonClick(ActionEvent e) {
        if (e.getActionCommand() != null) {
            if (e.getActionCommand().equals("run") && !aPathFinding.isRunning()) {
                runSimulation();
            } else if (e.getActionCommand().equals("clear")) {
                controlHandler.getButtonByName("run").setText("run");
                stage = "Map Creation";
                aPathFinding.reset();
            }
            if(e.getActionCommand().equals("deleteObstacles")) {
                clearWalls();
            }
        }
        repaint();
    }

    private void runSimulation() {
        controlHandler.getButtonByName("run").setText("stop");
        stage = "Running";
        start();
    }

    private void start() {
        aPathFinding.start(startNode, endNode);
        timer.start();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        currentKey = e.getKeyChar();
    }

    public void keyReleased(KeyEvent e) {
        currentKey = (char) 0;
    }

    public void mouseClicked(MouseEvent e) {
        mapCalculations(e);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        mapCalculations(e);
    }

    private void mapCalculations(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            handleLeftClick(e);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            handleRightClick(e);
        }
    }

    private void handleLeftClick(MouseEvent e) {
        if (currentKey == 's') {
            startNode = createNode(e, startNode);
        } else if (currentKey == 'e') {
            endNode = createNode(e, endNode);
        } else {
            aPathFinding.addBorder(createWall(e));
        }
        repaint();
    }

    private void handleRightClick(MouseEvent e) {
        int mouseBoxX = e.getX() - (e.getX() % SIZE);
        int mouseBoxY = e.getY() - (e.getY() % SIZE);

        if (currentKey == 's') {
            clearStartNode(mouseBoxX, mouseBoxY);
        } else if (currentKey == 'e') {
            clearEndNode(mouseBoxX, mouseBoxY);
        } else {
            removeWall(mouseBoxX, mouseBoxY);
        }
    }

    private Node createNode(MouseEvent e, Node node) {
        int xRollover = e.getX() % SIZE;
        int yRollover = e.getY() % SIZE;
        if (node == null) {
            return new Node(e.getX() - xRollover, e.getY() - yRollover);
        }
        node.setXY(e.getX() - xRollover, e.getY() - yRollover);
        return node;
    }

    private Node createWall(MouseEvent e) {
        int xBorder = e.getX() - (e.getX() % SIZE);
        int yBorder = e.getY() - (e.getY() % SIZE);

        return new Node(xBorder, yBorder);
    }

    private void clearStartNode(int mouseBoxX, int mouseBoxY) {
        if (startNode != null && mouseBoxX == startNode.getX() && startNode.getY() == mouseBoxY) {
            startNode = null;
            repaint();
        }
    }

    private void clearEndNode(int mouseBoxX, int mouseBoxY) {
        if (endNode != null && mouseBoxX == endNode.getX() && endNode.getY() == mouseBoxY) {
            endNode = null;
            repaint();
        }
    }

    private void removeWall(int mouseBoxX, int mouseBoxY) {
        int location = CollisionChecker.searchBorder(mouseBoxX, mouseBoxY, Astar.getObstacles());
        if (location != -1) {
            aPathFinding.removeBorder(location);
        }
        repaint();
    }

    private void clearWalls() {
        aPathFinding.clearBorder();
    }

    public void mouseMoved(MouseEvent e) {

    }
}
