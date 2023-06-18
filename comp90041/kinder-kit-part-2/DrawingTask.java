/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 * 
 * This class stores the drawing task of the user for the challenger mode in which they 
 * will need to follow the sample canvas from the file input. This class also composes
 * the constructor for initializing triangle, and methods that the drawing task can do 
 * (previewCanvas and compareCanvas).
 */
public class DrawingTask {

    private DrawingCanvas drawingCanvas; // details on the sample drawing canvas
    private char[][] canvas; // actual canvas matrix

    // Constructor
    // This constructor is used to initialize the drawing task for the sample drawing.
    public DrawingTask(DrawingCanvas drawingCanvas, char[][] canvas) {
        this.drawingCanvas = drawingCanvas;
        this.canvas = canvas;
    }

    // Methods
    /**
     * This method is used to compare the sample drawing with the current drawing.
     */
    public void compareCanvas(DrawingCanvas currentDrawingCanvas) {
        // We can use the sample's drawing canvas height and canvas weight since both 
        // drawings must be in the same dimensions.
        int height = drawingCanvas.getHeight();
        int width = drawingCanvas.getWidth();
        char[][] currentCanvas = currentDrawingCanvas.drawCanvas(); // getting the current drawing canvas used to compare the sample drawing canvas

        boolean isSuccessful = true; // initialize to be true
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if (canvas[i][j] != currentCanvas[i][j]) {
                    isSuccessful = false; // if one part of the drawing is not the same, it will turn false and stop the loop
                    break;
                }
            }
        }

        if (isSuccessful) { // isSuccessful = true
            System.out.println("You successfully complete the drawing task. Congratulations!!");
        } else {
            System.out.println("Not quite! Please edit your canvas and check the result again.");
        }

        System.out.println("This is the sample drawing:");
        previewCanvas(); // shows the sample drawing canvas
        System.out.println("And this is your drawing:");
        currentDrawingCanvas.showCanvas(); // shows the current drawing canvas
    }
    
    /**
     * This method is used to preview the sample drawing for challenge mode. It 
     * will print out the canvas of the sample drawing.
     */
    public void previewCanvas() {
        int height = drawingCanvas.getHeight();
        int width = drawingCanvas.getWidth();

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++){
                System.out.print(canvas[i][j]);
            }
            System.out.println();
        }
    }
}

