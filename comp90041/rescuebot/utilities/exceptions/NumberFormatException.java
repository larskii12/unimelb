package utilities.exceptions;

/**
 * An exception that is thrown when there is a number format issue in a cell of the row
 * @author Laradell Tria
 */
public class NumberFormatException {

    public NumberFormatException(int rowNumber) {
        System.out.println("WARNING: invalid number format in scenarios file in line " + rowNumber);
    }
}
