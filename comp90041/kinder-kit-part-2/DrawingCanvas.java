import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.isNull;
/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 *
 * This class contains the attributes of a drawing canvas (width, height, background 
 * character, and the list of triangles found in the canvas). Also, it contains the
 * constructor for initializing drawing canvas, mutators, accessors, and methods that 
 * the drawing canvas have (drawCanvas, showCanvas, editRemoveMenu, mainMenu).
 */
public class DrawingCanvas {
	// Main Menu Constants:
	public static final int ADD_TRIANGLE = 1;
	public static final int EDIT_TRIANGLE = 2;
	public static final int REMOVE_TRIANGLE = 3;
	public static final int EXIT_DRAWING_CANVAS_MENU = 4;

	// Rotation Angle Constants:
	public static final int NINETY_ANGLE = 90;
	public static final int ONE_HUNDRED_EIGHTY_ANGLE = 180;
	public static final int TWO_HUNDRED_SEVENTY_ANGLE = 270;

	// Instance variables for drawing canvas
	private int width;
	private int height;
	private char backgroundChar; // background character
	private List<Triangle> triangleList; // stores the list of triangles in the canvas

	// Constructor
	public DrawingCanvas(int width, int height, char backgroundChar) {
		setWidth(width);
		setHeight(height);
		setBackgroundChar(backgroundChar);
		this.triangleList = new ArrayList<Triangle>(); // initializing the triangleList to create a new ArrayList of type Triangle
	}

	// Getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public char getBackgroundChar() {
		return backgroundChar;
	}

	public List<Triangle> getTriangleList() {
		return triangleList;
	}

	// Setters
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setBackgroundChar(char backgroundChar) {
		this.backgroundChar = backgroundChar;
	}

	// Methods
	/**
	 * This method is used for drawing the canvas. It will compose of 2 parts:
	 * printing out the plain canvas and adding each triangles in the canvas.
	 * 
	 * 1.) Printing out the Plain Canvas -> Before we start printing the triangles, we
	 * start to initialize the canvas matrix with only the background character.
	 *
	 * 2.) Adding each Triangles in the Canvas -> After making the plain canvas, we
	 * will loop through the triangleList. Each triangle will be printed in the canvas.
	 * The order of the triangle placed will be from the oldest to the latest triangle 
	 * (where the later triangle is on top of the older one). 
	 *
	 * In each triangle, sideLength, xPos, yPos, rotationAngle, and printingChar are
	 * the variables of the triangle that is assigned ahead to prevent continuous 
	 * calling in the loop. 
	 * 
	 * To draw the triangle, there will be different ways of printing the triangle 
	 * according to its rotation angle. The following angles and their implementation:
	 *
	 * 1.) rotationAngle = 0 -> Default angle of Triangle. It will print out from the 
	 * top left corner of the bounding square. The sideLengthCtr will start from the 
	 * value of the triangle's sideLength and it will decrement as it goes in the next
	 * rows. If the current cell (var j) is between the xPos and the sideLengthCtr + 
	 * xPos, it will display the printingChar [left-part of the bounding square].
	 * ex: * * *
	 *     * * -
	 *	   * - -
	 *
	 * 2.) rotationAngle = 90 -> It will print out from the top right corner of the
	 * bounding square. The sideLengthCtr will start from 1 instead and it will 
	 * increment as it goes in the next rows. The printing char will only be printed if
	 * the current cell (var j) is between the xPos + (sideLengthCtr-1) and the 
	 * sideLengthCtr + xPos. 
	 * ex: * * *
	 *     - * *
	 *	   - - *
	 *
	 * 3.) rotationAngle = 180 -> It will print out from the lower right corner of the
	 * bounding square. The sideLengthCtr will start from triangle's sideLength and it 
	 * will increment as it goes in the next rows. The printing char will only be 
	 * printed if the current cell (var j) is between the xPos + (sideLengthCtr-1) and 
	 * the sideLengthCtr + xPos [right-part of the bounding square].
	 * ex: - - *
	 *     - * *
	 *	   * * *
	 * 
	 * 4.) rotationAngle = 270 -> It will print out from the lower left corner of the
	 * bounding square. The sideLengthCtr will start from 1 instead and it will 
	 * increment as it goes in the next rows. If the current cell (var j) is between 
	 * the xPos and the sideLengthCtr + xPos, it will display the printingChar
	 * [left-part of the bounding square].
	 * ex: * - -
	 *     * * -
	 *	   * * *
	 *
	 * All of these triangles needs to be printed only if current cell (var i) is in 
	 * between the yPos and the sideLengthCtr + yPos. This method will return the 
	 * canvas matrix of all printed triangles.
	 */
	public char[][] drawCanvas() {

		char[][] canvas = new char[height][width];
		for(int i = 0; i < height; i++) { // start with plain canvas
			for(int j = 0; j < width; j++){
				canvas[i][j] = backgroundChar;
			}
		}

		for(Triangle triangle: triangleList) { // add the triangles
			int sideLength = triangle.getSideLength();
			int xPos = triangle.getXPos();
			int yPos = triangle.getYPos();
			int rotationAngle = triangle.getRotationAngle();
			char printingChar = triangle.getPrintingChar();
			int sideLengthCtr; // counter used for creating triangle

			if (rotationAngle == NINETY_ANGLE || rotationAngle == TWO_HUNDRED_SEVENTY_ANGLE) {
				sideLengthCtr = 1;
			} else {
				sideLengthCtr = triangle.getSideLength(); 
			}

			for (int i = 0; i < height; i++) { // for traversing the columns of the canvas
				for (int j = 0; j < width; j++) { // for traversing the rows of the canvas
					if (rotationAngle == NINETY_ANGLE || rotationAngle == ONE_HUNDRED_EIGHTY_ANGLE) {
						// if the current cell (var j) is within the triangle's right side drawing area, it will display the printingChar
						if ((j >= xPos + (sideLengthCtr-1) && j < xPos + sideLength) && (i >= yPos && i < sideLength + yPos)) {
							canvas[i][j] = printingChar;
						}
					} else {
						// if the current cell (var j) is within the triangle's left side drawing area, it will display the printingChar
						if ((j >= xPos && j < sideLengthCtr + xPos) && (i >= yPos && i < sideLength + yPos)) {
							canvas[i][j] = printingChar;
						}
					}
				}

				// we can only change the sideLengthCtr if we started our printing the triangle (within the bounds of the yPos and sideLength + yPos)
				if (i >= yPos && i < sideLength + yPos) {
					// if the angle is 90 or 270, it will increment sideLengthCtr since we started the sideLengthCtr with 1
					if (rotationAngle == NINETY_ANGLE || rotationAngle == TWO_HUNDRED_SEVENTY_ANGLE) { 
						sideLengthCtr++;
					} else { // decrements the sideLengthCtr in other angles
						sideLengthCtr--;
					}
				}
			}
		}
		return canvas;
	}

	/*
	 * These methods are the public methods that were called from other classes.
	 * Both methods will call the showCanvas(boolean isShareCanvas) with different
	 * conditions.
	 */
	public void showCanvas() {
		showCanvas(false);
	}

	public void shareCanvas() {
		showCanvas(true);
	}

	/*
	 * This method shows the current canvas. It accepts a boolean isShareCanvas 
	 * argument which includes to share the drawing of the user canvas made from
	 * freestyle mode.
	 */
	private void showCanvas(boolean isShareCanvas) {
		char[][] canvas = drawCanvas();
		if(isShareCanvas) {
			System.out.println("This is the detail of your current drawing");
			System.out.println(height + "," + width + "," + backgroundChar);
		}

		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++){
				System.out.print(canvas[i][j]);
				// if it's not the last character in the row and it's called from share canvas, comma will be included after the character
				if (j != width-1 && isShareCanvas) { 
					System.out.print(",");
				}
			}
			System.out.println();
		}
	}

	/**
	 * This method serves as the Drawing Canvas Main Menu. This contains 4 options: 
	 * add a new triangle, edit a triangle, remove a triangle, and exit the menu
	 *
	 * 1.) Add a new Triangle - The user starts with inputting valid side length and 
	 * printing character of the triangle. After triangle was created, the user can 
	 * directly edit the newly added triangle by zoom, move, or rotate by redirecting
	 * to the zoomMoveRotateMainMenu.
	 * 2.) Edit a Triangle - The user chooses a triangle to edit from the list of
	 * triangles in the canvas. From the chosen triangle, the user can edit the
	 * triangle by zoom, move, or rotate by redirecting to the zoomMoveRotateMainMenu.
	 * 3.) Remove a Triangle - The user chooses a triangle to remove from the list of 
	 * triangles in the canvas. After the triangle is being removed, a new canvas
	 * without the removed triangle will show.
	 * 4.) Exit Drawing Canvas Menu - exits the menu and goes back to the respective 
	 * menus.
	 *
	 */
	public void mainMenu(Scanner scanner) {
		boolean stop = false; // boolean to stop drawing canvas menu
		while(!stop) { // while user is still in the drawing canvas menu
			showCanvas(); // shows the current drawing canvas

			System.out.println("Please select an option. Type 4 to go back to the previous menu.");
			System.out.println("1. Add a new Triangle");
			System.out.println("2. Edit a triangle");
			System.out.println("3. Remove a triangle");
			System.out.println("4. Go back");

			int option = Integer.parseInt(scanner.nextLine());
			switch (option) {
				case ADD_TRIANGLE:
					Triangle triangle = new Triangle(); // creates the new triangle
					boolean valid = false;
					while(!valid) { // while the side length is not valid
						System.out.println("Side length:");
						int sideLength = Integer.parseInt(scanner.nextLine());
						// validation check for the side length
						if (triangle.isValidSideLength(sideLength, height, width)){ 
							triangle.setSideLength(sideLength);
							valid = true;
						} else {
							System.out.println("Error! The side length is too long (Current canvas size is "+ width +"x"+ height +"). Please try again.");
						}
					}
					System.out.println("Printing character:");
					triangle.setPrintingChar(scanner.nextLine().charAt(0)); // this will set the printing character of the triangle from the user's first character input

					triangleList.add(triangle); // after getting all information needed, we can add the triangle in the list
					showCanvas(); // show the canvas with the newly added triangle

					triangle.editMainMenu(scanner, this); // redirects to the edit menu
					break;
				case EDIT_TRIANGLE:
					Integer index = editRemoveMenu(scanner, true); // index of the chosen triangle to edit returned from editRemoveMenu
					if (isNull(index)) { // if empty triangle list or invalid index, return back to menu
						break;
					}
					showCanvas(); // show the current drawing canvas

					Triangle chosenTriangle = triangleList.get(index);
					chosenTriangle.editMainMenu(scanner, this); // redirects to the main menu for editing the triangle
					break;
				case REMOVE_TRIANGLE:
					Integer indexToRemove = editRemoveMenu(scanner, false); // index of the chosen triangle to remove returned from editRemoveMenu
					if (isNull(indexToRemove)) { // if empty triangle list or invalid index, return back to menu
						break;
					}
					triangleList.remove((int) indexToRemove); // removes the chosen triangle from the triangleList, converting from Integer to int to make the remove work
					break;
				case EXIT_DRAWING_CANVAS_MENU:
					stop = true; // exits the drawing canvas menu
					break;
				default:
					System.out.println("Unsupported option. Please try again!");
					break;
			}

		}
	}

	/*
	 * This method serves as the initial menu for the edit and remove triangle.
	 * It displays the list of triangles in the current canvas. After choosing the 
	 * valid index of the triangle, this method will return the index of the triangle
	 * chosen by user.
	 */
	private Integer editRemoveMenu(Scanner scanner, boolean isEdit) {
		if (triangleList.size() == 0) { // if there are no triangles found in the canvas
			if (isEdit) { // if method was called from the edit option
				System.out.println("The current canvas is clean; there are no shapes to edit!");
			} else { // if method was called from the remove option
				System.out.println("The current canvas is clean; there are no shapes to remove!");
			}
			return null; // return null to go back to the main menu
		}

		int triangleNumber = 1; // triangleNumber is used to represent the index of the triangles, initializing to 1 for the first triangle
		for (Triangle t : triangleList) { // display the list of triangles
			System.out.println("Shape #" + triangleNumber + " - Triangle: xPos = " + t.getXPos() +", yPos = " + t.getYPos() +", tChar = " + t.getPrintingChar());
			triangleNumber++; // increments for the next number
		}

		if (isEdit) { // if method was called from the edit option
			System.out.println("Index of the shape:");
		} else { // if method was called from the remove option
			System.out.println("Index of the shape to remove:");
		}
		
		int index = Integer.parseInt(scanner.nextLine()); // chosen index from the user
		if ((index-1) > triangleList.size()) { // if chosen index was larger than the number of triangles in the list
			System.out.println("The shape index cannot be larger than the number of shapes!");
			return null; // return null to go back to the main menu
		}

		return index-1; // returns the index of the triangle (triangleList starts with index 0 instead of 1)
	}
}

