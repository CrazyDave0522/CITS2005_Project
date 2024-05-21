package itertools;

import java.util.function.Function;

public class DoubleEndedMapIterator<T, R> implements DoubleEndedIterator<R> {

    private final DoubleEndedIterator<T> iterator;
    private final Function<T, R> function;

    public DoubleEndedMapIterator(DoubleEndedIterator<T> iterator, Function<T, R> function) {
        this.iterator = iterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public R next() {
        return function.apply(iterator.next());
    }

    @Override
    public R reverseNext() {
        return function.apply(iterator.reverseNext());
    }

}
