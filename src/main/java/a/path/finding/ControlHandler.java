package a.path.finding;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;


public class ControlHandler {
    private Frame frame;
    private JLabel modeText, noPathT;
    private JButton run;
    private ArrayList<JLabel> labels;
    private ArrayList<JButton> buttons;
    Dimension npD;

    public ControlHandler(Frame frame) {
        this.frame = frame;
        labels = new ArrayList<>();
        buttons = new ArrayList<>();

        setUpLabels();
        setUpButton();
        addLabels();
        addButtons();
    }

    private void setUpLabels() {
        modeText = new JLabel("Stage: ");
        modeText.setName("modeText");
        modeText.setFont(Style.bigText);
        modeText.setForeground(Style.darkText);
        modeText.setVisible(true);

        noPathT = new JLabel("NO PATH");
        noPathT.setName("noPathT");
        noPathT.setForeground(Color.white);
        noPathT.setFont(Style.REALBIGText);
        npD = noPathT.getPreferredSize();
    }

    private void setUpButton() {
        run = new JButton();
        run.setText("run");
        run.setName("run");
        run.setFocusable(false);
        run.addActionListener(frame);
        run.setMargin(new Insets(0, 0, 0, 0));
        run.setVisible(true);
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
    }

    public void addAllComponents() {
        frame.add(run);
        frame.add(modeText);
    }

    private void addLabels() {
        labels.add(modeText);
        labels.add(noPathT);
    }

    private void addButtons() {
        buttons.add(run);
    }
}
