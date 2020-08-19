package tracker;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A class for changing scenes in the application.
 * 
 * @author Pattarin Wongwaipanich
 */
public class StageManager {
    private static StageManager stageManager = null;
    private Stage stage;
    private Map<String, Scene> scenes = new HashMap<>();

    private StageManager() {
    }

    /**
     * Get singleton instance of StageManager
     * 
     * @return instance of StageManager
     */
    public synchronized static StageManager getInstance() {
        if (stageManager == null)
            stageManager = new StageManager();
        return stageManager;
    }

    /**
     * Set the satge to use.
     * 
     * @param s
     */
    public void setStage(Stage s) {
        this.stage = s;
    }

    /**
     * Map the scene with name to be used later.
     * 
     * @param sceneName name of the scene.
     * @param s         a scene to be displayed.
     */
    public void register(String sceneName, Scene s) {
        this.scenes.put(sceneName.toLowerCase(), s);
    }

    /**
     * Show the updated scene
     * 
     * @param sceneName scene to be displayed
     */
    public void showScene(String sceneName) {
        sceneName = sceneName.toLowerCase();
        Scene scene = this.scenes.get(sceneName);
        if (scene == null) {
            scene = makeScene(sceneName);
            register(sceneName, scene);
        }
        // Set scene to the stage
        this.stage.setScene(scene);
        this.stage.sizeToScene();
        this.stage.show();
    }

    /**
     * Make a new scene of scenes map doesn't contain this sceneName else return the
     * scene of sceneName
     * 
     * @param sceneName name of the scene (country)
     * @return scene
     */
    public Scene makeScene(String sceneName) {
        if (!scenes.containsKey(sceneName)) {
            checkMapsize();
            Parent root = new SearchPageController().sceneBuild();
            register(sceneName, new Scene(root));
        }
        return scenes.get(sceneName);
    }

    /**
     * Control map size not to exceed 10 elements int the map.
     */
    private void checkMapsize() {
        if (scenes.size() > 10) {
            for (String s : scenes.keySet()) {
                if (!s.equals("main")) {
                    scenes.remove(s);
                    break;
                }
            }
        }
    }

}