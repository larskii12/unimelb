package models.main;

import models.enums.BodyType;
import models.enums.Gender;

/**
 * A class for representing a character in a location.
 * Base class for every character in the location.
 *
 * @author Laradell Tria
 */
public abstract class Character {

    /**
     * The age of the character
     * Pre-condition: age must be greater than or equal to 0
     */
    private Integer age;

    /**
     * The gender of the character
     */
    private Gender gender;

    /**
     * The body type of the character
     */
    private BodyType bodyType;

    /**
     * Create a new character.
     */
    public Character() {}

    /**
     * Create a new character with arguments.
     * @param age the age of the character
     * @param gender the gender of the character
     * @param bodyType the body type of the character
     */
    public Character(Integer age,
                     Gender gender,
                     BodyType bodyType) {
        setAge(age);
        setGender(gender);
        setBodyType(bodyType);
    }

    /**
     * Returns the age of the character
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Returns the body type of the character
     * @return The string value of the body type
     */
    public String getBodyType() {
        return bodyType.getDisplayName();
    }

    /**
     * Returns the gender of the character
     * @return The string value of the gender
     */
    public String getGender() {
        return gender.getDisplayName();
    }

    /**
     * Setting age of the character
     * @param age the age of the character
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Returns the body type of the character
     * @return The string value of the body type
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns the body type of the character
     * @return The string value of the body type
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Return a description of a character.
     * @return a string describing the character
     */
    public abstract String describe();

    /**
     * This method is to generate a random character.
     */
    public abstract void randomize();
}
