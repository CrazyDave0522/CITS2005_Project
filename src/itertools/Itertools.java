package itertools;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A collection of useful methods for working with iterators.
 *
 * @see Iterator
 * @see DoubleEndedIterator
 */
public class Itertools {

    /**
     * Given an iterator and a number of elements, returns an iterator over that
     * number of elements taken from the iterator (or as many as it contains, if
     * less than that number).
     *
     * <p>
     * Elements are consumed from the given iterator only as needed.
     *
     * @param <T> The type of elements in the iterator.
     * @param it The iterator from which to take elements.
     * @param count The maximum number of elements to take.
     * @return An iterator over the first `count` elements of `it`.
     */
    public static <T> Iterator<T> take(Iterator<T> it, int count) {
        // TASK(1): Implement take

        // Create a new object of `TakeIterator<T>` class
        TakeIterator<T> takeIterator = new TakeIterator<>(it, count);
        return takeIterator;
    }

    /**
     * Returns a (double ended) iterator in the reverse order of the one given.
     *
     * <p>
     * Elements are consumed from the given iterator only as needed.
     *
     * @param <T> The type of elements in the iterator.
     * @param it The (double ended) iterator to reverse.
     * @return The reverse of the given iterator.
     */
    public static <T> Iterator<T> reversed(DoubleEndedIterator<T> it) {
        // TASK(2): Implement reversed
        ReversedIterator<T> reversedIterator = new ReversedIterator<>(it);
        return reversedIterator;
    }

    /**
     * Returns an iterator over the elements of a given iterator with those
     * elements that do not satisfy a given {@link Predicate} dropped.
     *
     * <p>
     * Elements are consumed from the given iterator only as needed (though it
     * may be necessary to consume elements to determine whether there is a next
     * element that satisfies the predicate).
     *
     * <p>
     * Java's {@link Predicate} interface can be used by calling `pred.test(x)`,
     * and will return `true` if and only if `x` satisfies the predicate.
     *
     * @param <T> The type of elements in the iterator.
     * @param it The iterator to filter.
     * @param pred The predicate to use to determine whether to keep or drop an
     * element.
     * @return An iterator over the elements of `it` with elements not
     * satisfying `pred` removed.
     */
    public static <T> Iterator<T> filter(Iterator<T> it, Predicate<T> pred) {
        // TASK(3): Implement filter
        FilterIterator<T> filterIterator = new FilterIterator<>(it, pred);
        return filterIterator;
    }

    /**
     * Returns an iterator over the elements of a given iterator with a given
     * function applied to each element.
     *
     * <p>
     * That is, given a function `f` and an iterator over the elements `a, b, c,
     * ...`, returns an iterator over `f(a), f(b), f(c), ...`.
     *
     * <p>
     * Elements are consumed from the given iterator only as needed.
     *
     * <p>
     * Java's {@link Function} interface can be used by calling `f.apply(x)` and
     * will return `f(x)`.
     *
     * @param <T> The type of elements in the input iterator.
     * @param <R> The type of elements in the result iterator.
     * @param it The iterator to map over.
     * @param f The function to apply to each element.
     * @return An iterator over the results of applying `f` to each element in
     * `it`.
     */
    public static <T, R> Iterator<R> map(Iterator<T> it, Function<T, R> f) {
        // TASK(4): Implement map
        MapIterator<T, R> mapIterator = new MapIterator<>(it, f);
        return mapIterator;
    }

    /**
     * A double-ended overload of {@link #map}.
     *
     * @param <T> The type of elements in the input iterator.
     * @param <R> The type of elements in the result iterator.
     * @param it The iterator to map over.
     * @param f The function to apply to each element.
     * @return An iterator over the results of applying `f` to each element in
     * `it`.
     */
    public static <T, R> DoubleEndedIterator<R> map(DoubleEndedIterator<T> it, Function<T, R> f) {
        // TASK(5): Implement map (double ended)
        DoubleEndedMapIterator<T, R> doubleEndedMapIterator = new DoubleEndedMapIterator<>(it, f);
        return doubleEndedMapIterator;
    }

    /**
     * Returns an iterator over the results of combining each pair of elements
     * from a pair of given iterators using a given function.
     *
     * <p>
     * That is, given a function `f` and iterators over the elements `a, b, c,
     * ...` and `x, y, z, ...` returns an iterator over `f(a, x), f(b, y), f(c,
     * z), ...`.
     *
     * <p>
     * The iterator ends when either input iterator ends.
     *
     * <p>
     * Elements are consumed from the given iterators only as needed.
     *
     * <p>
     * Java's {@link BiFunction} interface can be used by calling `f.apply(x,
     * y)` and will return `f(x, y)`.
     *
     * @param <T> The type of elements in the "left-hand" iterator.
     * @param <U> The type of elements in the "right-hand" iterator.
     * @param <R> The type of elements in the result iterator.
     * @param lit The "left-hand" iterator.
     * @param rit The "right-hand" iterator.
     * @param f A function to use to combine elements from `lit` and `rit`.
     * @return An iterator over the result of combining elements from `lit` and
     * `rit` using `f`.
     */
    public static <T, U, R> Iterator<R> zip(
            Iterator<T> lit, Iterator<U> rit, BiFunction<T, U, R> f) {
        // TASK(6): Implement zip
        ZipIterator<T, U, R> zipIterator = new ZipIterator<>(lit, rit, f);
        return zipIterator;
    }

    /**
     * Returns the result of combining all the elements from the given iterator
     * using the given function.
     *
     * <p>
     * Each element is combined with the current value using the given function.
     *
     * <p>
     * For example, given a function `f`, an initial value `x`, and an iterator
     * over the elements `a, b, c`, returns `f(f(f(x, a), b), c)`.
     *
     * <p>
     * Java's {@link BiFunction} interface can be used by calling `f.apply(x,
     * y)` and will return `f(x, y)`.
     *
     * @param <T> The type of elements in the "left-hand" iterator.
     * @param <R> The type of the result.
     * @param it The iterator to reduce.
     * @param init The initial value.
     * @param f The function to use to combine each element into the reduction
     * value.
     * @return The value after all elements have been combined.
     */
    public static <T, R> R reduce(Iterator<T> it, R init, BiFunction<R, T, R> f) {
        // TASK(7): Implement reduce
        R result = init; // Initialize the result
        while (it.hasNext()) {
            result = f.apply(result, it.next());
        }
        return result;
    }
}
