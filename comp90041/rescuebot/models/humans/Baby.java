package models.humans;

import models.enums.BodyType;
import models.enums.Gender;

import java.util.Random;

/**
 * A class representing all baby humans in the location.
 *
 * @author Laradell Tria
 */
public class Baby extends Human {

    /**
     * Create a new baby.
     */
    public Baby() {}

    /**
     * Create a new baby with arguments.
     * @param age the age of the baby
     * @param gender the gender of the baby
     * @param bodyType the body type of the baby
     */
    public Baby(Integer age,
                Gender gender,
                BodyType bodyType) {
        super(age, gender, bodyType);
    }

    /**
     * Return a description of a baby.
     * It gives out the body type, and gender.
     *
     * @return a string describing this baby
     */
    @Override
    public String describe() {
        return getBodyType() + " baby " + getGender();
    }

    /**
     * This method is to generate a random valid baby.
     */
    @Override
    public void randomize() {
        Random random = new Random();
        int age = random.nextInt(5) ;

        setAge(age);
        setGender(Gender.randomGender());
        setBodyType(BodyType.randomBodyType());
    }
}
