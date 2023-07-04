package models.enums;

import utilities.random.RandomCollection;

/**
 * Body types of a character
 * @author Laradell Tria
 */
public enum BodyType {
    AVERAGE("average"),
    ATHLETIC("athletic"),
    OVERWEIGHT("overweight"),
    UNSPECIFIED("unspecified");

    private final String displayName;

    BodyType(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Returns a randomised body type.
     * Each body types are proportioned for equal probabilities, except for
     * UNSPECIFIED to lessen its possibility.
     * 
     * @return BodyType randomly picked body type
     */
    public static BodyType randomBodyType() {
        RandomCollection<BodyType> rc = new RandomCollection<>();

        rc.add(30, AVERAGE);
        rc.add(30, ATHLETIC);
        rc.add(30, OVERWEIGHT);
        rc.add(10, UNSPECIFIED);

        return rc.pick();
    }
}
