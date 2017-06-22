/**
 *
 */
package net.shiild.trax.inventory;

import java.util.Scanner;

/**
 * A movie in the rental system inventory.
 *
 * @author StephenHildebrand
 */
public class Thing {
    /** The movie's name */
    private String name;
    /** The number of copies of the movie in stock */
    private int inStock;

    /**
     * Constructs a Thing from a string of the format
     * <number-in-stock><whitespace><movie-title> [UC1, S3].
     * <p>
     * This constructor throws an IllegalArgumentException if the string
     * argument is not valid (exception on reading a number or empty name after
     * trimming the white space off the ends of the name) [UC1, E2].
     * <p>
     * If  <number-in-stock> is negative, this constructor sets it to 0.
     *
     * @param rawLine an unedited line from movie list file
     * @throws IllegalArgumentException if the string parameter is invalid
     */
    public Thing(String rawLine) throws IllegalArgumentException {
        // check for null or empty parameter
        if (rawLine == null || rawLine.equals("")) {
            throw new IllegalArgumentException();
        }
        rawLine = rawLine.trim();

        int numberInStock;
        Scanner lineScanner = new Scanner(rawLine);

        if (lineScanner.hasNext()) {
            // Get the number of in-stock copies from the unedited movie string.
            try {
                numberInStock = lineScanner.nextInt();
                if (numberInStock >= 0) {
                    inStock = numberInStock;
                } else if (numberInStock < 0) {
                    inStock = 0;
                } else {
                    lineScanner.close();
                    throw new IllegalArgumentException("Invalid movie list file.");
                }
            } catch (Exception e) {
                lineScanner.close();
                throw new IllegalArgumentException("Invalid movie list file.");
            }
        } else {
            lineScanner.close();
            throw new IllegalArgumentException("Invalid movie list file.");
        }

        // Get the movie name from the unedited movie string.
        if (lineScanner.hasNext()) {
            name = lineScanner.nextLine().trim();
        } else {
            lineScanner.close();
            throw new IllegalArgumentException("Invalid movie list file.");
        }
        lineScanner.close();
    }

    /**
     * Returns the movie's name.
     *
     * @return the movie's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the movie title + "(currently unavailable)" if the movie is out
     * of stock; returns only the movie title if the movie is in stock [UC7,
     * S1].
     *
     * @return the movie's title with "(currently unavailable)" appended if not
     * available
     */
    public String getDisplayName() {
        if (!this.isAvailable()) {
            return name + " (currently unavailable)";
        }
        return name;
    }

    /**
     * Compares titles of two movies to determine their order in the inventory
     * list [UC7,S3]. Ignores the initial articles "A", "An" or "The".
     * <p>
     * If the argument Thing name is lexically equivalent to this movie name,
     * zero is returned.
     * <p>
     * A negative number is returned if this Thing name is lexically less than
     * the argument Thing (meaning this Thing belongs prior to the argument
     * movie).
     * <p>
     * A positive number is returned if this movie is greater (and the argument
     * movie belongs before it).
     *
     * @param otherMovie to be compared to this movie
     * @return zero if parameter movie is lexically equivalent, negative number
     * if this Thing is before, positive if the movie is after
     */
    public int compareToByName(Thing otherMovie) {
        // Does a null otherMovie need to be handled???
        String movieName = name;
        String otherMovieName = otherMovie.name;

        // If this movie's name starts with "A", "An", or "The", remove it for
        // comparison with the other movie (regardless of case)
        if (name.startsWith("a ") || name.startsWith("A ")) {
            movieName = movieName.substring(2);
        } else if (name.startsWith("an ") || name.startsWith("An ")) {
            movieName = movieName.substring(3);
        } else if (name.startsWith("the ") || name.startsWith("The ")) {
            movieName = movieName.substring(4);
        }
        // Repeat with the other movie's name
        if (otherMovieName.startsWith("a ") || otherMovieName.startsWith("A ")) {
            otherMovieName = otherMovieName.substring(2);
        } else if (otherMovieName.startsWith("an ") || otherMovieName.startsWith("An ")) {
            otherMovieName = otherMovieName.substring(3);
        } else if (otherMovieName.startsWith("the ") || otherMovieName.startsWith("The ")) {
            otherMovieName = otherMovieName.substring(4);
        }
        return movieName.compareTo(otherMovieName);
    }

    /**
     * Returns true if there are copies of this movie in stock in the inventory.
     *
     * @return true if there are copies in stock.
     */
    public boolean isAvailable() {
        if (inStock > 0) {
            return true;
        }
        return false;
    }

    /**
     * Puts a copy of the movie back into the inventory stock [UC10].
     */
    public void backToInventory() {
        inStock++;
    }

    /**
     * Removes a copy of the movie from the inventory stock [UC8,S1].
     * <p>
     * Throws an IllegalStateException if no copies of the movie are in stock.
     *
     * @throws IllegalStateException if no copies are in stock
     */
    public void removeOneCopyFromInventory() throws IllegalStateException {
        if (inStock <= 0) {
            throw new IllegalStateException();
        }
        inStock--;
    }
}
