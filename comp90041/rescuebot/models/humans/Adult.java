package models.humans;

import models.enums.BodyType;
import models.enums.Gender;
import models.enums.Profession;

import java.util.Random;

import static models.enums.Profession.professions;
import static utilities.constant.Constants.EMPTY;
import static utilities.constant.Constants.SPACE;

/**
 * A class representing all adult humans in the location.
 *
 * @author Laradell Tria
 */
public class Adult extends Human {

    /**
     * The profession of the adult
     */
    private Profession profession;

    /**
     * If the adult is pregnant
     * Pre-condition: adult must be female
     */
    private boolean isPregnant;

    /**
     * Create a new adult.
     */
    public Adult() {}

    /**
     * Create a new adult with arguments.
     * @param age the age of the adult
     * @param gender the gender of the adult
     * @param bodyType the body type of the adult
     * @param profession the profession of the adult
     * @param isPregnant if the adult is pregnant
     */
    public Adult(Integer age,
                 Gender gender,
                 BodyType bodyType,
                 Profession profession,
                 boolean isPregnant) {
        super(age, gender, bodyType);
        this.profession = profession;
        this.isPregnant = isPregnant;
    }

    /**
     * Returns the string version of the profession.
     * @return a string o the profession description
     */
    private String getProfession() {
        if (profession.equals(Profession.NONE)) {
            return EMPTY;
        } else {
            return profession.getDisplayName() + SPACE;
        }
    }

    /**
     * Return a description of an adult.
     * It gives out the body type, profession, gender, and if they are pregnant.
     *
     * @return a string describing this adult
     */
    @Override
    public String describe() {
        return getBodyType() + " adult " + getProfession() +
                getGender() + ((isPregnant) ? " pregnant" : EMPTY);
    }

    /**
     * This method is to generate a random valid adult.
     */
    @Override
    public void randomize() {
        Random random = new Random();
        int age = random.nextInt(52) + 17;
        this.profession = professions[random.nextInt(professions.length)];
        Gender randomGender = Gender.randomGender();
        if(randomGender == Gender.FEMALE) {
            this.isPregnant = random.nextDouble() > 0.5;
        } else {
            this.isPregnant = false;
        }

        setAge(age);
        setGender(randomGender);
        setBodyType(BodyType.randomBodyType());
    }
}
