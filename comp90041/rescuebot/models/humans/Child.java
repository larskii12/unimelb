package models.humans;

import models.enums.BodyType;
import models.enums.Gender;

import java.util.Random;

/**
 * A class representing all child humans in the location.
 *
 * @author Laradell Tria
 */
public class Child extends Human {

    /**
     * Create a new child.
     */
    public Child() {}

    /**
     * Create a new child with arguments.
     * @param age the age of the child
     * @param gender the gender of the child
     * @param bodyType the body type of the child
     */
    public Child(Integer age,
                 Gender gender,
                 BodyType bodyType) {
        super(age, gender, bodyType);
    }

    /**
     * Return a description of a child.
     * It gives out the body type, and gender.
     *
     * @return a string describing this child
     */
    @Override
    public String describe() {
        return getBodyType() + " child " + getGender();
    }

    /**
     * This method is to generate a random valid child.
     */
    @Override
    public void randomize() {
        Random random = new Random();
        int age = random.nextInt(12) + 5;

        setAge(age);
        setGender(Gender.randomGender());
        setBodyType(BodyType.randomBodyType());
    }
}
