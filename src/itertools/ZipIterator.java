package itertools;

import java.util.Iterator;
import java.util.function.BiFunction;

public class ZipIterator<T, U, R> implements Iterator<R> {

    private final Iterator<T> leftIterator;
    private final Iterator<U> rightIterator;
    private final BiFunction<T, U, R> function;

    public ZipIterator(Iterator<T> leftIterator, Iterator<U> rightIterator, BiFunction<T, U, R> function) {
        this.leftIterator = leftIterator;
        this.rightIterator = rightIterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        // Return true only when both Iterators have the next element 
        return leftIterator.hasNext() && rightIterator.hasNext();
    }

    @Override
    public R next() {
        return function.apply(leftIterator.next(), rightIterator.next());
    }

}
