package a.path.finding.boundary;

import a.path.finding.APathFinding;
import a.path.finding.Astar;
import a.path.finding.ControlHandler;
import a.path.finding.control.*;
import a.path.finding.entity.GlobalConstants;
import a.path.finding.entity.Node;
import a.path.finding.entity.Setup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static a.path.finding.entity.GlobalConstants.START_ORIENTATION;
import static a.path.finding.entity.GlobalConstants.SIZE;
import static a.path.finding.entity.GlobalConstants.TIME_INTERVAL;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
    private ControlHandler controlHandler;
    private JFrame window;
    private APathFinding aPathFinding;
    private char currentKey = (char) 0;
    private Node startNode, endNode;
    private String stage;
    private PathConnector pathConnector = new PathConnector();
    private Astar astar = Astar.getInstance();
    private Drawer drawer;
    private Timer timer = new Timer(TIME_INTERVAL, this);
    private long startTime = 0;

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        startNode = new Node(15 * SIZE, 5* SIZE, START_ORIENTATION);
        endNode = new Node(25 * SIZE, 15 * SIZE);
        controlHandler = new ControlHandler(this);
        stage = "Map Creation";
        aPathFinding = new APathFinding(this, startNode, endNode, pathConnector, controlHandler);
        controlHandler.addAllComponents();
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setupWindow();
        drawer = new Drawer(this);
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
        drawBoard(g);
        controlHandler.getLabelByName("modeText").setText(stage);
        controlHandler.positionElements();
    }

    public void drawBoard(Graphics g) {
        drawer.drawGrid(g);
        drawer.drawStartPoint(g, startNode);
        drawer.drawObstacles(g, astar.getObstacles());
        drawer.drawOpenNodes(g, astar.getOpenNodes());
        drawer.drawClosedNodes(g, astar.getClosedNodes());
        drawer.drawPath(g, pathConnector);
        drawer.drawEndPoint(g, endNode);
        drawer.drawControlPanel(g);
    }

    public void actionPerformed(ActionEvent e) {
        if (aPathFinding.isRunning()) {
            aPathFinding.findPath(aPathFinding.getParent());
        } else if (aPathFinding.isComplete()) {
            stage = "Finished";
            timer.stop();
        }
        updateTime();
        handleButtonClick(e);
    }

    private void updateTime() {
        long now = System.currentTimeMillis();
        long time = now - startTime;
        JLabel timeLabel = controlHandler.getLabelByName("simulationTime");
        timeLabel.setText("Time: " + time + " ms.");
    }

    private void handleButtonClick(ActionEvent e) {
        if (e.getActionCommand() != null) {
            if (e.getActionCommand().equals("run") && !aPathFinding.isRunning()) {
                runSimulation();
            } else if (e.getActionCommand().equals("run")) {
                continuePathFinding();
            } else if (e.getActionCommand().equals("stop")) {
                stopPathFinding();
            } else if (e.getActionCommand().equals("clear")) {
                resetBoard();
            } else if (e.getActionCommand().equals("saveSetup")) {
                Setup setup = new Setup(astar, startNode, endNode);
                SetupSaver.saveSetup(setup);
            } else if (e.getActionCommand().equals("loadSetup")) {
                Setup setup = SetupLoader.loadSetup();
                GlobalConstants.updateSetup(setup);
                controlHandler.updateLabels();
                astar.update(setup);
                startNode = setup.getStartNode();
                endNode = setup.getEndNode();
            } else if(e.getActionCommand().equals("change")) {
                controlHandler.changeRpAndTi();
            }
            if (e.getActionCommand().equals("deleteObstacles")) {
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
        startTime = System.currentTimeMillis();
        start();
        updateTime();
    }

    private void start() {
        timer.setDelay(TIME_INTERVAL);
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
