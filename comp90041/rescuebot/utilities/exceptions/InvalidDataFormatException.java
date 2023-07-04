package utilities.exceptions;

/**
 * An exception that is thrown when there is an invalid data format in the row
 * @author Laradell Tria
 */
public class InvalidDataFormatException {

    public InvalidDataFormatException(int rowNumber) {
        System.out.println("WARNING: invalid data format in scenarios file in line " + rowNumber);
    }
}
