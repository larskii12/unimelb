package utilities.random;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * A generic class used to keep the randomization weighted with certain attributes
 * @author Laradell Tria
 */
public class RandomCollection<T> {
    /**
     * The map used for storing the result with its weight on the collection
     */
    private final NavigableMap<Double, T> map = new TreeMap<>();
    /**
     * The random variable for generating random results
     */
    private final Random random;
    /**
     * The total weight of the collection
     */
    private double total = 0;

    /**
     * Create a new random collection.
     */
    public RandomCollection() {
        this.random = new Random();
    }

    /**
     * This method is for adding result with its weight to the random collection.
     * @param weight the weight of the result in the random collection
     * @param result the generic value in the random collection
     * @return random collection with the newly added value with its weight.
     */
    public RandomCollection<T> add(double weight, T result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    /**
     * Returns the randomly picked value.
     * @return generic value randomly picked with higher weight. 
     */
    public T pick() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
