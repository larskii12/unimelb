package models.animals;

import models.enums.BodyType;
import models.enums.Gender;
import models.enums.Species;
import models.main.Character;

import java.util.Random;

import static utilities.constant.Constants.EMPTY;
import static models.enums.Species.speciesList;

/**
 * A class representing all animal characters.
 *
 * @author Laradell Tria
 */
public class Animal extends Character {

    /**
     * The species name of the animal
     */
    private String species;

    /**
     * If the animal is a pet
     */
    private boolean isPet;

    /**
     * Create a new animal.
     */
    public Animal() {}

    /**
     * Create a new animal with arguments.
     * @param age the age of the animal
     * @param gender the gender of the animal
     * @param bodyType the body type of the animal
     * @param species the species of the animal
     * @param isPet if the animal is a pet
     */
    public Animal(Integer age,
                  Gender gender,
                  BodyType bodyType,
                  String species,
                  boolean isPet) {
        super(age, gender, bodyType);
        this.species = species;
        this.isPet = isPet;
    }

    /**
     * Return a description of animal.
     * It gives out the species name of the animal and if the animal is a pet.
     *
     * @return a string describing this animal
     */
    @Override
    public String describe() {
        return species + (isPet ? " is pet" : EMPTY);
    }

    /**
     * This method is to generate a random valid animal.
     */
    @Override
    public void randomize() {
        Random random = new Random();
        int age = random.nextInt(15) + 1;
        Species chosenSpecies = speciesList[random.nextInt(speciesList.length)];
        this.species = chosenSpecies.getDisplayName();
        this.isPet = chosenSpecies.getIsPet();

        setAge(age);
        setGender(Gender.randomGender());
        setBodyType(BodyType.randomBodyType());
    }
}
