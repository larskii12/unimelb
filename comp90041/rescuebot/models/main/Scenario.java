package models.main;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for representing the scenario dilemma needed to be rescued
 *
 * @author Laradell Tria
 */
public class Scenario {

    /**
     * The description of the scenario
     */
    private final String descriptor;
    /**
     * The list of all the locations that are in need of help in the scenario
     */
    private final List<Location> locations = new ArrayList<>();

    /**
     * Create a new scenario.
     * @param descriptor the description of the scenario
     */
    public Scenario(String descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Returns the description of the scenario
     * @return The descriptor in scenario
     */
    public String getDescriptor() {
        return descriptor;
    }

    /**
     * Returns the location from the list given the position.
     * @param index the position of location in the list
     * @return The descriptor in scenario
     */
    public Location getLocation(int index) {
        return locations.get(index - 1);
    }

    /**
     * Returns the list of locations spotted in the scenario
     * @return The list of locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Returns the number of locations found in scenario
     * @return The size of the list of locations
     */
    public int getNoOfLocations() {
        return locations.size();
    }

    /**
     * Appends a location in the list found in scenario
     */
    public void addLocation(Location location) {
        locations.add(location);
    }

    /**
     * This method displays in the console the details of the scenario
     * with all its locations' descriptions.
     */
    public void display() {
        System.out.println("======================================");
        System.out.println("# Scenario: " + descriptor);
        System.out.println("======================================");
        int index = 1;
        for(Location location : locations) {
            System.out.print("[" + index + "] ");
            location.display();
            index++;
        }
    }
}
