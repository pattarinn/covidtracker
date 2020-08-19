package tracker;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for getting path to information file.
 * 
 * @author Pattarin Wongwaipanich
 */
public class PathFile {

    public static String cases;
    public static String death;
    public static String recovery;

    private static PathFile pathFile = null;

    private PathFile() {
    }

    /**
     * Get instance of singleton class
     * 
     * @return instance of this class
     */
    public synchronized static PathFile getInstance() {
        if (pathFile == null) {
            pathFile = new PathFile();
        }
        return pathFile;
    }

    /**
     * Getting path for information
     * 
     * @param fileName
     * @return
     */
    public Path getPath(String fileName) {
        Path path;
        String datadir = PropertyManager.getInstance().getProperty("data.dir");
        if (datadir.startsWith("/")) {
            path = Paths.get(datadir, fileName);
        } else {
            String home = System.getProperty("user.home");
            path = Paths.get(home, datadir, fileName);
        }
        return path;
    }
}