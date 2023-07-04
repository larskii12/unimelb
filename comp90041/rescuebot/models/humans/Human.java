package models.humans;

import models.enums.BodyType;
import models.enums.Gender;
import models.main.Character;

/**
 * A class representing all human characters in the location.
 *
 * @author Laradell Tria
 */
public abstract class Human extends Character {
    
    /**
     * Create a new human.
     */
    public Human() {
        super();
    }
    
    /**
     * Create a new human with arguments.
     * @param age the age of the human
     * @param gender the gender of the human
     * @param bodyType the body type of the human
     */
    public Human(Integer age,
                 Gender gender,
                 BodyType bodyType) {
        super(age, gender, bodyType);
    }
}
