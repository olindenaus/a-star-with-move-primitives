package a.path.finding;

import a.path.finding.boundary.Frame;
import a.path.finding.entity.Style;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class ControlHandler {
    private Frame frame;
    private JLabel modeText;
    private JButton run, deleteObstacles;
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

        deleteObstacles = new JButton();
        deleteObstacles.setText("Delete obstacles");
        deleteObstacles.setActionCommand("deleteObstacles");
        deleteObstacles.setName("deleteObstacles");
        deleteObstacles.setFocusable(false);
        deleteObstacles.addActionListener(frame);
        deleteObstacles.setMargin(new Insets(0,0,0,0));
        deleteObstacles.setVisible(true);
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
        run.setBounds(10, frame.getHeight() - 60, 52, 22);
        deleteObstacles.setBounds(70, frame.getHeight() - 60, 110, 22);
    }

    public void addAllComponents() {
        frame.add(run);
        frame.add(deleteObstacles);
        frame.add(modeText);
    }

    private void addLabels() {
        labels.add(modeText);
    }

    private void addButtons() {
        buttons.add(run);
        buttons.add(deleteObstacles);
    }
}
