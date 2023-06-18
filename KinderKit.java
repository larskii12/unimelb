import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Assignment 1
 * @author: Laradell Tria  
 * Student Id: 1417478
 * Email: ltria@student.unimelb.edu.au
 */
public class KinderKit {

  public static void main(String[] args) {
    
    // Initializing drawing canvas settings
    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);
    char backgroundChar = args[2].charAt(0);

    // Making initial drawing canvas
    DrawingCanvas drawingCanvas = new DrawingCanvas(width, height, backgroundChar);
    
    Scanner scanner = new Scanner(System.in); // used for scanning input for menu
    int input = 0; // initializing input to 0 for the loop
    
    while (input != 4) { // while user doesn't want to exit the menu
      
      System.out.println("Please select an option. Type 4 to exit.");
      System.out.println("1. Draw triangles");
      System.out.println("2. Draw rectangles");
      System.out.println("3. Update drawing canvas settings");
      System.out.println("4. Exit");
      input = Integer.parseInt(scanner.nextLine());
      
      switch(input) {
        case 1:
          Triangle triangle = new Triangle();
          triangle.mainMenu(scanner, drawingCanvas); // redirects to the main menu method in the Triangle class
          break;
        case 2:
          Rectangle rectangle = new Rectangle();
          rectangle.mainMenu(scanner, drawingCanvas); // redirects to the main menu method in the Rectangle class
          break;
        case 3:
          System.out.print("Canvas width: ");
          width = Integer.parseInt(scanner.nextLine()); // getting width
          System.out.print("Canvas height: "); 
          height = Integer.parseInt(scanner.nextLine()); //getting height
          System.out.print("Background character: ");
          backgroundChar = scanner.nextLine().charAt(0);
          drawingCanvas.updateCanvas(width, height, backgroundChar);
          break;
        case 4:
          System.out.println("Goodbye! We hope you had fun :)");
          break;
        default:
          System.out.println("Unsupported option. Please try again!");
          break;
      }
    }

  }
}
