package itertools;

import java.util.NoSuchElementException;

public class ReversedIterator<T> implements DoubleEndedIterator<T> {

    // An interator with the type `DoubleEndedIterator<T>`
    private final DoubleEndedIterator<T> iterator;

    public ReversedIterator(DoubleEndedIterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        /* Return the next element in reversed order if there is one to return */
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else {
            return iterator.reverseNext();
        }
    }

    @Override
    public T reverseNext(){
        // No need to implement this method
        throw new UnsupportedOperationException("Unimplemented method 'reverseNext'");
    }
}
