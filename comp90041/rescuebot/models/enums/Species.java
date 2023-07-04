package models.enums;

/**
 * Species of all animals
 * @author Laradell Tria
 */
public enum Species {

    CAT("cat", true),
    COCKATOO("cockatoo", false),
    DINGO("dingo", false),
    DOG("dog", true),
    FERRET("ferret", true),
    HAMSTER("hamster", true),
    KOALA("koala", false),
    PARROT("parrot", false),
    PLATYPUS("platypus", false),
    POSSUM("possum", false),
    SNAKE("snake", false),
    WALLABY("wallaby", false),
    OTHER("other", false);

    private final String displayName;
    private final boolean isPet;

    Species(final String displayName, final boolean isPet) {
        this.displayName = displayName;
        this.isPet = isPet;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean getIsPet() { return this.isPet; }

    /**
     *  Array of all species
     */
    public static final Species[] speciesList = Species.values();
}
