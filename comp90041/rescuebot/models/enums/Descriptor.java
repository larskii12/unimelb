package models.enums;

/**
 * Descriptors of possible scenarios
 * @author Laradell Tria
 */
public enum Descriptor {
    BUSHFIRE("bushfire"),
    CYCLONE("cyclone"),
    EARTHQUAKE("earthquake"),
    FLOOD("flood"),
    TSUNAMI("tsunami");

    private final String displayName;

    Descriptor(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     *  Array of all scenario descriptors
     */
    public static final Descriptor[] descriptors = Descriptor.values();
}
