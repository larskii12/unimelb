package models.main;

import java.util.ArrayList;
import java.util.List;

import static utilities.constant.Constants.SPACE;

/**
 * A class for representing a location found in a certain scenario
 *
 * @author Laradell Tria
 */
public class Location {

    /**
     * The latitude of the location
     * Pre-condition: can only be a valid positive integer
     */
    private double latitude;
    /**
     * The direction of the latitude of the location
     * Pre-condition: can only be N or S
     */
    private char latitudeDirection;
    /**
     * The longitude of the location
     * Pre-condition: can only be a valid positive integer
     */
    private double longitude;
    /**
     * The direction of the longitude of the location
     * Pre-condition: can only be W or E
     */
    private char longitudeDirection;
    /**
     * If the characters in this location are trespassing
     */
    private boolean isTrespassing;
    /**
     * List of all the characters found in this location
     */
    private final List<Character> characters = new ArrayList<>();

    /**
     * Create a new location.
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     * @param trespassing if the characters in the location are trespassing
     */
    public Location(String latitude, String longitude, String trespassing) {
        setLatitude(latitude);
        setLongitude(longitude);
        setIsTrespassing(trespassing);
    }

    /**
     * Returns the latitude of the location
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the direction of the latitude in location
     * @return The direction character of latitude
     */
    public char getLatitudeDirection() {
        return latitudeDirection;
    }

    /**
     * Returns the longitude of the location
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the direction of the longitude in location
     * @return The direction character of longitude
     */
    public char getLongitudeDirection() {
        return longitudeDirection;
    }

    /**
     * Returns true if trespassing, false if not
     * @return The boolean value of trespassing
     */
    public boolean getIsTrespassing() {
        return isTrespassing;
    }

    /**
     * Returns the list of characters found in location
     * @return The list of characters
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * Appends a character in the list found in location
     */
    public void addCharacter(Character character) {
        characters.add(character);
    }

    /**
     * This method converts the latitude string to its corresponding variable
     */
    public void setLatitude(String latitude) {
        String[] splits = latitude.split(" ");
        this.latitude = Double.parseDouble(splits[0]);
        this.latitudeDirection = splits[1].charAt(0);
    }

    /**
     * This method converts the longitude string to its corresponding variable
     */
    public void setLongitude(String longitude) {
        String[] splits = longitude.split(" ");
        this.longitude = Double.parseDouble(splits[0]);
        this.longitudeDirection = splits[1].charAt(0);
    }

    /**
     * This method converts the trespassing string to boolean
     */
    public void setIsTrespassing(String trespassing) {
        isTrespassing = trespassing.equals("trespassing");
    }

    /**
     * This method displays in the console the details of the location
     * with all its characters.
     */
    public void display() {
        System.out.print("Location: " + latitude +  SPACE + latitudeDirection);
        System.out.println(", " + longitude + SPACE + longitudeDirection);
        System.out.println("Trespassing: " + ((isTrespassing) ? "yes" : "no"));
        System.out.println(characters.size() + " Characters: ");
        for(Character character : characters) {
            System.out.println("- " + character.describe());
        }
    }
}
