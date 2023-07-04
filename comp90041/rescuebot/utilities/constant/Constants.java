package utilities.constant;

import utilities.exceptions.InvalidInputException;

import java.util.Scanner;

/**
 * A class for representing all constants used all throughout the program.
 *
 * @author Laradell Tria
 */
public class Constants {

    // Punctuation Constants
    public static final String COMMA = ",";
    public static final String COMMAND_PROMPT = "> ";
    public static final String DOUBLE_COMMA = ",,";
    public static final String EMPTY = "";
    public static final String SEMICOLON = ";";
    public static final String SPACE = " ";

    // File Tags
    public static final String HEADERS = ",gender,age,bodyType,profession,pregnant,species,isPet";
    public static final String HUMAN_TAG = "human";
    public static final String ANIMAL_TAG = "animal";
    public static final String LOCATION_TAG = "location:";
    public static final String SCENARIO_TAG = "scenario:";
    public static final String DEFAULT_LOG_FILE = "rescuebot.log";

    /**
     * Prints out the default help message of the program.
     */
    public static void helpMessage() {
        System.out.println("RescueBot - COMP90041 - Final Project");
        System.out.println();
        System.out.println("Usage: java RescueBot [arguments]");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("-s or --scenarios\tOptional: path to scenario file");
        System.out.println("-h or --help\t\tOptional: Print Help (this message) and exit");
        System.out.println("-l or --log\t\tOptional: path to data log file");
        System.exit(0);
    }

    /**
     * This method is for getting the user's valid answer from the question given.
     * It prints out InvalidInputException() for any invalid answers.
     *
     * @param scanner the user's input
     * @param question the inquiry of the program
     * @return user's valid answer of the question
     */
    public static boolean validAnswer(Scanner scanner, String question) {
        while(true) {
            System.out.println(question);
            System.out.print(COMMAND_PROMPT);
            String answer = scanner.nextLine().toLowerCase();
            switch (answer) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    new InvalidInputException();
            }
        }
    }
}
