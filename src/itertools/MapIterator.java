package itertools;

import java.util.Iterator;
import java.util.function.Function;

public class MapIterator<T, R> implements Iterator<R> {

    private final Iterator<T> iterator;
    private final Function<T, R> function;

    public MapIterator(Iterator<T> iterator, Function<T, R> function) {
        this.iterator = iterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public R next() {
        // Apply the function to the next element of the Iterator
        return function.apply(iterator.next());
    }
}
