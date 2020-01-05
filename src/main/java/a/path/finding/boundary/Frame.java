package a.path.finding.boundary;

import a.path.finding.APathFinding;
import a.path.finding.Astar;
import a.path.finding.control.CollisionChecker;
import a.path.finding.ControlHandler;
import a.path.finding.control.PathConnector;
import a.path.finding.control.SetupLoader;
import a.path.finding.control.SetupSaver;
import a.path.finding.entity.GlobalConstants;
import a.path.finding.entity.Node;
import a.path.finding.entity.Setup;
import a.path.finding.entity.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static a.path.finding.entity.GlobalConstants.SIZE;
import static a.path.finding.entity.GlobalConstants.TIME_INTERVAL;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    /**
     * more complex scenarios
     * case studies
     * */

    ControlHandler controlHandler;
    JFrame window;
    APathFinding aPathFinding;
    char currentKey = (char) 0;
    Node startNode, endNode;
    String stage;
    PathConnector pathConnector = new PathConnector();
    Astar astar = Astar.getInstance();

    Timer timer = new Timer(TIME_INTERVAL, this);

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
        window.getContentPane().setPreferredSize(new Dimension(1600, 960));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawPoint(g, startNode, true);
        drawObstacles(g);
        drawOpenNodes(g);
        drawClosedNodes(g);
        drawPath(g);
        drawPoint(g, endNode, false);
        drawControlPanel(g);
        controlHandler.getLabelByName("modeText").setText(stage);
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
        for (int i = 0; i < astar.getObstacles().size(); i++) {
            g.fillRect(astar.getObstacles().get(i).getX() + 1, astar.getObstacles().get(i).getY() + 1,
                    SIZE - 1, SIZE - 1);
        }
    }

    private void drawOpenNodes(Graphics g) {
        for (int i = 0; i < astar.getOpenNodes().size(); i++) {
            Node current = astar.getOpenNodes().get(i);
            g.setColor(Style.greenHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
            drawInfo(current, g);
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
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
            drawInfo(current, g);
        }
    }

    private void drawArc(Graphics g, Node current, Node next) {
        int offset = SIZE/2;
        int startX = current.getX() + offset;
        int startY = current.getY() + offset;
        int endX = next.getX() + offset;
        int endY = next.getY() + offset;
        g.setColor(Style.darkText);
        g.drawLine(startX, startY, endX, endY);
    }

    private void drawClosedNodes(Graphics g) {
        for (int i = 0; i < astar.getClosedList().size(); i++) {
            Node current = astar.getClosedList().get(i);
            g.setColor(Style.redHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, SIZE - 1, SIZE - 1);
            drawInfo(current, g);
        }
    }

    public void drawInfo(Node current, Graphics g) {
        if (SIZE > 49) {
            g.setFont(Style.smallNumbers);
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
        g.fillRect(5, getHeight() - 100, 240, 100);
    }

    public void actionPerformed(ActionEvent e) {
        if (aPathFinding.isRunning()) {
            aPathFinding.findPath(aPathFinding.getParent());
        } else if (aPathFinding.isComplete()) {
            stage = "Finished";
            timer.stop();
        }
        handleButtonClick(e);
    }

    private void handleButtonClick(ActionEvent e) {
        if (e.getActionCommand() != null) {
            if (e.getActionCommand().equals("run") && !aPathFinding.isRunning()) {
                runSimulation();
            } else if(e.getActionCommand().equals("run")) {
                continuePathFinding();
            }
            else if(e.getActionCommand().equals("stop")) {
                stopPathFinding();
            }
            else if (e.getActionCommand().equals("clear")) {
                resetBoard();
            } else if(e.getActionCommand().equals("saveSetup")) {
                Setup setup = new Setup(astar, startNode, endNode);
                SetupSaver.saveSetup(setup);
            } else if(e.getActionCommand().equals("loadSetup")) {
                Setup setup = SetupLoader.loadSetup();
                GlobalConstants.updateSetup(setup);
                astar.update(setup);
                startNode = setup.getStartNode();
                endNode = setup.getEndNode();
            }
            if(e.getActionCommand().equals("deleteObstacles")) {
                clearWalls();
            }
        }
        repaint();
    }

    private void continuePathFinding() {
        controlHandler.getButtonByName("run").setText("stop");
        controlHandler.getButtonByName("run").setActionCommand("stop");
        timer.restart();
    }

    private void stopPathFinding() {
        controlHandler.getButtonByName("run").setText("run");
        controlHandler.getButtonByName("run").setActionCommand("run");
        timer.stop();
    }

    private void resetBoard() {
        aPathFinding.reset();
        stage = "Map Creation";
    }

    private void runSimulation() {
        controlHandler.getButtonByName("run").setText("stop");
        controlHandler.getButtonByName("run").setActionCommand("stop");
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
