package models.enums;

import utilities.random.RandomCollection;

import java.util.Set;

/**
 * Genders of the character
 * @author Laradell Tria
 */
public enum Gender {
    FEMALE("female"),
    MALE("male"),
    UNKNOWN("unknown");

    private final String displayName;

    Gender(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Returns a randomised gender.
     * Each body types are proportioned for equal probabilities, except for
     * UNKOWN to lessen its possibility.
     *
     * @return Gender randomly picked gender
     */
    public static Gender randomGender() {
        RandomCollection<Gender> rc = new RandomCollection<>();

        rc = rc.add(45, MALE);
        rc = rc.add(45, FEMALE);
        rc = rc.add(10, UNKNOWN);

        return rc.pick();
    }

    /**
     *  Set of all genders in their display name
     */
    public static final Set<String> genderSet = Set.of(
            MALE.displayName, 
            FEMALE.displayName, 
            UNKNOWN.displayName
    );
}
