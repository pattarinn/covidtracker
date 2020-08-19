package tracker;

import java.io.File;
import java.nio.file.Path;

/**
 * A class for updating information.
 * 
 * @author Pattarin Wongwaipanich
 */
public class Updateinfo {

    /** update information */
    public static void update() {
        PathFile pf = PathFile.getInstance();
        Path path1 = pf.getPath("info.csv");
        Path path2 = pf.getPath("deathcases.csv");
        Path path3 = pf.getPath("recoverycases.csv");
        PathFile.cases = path1.toString();
        PathFile.death = path2.toString();
        PathFile.recovery = path3.toString();

        File caseFile = path1.toFile();
        File deathFile = path2.toFile();
        File recoveryFile = path3.toFile();

        String caseURL = PropertyManager.getInstance().getProperty("download.case");
        String deathURL = PropertyManager.getInstance().getProperty("download.death");
        String recoveryURL = PropertyManager.getInstance().getProperty("download.recovery");

        DownloadInformation di = new DownloadInformation();
        di.download(caseFile, caseURL);
        di.download(deathFile, deathURL);
        di.download(recoveryFile, recoveryURL);

    }
}