package a.path.finding;

import a.path.finding.boundary.Frame;
import a.path.finding.entity.Style;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static a.path.finding.entity.GlobalConstants.*;


public class ControlHandler {
    private Frame frame;
    private JLabel modeText, timeInterval, resolutionPenalty;
    private JButton run, deleteObstacles, saveSetup, loadSetup, clear;
    private ArrayList<JLabel> labels;
    private ArrayList<JButton> buttons;

    public ControlHandler(Frame frame) {
        this.frame = frame;
        labels = new ArrayList<>();
        buttons = new ArrayList<>();

        setUpLabels();
        setUpButtons();
        addLabels();
        addButtons();
    }

    private void setUpLabels() {
        modeText = new JLabel("Stage: ");
        modeText.setName("modeText");
        modeText.setFont(Style.bigText);
        modeText.setForeground(Style.darkText);
        modeText.setVisible(true);

        resolutionPenalty = new JLabel("RP: " + RESOLUTION_PENALTY);
        resolutionPenalty.setName("resolutionPenalty");
        resolutionPenalty.setFont(Style.numbers);
        resolutionPenalty.setForeground(Style.darkText);
        resolutionPenalty.setVisible(true);

        timeInterval = new JLabel("TI: " + TIME_INTERVAL);
        timeInterval.setName("timeInterval");
        timeInterval.setFont(Style.numbers);
        timeInterval.setForeground(Style.darkText);
        timeInterval.setVisible(true);
    }

    private void setUpButtons() {
        run = new JButton();
        run.setText("Run");
        run.setActionCommand("run");
        run.setName("run");
        run.setFocusable(false);
        run.addActionListener(frame);
        run.setMargin(new Insets(0, 0, 0, 0));
        run.setVisible(true);

        clear = new JButton();
        clear.setText("Clear");
        clear.setActionCommand("clear");
        clear.setName("clear");
        clear.setFocusable(false);
        clear.addActionListener(frame);
        clear.setMargin(new Insets(0, 0, 0, 0));
        clear.setVisible(true);

        deleteObstacles = new JButton();
        deleteObstacles.setText("Delete obstacles");
        deleteObstacles.setActionCommand("deleteObstacles");
        deleteObstacles.setName("deleteObstacles");
        deleteObstacles.setFocusable(false);
        deleteObstacles.addActionListener(frame);
        deleteObstacles.setMargin(new Insets(0, 0, 0, 0));
        deleteObstacles.setVisible(true);

        saveSetup = new JButton();
        saveSetup.setText("Save setup");
        saveSetup.setName("saveSetup");
        saveSetup.addActionListener(frame);
        saveSetup.setActionCommand("saveSetup");
        saveSetup.setMargin(new Insets(0, 0, 0, 0));
        saveSetup.setVisible(true);

        loadSetup = new JButton();
        loadSetup.setText("Load setup");
        loadSetup.setName("loadSetup");
        loadSetup.setActionCommand("loadSetup");
        loadSetup.addActionListener(frame);
        loadSetup.setMargin(new Insets(0, 0, 0, 0));
        loadSetup.setVisible(true);
    }

    public JLabel getLabelByName(String name) {
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getName().equals(name)) {
                return labels.get(i);
            }
        }
        return null;
    }

    public JButton getButtonByName(String name) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getName().equals(name)) {
                return buttons.get(i);
            }
        }
        return null;
    }

    public void positionElements() {
        Dimension size = modeText.getPreferredSize();
        modeText.setBounds(10, frame.getHeight() - 40, size.width, size.height);
        resolutionPenalty.setBounds(200, frame.getHeight() - 50, 100, 20);
        timeInterval.setBounds(200, frame.getHeight() - 30, 100, 20);
        run.setBounds(10, frame.getHeight() - 60, 52, 22);
        deleteObstacles.setBounds(70, frame.getHeight() - 60, 110, 22);
        saveSetup.setBounds(10, frame.getHeight() - 90, 80, 22);
        loadSetup.setBounds(90, frame.getHeight() - 90, 80, 22);
        clear.setBounds(180, frame.getHeight() - 90, 50, 20);
    }

    public void updateLabels() {
        resolutionPenalty.setText("RP: " + RESOLUTION_PENALTY);
        timeInterval.setText("TI: " + TIME_INTERVAL);
    }

    public void addAllComponents() {
        frame.add(run);
        frame.add(deleteObstacles);
        frame.add(saveSetup);
        frame.add(loadSetup);
        frame.add(modeText);
        frame.add(timeInterval);
        frame.add(resolutionPenalty);
        frame.add(clear);
    }

    private void addLabels() {
        labels.add(modeText);
        labels.add(timeInterval);
        labels.add(resolutionPenalty);
    }

    private void addButtons() {
        buttons.add(run);
        buttons.add(deleteObstacles);
        buttons.add(saveSetup);
        buttons.add(loadSetup);
        buttons.add(clear);
    }
}
