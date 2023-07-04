package models.humans;

import models.enums.BodyType;
import models.enums.Gender;

import java.util.Random;

/**
 * A class representing all senior humans in the location.
 *
 * @author Laradell Tria
 */
public class Senior extends Human {

    /**
     * Create a new senior.
     */
    public Senior() {}

    /**
     * Create a new senior with arguments.
     * @param age the age of the senior
     * @param gender the gender of the senior
     * @param bodyType the body type of the senior
     */
    public Senior(Integer age,
                  Gender gender,
                  BodyType bodyType) {
        super(age, gender, bodyType);
    }

    /**
     * Return a description of a senior.
     * It gives out the body type, and gender.
     *
     * @return a string describing this senior
     */
    @Override
    public String describe() {
        return getBodyType() + " senior " + getGender();
    }

    /**
     * This method is to generate a random valid senior.
     */
    @Override
    public void randomize() {
        Random random = new Random();
        int age = random.nextInt(42) + 69;

        setAge(age);
        setGender(Gender.randomGender());
        setBodyType(BodyType.randomBodyType());
    }
}
