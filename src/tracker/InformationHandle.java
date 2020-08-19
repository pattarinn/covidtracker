package tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Class for handling information
 * 
 * @author Pattarin Wongwaipanich
 */
public class InformationHandle {
    private static InformationHandle ih = null;

    public Map<String, Integer> allCountriesCase = new TreeMap<>();
    public String[] dateCase;

    public Map<String, Integer> allCountriesDeath = new TreeMap<>();
    public String[] dateDeathCase;

    public Map<String, Integer> allCountriesRecovery = new TreeMap<>();
    public String[] dateRecoveryCase;

    private InformationHandle() {
    }

    /**
     * Get instance of InformationHandle.
     * 
     * @return
     */
    public static InformationHandle getInstance() {
        if (ih == null)
            ih = new InformationHandle();
        return ih;
    }

    /**
     * Load the information
     * 
     * @param allCountries map a country with its sentence number
     * @param top5         of the most case
     * @param sourceFile   file to get the information
     */
    private void getFundamentalInfo(Map<String, Integer> allCountries, String sourceFile) {
        File file = new File(sourceFile);
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);) {
            br.readLine();
            int count = 1;
            String line;
            String[] lineSplit;
            while ((line = br.readLine()) != null) {
                count++;
                lineSplit = line.split(",");
                String name = null;
                if (line.startsWith("\"")) {
                    name = lineSplit[2].concat(", " + lineSplit[0] + lineSplit[1]);
                } else {
                    if (line.startsWith(",")) {
                        if (lineSplit[1].startsWith("\"")) {
                            name = lineSplit[2].substring(1, lineSplit[2].length() - 1)
                                    .concat(" " + lineSplit[1].substring(1));
                        } else {
                            name = lineSplit[1];
                        }
                    } else {
                        name = lineSplit[1].concat(", " + lineSplit[0]);
                    }
                }
                allCountries.put(name, count);
            }
        } catch (IOException e) {
            System.out.println("Error occurs while loading basic information.");
            System.exit(1);
        }

    }

    /**
     * Get date for the case.
     * 
     * @param sourceFile file to get the information.
     * @return set of date.
     */
    public String[] getDate(String sourceFile) {
        File file = new File(sourceFile);
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);) {
            return br.readLine().split(",");
        } catch (IOException e) {
            System.out.println("Error at InformationHandle. Cannot download date.");
            System.exit(1);
        }
        return null;
    }

    /**
     * Load all the information to be used.
     */
    public void getAllInfo() {
        Updateinfo.update();
        getFundamentalInfo(allCountriesCase, PathFile.cases);
        getFundamentalInfo(allCountriesDeath, PathFile.death);
        getFundamentalInfo(allCountriesRecovery, PathFile.recovery);
        dateCase = getDate(PathFile.cases);
        dateDeathCase = getDate(PathFile.death);
        dateRecoveryCase = getDate(PathFile.recovery);
    }

    /**
     * Get the number of cases of the latest week for a specific country.
     * 
     * @param num        number of sentence that the country locates.
     * @param sourceFile file to collect the infomation of that country.
     * @return the number of cases of latest week.
     */
    public int[] latestWeek(int num, String sourceFile) {
        String line;
        int[] lastWeek = new int[7];
        String[] lineSplit;
        File file = new File(sourceFile);
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);) {
            int count = 0;
            while (count < num - 1) {
                br.readLine();
                count++;
            }
            line = br.readLine();
            if (line == null) {
                return new int[0];
            }
            lineSplit = line.split(",");
            int number;
            for (int i = 7; i > 0; i--) {
                number = Integer.parseInt(lineSplit[lineSplit.length - i]);
                lastWeek[7 - i] = number;
            }
        } catch (IOException e) {
            System.out.println("Error at InformationHandle, latestWeek function");
            System.exit(1);
        }
        return lastWeek;
    }

    /**
     * Get the latest number of cases.
     * 
     * @param sourceFile file to collect the infomation.
     * @return latest number of cases
     */
    public int globalCase(String sourceFile) {
        int num = 0;
        File file = new File(sourceFile);
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);) {
            String line;
            String[] splitLine;
            br.readLine();
            while ((line = br.readLine()) != null) {
                splitLine = line.split(",");
                num += Integer.parseInt(splitLine[splitLine.length - 1]);
            }
        } catch (IOException e) {
            System.out.println("Error at InformationHandle, globalCase function");
            System.exit(1);
        }
        return num;
    }

    /**
     * Gather the number cases of the latest week.
     * 
     * @param sourceFile file to collect the infomation.
     * @return a set of number of cases of the latest week
     */
    public int[] latestWeekGlobal(String sourceFile) {
        String line;
        int[] lastWeek = new int[7];
        String[] lineSplit;
        File file = new File(sourceFile);
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);) {
            line = br.readLine();
            int number;
            while ((line = br.readLine()) != null) {
                lineSplit = line.split(",");
                for (int i = 7; i > 0; i--) {
                    number = Integer.parseInt(lineSplit[lineSplit.length - i]);
                    lastWeek[7 - i] += number;
                }
            }
        } catch (IOException e) {
            System.out.println("Error at InformationHandle, latestWeekGlobal function");
            System.exit(1);
        }
        return lastWeek;
    }

    /**
     * Find the sentence number of the information file.
     * 
     * @param countryToSearch country to find the sentence number
     * @return sentence number
     */
    public int sentencePosition(String countryToSearch, String type) {
        String[] place = countryToSearch.split(" ");
        if (place.length > 1) {
            countryToSearch = place[place.length - 2] + " " + place[place.length - 1];
        } else {
            countryToSearch = place[0];
        }
        Map<String, Integer> mapToSearch;
        if (type.equals("recovery")) {
            mapToSearch = InformationHandle.getInstance().allCountriesRecovery;
        } else if (type.equals("death")) {
            mapToSearch = InformationHandle.getInstance().allCountriesDeath;
        } else {
            mapToSearch = InformationHandle.getInstance().allCountriesCase;
        }
        for (String s : mapToSearch.keySet()) {
            if (s.contains(countryToSearch)) {
                int atSentence = mapToSearch.get(s);
                return atSentence;
            }
        }
        return -1;
    }
}