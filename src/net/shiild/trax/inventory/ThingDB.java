/**
 *
 */
package net.shiild.trax.inventory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.shiild.trax.util.MultiPurposeList;

/**
 * An internal database of movies that uses a MultiPurposeList of Movies for
 * maintaining its collection of things.
 *
 * @author StephenHildebrand
 */
public class ThingDB {
    /** List of Thing elements in the inventory */
    private MultiPurposeList<Thing> movies;

    /**
     * Constructs the database from a file [UC1,S3], where the parameter is the
     * name of the file. Throws an IllegalArgumentException if the file cannot
     * be read. If the file name is a valid, empty file - a movie list
     * containing no movies will be created.
     *
     * @param fileName name of the file to be read
     * @throws IllegalArgumentException if the file cannot be read
     */
    public ThingDB(String fileName) throws IllegalArgumentException {
        movies = new MultiPurposeList<Thing>();
        readFromFile(fileName);
    }

    /**
     * Returns a string corresponding to the movies in the database in the
     * proper order. Strings for successive movies are separated by newlines.
     * The string is appropriate for the display in the Thing Inventory area
     * [UC7].
     *
     * @return String corresponding to the database movies in order
     */
    public String traverse() {
        String stringDB = null;
        if (!movies.isEmpty()) {
            stringDB = "";
            movies.resetIterator();
            while (movies.hasNext()) {
                stringDB = stringDB + movies.next().getDisplayName() + "\n";
            }
        }
        return stringDB;
    }

    /**
     * Returns the movie at the given position. Throws an
     * IllegalArgumentException if the position is out of range (less than 0 or
     * >= size).
     *
     * @param psn position of the target movie
     * @return target Thing
     * @throws IllegalArgumentException if position is less than 0 or greater than or equal to size
     */
    public Thing findThingAt(int psn) throws IllegalArgumentException {
        if (psn < 0 || psn >= movies.size()) {
            throw new IllegalArgumentException();
        }
        return movies.lookAtItemN(psn);
    }

    /**
     * Reads the file with the given file name.
     *
     * @param fileName the name of the file to read
     */
    private void readFromFile(String fileName) throws IllegalArgumentException {
        try {
            // Create a BufferedReader from the file name
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            // Read the first BufferedReader line
            String line = input.readLine();
            while (line != null) {
                if (line.length() > 0) { // line is not empty
                    insertInOrder(new Thing(line));
                }
                line = input.readLine();
            }
            input.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file could not be read.");
        } catch (IOException e) {
            throw new IllegalArgumentException("The file could not be read.");
        }
    }

    /**
     * Inserts a movie into the movieDB in the appropriate location based on its
     * lexicographic value.
     *
     * @param newMovie Thing to be inserted
     */
    private void insertInOrder(Thing newMovie) {
        if (movies != null & newMovie != null) { // movies is instantiated
            movies.resetIterator(); // Reset iterator to position 0
            int psn = 0; // Tracker for the current position
            boolean found = false;
            // -----------------------------
            // While the next element after iterator is not null
            while (movies.hasNext() && !found) {
                // Return the current movie and move to the next element
                Thing currentMovie = movies.next();
                // If the current movie is lexically larger than the new movie,
                // the new movie belongs in front of it.
                if (currentMovie.compareToByName(newMovie) > 0) {
                    movies.addItem(psn, newMovie);
                    found = true;
                }
                psn++; // Increment position by one
            }
            // If the end of the list is reached and no match was found
            if (!movies.hasNext() && !found) {
                movies.addItem(psn, newMovie);
            }
        }
    }
}
