package a.path.finding.control;

import a.path.finding.entity.Setup;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SetupSaver {

    public static void saveSetup(Setup setup) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_HH_mm_ss");
        String fileName = LocalDateTime.now().format(formatter) + "_" + setup.fileName();
        try {
            FileOutputStream fos = new FileOutputStream(fileName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(setup);
            out.close();
            fos.close();
            System.out.println("Saved setup");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
