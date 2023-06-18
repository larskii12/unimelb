import java.util.Scanner;
/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 *
 * This class contains the attributes of a Triangle (sideLength, triangle printing 
 * character, x-position, y-position, and rotation angle). Also, it contains the
 * constructor for initializing triangle, methods that the triangle can do (zoom, 
 * move, and rotate), mutators, and accessors that composes the triangle.
 */
public class Triangle {

	// Rotation Angle Constants:
	public static final int NINETY_DEGREES = 90;
	public static final int FULL_ROTAION_ANGLE = 360;

	private int sideLength; 
	private char printingChar; // printing character
	private int xPos; // x position of the top leftmost character of triangle
	private int yPos; // y position of the top leftmost character of triangle
	private int rotationAngle; // angle of the triangle

	// Constructor 
	public Triangle() {
		xPos = 0; // x position starts always at the top leftmost of the drawing canvas
		yPos = 0; // y position starts always at the top leftmost of the drawing canvas
		rotationAngle = 0; // rotation angle will always start in the 0 degree angle
	}

	// Getters
	public int getSideLength() {
		return sideLength;
	}

	public char getPrintingChar() {
		return printingChar;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getRotationAngle() {
		return rotationAngle;
	}

	// Setters
	public void setSideLength(int sideLength) {
		this.sideLength = sideLength;
	}

	public void setPrintingChar(char printingChar) {
		this.printingChar = printingChar;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	// Methods
	/**
	 * This method validates if the given side length doesn't exceed the dimensions of 
	 * the canvas. canvasHeight - yPos) & (canvasWidth - xPos) are used as reference
	 * because we should also consider that the current triangle may have been located 
	 * in a different position of the canvas.
	 */
	public boolean isValidSideLength(int sideLength, int canvasHeight, int canvasWidth) {
		if((sideLength > (canvasHeight - yPos)|| sideLength > (canvasWidth - xPos)) || sideLength < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method validates if the given x position doesn't exceed the canvas width 
	 * before moving and sets the new position if valid. (xPos + sideLength) is used
	 * because we have to consider that the drawn triangle will also be within the 
	 * bounds of the canvas width.
	 */
	private void moveXPos(int xPos, int canvasWidth) {
		if((xPos + sideLength) > canvasWidth || xPos < 0) {
			System.out.println("You cannot move this triangle outside of the drawing canvas!");
		} else {
			setXPos(xPos);
		}
	}

	/**
	 * This method validates if the given y position doesn't exceed the canvas height 
	 * before moving and sets the new position if valid. (yPos + sideLength) is used 
	 * because we have to consider that the drawn triangle will also be within the
	 * bounds of the canvas height.
	 */
	private void moveYPos(int yPos, int canvasHeight) {
		// if the y position is within the bounds of the canvas height
		if((yPos + sideLength) > canvasHeight || yPos < 0) {
			System.out.println("You cannot move this triangle outside of the drawing canvas!");
		} else {
			setYPos(yPos);
		}
	}

	/**
	 * This method is used for the zooming feature of the Triangle.
	 */
	public void zoom(Scanner scanner, DrawingCanvas drawingCanvas) {

		boolean isExit = false; // isExit is used for the while loop if the user is still in the zoom menu
		drawingCanvas.showCanvas(); // draw first the triangle before zooming

		while(!isExit) { // while still inside zoom triangle menu
			System.out.println("Type I/O to zoom in/out. Use other keys to go back to the Zooming/Moving/Rotating menu.");
			char zoomLevel = scanner.nextLine().toLowerCase().charAt(0); // getting the lowercase version of the first character in the input

			if(zoomLevel == 'i') { // zoom in condition
				if(isValidSideLength((sideLength+1), drawingCanvas.getHeight(), drawingCanvas.getWidth())) {
					sideLength++;
				} else {
					System.out.println("This triangle reaches its limit. You cannot make it bigger!");
				}
			} else if (zoomLevel == 'o') { // zoom out condition
				if(isValidSideLength((sideLength-1), drawingCanvas.getHeight(), drawingCanvas.getWidth())) {
					sideLength--;
				} else {
					System.out.println("This triangle reaches its limit. You cannot make it smaller!");
				}
			} else {
				isExit = true; // exits the zoom menu
			}
			drawingCanvas.showCanvas(); // prints out the triangle with the new changes on the side length
		}
	}

	/**
	 * This method is used for the moving feature of the Triangle.
	 */
	public void move(Scanner scanner, DrawingCanvas drawingCanvas) {

		boolean isExit = false; // isExit is used for the while loop if the user is still in the move menu
		drawingCanvas.showCanvas(); // draw first the triangle before moving

		while(!isExit) { // while still inside move triangle menu
			System.out.println("Type A/S/W/Z to move left/right/up/down. Use other keys to go back to the Zooming/Moving/Rotating menu.");
			char direction = scanner.nextLine().toLowerCase().charAt(0); // getting the lowercase version of the first character in the input
			switch (direction) {
				case 'a': // change in the x position to the left
					moveXPos((xPos - 1), drawingCanvas.getWidth());
					break;
				case 's': // change in the x position to the right
					moveXPos((xPos + 1), drawingCanvas.getWidth());
					break;
				case 'w': // change in the y position upward
					moveYPos((yPos - 1), drawingCanvas.getHeight());
					break;
				case 'z': // change in the y position downward
					moveYPos((yPos + 1), drawingCanvas.getHeight());
					break;
				default: // exits the move menu
					isExit = true;
					break;
			}
			drawingCanvas.showCanvas(); // prints out the triangle with the new changes on its position
		}
	}

	/**
	 * This method is used for the rotating feature of the Triangle.
	 */
	public void rotate(Scanner scanner, DrawingCanvas drawingCanvas) {

		boolean isExit = false; // isExit is used for the while loop if the user is still in the rotate menu
		drawingCanvas.showCanvas(); // draw first the triangle before rotating

		while(!isExit) { // while still inside rotate triangle menu
			System.out.println("Type R/L to rotate clockwise/anti-clockwise. Use other keys to go back to the Zooming/Moving/Rotating menu.");
			char direction = scanner.nextLine().toLowerCase().charAt(0); // getting the lowercase version of the first character in the input
			switch (direction) {
				case 'r': // rotates the triangle clockwise by 90 degrees
					rotationAngle += NINETY_DEGREES;
					break;
				case 'l': // rotates the triangle anti-clockwise by 90 degrees
					rotationAngle -= NINETY_DEGREES;
					break;
				default: // exits rotate menu
					isExit = true;
					break;
			}
			if (rotationAngle == FULL_ROTAION_ANGLE) { // if rotation angle reaches 360 degrees, angle returns back to 0
				rotationAngle = 0;
			} else if (rotationAngle < 0) { // if rotation angle is negative, angle changes to its positive version
				rotationAngle = FULL_ROTAION_ANGLE + rotationAngle;
			}
			drawingCanvas.showCanvas(); // prints out the triangle with the new changes on its angle
		}
	}

	/**
	 * This method serves as the Edit Main Menu for ZOOM, MOVE, or ROTATE triangle.
	 * 1.) If the user selects zoom(z) triangle, it will redirect to the zoom menu
	 * 2.) If the user selects move(m) triangle, it will redirect to the move menu
	 * 3.) If the user selects rotate(r) triangle, it will redirect to the rotate menu
	 * 4.) If the user inputs any other charcter, it will quit the menu
	 */
	public void editMainMenu(Scanner scanner, DrawingCanvas drawingCanvas) {
		boolean quitMenu = false; // boolean to stop the menu
		while(!quitMenu) { // while user is still in the menu
			System.out.println("Type Z/M/R for zooming/moving/rotating. Use other keys to quit the Zooming/Moving/Rotating mode.");
			char option = scanner.nextLine().toLowerCase().charAt(0); // option is for user's choice in the zooming or moving menu
			if(option == 'z') {
				zoom(scanner, drawingCanvas);
			} else if (option == 'm') {
				move(scanner, drawingCanvas);
			} else if (option == 'r') {
				rotate(scanner, drawingCanvas);
			} else {
				quitMenu = true;
			}
		}
	}
}
