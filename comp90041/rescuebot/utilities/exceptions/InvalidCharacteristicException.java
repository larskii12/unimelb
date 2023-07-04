package utilities.exceptions;

/**
 * An exception that is thrown when there is an invalid characteristic in a cell 
 * in the row
 * @author Laradell Tria
 */
public class InvalidCharacteristicException {

    public InvalidCharacteristicException(int rowNumber) {
        System.out.println("WARNING: invalid characteristic in scenarios file in line " + rowNumber);
    }
}
