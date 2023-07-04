package utilities.parser;

import models.animals.Animal;
import models.enums.BodyType;
import models.enums.Gender;
import models.enums.Profession;
import models.humans.Adult;
import models.humans.Baby;
import models.humans.Child;
import models.humans.Senior;
import models.main.Character;
import models.main.*;
import utilities.exceptions.DirectoryNotExist;
import utilities.exceptions.InvalidCharacteristicException;
import utilities.exceptions.InvalidDataFormatException;
import utilities.exceptions.NumberFormatException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.util.Objects.nonNull;
import static models.enums.Gender.genderSet;
import static utilities.constant.Constants.*;

/**
 * A class for parsing and saving files
 * @author Laradell Tria
 */
public class FileParser {

    /**
     * This method is used for parsing the scenarios file to generate a scenario and
     * the list of locations found in each scenario.
     * @param scenariosFile the file where to read the scenarios.
     * @return List of scenarios parsed from the scenarios file
     */
    public static List<Scenario> readScenarios(String scenariosFile) throws FileNotFoundException {
        List<Scenario> scenarioList = new ArrayList<>();
        try {
            Scanner inputStream = new Scanner(new FileInputStream(scenariosFile));

            String line;
            inputStream.nextLine(); // skip the first line
            int rowIndex = 1;

            //Read the scenarios file
            Scenario scenario = null;
            Location location = null;

            while (inputStream.hasNextLine()) {
                rowIndex++;
                line = inputStream.nextLine();
                long noOfColumns = line.chars().filter(ch -> ch == ',').count();
                String[] cells = line.split(",");
                if (noOfColumns != 7) {
                    new InvalidDataFormatException(rowIndex);
                } else {
                    if (cells.length == 1) {
                        if (cells[0].startsWith(SCENARIO_TAG)) { // reads the scenario row;
                            if(nonNull(scenario)) {
                                scenarioList.add(scenario);
                                location = null;
                            }
                            String scenarioDescriptor = cells[0].replace(SCENARIO_TAG,"");
                            scenario = new Scenario(scenarioDescriptor);
                        } else if (cells[0].startsWith(LOCATION_TAG)) { // reads the location row
                            if (nonNull(scenario)) {
                                location = parseLocation(cells, rowIndex);
                                if (nonNull(location))
                                    scenario.addLocation(location);
                            }
                        } else { // invalid or corrupted row
                            new InvalidDataFormatException(rowIndex);
                            scenario = null;
                        }
                    } else {
                        // if character is valid (only human or animal)
                        if (cells[0].equals(HUMAN_TAG) || cells[0].equals(ANIMAL_TAG)){
                            // if location is valid
                            if (nonNull(location)){ // reads all character rows
                                Character character = parseCharacter(cells, rowIndex);
                                if (nonNull(character))
                                    location.addCharacter(character);
                            }
                        } else { // invalid or corrupted row
                            new InvalidDataFormatException(rowIndex);
                        }
                    }
                }
            }
            if(nonNull(scenario)) { // add the last scenario
                scenarioList.add(scenario);
            }
            //Close the file input stream
            inputStream.close();
        } catch (Exception e) {
            throw new FileNotFoundException("could not find scenarios file.");
        }
        return scenarioList;
    }

    /**
     * This method is used for parsing the details in the row and validation check
     * to create a location.
     * @param cells the array of cells in the row parsed from file
     * @param rowIndex row number from file
     * @return Newly created Location from the parsed file or null if InvalidDataFormat
     */
    private static Location parseLocation(String[] cells, int rowIndex) {
        Location location;
        cells[0] = cells[0].replace(LOCATION_TAG,"");
        String[] details = cells[0].split(";");

        Random random = new Random();
        DecimalFormat decfor = new DecimalFormat("0.0000");

        String[] splits;

        try { // validation check for latitude details
            splits = details[0].split(" ");
            if (Double.parseDouble(splits[0]) < 0.0) {
                new NumberFormatException(rowIndex);
                splits[0] = decfor.format(random.nextDouble() * 90);
            }
            if (!(splits[1].charAt(0) == 'N' || splits[1].charAt(0) == 'S')) {
                new InvalidCharacteristicException(rowIndex);
                if (random.nextDouble() > 0.5) {
                    splits[1] = "N";
                } else {
                    splits[1] = "S";
                }
            }
            details[0] = splits[0] + SPACE + splits[1]; // valid latitude details
        } catch (Exception e) {
            new NumberFormatException(rowIndex);
        }

        try { // validation check for longitude details
            splits = details[1].split(" ");
            if (Double.parseDouble(splits[0]) < 0.0) {
                new InvalidCharacteristicException(rowIndex);
                splits[0] = decfor.format(random.nextDouble() * 180);
            }
            if (!(splits[1].charAt(0) == 'W' || splits[1].charAt(0) == 'E')) {
                new InvalidCharacteristicException(rowIndex);
                if (random.nextDouble() > 0.5) {
                    splits[1] = "W";
                } else {
                    splits[1] = "E";
                }
            }
            details[1] = splits[0] + SPACE + splits[1]; // valid longitude details
        } catch (Exception e) {
            new NumberFormatException(rowIndex);
        }

        // validation check for trespassing
        if(!(details[2].equals("trespassing") || details[2].equals("legal"))) {
            new InvalidCharacteristicException(rowIndex);
            details[2] = "legal"; // default legal
        }

        if (details.length == 3) {
            location = new Location(
                    details[0], // latitude
                    details[1], // longitude
                    details[2]  // trespassing or not
            );
        } else {
            new InvalidDataFormatException(rowIndex);
            location = null;
        }
        return location;
    }

    /**
     * This method is used for parsing the details in the row and validation check
     * to create a character.
     * @param cells the array of cells in the row parsed from file
     * @param rowIndex row number from file
     * @return Newly created Character from the parsed file
     */
    private static Character parseCharacter(String[] cells, int rowIndex) {
        Boolean isHuman = null;
        Gender gender = Gender.UNKNOWN;
        int age = -1;
        BodyType bodyType = BodyType.UNSPECIFIED;
        Profession profession = Profession.NONE;
        boolean isPregnant = false;
        String species = EMPTY;
        boolean isPet = false;

        loop: for (int j = 0; j < cells.length; j++) {
            String detail = cells[j];
            switch (j) {
                case 0: // human or animal
                    if (detail.equals("human")) {
                        isHuman = true;
                    } else if (detail.equals("animal")) {
                        isHuman = false;
                    } else {
                        new InvalidCharacteristicException(rowIndex);
                        break loop;
                    }
                    break;
                case 1:  //
                    try {
                        gender = Gender.valueOf(detail.toUpperCase());
                    } catch (Exception e) {
                        new InvalidCharacteristicException(rowIndex);
                        gender = Gender.UNKNOWN;
                    }
                    break;
                case 2:
                    try {
                        age = Integer.parseInt(detail);
                        if (age < 0) {
                            new InvalidCharacteristicException(rowIndex);
                        }
                    } catch (Exception e) {
                        new NumberFormatException(rowIndex);
                    }
                    break;
                case 3:
                    try {
                        bodyType = BodyType.valueOf(detail.toUpperCase());
                    } catch (Exception e) {
                        new InvalidCharacteristicException(rowIndex);
                        bodyType = BodyType.UNSPECIFIED;
                    }
                    break;
                case 4:
                    try {
                        if (isHuman) {
                            profession = Profession.valueOf(detail.toUpperCase());
                            if (!(age >= 17 && age <= 68)) {
                                if(!profession.equals(Profession.NONE)) {
                                    new InvalidCharacteristicException(rowIndex);
                                    profession = Profession.NONE;
                                }
                            }
                        } else {
                            if(!detail.isEmpty()) {
                                new InvalidCharacteristicException(rowIndex);
                            }
                        }
                        if(age < 0) {
                            age = new Random().nextInt(52) + 17;
                        }
                    } catch (Exception e) {
                        new InvalidCharacteristicException(rowIndex);
                        profession = Profession.NONE;
                        age = new Random().nextInt(101);
                    }
                    break;
                case 5:
                    if (isHuman) {
                        isPregnant = Boolean.parseBoolean(detail);
                        if (!gender.equals(Gender.FEMALE) && isPregnant) {
                            new InvalidCharacteristicException(rowIndex);
                            isPregnant = false;
                        }
                        if (!(age >= 17 && age <= 68)) {
                            if(isPregnant) {
                                new InvalidCharacteristicException(rowIndex);
                                isPregnant = false;
                            }
                        }
                    } else {
                        if(!detail.isEmpty()) {
                            new InvalidCharacteristicException(rowIndex);
                        }
                    }
                    break;
                case 6:
                    if (!isHuman) {
                        species = detail;
                    }
                    break;
                case 7:
                    try {
                        if (!isHuman){
                            isPet = Boolean.parseBoolean(detail);
                        }
                    } catch (Exception e) {
                        new InvalidCharacteristicException(rowIndex);
                        isPet = false;
                    }
                    break;
                default:
            }
        }
        Character character;
        if (nonNull(isHuman)) {
            if(isHuman) {
                if(age >= 0 && age <= 4){
                    character = new Baby(age, gender, bodyType);
                } else if(age >= 5 && age <= 16) {
                    character = new Child(age, gender, bodyType);
                } else if(age >= 17 && age <= 68) {
                    character = new Adult(age, gender, bodyType, profession, isPregnant);
                } else {
                    character = new Senior(age, gender, bodyType);
                }
            } else {
                character = new Animal(age, gender, bodyType, species, isPet);
            }
            return character;
        } else {
            return null;
        }
    }

    /**
     * This method is used for save all the list of decisions from the statistics passed
     * into the logfile given.
     * @param logFile the audit log file given by user or default logfile which is used for saving
     * @param statistics all the details of the statistics (list of decisions and decided by user/algorithm)
     */
    public static void saveJudgedScenarios(String logFile, Statistics statistics) {
        try{
            FileWriter fileWriter = new FileWriter(logFile, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(HEADERS);
            for(Decision decision : statistics.getDecisions()) {
                // print first the scenario
                Scenario scenario = decision.getScenario();
                printWriter.println(SCENARIO_TAG + scenario.getDescriptor() + ",,,,,,,");
                int index = 1;
                for(Location location: scenario.getLocations()){
                    // print each location details
                    printWriter.print(LOCATION_TAG);
                    printWriter.print(location.getLatitude() + SPACE + location.getLatitudeDirection() + SEMICOLON);
                    printWriter.print(location.getLongitude() + SPACE + location.getLongitudeDirection() + SEMICOLON);
                    printWriter.print((location.getIsTrespassing() ? "trespassing" : "legal"));
                    if(index == decision.getChosenLocationIndex()) {
                        printWriter.print(SEMICOLON + (decision.isDecidedByUser() ? "user" : "algorithm") + " chosen");
                    }
                    printWriter.println(",,,,,,,");
                    // print each character found in a location
                    for(Character character: location.getCharacters()) {
                        boolean isHuman = true;
                        String gender = character.getGender();
                        int age = character.getAge();
                        String bodyType = character.getBodyType();
                        String profession = "none";
                        boolean isPregnant = false;
                        String species = EMPTY;
                        Boolean isPet = null;

                        String description = character.describe();
                        String[] attributes =  description.split(" ");
                        int i = 0;

                        while (i < attributes.length) {
                            if(attributes[i].startsWith("adult")) {
                                if(genderSet.contains(attributes[i+1])) {
                                    profession = "none";
                                } else {
                                    profession = attributes[++i];
                                }
                            }
                            if(attributes[i].startsWith("pregnant"))
                                isPregnant = true;
                            if(attributes[i].startsWith("pet")) {
                                isHuman = false;
                                isPet = true;
                                species = attributes[0];
                            }
                            i++;
                        }
                        if (attributes.length == 1) {
                            isHuman = false;
                            isPet = false;
                            species = attributes[0];
                        }
                        // writing the character details in the row
                        printWriter.print((isHuman ? "human" : "animal") + COMMA);
                        printWriter.print(gender + COMMA + age + COMMA + bodyType + COMMA);
                        if (isHuman) {
                            printWriter.print(profession + COMMA + isPregnant);
                            printWriter.println(DOUBLE_COMMA);
                        } else {
                            printWriter.print(DOUBLE_COMMA);
                            printWriter.println(species + COMMA + isPet);
                        }
                    }
                    index++;
                }
            }
            printWriter.println();
            printWriter.close();
            fileWriter.close();
        } catch(Exception e){
            new DirectoryNotExist();
        }
    }

    /**
     * This method is used for reading log file for the list of decisions
     * made from audit history.
     * @param logFile the audit log file given by user or default logfile
     * @return the list of decisions that were saved from the audit log
     */
    public static List<Decision> readAuditLogs(String logFile) {
        List<Decision> decisions = new ArrayList<>();
        try {
            if(logFile.isEmpty()) {
                logFile = DEFAULT_LOG_FILE;
            }
            Scanner inputStream = new Scanner(new FileInputStream(logFile));

            int rowIndex = 2;
            inputStream.nextLine(); // skip the first line

            //Read the scenarios file
            Scenario scenario = null;
            Location location = null;

            String decisionMaker = null;
            int chosenLocationIndex = 0;
            int locationIndex = 0;
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();

                if(line.isEmpty() || line.equals(HEADERS)) { // skips header lines and empty lines
                    continue;
                }
                long noOfColumns = line.chars().filter(ch -> ch == ',').count();
                String[] cells = line.split(",");
                if (noOfColumns != 7) { // if the row is not 8 columns
                    System.out.println(new InvalidDataFormatException(rowIndex));
                } else {
                    if (cells[0].startsWith(SCENARIO_TAG)) {
                        if (nonNull(scenario) && nonNull(decisionMaker)) {
                            decisions.add(new Decision(scenario, chosenLocationIndex, (decisionMaker.startsWith("user"))));
                        }
                        String scenarioDescriptor = cells[0].replace(SCENARIO_TAG, "");
                        scenario = new Scenario(scenarioDescriptor);
                        chosenLocationIndex = 0; locationIndex = 0;
                        decisionMaker = null;
                    } else if (cells[0].startsWith(LOCATION_TAG)) {
                        locationIndex++;
                        cells[0] = cells[0].replace(LOCATION_TAG, "");
                        String[] details = cells[0].split(";");
                        location = new Location(
                                details[0], // latitude
                                details[1], // longitude
                                details[2]  // trespassing or not
                        );
                        if (details.length > 3) {
                            chosenLocationIndex = locationIndex;
                            decisionMaker = details[3].split(" ")[0]; // gets who created the decision (user or algorithm)
                        }
                        if (nonNull(scenario)) {
                            scenario.addLocation(location);
                        }
                    } else {
                        if(nonNull(location))
                            location.addCharacter(parseCharacter(cells, rowIndex));
                    }
                }
                rowIndex++;
            }
            if(nonNull(scenario) && nonNull(decisionMaker)) { // add the last decision
                decisions.add(new Decision(scenario, chosenLocationIndex, (decisionMaker.startsWith("user"))));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No history found. Press enter to return to main menu.");
            System.out.print(COMMAND_PROMPT);
        }
        return decisions;
    }
}
