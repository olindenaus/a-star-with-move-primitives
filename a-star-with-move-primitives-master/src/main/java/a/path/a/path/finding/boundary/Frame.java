package a.path.finding.boundary;

import a.path.finding.APathFinding;
import a.path.finding.Astar;
import a.path.finding.ControlHandler;
import a.path.finding.control.CollisionChecker;
import a.path.finding.control.PathConnector;
import a.path.finding.entity.GlobalConstants;
import a.path.finding.entity.Node;
import a.path.finding.entity.Style;
import a.path.finding.orientation.OrientationDown;
import a.path.finding.orientation.OrientationLeft;
import a.path.finding.orientation.OrientationRight;
import a.path.finding.orientation.OrientationUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static a.path.finding.entity.GlobalConstants.SIZE;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    /*
     * try refactoring orientation related code in generic manner
     * draw information on nodes, like, F and H cost, orientation in the node
     * */

    private OrientationDown orientationDown = new OrientationDown();
    private OrientationLeft orientationLeft = new OrientationLeft();
    private OrientationUp orientationUp = new OrientationUp();
    private OrientationRight orientationRight = new OrientationRight();

    ControlHandler controlHandler;
    JFrame window;
    APathFinding aPathFinding;
    char currentKey = (char) 0;
    Node startNode, endNode;
    String stage;
    PathConnector pathConnector = new PathConnector();
    Astar astar = Astar.getInstance();

    Timer timer = new Timer(50, this);

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        startNode = new Node(5 * GlobalConstants.SIZE, GlobalConstants.SIZE);
        endNode = new Node(5 * GlobalConstants.SIZE, 5 * GlobalConstants.SIZE);
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
        window.getContentPane().setPreferredSize(new Dimension(800, 600));
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
        controlHandler.getLabelByName("modeText").setText(stage);
        controlHandler.positionElements();
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.lightGray);
        for (int j = 0; j < this.getHeight(); j += GlobalConstants.SIZE) {
            for (int i = 0; i < this.getWidth(); i += GlobalConstants.SIZE) {
                g.drawRect(i, j, GlobalConstants.SIZE, GlobalConstants.SIZE);
            }
        }
    }

    private void drawObstacles(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < astar.getObstacles().size(); i++) {
            g.fillRect(astar.getObstacles().get(i).getX() + 1, astar.getObstacles().get(i).getY() + 1,
                    GlobalConstants.SIZE - 1, GlobalConstants.SIZE - 1);
        }
    }

    private void drawOpenNodes(Graphics g) {
        for (int i = 0; i < astar.getOpenNodes().size(); i++) {
            Node current = astar.getOpenNodes().get(i);
            g.setColor(Style.greenHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, GlobalConstants.SIZE - 1, GlobalConstants.SIZE - 1);

            drawInfo(current, g);
            drawOrientation(current, g);

        }
    }

    private void drawPath(Graphics g) {
        ArrayList<Node> pathNodes = pathConnector.getPath();
        for (int i = 0; i < pathNodes.size(); i++) {
            Node current = pathNodes.get(i);
            if (i != pathNodes.size() - 1) {
                Node next = pathNodes.get(i+1);
                drawArc(g, current, next);
            }
            g.setColor(Style.blueHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, GlobalConstants.SIZE - 1, GlobalConstants.SIZE - 1);
        }
    }

    private void drawArc(Graphics g, Node current, Node next) {
        int startX = current.getX();
        int startY = current.getY();
        int endX = next.getX();
        int endY = next.getY();
        g.setColor(Style.darkText);
        g.drawLine(startX, startY, endX, endY);
    }

    private void drawClosedNodes(Graphics g) {
        for (int i = 0; i < astar.getClosedList().size(); i++) {
            Node current = astar.getClosedList().get(i);
            g.setColor(Style.redHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, GlobalConstants.SIZE - 1, GlobalConstants.SIZE - 1);

            drawInfo(current, g);
            drawOrientation(current, g);

        }
    }

    private void drawPoint(Graphics g, Node node, boolean isStart) {
        if (node != null) {
            g.setColor(Color.blue);
            if (!isStart) {
                g.setColor(Color.red);
            }
            g.fillRect(node.getX() + 1, node.getY() + 1, GlobalConstants.SIZE - 1, GlobalConstants.SIZE - 1);

        }
    }


    // Draws info (f, g, h) on current node
    public void drawInfo(Node current, Graphics g) {
        if (SIZE > 49) {
            g.setFont(Style.smallNumbers);
            g.setColor(Color.black);
            g.drawString(Integer.toString(current.getF()), current.getX() + 4, current.getY() + 16);
            g.setFont(Style.smallNumbers);
            g.drawString(Integer.toString(current.getG()), current.getX() + 4, current.getY() + SIZE - 7);
            g.drawString(Integer.toString(current.getH()), current.getX() + SIZE - 26, current.getY() + SIZE - 7);
        }
    }


    // "gets orientation and prints it as a string, what I  is: current.getOrientation())"
    // it didn't work so I put a string to print  "PRINTED ORIENTATION"
    // Draws info (orientation) on current node
    public void drawOrientation(Node current, Graphics g) {
        if (SIZE > 49) {
            g.setFont(Style.smallNumbers);
            g.setColor(Color.black);
            g.drawString( "PRINTED ORIENTATION" , current.getX(), current.getY() );
        }
    }



    private void drawControlPanel(Graphics g) {
        g.setColor(Style.btnPanel);
        g.fillRect(5, getHeight() - 70, 182, 70);
    }

    public void actionPerformed(ActionEvent e) {
        if (aPathFinding.isRunning()) {
            aPathFinding.findPath(aPathFinding.getParent());
        } else if (aPathFinding.isComplete()) {
            stage = "Finished";
            controlHandler.getButtonByName("run").setText("clear");
            timer.stop();
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
        int mouseBoxX = e.getX() - (e.getX() % GlobalConstants.SIZE);
        int mouseBoxY = e.getY() - (e.getY() % GlobalConstants.SIZE);

        if (currentKey == 's') {
            clearStartNode(mouseBoxX, mouseBoxY);
        } else if (currentKey == 'e') {
            clearEndNode(mouseBoxX, mouseBoxY);
        } else {
            removeWall(mouseBoxX, mouseBoxY);
        }
    }

    private Node createNode(MouseEvent e, Node node) {
        int xRollover = e.getX() % GlobalConstants.SIZE;
        int yRollover = e.getY() % GlobalConstants.SIZE;
        if (node == null) {
            return new Node(e.getX() - xRollover, e.getY() - yRollover);
        }
        node.setXY(e.getX() - xRollover, e.getY() - yRollover);
        return node;
    }

    private Node createWall(MouseEvent e) {
        int xBorder = e.getX() - (e.getX() % GlobalConstants.SIZE);
        int yBorder = e.getY() - (e.getY() % GlobalConstants.SIZE);

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
        int location = CollisionChecker.searchBorder(mouseBoxX, mouseBoxY, astar.getObstacles());
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
