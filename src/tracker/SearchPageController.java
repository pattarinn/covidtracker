package tracker;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller class for search in GUI
 * 
 * @author Pattarin Wongwaipanich
 */
public class SearchPageController {
    @FXML
    Label countryName;
    @FXML
    BarChart<CategoryAxis, NumberAxis> graph;
    @FXML
    Button back;
    @FXML
    Label date;
    @FXML
    Label newCases;
    @FXML
    Label newRecovered;
    @FXML
    Label newDeath;
    @FXML
    Label totalCase;
    @FXML
    Label totalRecovered;
    @FXML
    Label totalDeath;
    @FXML
    Label rateRecov;
    @FXML
    Label rateDeath;

    public static String selectedCountry;
    private String latestDate;

    @FXML
    public void initialize() {
        // System.out.println("SPC initialize executed");
        latestDate = InformationHandle.getInstance().dateCase[InformationHandle.getInstance().dateCase.length - 1];
        countryName.setText(selectedCountry);
        date.setText(latestDate);

        // today's new cases, deathcases
        String lateCase = latestNewCase(weeklyCases(PathFile.cases, "confirm"));
        newCases.setText(lateCase);
        String lateDeath = latestNewCase(weeklyCases(PathFile.death, "death"));
        newDeath.setText(lateDeath);

        // total number
        int[] thisWeekCase = weeklyCases(PathFile.cases, "confirm");
        String totalCasesString = String.format("%,d", thisWeekCase[thisWeekCase.length - 1]);
        totalCase.setText(totalCasesString);
        // total death
        int[] thisWeekDeath = weeklyCases(PathFile.death, "death");
        String totalDeathString = String.format("%,d", thisWeekDeath[thisWeekDeath.length - 1]);
        totalDeath.setText(totalDeathString);

        // set rate
        rateDeath.setText(rateInPercent(thisWeekDeath, thisWeekCase[thisWeekCase.length - 1]) + "%");

        int[] numRecov = weeklyCases(PathFile.recovery, "recovery");
        // some country doesn't report recovery cases
        if (numRecov.length <= 0) {
            newRecovered.setText("no data");
            totalRecovered.setText("no data");
            rateRecov.setText("no data");
        } else {
            String lateRecov = latestNewCase(numRecov);
            newRecovered.setText(lateRecov);
            String totalRecovString = String.format("%,d", numRecov[numRecov.length - 1]);
            totalRecovered.setText(totalRecovString);
            rateRecov.setText(rateInPercent(numRecov, thisWeekCase[thisWeekCase.length - 1]) + "%");
        }
        createGraph();
    }

    /**
     * Create a scene for each country.
     * 
     * @return scene
     */
    public Parent sceneBuild() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("SearchPage.fxml"));
            return root;
        } catch (IOException e) {
            System.out.println("Cannot build scene");
            return null;
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        StageManager.getInstance().showScene("main");
    }

    /**
     * Get the information of past 7 days
     * 
     * @param sourceFile source of information
     * @return last 7 days cases
     */
    public int[] weeklyCases(String sourceFile, String type) {
        int[] lastWeekCases;
        int position = InformationHandle.getInstance().sentencePosition(selectedCountry, type);
        if (position < 0) {
            return new int[0];
        }
        lastWeekCases = InformationHandle.getInstance().latestWeek(position, sourceFile);
        return lastWeekCases;
    }

    /**
     * Get the information of the latest day.
     * 
     * @param cases to be calculated.
     * @return recent cases added
     */
    public String latestNewCase(int[] cases) {
        int secondLast = cases[cases.length - 2];
        int last = cases[cases.length - 1];
        int newCase = last - secondLast;
        return String.format("%,d", newCase);
    }

    /**
     * Calculate the rate of cases in percent
     * 
     * @return rate in string
     */
    public String rateInPercent(int[] cases, int total) {
        int totalCase = cases[cases.length - 1];
        double rate;
        rate = (totalCase * 100) / total;
        return String.format("%.0f", rate);
    }

    /**
     * create graph for GUI
     */
    public void createGraph() {
        String[] thisWeekDate = InformationHandle.getInstance().dateCase;
        XYChart.Series<CategoryAxis, NumberAxis> cases = new XYChart.Series();
        XYChart.Series<CategoryAxis, NumberAxis> death = new XYChart.Series();
        XYChart.Series<CategoryAxis, NumberAxis> recovered = new XYChart.Series();

        cases.setName("CASES");
        death.setName("DEATHS");
        recovered.setName("RECOVERED");
        int[] newCase = weeklyCases(PathFile.cases, "confirm");
        int[] newDeath = weeklyCases(PathFile.death, "death");
        int[] newRecovered = weeklyCases(PathFile.recovery, "recovery");

        addGraph(cases, thisWeekDate, newCase);
        addGraph(death, thisWeekDate, newDeath);
        if (newRecovered.length > 0) {
            addGraph(recovered, thisWeekDate, newRecovered);
        } else {
            for (int i = 4; i > 0; i--) {
                recovered.getData().add(new XYChart.Data(thisWeekDate[thisWeekDate.length - i], 0));
            }
        }
        graph.getData().addAll(cases, death, recovered);
    }

    /**
     * Add components to graph
     * 
     * @param s      series of graph
     * @param date   date to show
     * @param number of cases
     */
    public void addGraph(XYChart.Series s, String[] date, int[] number) {
        for (int i = 4; i > 0; i--) {
            s.getData().add(
                    new XYChart.Data(date[date.length - i], number[number.length - i] - number[number.length - i - 1]));
        }
    }
}