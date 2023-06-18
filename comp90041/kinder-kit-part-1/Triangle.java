import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Assignment 1
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 */
public class Triangle {

    private int sideLength;
    private char printingChar; // printing character
    private int xPos; // x position of the top leftmost character of triangle
    private int yPos; // y position of the top leftmost character of triangle

    // Constructor 
    public Triangle() {
        xPos = 0; // the x position starts always at the top leftmost of the drawing canvas
        yPos = 0; // the y position starts always at the top leftmost of the drawing canvas
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
    /*
    * This method validates if the given side length doesn't exceed the dimensions of the canvas.
    * (canvasHeight - yPos) & (canvasWidth - xPos) are used as reference because we should also consider 
    * that the current triangle may have been located in a different position of the canvas.
    */
    private boolean isValidSideLength(int sideLength, int canvasHeight, int canvasWidth) {
        if((sideLength > (canvasHeight - yPos)|| sideLength > (canvasWidth - xPos)) || sideLength < 1) { 
            return false;
        } else {
            return true;
        }
    }

    /*
    * This method validates if the given x position doesn't exceed the canvas width before moving and sets the new position if valid.
    * (xPos + sideLength) is used because we have to consider that the drawn triangle will also be within the bounds of the canvas width.
    */
    private void moveXPos(int xPos, int canvasWidth) {
        if((xPos + sideLength) > canvasWidth || xPos < 0) {
            System.out.println("You cannot move this triangle outside of the drawing canvas!");
        } else {
            setXPos(xPos);
        }
    }

    /*
    * This method validates if the given y position doesn't exceed the canvas height before moving and sets the new position if valid.
    * (yPos + sideLength) is used because we have to consider that the drawn triangle will also be within the bounds of the canvas height.
    */
    private void moveYPos(int yPos, int canvasHeight) {
        // if the y position is within the bounds of the canvas height
        if((yPos + sideLength) > canvasHeight || yPos < 0) {
            System.out.println("You cannot move this triangle outside of the drawing canvas!");
        } else {
            setYPos(yPos);
        }
    }

    /*
    * This method is used for the zooming feature of the Triangle.
    */
    private void zoomTriangle(Scanner scanner, DrawingCanvas drawingCanvas) {

        boolean isExit = false; // isExit is used for the while loop if the user is still in the zoom menu
        createTriangle(drawingCanvas); // draw first the triangle before zooming 
        
        while(!isExit) { // while still inside zoom triangle menu
            System.out.println("Type I/O to zoom in/out. Use other keys to go back to the Zooming/Moving menu.");
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
            createTriangle(drawingCanvas); // prints out the triangle with the new changes on the side length
        }
    }

    /*
    * This method is used for the moving feature of the Triangle.
    */
    private void moveTriangle(Scanner scanner, DrawingCanvas drawingCanvas) {
        
        boolean isExit = false; // isExit is used for the while loop if the user is still in the move menu
        createTriangle(drawingCanvas); // draw first the triangle before moving
       
        while(!isExit) { // while still inside move triangle menu
            System.out.println("Type A/S/W/Z to move left/right/up/down. Use other keys to go back to the Zooming/Moving menu.");
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
            createTriangle(drawingCanvas); // prints out the triangle with the new changes on its position
        }
    }

    /*
    * This method is used for drawing the Triangle. canvasHeight, canvasWidth, and backgroundChar are variables from DrawingCanvas
    * that we assigned ahead to prevent continuous calling in the loop. 
    *
    * To draw the triangle, we define a sideLengthCtr which serves as a counter for the number of printing characters it will draw in the row.
    * We can only draw out the printingChar if the current position in the loop is between the starting x & y position of the triangle until the 
    * all of the sideLengthCtr has been exhausted.
    */
    private void createTriangle(DrawingCanvas drawingCanvas) {
        int sideLengthCtr = sideLength; // counter used for creating triangle
        int canvasHeight = drawingCanvas.getHeight();
        int canvasWidth = drawingCanvas.getWidth();
        char backgroundChar = drawingCanvas.getBackgroundChar();

        for(int i = 0; i < canvasHeight; i++) { // for traversing the columns of the canvas
            for(int j = 0; j < canvasWidth; j++) { // for traversing the rows of the canvas
                // if the current cell (var j) is within the triangle's drawing area, it will display the printinChar, otherwise the backgroundChar
                if ((j >= xPos && j < sideLengthCtr+xPos) && (i >= yPos && i < sideLength+yPos)) {
                    System.out.print(printingChar);
                } else {
                    System.out.print(backgroundChar);
                }
            }
            System.out.println(); // after each row, we print out a new line to start a new row

            // we can only deduct the sideLengthCtr if we started out printing the triangle
            if (i >= yPos && i < sideLength+yPos){
                sideLengthCtr--;
            }
        }
    }

    /*
    * This method serves as the Triangle Main Menu. This contains 3 parts: setting up the triangle, zooming and moving triangle, and draw another triangle
    * 
    * 1.) Setting up the Triangle - we will ask the user on the valid side length and printing character of the triangle
    * 2.) Zooming and Moving Triangle - after creating the triangle, we can ask the user to either zoom or move the triangle
    *   a.) If the user wants to zoom the triangle, we will redirect to the Zooming Triangle Menu
    *   b.) If the user wants to move the triangle, we will redirect to the Moving Triangle Menu
    * 3.) Draw another Triangle - if the user exits from Zooming or Moving Triangle, we will ask the user if they want to make another triangle.
    *
    * Once the user says "No", the program will exit the Triangle Menu.
    */
    public void mainMenu(Scanner scanner, DrawingCanvas drawingCanvas) {

        boolean stop = false; // boolean to stop triangle menu
        while(!stop) { // while user is still in the triangle menu
            // initializing the triangle's position to the top leftmost position
            setXPos(0);
            setYPos(0);
            // this portion is for getting the valid side length from user
            boolean valid = false;
            while(!valid) { // while the side length is not valid
                System.out.println("Side length:");
                int sideLength = Integer.parseInt(scanner.nextLine());
                if (isValidSideLength(sideLength, drawingCanvas.getHeight(), drawingCanvas.getWidth())) { // validation check for the side length
                    setSideLength(sideLength);
                    valid = true;
                } else {
                    System.out.println("Error! The side length is too long (Current canvas size is "+ drawingCanvas.getWidth() +"x"+ drawingCanvas.getHeight() +"). Please try again.");
                }
            }
            System.out.println("Printing character:");
            setPrintingChar(scanner.nextLine().charAt(0)); // this will set the printing character of the triangle from the user's first character input

            createTriangle(drawingCanvas); // after getting all information needed, we can create the triangle
            // this portion is for zoom or move triangle
            boolean quitZoomMenu = false; // boolean to stop the zoom and move menu
            while(!quitZoomMenu) { // while user is still in zoom and move menu
                System.out.println("Type Z/M for zooming/moving. Use other keys to quit the Zooming/Moving mode.");
                char option = scanner.nextLine().toLowerCase().charAt(0); // option is for user's choice in the zooming or moving menu
                if(option == 'z') {
                    zoomTriangle(scanner, drawingCanvas);
                } else if (option == 'm') {
                    moveTriangle(scanner, drawingCanvas);
                } else {
                    quitZoomMenu = true;
                }
            }

            // this portion is after doing zoom or move, it will prompt to ask for another triangle
            char answer = 'x'; // answer is for user's input in drawing another triangle
            while(!(answer == 'y' || answer == 'n')) {
                System.out.println("Draw another triangle (Y/N)?");
                answer = scanner.nextLine().toLowerCase().charAt(0);
                if(!(answer == 'y' || answer == 'n')) {
                    System.out.println("Unsupported option. Please try again!");
                }
            }
            if (answer == 'n') {
                stop = true; // this stops the triangle menu loop and goes back to the main menu
            }
          }  
    }
}
