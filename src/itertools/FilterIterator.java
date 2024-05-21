package itertools;

import java.util.Iterator;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {

    private final Iterator<T> iterator;
    private final Predicate<T> pred;
    private T nextEle; // The next element that could be picked

    // Consructor
    public FilterIterator(Iterator<T> iterator, Predicate<T> pred) {
        this.iterator = iterator;
        this.pred = pred;
        findNext(); // Run the method to get the fisrt element, for the first time
    }

    /* Find out elements that satisfy the predicate  */
    private void findNext() {
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (pred.test(element)) {
                nextEle = element;
                return;
            }
        }
        nextEle = null;
    }

    @Override
    public boolean hasNext() {
        return nextEle != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        T result = nextEle;
        findNext();
        return result;
    }
}
