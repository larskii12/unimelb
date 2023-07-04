package utilities.exceptions;

/**
 * An exception that is thrown when the directory does not exist.
 * @author Laradell Tria
 */
public class DirectoryNotExist {

    public DirectoryNotExist() {
        System.out.println("ERROR: could not print results. Target directory does not exist.");
    }
}

