package models.main;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static java.util.Objects.nonNull;

/**
 * A class for detailing the statistics of all the decisions made
 * in the program.
 * @author Laradell Tria
 */
public class Statistics {

    /**
     * Set of attributes not to be included in the statistics
     */
    private static final Set<String> excludedAttributes = Set.of(
            "unknown", "unspecified", "is"
    );
    /**
     * List of all decisions used to get statistics
     */
    private final List<Decision> decisions;
    /**
     * If the statistics is user-based decisions or algorithm-based
     */
    private final boolean isUserAudit;
    /**
     * Average age of all the human survivors
     */
    private double averageAge;

    /**
     * Create a new statistics.
     * @param isUserAudit if these decisions are chosen from user
     */
    public Statistics(boolean isUserAudit) {
        this.decisions = new ArrayList<>();
        this.isUserAudit = isUserAudit;
    }

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void addDecision(Decision decision) {
        decisions.add(decision);
    }

    /**
     * This method is used for calculating the percentage of all the
     * attributes that were saved made from the decisions.
     * @return the overall saved statistics made from the decisions
     */
    public Map<String, Double> getSavedStatistics() {
        int totalAge = 0;
        int totalNoOfHumans = 0;

        Map<String, Double> totalStatistics = new HashMap<>();
        Map<String, Double> savedStatistics = new HashMap<>();

        for(Decision decision: decisions) {
            Scenario scenario = decision.getScenario();

            List<Location> locations = scenario.getLocations();
            for (Location location : locations) {
                List<Character> characters = location.getCharacters();
                for (Character character : characters) {
                    boolean isHuman = true;
                    String description = character.describe();
                    String[] attributes = description.split(" ");
                    for (String attribute : attributes) {
                        if (excludedAttributes.contains(attribute)) {
                            continue;
                        }
                        addStatistics(totalStatistics, attribute, 1.00);
                        if(attribute.startsWith("pet")) {
                            isHuman = false;
                        }
                    }
                    if (attributes.length == 1) {
                        isHuman = false;
                    }
                    String attribute;
                    if(isHuman) {
                        attribute = "human";
                    } else {
                        attribute = "animal";
                    }
                    addStatistics(totalStatistics, attribute, 1.00);
                }
                String attribute;
                if (location.getIsTrespassing()){
                    attribute = "trespassing";
                } else {
                    attribute = "legal";
                }
                addStatistics(totalStatistics, attribute, characters.size());
            }

            Location location = scenario.getLocation(decision.getChosenLocationIndex());
            List<Character> characters = location.getCharacters();
            for (Character character: characters) {
                boolean isHuman = true;
                String description = character.describe();
                String[] attributes =  description.split(" ");
                for (String attribute: attributes) {
                    if (excludedAttributes.contains(attribute)) {
                        continue;
                    }
                    addStatistics(savedStatistics, attribute, 1.00);
                    if(attribute.startsWith("pet")) {
                        isHuman = false;
                    }
                }
                if (attributes.length == 1) {
                    isHuman = false;
                }
                String attribute;
                if(isHuman) {
                    attribute = "human";
                    totalAge += character.getAge();
                    totalNoOfHumans++;
                } else {
                    attribute = "animal";
                }
                addStatistics(savedStatistics, attribute, 1.00);
            }
            String attribute;
            if (location.getIsTrespassing()){
                attribute = "trespassing";
            } else {
                attribute = "legal";
            }
            addStatistics(savedStatistics, attribute, characters.size());
        }

        averageAge = (double) totalAge / totalNoOfHumans;

        for(String key: totalStatistics.keySet()) {
            if (nonNull(savedStatistics.get(key))) {
                double finalStatistic = savedStatistics.get(key) / totalStatistics.get(key);
                savedStatistics.put(key, finalStatistic);
            } else {
                savedStatistics.put(key, 0.00);
            }
        }

        return savedStatistics;
    }

    /**
     * This method is to add the attribute in the statistics
     * @param addend value to be added with the current attribute value
     * @param attribute the characteristic that is added
     * @param statistics where the attribute will be added
     */
    private void addStatistics(Map<String, Double> statistics, String attribute, double addend) {
        double noOfAttributes = 0.00;
        if(nonNull(statistics.get(attribute))) {
            noOfAttributes = statistics.get(attribute);
        }
        statistics.put(attribute, noOfAttributes + addend);
    }

    /**
     * Display the overall statistics from all the decisions
     */
    public void displayStatistics() {
        System.out.println("======================================");
        System.out.println("# Statistic");
        System.out.println("======================================");
        statisticsTable();
    }

    /**
     * Display the audit statistics that were read from audit logs
     */
    public void auditStatistics() {

        System.out.println("======================================");
        if(isUserAudit) {
            System.out.println("# User Audit");
        } else {
            System.out.println("# Algorithm Audit");
        }
        System.out.println("======================================");
        statisticsTable();
    }

    /**
     * Display the statistics table
     */
    private void statisticsTable() {
        System.out.println("- % SAVED AFTER " + decisions.size() + " RUNS");

        DecimalFormat decfor = new DecimalFormat("0.00");
        decfor.setRoundingMode(RoundingMode.UP);

        Map<String, Double> savedStatistics = getSavedStatistics();
        Map<String, Double> sortedTreeMap = new TreeMap<>(savedStatistics);

        sortedTreeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(stringFloatEntry -> {
                    System.out.print(stringFloatEntry.getKey() + ": ");
                    System.out.println(decfor.format(savedStatistics.get(stringFloatEntry.getKey())));
                });

        System.out.println("--");
        System.out.println("average age: " + decfor.format(averageAge));
    }
}
