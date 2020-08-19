package tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * A class for home page GUI.
 * 
 * @author Pattarin Wongwaipanich
 */
public class HomePageController {

    @FXML
    Label title;
    @FXML
    ComboBox<String> countrySearch;
    @FXML
    LineChart<CategoryAxis, NumberAxis> graph;
    @FXML
    Button go;
    @FXML
    CategoryAxis x;
    @FXML
    NumberAxis y;
    @FXML
    Label confirmCaseGlobal;
    @FXML
    Label deathCaseGlobal;
    @FXML
    Label recoveryCaseGlobal;
    @FXML
    Label confirmNumGlobal;
    @FXML
    Label deathNumGlobal;
    @FXML
    Label recoveryNumGlobal;

    public InformationHandle info = InformationHandle.getInstance();

    @FXML
    public void initialize() {
        info.getAllInfo();
        countrySearch.getItems().addAll(info.allCountriesCase.keySet());
        createGraph(info);
        summary();
    }

    /**
     * Create a grapgh displaying in GUI.
     */
    private void createGraph(InformationHandle info) {
        XYChart.Series<CategoryAxis, NumberAxis> seriesCase = new XYChart.Series<>();
        XYChart.Series<CategoryAxis, NumberAxis> seriesDeath = new XYChart.Series<>();
        XYChart.Series<CategoryAxis, NumberAxis> seriesRecov = new XYChart.Series<>();

        // creating confirm cases graph
        seriesCase.setName("Confirm cases");
        int[] lastWeekCase = info.latestWeekGlobal(PathFile.cases);
        String[] dateCase = info.dateCase;
        for (int i = 0; i < 7; i++) {
            seriesCase.getData().add(new XYChart.Data(dateCase[dateCase.length - 7 + i], lastWeekCase[i]));
        }

        // creating dead cases graph, date depends on confirm case update
        seriesDeath.setName("Death cases");
        int[] lastWeekDeath = info.latestWeekGlobal(PathFile.death);
        String[] dateDeath = info.dateDeathCase;
        for (int i = 0; i < 7; i++) {
            seriesDeath.getData().add(new XYChart.Data(dateDeath[dateDeath.length - 7 + i], lastWeekDeath[i]));
        }

        // creating recovery cases graph, date depends on confirm case update
        seriesRecov.setName("Recovery cases");
        int[] lastWeekRecov = info.latestWeekGlobal(PathFile.recovery);
        String[] dateRecov = info.dateRecoveryCase;
        for (int i = 0; i < 7; i++) {
            seriesRecov.getData().add(new XYChart.Data(dateRecov[dateRecov.length - 7 + i], lastWeekRecov[i]));
        }
        graph.getData().addAll(seriesCase, seriesDeath, seriesRecov);
    }

    /** Summary number for global */
    private void summary() {
        confirmNumGlobal.setText(String.format("%,d", info.globalCase(PathFile.cases)));
        deathNumGlobal.setText(String.format("%,d", info.globalCase(PathFile.death)));
        recoveryNumGlobal.setText(String.format("%,d", info.globalCase(PathFile.recovery)));
    }

    /** button handler */
    public void goButton(ActionEvent event) {
        String country = countrySearch.getValue();
        if (country != null) {
            SearchPageController.selectedCountry = country;
            StageManager.getInstance().showScene(country);
        }
    }

}