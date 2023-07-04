package models.main;

/**
 * A class for representing the decision of which location will be
 * saved from the given scenario.
 *
 * @author Laradell Tria
 */
public class Decision {

    /**
     * Scenario based from in making the decision
     */
    private final Scenario scenario;
    /**
     * The index of the chosen location from the scenario
     * Pre-condition: index must be between 0 and size of the list of locations in scenario
     */
    private final int chosenLocationIndex;
    /**
     * If the decision was made by the user or the algorithm
     */
    private final boolean isDecidedByUser;

    /**
     * Create a new decision.
     * @param scenario the scenario from the decision made
     * @param chosenLocationIndex the index of the chosen location
     * @param isDecidedByUser if the decision was made by the user
     */
    public Decision(Scenario scenario,
                    int chosenLocationIndex,
                    boolean isDecidedByUser) {
        this.scenario = scenario;
        this.chosenLocationIndex = chosenLocationIndex;
        this.isDecidedByUser = isDecidedByUser;
    }

    /**
     * @return a scenario from the decision.
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * @return the index of the chosen location.
     */
    public int getChosenLocationIndex() {
        return chosenLocationIndex;
    }

    /**
     * @return if the user made this decision.
     */
    public boolean isDecidedByUser() {
        return isDecidedByUser;
    }
}
