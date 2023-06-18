import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Assignment 1
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 */
public class Rectangle {

    private int width; // width for the rectangle
    private int height; // height for the rectangle
    private char printingChar; // printing character
    private int xPos; // x position of the top leftmost character of rectangle
    private int yPos; // y position of the top leftmost character of rectangle

    // Constructor 
    public Rectangle() {
        xPos = 0; // the x position starts always at the top leftmost of the drawing canvas
        yPos = 0; // the y position starts always at the top leftmost of the drawing canvas
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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
    * This method validates if the given width doesn't exceed the dimensions of the canvas.
    * (minDimension - xPos) is used as reference because we should also consider that 
    * the current rectangle may have been located in a different position of the canvas.
    */
    private boolean isValidWidth(int width, int minDimension) {
        // if the rectangle width is bigger than the canvas width or if it's not a positive integer, return false
        if(width > (minDimension - xPos) || width < 1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    * This method validates if the given height doesn't exceed the dimensions of the canvas.
    * (minDimension - yPos) is used as reference because we should also consider that 
    * the current rectangle may have been located in a different position of the canvas.
    */
    private boolean isValidHeight(int height, int minDimension) {
        if(height > (minDimension - yPos) || height < 1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    * This method validates if the given x position doesn't exceed the canvas width before moving and sets the new position if valid.
    * (xPos + width) is used because we have to consider that the drawn rectangle will also be within the bounds of the canvas width.
    */
    private void moveXPos(int xPos, int canvasWidth) {
        if((xPos + width) > canvasWidth || xPos < 0) {
            System.out.println("You cannot move this rectangle outside of the drawing canvas!");
        } else {
            setXPos(xPos);
        }
    }

    /*
    * This method validates if the given y position doesn't exceed the canvas height before moving and sets the new position if valid.
    * (yPos + height) is used because we have to consider that the drawn rectangle will also be within the bounds of the canvas height.
    */
    private void moveYPos(int yPos, int canvasHeight) {
        if((yPos + height) > canvasHeight || yPos < 0) {
            System.out.println("You cannot move this rectangle outside of the drawing canvas!");
        } else {
            setYPos(yPos);
        }
    }

    /*
    * This method is used for the zooming feature of the Rectangle.
    */
    private void zoomRectangle(Scanner scanner, DrawingCanvas drawingCanvas) {
        
        boolean isExit = false; // isExit is used for the while loop if the user is still in the zoom menu
        createRectangle(drawingCanvas); // draw first the rectangle before zooming 

        while(!isExit) { // while still inside zoom rectangle menu
            System.out.println("Type I/O to zoom in/out. Use other keys to go back to the Zooming/Moving menu.");
            char zoomLevel = scanner.nextLine().toLowerCase().charAt(0); // getting the lowercase version of the first character in the input

            if(zoomLevel == 'i') { // zoom in condition
                if(isValidHeight((height + 1), drawingCanvas.getHeight()) && isValidWidth((width + 1), drawingCanvas.getWidth())) { 
                    height++;
                    width++;
                } else {
                    System.out.println("This rectangle reaches its limit. You cannot make it bigger!");
                }
            } else if (zoomLevel == 'o') { // zoom out condition
                if(isValidHeight((height - 1), drawingCanvas.getHeight()) && isValidWidth((width - 1), drawingCanvas.getWidth())) {
                    height--;
                    width--;
                } else {
                    System.out.println("This rectangle reaches its limit. You cannot make it smaller!");
                }
            } else {
                isExit = true; // exits the zoom menu
            }
            createRectangle(drawingCanvas); // prints out the rectangle with the new changes on the height and width
        }
    }

    /*
    * This method is used for the moving feature of the Rectangle.
    */
    private void moveRectangle(Scanner scanner, DrawingCanvas drawingCanvas) {
        
        boolean isExit = false; // isExit is used for the while loop if the user is still in the move menu
        createRectangle(drawingCanvas); // draw first the rectangle before moving 

        while(!isExit) { // while still inside move rectangle menu
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
            createRectangle(drawingCanvas); // prints out the rectangle with the new changes on its position
        }
    }

    /*
    * This method is used for drawing the Rectangle. canvasHeight, canvasWidth, and backgroundChar are variables from DrawingCanvas
    * that we assigned ahead to prevent continuous calling in the loop. 
    *
    * To draw the rectangle, we can only draw out the printingChar if the current position in the loop is between the starting x & y position 
    * of the rectangle until we reach the width and height of the rectangle.
    */
    private void createRectangle(DrawingCanvas drawingCanvas) {
        int canvasHeight = drawingCanvas.getHeight();
        int canvasWidth = drawingCanvas.getWidth();
        char backgroundChar = drawingCanvas.getBackgroundChar();

        for(int i = 0; i < canvasHeight; i++) { // for traversing the columns of the canvas
            for(int j = 0; j < canvasWidth; j++) { // for traversing the rows of the canvas
                if ((j >= xPos && j < width+xPos) && (i >= yPos && i < height+yPos)) {
                    System.out.print(printingChar);
                } else {
                    System.out.print(backgroundChar);
                }
            }
            System.out.println(); // after each row, we print out a new line to start a new row
        }
    }

    /*
    * This method serves as the Rectangle Main Menu. This contains 3 parts: setting up the rectangle, zooming and moving rectangle, and draw another rectangle
    * 
    * 1.) Setting up the Rectangle - we will ask the user on the valid width, valid height, and printing character of the rectangle
    * 2.) Zooming and Moving Rectangle - after creating the rectangle, we can ask the user to either zoom or move the rectangle
    *   a.) If the user wants to zoom the rectangle, we will redirect to the Zooming Rectangle Menu
    *   b.) If the user wants to move the rectangle, we will redirect to the Moving Rectangle Menu
    * 3.) Draw another Rectangle - if the user exits from Zooming or Moving Rectangle, we will ask the user if they want to make another rectangle.
    *
    * Once the user says "No", the program will exit the Rectangle Menu.
    */
    public void mainMenu(Scanner scanner, DrawingCanvas drawingCanvas) {
        boolean stop = false; // boolean to stop rectangle menu
          while(!stop) { // while user is still in the rectangle menu
            // initializing the triangle's position to the top leftmost position
            setXPos(0);
            setYPos(0);
            // this portion is for getting the valid width and height from user
            boolean valid = false;
            while(!valid) { // while the width is not valid
              System.out.println("width:");
              int width = Integer.parseInt(scanner.nextLine()); 
              if (isValidWidth(width, drawingCanvas.getWidth())) { // validation check for the rectangle width
                  setWidth(width);
                  valid = true;
              } else {
                  System.out.println("Error! The width is too large (Current canvas size is " + drawingCanvas.getWidth() + "x" + drawingCanvas.getHeight() + "). Please try again.");
              }
            }
            valid = false;
            while(!valid) { // while the height is not valid
              System.out.println("height:");
              int height = Integer.parseInt(scanner.nextLine());
              if (isValidHeight(height, drawingCanvas.getHeight())) { // validation check for the rectangle height
                  setHeight(height);
                  valid = true;
              } else {
                  System.out.println("Error! The height is too large (Current canvas size is " + drawingCanvas.getWidth() + "x" + drawingCanvas.getHeight() + "). Please try again.");
              }
            }

            System.out.println("Printing character:");
            setPrintingChar(scanner.nextLine().charAt(0)); // this will set the printing character of the rectangle from the user's first character input

            createRectangle(drawingCanvas); // after getting all information needed, we can create the rectangle

            // this portion is for zoom or move rectangle
            boolean quitZoomMenu = false; // boolean to stop the zoom and move menu
            while(!quitZoomMenu) { // while user is still in zoom and move menu
              System.out.println("Type Z/M for zooming/moving. Use other keys to quit the Zooming/Moving mode.");
              char option = scanner.nextLine().toLowerCase().charAt(0); // option is for user's choice in the zooming or moving menu
              if(option == 'z') {
                zoomRectangle(scanner, drawingCanvas);
              } else if (option == 'm') {
                moveRectangle(scanner, drawingCanvas);
              } else {
                quitZoomMenu = true;
              }
            }

            // this portion is after doing zoom or move, it will prompt to ask for another rectangle
            char answer = 'x'; // answer is for user's input in drawing another rectangle
            while(!(answer == 'y' || answer == 'n')) {
                System.out.println("Draw another rectangle (Y/N)?");
                answer = scanner.nextLine().toLowerCase().charAt(0);
                if(!(answer == 'y' || answer == 'n')) {
                    System.out.println("Unsupported option. Please try again!");
                }
            }
            if (answer == 'n') {
                stop = true; // this stops the rectangle menu loop and goes back to the main menu
            }
          } 
    }
}
