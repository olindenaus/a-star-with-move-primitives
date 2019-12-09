package a.path.finding.control;

import a.path.finding.entity.Setup;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SetupLoader {

    public static Setup loadSetup() {
        String filePath = getFile();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fis);
            Setup setup = (Setup) in.readObject();
            in.close();
            fis.close();
            return setup;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Setup();
        }
    }

    private static String getFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Choose setup file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return "";
    }
}
