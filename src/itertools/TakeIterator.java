package itertools;

import java.util.Iterator;

/* Create a class `TakeIterator` which is an implementation of the interface `Iterator`. */
public class TakeIterator<T> implements Iterator<T> {

    private final Iterator<T> iterator;
    private final int count;
    private int taken;// The number of elements already taken

    // Constructor
    public TakeIterator(Iterator<T> iterator, int count) {
        this.iterator = iterator;
        this.count = count;
        this.taken = 0;
    }

    /* 
    Return true if have not reached target number `count` 
    and there are more elements to go on 
     */
    @Override
    public boolean hasNext() {
        return (taken < count) && iterator.hasNext();
    }

    // Return the next element if there is one, otherwise throw an exception
    @Override
    public T next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        } else {
            taken++;
            return iterator.next();
        }
    }
}
