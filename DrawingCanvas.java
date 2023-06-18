import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Assignment 1
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 */
public class DrawingCanvas {

    // Instance variables for drawing canvas
    private int width;
    private int height;
    private char backgroundChar; // background character

    // Constructor
    public DrawingCanvas(int width, int height, char backgroundChar) {
          this.width = width;
          this.height = height;
          this.backgroundChar = backgroundChar;
          welcomeMessage();
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
    private void welcomeMessage() {
        System.out.println("----DIGITAL KINDER KIT: LET'S PLAY & LEARN----");
        currentSettings();
    }

    private void currentSettings() {
        System.out.println("Current drawing canvas settings:");
        System.out.println("- Width: " + width); 
        System.out.println("- Height: " + height);
        System.out.println("- Background character: " + backgroundChar);
        System.out.println();
    }

    public void updateCanvas(int width, int height, char backgroundChar) {
        setWidth(width);
        setHeight(height);
        setBackgroundChar(backgroundChar);
        System.out.println("Drawing canvas has been updated!");
        System.out.println();
        currentSettings();
    }
}
