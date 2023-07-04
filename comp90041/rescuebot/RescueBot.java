import models.animals.Animal;
import models.humans.Adult;
import models.humans.Baby;
import models.humans.Child;
import models.humans.Senior;
import models.main.Character;
import models.main.*;
import utilities.constant.Constants;
import utilities.exceptions.InvalidInputException;
import utilities.random.RandomCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static java.util.Objects.nonNull;
import static models.enums.Descriptor.descriptors;
import static utilities.constant.Constants.*;
import static utilities.parser.FileParser.*;


/**
 * COMP90041, Sem1, 2023: Final Project <br>
 * Student ID: 1417478 <br>
 * Student Email: ltria@student.unimelb.edu.au
 * @author Laradell Tria
 */
public class RescueBot {

    /**
     * All files that will be used all throughout the program.
     */
    private static String logFile = EMPTY;
    private static String scenariosFile = EMPTY;

    /**
     * Decides which group will be saved based from the priority
     * of character attributes.
     *
     * @param scenario the ethical dilemma
     * @return the chosen location where the group of characters is saved
     */
    public static Location decide(Scenario scenario) {
        /*
        Priority 1: Baby and Child,
        Location is chosen with higher number of babies and children or has a baby or child

        Priority 2: Pregnant Woman
        Location is chosen with higher number of pregnant women or has any pregnant

        Priority 3: Pet Animals (Dog and Cat),
        Location is chosen with higher number of pets or has any pet

        Priority 4: Senior Citizens
        Location is chosen with higher number of seniors or has any senior

        Priority 5: Adults working in Hospital
        Location is chosen with higher number of doctors & nurses or has any of both

        If neither priorities have come up, it will depend on the most number of characters in the location.
         */

        Map<Integer, Set<String>> priorityAttributesMap = new HashMap<>();
        priorityAttributesMap.put(1, Set.of("baby", "child"));
        priorityAttributesMap.put(2, Set.of("pregnant"));
        priorityAttributesMap.put(3, Set.of("dog", "cat"));
        priorityAttributesMap.put(4, Set.of("senior"));
        priorityAttributesMap.put(5, Set.of("doctor", "nurse"));

        List<Location> locations = scenario.getLocations();

        for(int i = 1; i <= 5; i++) {
            Set<String> priorityAttributes = priorityAttributesMap.get(i);
            int decidedLocation = -1;
            int maxNoOfAttributes = 0;
            for(Location location: locations) {
                int noOfAttributes = 0;
                for(Character character : location.getCharacters()) {
                    String description = character.describe();
                    String[] attributes = description.split(" ");
                    for (String attribute : attributes) {
                        if (priorityAttributes.contains(attribute)){
                            noOfAttributes++;
                        }
                    }
                }
                if (noOfAttributes > 0 && noOfAttributes > maxNoOfAttributes) {
                    decidedLocation = locations.indexOf(location);
                    maxNoOfAttributes = noOfAttributes;
                }
            }

            if (decidedLocation > -1 && decidedLocation < locations.size()) {
                return locations.get(decidedLocation);
            }
        }

        int decidedLocation = -1;
        int maxNoOfCharacters = 0;
        for (Location location : locations) {
            int noOfCharacters = location.getCharacters().size();
            if(noOfCharacters > 0  && noOfCharacters > maxNoOfCharacters) {
                maxNoOfCharacters = noOfCharacters;
                decidedLocation = locations.indexOf(location);
            }
        }
        if (decidedLocation > -1 && decidedLocation < locations.size()) {
            return locations.get(decidedLocation);
        }
        return locations.get(new Random().nextInt(locations.size()) + 1);
    }

    /**
     * Program entry
     * @param args The command line arguments.
     * @throws java.io.IOException when we can't read a file.
     */
    public static void main(String[] args) throws IOException {
        argumentChecker(args);

        // print the welcome message
        Scanner scanner = new Scanner(new FileInputStream("welcome.ascii"));
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        String command = "";
        Scanner keyboard = new Scanner(System.in);
        List<Scenario> scenarioList;
        boolean isRandomlyGenerated = false;
        String runSimulationFile = scenariosFile;

        // initialise scenario list
        if (!scenariosFile.isBlank()) {
            scenarioList = readScenarios(scenariosFile);
            System.out.println(scenarioList.size() + " scenarios imported.");
        } else {
            scenarioList = randomGenerator(keyboard, false);
            isRandomlyGenerated = true;
        }

        if (logFile.isEmpty()) {
            logFile = DEFAULT_LOG_FILE;
        }

        while(!(command.equals("quit") || command.equals("q"))) {

            System.out.println("Please enter one of the following commands to continue:\n" +
                    "- judge scenarios: [judge] or [j]\n" +
                    "- run simulations with the in-built decision algorithm: [run] or [r]\n" +
                    "- show audit from history: [audit] or [a]\n"+
                    "- quit the program: [quit] or [q]");
            System.out.print(COMMAND_PROMPT);

            command = keyboard.nextLine().toLowerCase();
            switch (command) {
                case "j":
                case "judge":
                    String message = "Do you consent to have your decisions saved to a file? (yes/no)";
                    boolean isSavedToLog = validAnswer(keyboard, message); // consent to save decisions
                    int noOfScenarios = 0;
                    Statistics statistics = new Statistics(true); // used for the decision statistics

                    loop: while(true) {
                        for(Scenario scenario: scenarioList) {
                            noOfScenarios++;
                            scenario.display();
                            boolean isValid =  false;
                            while(!isValid) { // choosing valid location
                                System.out.println("To which location should RescueBot be deployed?");
                                System.out.print(COMMAND_PROMPT);
                                int option = Integer.parseInt(keyboard.nextLine());
                                if (option >= 1 && option <= scenario.getNoOfLocations()) {
                                    statistics.addDecision(new Decision(scenario, option,true));
                                    isValid = true;
                                } else {
                                    new InvalidInputException();
                                }
                            }
                            // after group of 3 scenarios judged, it will prompt to show the current statistics
                            if (noOfScenarios % 3 == 0) {
                                statistics.displayStatistics();
                                message = "Would you like to continue? (yes/no)";
                                if(!validAnswer(keyboard, message)) {
                                    break loop;
                                }
                                
                            }
                        }
                        if(isRandomlyGenerated) {
                            scenarioList = randomGenerator(keyboard, false);
                        } else {
                            break;
                        }
                    }
                    // display statistics after all scenarios (if not yet printed during loop)
                    if(noOfScenarios % 3 != 0) {
                        statistics.displayStatistics();
                    }
                    // has consented to save the decisions
                    if(isSavedToLog) {
                        saveJudgedScenarios(logFile, statistics);
                    }
                    System.out.println("That's all. Press Enter to return to main menu.");
                    System.out.print(COMMAND_PROMPT);
                    keyboard.nextLine();
                    break;
                case "r":
                case "run":
                    // check if no scenarios file (file for run simulation), create randomly generated
                    List<Scenario> runSimulationList;
                    if(runSimulationFile.isBlank())
                        runSimulationList = randomGenerator(keyboard, true);
                    else runSimulationList = scenarioList;

                    Statistics algorithmStatistics = new Statistics(false); // used for the algorithm statistics
                    for(Scenario scenario: runSimulationList) {
                        int index = scenario.getLocations().indexOf(decide(scenario));
                        algorithmStatistics.addDecision(new Decision(
                                scenario,
                                index + 1,
                                false
                        ));
                    }
                    // after running the simulation, display the stats
                    algorithmStatistics.displayStatistics();
                    runSimulationFile = EMPTY; // emptying up judged scenarios

                    // save algorithm decisions to file
                    saveJudgedScenarios(logFile, algorithmStatistics);
                    System.out.println("That's all. Press Enter to return to main menu.");
                    keyboard.nextLine();
                    break;
                case "a":
                case "audit":
                    List<Decision> readDecisions = readAuditLogs(logFile); // read audit logs
                    if(readDecisions.size() == 0) {
                        keyboard.nextLine();
                        break;
                    }
                    Statistics userAudit = new Statistics(true);
                    Statistics algorithmAudit = new Statistics(false);
                    // separating decisions by user and algorithm
                    for(Decision decision: readDecisions) {
                        if (decision.isDecidedByUser())
                            userAudit.addDecision(decision);
                        else algorithmAudit.addDecision(decision);
                    }

                    if (algorithmAudit.getDecisions().size() != 0) {
                        algorithmAudit.auditStatistics();
                        System.out.println();
                    }
                    if (userAudit.getDecisions().size() != 0) {
                        userAudit.auditStatistics();
                    }

                    System.out.println("That's all. Press Enter to return to main menu.");
                    keyboard.nextLine();
                    break;
                case "q":
                case "quit":
                    break;
                default:
                    System.out.print("Invalid command! ");
            }
        }
    }

    /**
     * This method serves as the initial argument checker for all the
     * passed arguments from the start of the program. No returns needed,
     * but if there are any scenario file and log file, it will be appended
     * to the static variables, which is used for the whole program.
     *
     * @param  args all the arguments added in the start of the program
     */
    private static void argumentChecker(String[] args) {
        int i = 0;
        String arg;
        boolean sFlag = false;
        boolean lFlag = false;

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            // use this to check help arguments
            if (arg.equals("-h") || arg.equals("--help")) {
                Constants.helpMessage();
            }

            // use this to check log arguments
            else if (arg.equals("-l") || arg.equals("--log")) {
                if (i < args.length && !lFlag) {
                    logFile = args[i++];
                    lFlag = true;
                }
                else
                    Constants.helpMessage();
            }

            // use this to check scenario arguments
            else if (arg.equals("-s") || arg.equals("--scenarios")) {
                if (i < args.length && !sFlag) {
                    scenariosFile = args[i++];
                    sFlag = true;
                } else
                    Constants.helpMessage();
                File file = new File(scenariosFile);
                if (!file.exists()) {
                    System.err.println("java.io.FileNotFoundException: could not find scenarios file.");
                    Constants.helpMessage();
                }
            } else {
                Constants.helpMessage();
            }
        }
    }

    /**
     * Returns the randomly generated scenarios.
     * This method creates random valid scenarios which will be used for
     * the decision program. It starts by asking the number of scenarios (if it is
     * from running simulation). Then, all information will be randomly generated
     * and saved as a scenario.
     *
     * @param  keyboard system input for the number of scenarios that the user prompts
     * @param  isFromSimulation if this method was called from running a simulation or from the start of program
     * @return List of Scenarios the list of randomly generated scenarios
     */
    private static List<Scenario> randomGenerator(Scanner keyboard, boolean isFromSimulation) {
        int noOfScenarios;
        List<Scenario> scenarioList = new ArrayList<>();
        Random random = new Random();

        if (isFromSimulation) {
            while (true) {
                System.out.println("How many scenarios should be run?");
                System.out.print(COMMAND_PROMPT);
                try {
                    noOfScenarios = Integer.parseInt(keyboard.nextLine());
                    if(noOfScenarios > 0){
                        break;
                    }
                } catch (Exception e) {
                    System.out.print("Invalid input! ");
                }
            }
        } else {
            noOfScenarios = 3;
        }


        RandomCollection<String> randomCollection = new RandomCollection<>();
        randomCollection.add(80, "human");
        randomCollection.add(20, "animal");

        RandomCollection<String> humanCollection = new RandomCollection<>();
        humanCollection.add(15, "baby");
        humanCollection.add(15, "child");
        humanCollection.add(55, "adult");
        humanCollection.add(15, "senior");

        DecimalFormat decfor = new DecimalFormat("0.0000");

        int size = descriptors.length;
        for(int i = 0; i < noOfScenarios; i++){
            String randomDescriptor = descriptors[random.nextInt(size)].getDisplayName();

            Scenario scenario = new Scenario(randomDescriptor);
            int noOfLocations = random.nextInt(10) + 2;
            for (int j = 0; j < noOfLocations; j++) {

                double latitude = Double.parseDouble(decfor.format(random.nextDouble() * 90));
                char latitudeDirection = (random.nextDouble() > 0.5 ? 'N' : 'S');
                double longitude = Double.parseDouble(decfor.format(random.nextDouble() * 180));
                char longitudeDirection = (random.nextDouble() > 0.5 ? 'W' : 'E');
                boolean isTrespassing = (random.nextDouble() > 0.5);

                Location location = new Location(
                        latitude + SPACE + latitudeDirection,
                        longitude + SPACE + longitudeDirection,
                        Objects.toString(isTrespassing)
                );

                int noOfCharacters = random.nextInt(20) + 2;
                Character character = null;
                switch(humanCollection.pick()) {
                    case "baby":
                        character = new Baby();
                        break;
                    case "child":
                        character = new Child();
                        break;
                    case "adult":
                        character = new Adult();
                        break;
                    case "senior":
                        character = new Senior();
                        break;
                    default:
                }
                if(nonNull(character)){
                    character.randomize();
                    location.addCharacter(character);
                }

                character = new Animal();
                character.randomize();
                location.addCharacter(character);

                for (int k = 2; k < noOfCharacters; k++) {
                    if(randomCollection.pick().equals("human")) {
                        switch(humanCollection.pick()) {
                            case "baby":
                                character = new Baby();
                                break;
                            case "child":
                                character = new Child();
                                break;
                            case "adult":
                                character = new Adult();
                                break;
                            case "senior":
                                character = new Senior();
                                break;
                            default:
                        }
                    } else {
                        character = new Animal();
                    }
                    character.randomize();
                    location.addCharacter(character);
                }
                scenario.addLocation(location);
            }
            scenarioList.add(scenario);
        }
        return scenarioList;
    }
}
