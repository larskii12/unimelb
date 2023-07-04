package models.enums;

/**
 * Professions of an adult human
 * @author Laradell Tria
 */
public enum Profession {

    ACTOR("actor"),
    ARTIST("artist"),
    BAKER("baker"),
    CEO("ceo"),
    CHEF("chef"),
    CRIMINAL("criminal"),
    DOCTOR("doctor"),
    DRIVER("driver"),
    ENGINEER("engineer"),
    HOMELESS("homeless"),
    JANITOR("janitor"),
    LEADER("leader"),
    NURSE("nurse"),
    POLICE("police"),
    PROFESSOR("professor"),
    STUDENT("student"),
    TEACHER("teacher"),
    UNEMPLOYED("unemployed"),
    VETERINARIAN("veterinarian"),
    NONE("none");

    private final String displayName;

    Profession(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    /**
     *  Array of all professions
     */
    public static final Profession[] professions = Profession.values();
}
