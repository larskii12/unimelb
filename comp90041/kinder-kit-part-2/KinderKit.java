import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 */
public class KinderKit {

	// Constants
	// Main Menu Constants:
	public static final int CHALLENGE_MODE = 1;
	public static final int FREESTYLE_MODE = 2;
	public static final int EXIT_MAIN_MENU = 3;

	// Challenge Mode Constants:
	public static final int PREVIEW_DRAWING = 1;
	public static final int START_DRAWING_CHALLENGE = 2;
	public static final int CHECK_RESULT = 3;
	public static final int EXIT_CHALLENGE_MODE = 4;

	// Freestyle Mode Constants:
	public static final int START_FREESTYLE_DRAWING = 1;
	public static final int SHARE_DRAWING = 2;
	public static final int EXIT_FREESTYLE_MODE = 3;

	public static void main(String[] args) {
	//DON'T TOUCH LINES 8 to 59
	//Check program arguments
	if (args.length != 1) {
		System.out.println("This program takes exactly one argument!");
		return;
	}

	//Read sample drawing from file
	Scanner inputStream = null;
	char[][] bitmap = null;
	int rows = 0, cols = 0;
	char bgChar;

	try {
		inputStream = new Scanner(new FileInputStream(args[0]));
		String line;
 
		//Read the first line
	  	if (inputStream.hasNextLine()) {
			line = inputStream.nextLine();
			String[] tmps = line.split(",");
			if (tmps.length != 3) {
				System.out.println("The given file is in wrong format!");
				return;
			} else {
				rows = Integer.parseInt(tmps[0]);
				cols = Integer.parseInt(tmps[1]);
				bgChar = tmps[2].charAt(0);
				bitmap = new char[rows][cols];
			}
		} else {
		System.out.println("The given file seems empty!");
		return;
		}

		//Read the remaining lines
		int rowIndex = 0;
		while (inputStream.hasNextLine()) {
			line = inputStream.nextLine();
			String[] tmps = line.split(",");
			for(int i = 0; i < tmps.length; i++) {
				bitmap[rowIndex][i] = tmps[i].charAt(0);
			}
			rowIndex++;
		}
		//Close the file input stream
		inputStream.close();
	} catch (FileNotFoundException e) {
		System.out.println("The given file is not found!");
		return;
	}

	/**
	 *  KINDER KIT APPLICATION: MY CODING AREA
	 *
	 *  This part is the starting main menu of the digital kinder kit. We start by
	 *  initializing our drawing canvas and drawing task from the input file. This
	 *  drawing task serves our pre-defined object that we will use in our challenge
	 *  mode. 
	 *
	 *  After initializing, the user can choose from the 3 inputs where:
	 *  1 -> Challenge Mode
	 *  2 -> Freestyle Mode
	 *  3 -> Exit
	 *  Any invalid inputs will prompt an error message. Each inputs will have their
	 *  own sub-menus except the exit input. 
	 *
	 *  Challenge Mode:
	 *  For this mode, we will initialize first a new drawing canvas for the user to
	 *  use in the the drawing part. The user will be able to choose from the 4 
	 *  options where:
	 *  1 -> Preview the Sample Drawing (shows the pre-defined drawing the user needs 
	 *  to follow)
	 *  2 -> Start or Edit the Current Drawing Canvas (draw the new canvas)
	 *  3 -> Check Result (compare the sample drawing and the new drawing)
	 *  4 -> Go Back to the Main Menu (goes back to the main menu)
	 *  Any invalid options will prompt an error message. Each options will be 
	 *  redirected to their methods they are called.
	 *
	 *  Freestyle Mode:
	 *  For this mode, user has the freedom to draw anything they want. First, we will 
	 *  ask the canvas details that the user wants on their drawing canvas and 
	 *  initialize them new drawing canvas for the user to use in the drawing part.
	 *  The user will be able to choose from the 3 options where:
	 *  1 -> Start or Edit the Current Drawing Canvas (draw the new canvas)
	 *  2 -> Share the Current Drawing (user can share their drawing with other people 
	 *  for them to challenge)
	 *  3 -> Go Back to the Main Menu (goes back to the main menu)
	 *  Any invalid options will prompt an error message as wel. Each options will be 
	 *  redirected to their methods they are called.
	 */

	// Initialize drawing canvas and drawing task (for challenge mode)
	DrawingCanvas sampleDrawingCanvas = new DrawingCanvas(cols, rows, bgChar);
	DrawingTask drawingTask = new DrawingTask(sampleDrawingCanvas, bitmap);

	Scanner scanner = new Scanner(System.in); // used for scanning input for menu
	int input = 0; // initializing input to 0 for the loop

	System.out.println("----DIGITAL KINDER KIT: LET'S PLAY & LEARN----");
	while (input != EXIT_MAIN_MENU) { // while user doesn't want to exit the menu
		System.out.println("Please select an option. Type 3 to exit.");
		System.out.println("1. Draw a predefined object");
		System.out.println("2. Freestyle Drawing");
		System.out.println("3. Exit");

		DrawingCanvas newDrawingCanvas; // create a drawing canvas
		input = Integer.parseInt(scanner.nextLine());
		switch (input) {
			case CHALLENGE_MODE:
				newDrawingCanvas = new DrawingCanvas(cols, rows, bgChar); // we initialize the new drawing canvas similar with sample drawing
				int option = 0; // initializing option to 0
				while (option != EXIT_CHALLENGE_MODE) {
					System.out.println("Please select an option. Type 4 to go back to the main menu.");
					System.out.println("1. Preview the sample drawing");
					System.out.println("2. Start/edit the current canvas");
					System.out.println("3. Check result");
					System.out.println("4. Go back to the main menu");

					option = Integer.parseInt(scanner.nextLine());
					switch (option) {
						case PREVIEW_DRAWING: 
							System.out.println("This is your task. Just try to draw the same object. Enjoy!");
							drawingTask.previewCanvas(); // redirects to the previewCanvas method in drawing task
							break;
						case START_DRAWING_CHALLENGE: 
							newDrawingCanvas.mainMenu(scanner); // redirects to the main menu of the drawing canvas
							break;
						case CHECK_RESULT: 
							drawingTask.compareCanvas(newDrawingCanvas); // redirects to the compareCanvas method in drawing task
							break;
						case EXIT_CHALLENGE_MODE: 
							break;
						default: // Invalid Option
							System.out.println("Unsupported option. Please try again.");
					}
				}
				break;
			case FREESTYLE_MODE: 
				System.out.print("Canvas width: ");
				int width = Integer.parseInt(scanner.nextLine()); // getting width
				System.out.print("Canvas height: ");
				int height = Integer.parseInt(scanner.nextLine()); // getting height
				System.out.print("Background character: ");
				char backgroundChar = scanner.nextLine().charAt(0); // getting the background character
				newDrawingCanvas = new DrawingCanvas(width, height, backgroundChar);

				option = 0; // initializing option to 0
				while(option != EXIT_FREESTYLE_MODE) {
					System.out.println("Please select an option. Type 3 to go back to the main menu.");
					System.out.println("1. Start/edit your current canvas");
					System.out.println("2. Share your current drawing");
					System.out.println("3. Go back to the main menu");

					option = Integer.parseInt(scanner.nextLine());
					switch (option) {
						case START_FREESTYLE_DRAWING: 
							newDrawingCanvas.mainMenu(scanner); // redirects to the main menu of the drawing canvas
							break;
						case SHARE_DRAWING:
							newDrawingCanvas.shareCanvas(); // calls the shareCanvas method in newDrawingCanvas to share drawing
							break;
						case EXIT_FREESTYLE_MODE: 
							break;
						default: // Invalid Option
							System.out.println("Unsupported option. Please try again.");
					}
				}
				break;
			case EXIT_MAIN_MENU: // Quit Digital Kinder Kit
				System.out.println("Goodbye! We hope you had fun :)");
				scanner.close(); // before terminating the system, we close the scanner
				break;
			default: // Invalid Option
				System.out.println("Unsupported option. Please try again!");
		}
	}
	}
}
