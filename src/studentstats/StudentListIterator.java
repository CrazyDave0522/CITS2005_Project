package studentstats;

import itertools.DoubleEndedIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import studentapi.*;

/**
 * A (double ended) iterator over student records pulled from the student API.
 *
 * <p>
 * This does not load the whole student list immediately, but rather queries the
 * API ({@link StudentList#getPage}) only as needed.
 */
public class StudentListIterator implements DoubleEndedIterator<Student> {

    private StudentList list;
    private int currentIndex;
    private int currentPageIndex;
    private List<Student> currentPage;
    private int retries;

    /**
     * Construct an iterator over the given {@link StudentList} with the
     * specified retry quota.
     *
     * @param list The API interface.
     * @param retries The number of times to retry a query after getting
     * {@link QueryTimedOutException} before declaring the API unreachable and
     * throwing an {@link ApiUnreachableException}.
     */
    public StudentListIterator(StudentList list, int retries) {
        this.list = list;
        // -1 means not started yet
        this.currentIndex = -1;
        this.currentPageIndex = 0;
        this.currentPage = new ArrayList<>();
        this.retries = retries;
        loadPage(currentPageIndex);
    }

    /**
     * Construct an iterator over the given {@link StudentList} with a default
     * retry quota of 3.
     *
     * @param list The API interface.
     */
    public StudentListIterator(StudentList list) {
        this(list, 3);
    }

    private void loadPage(int pageNum) {
        int attempts = 0;
        while (attempts < retries) {
            try {
                currentPage = Arrays.asList(list.getPage(pageNum));
                // always set the current index to 0 when start a new page
                currentIndex = -1;
                return;
            } catch (QueryTimedOutException e) {
                attempts++;
            }
        }
        throw new ApiUnreachableException();
    }

    @Override
    public boolean hasNext() {
        // if current page is not finished
        if (currentIndex < currentPage.size() - 1) {
            return true;
        }
        // if there's another page to go
        return currentPageIndex < list.getNumPages() - 1;
    }

    @Override
    public Student next() {
        // there is no element next
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        // if current page is finished
        if (currentIndex >= currentPage.size() - 1) {
            // move to next page
            currentPageIndex++;
            loadPage(currentPageIndex);
            currentIndex++;
            return currentPage.get(currentIndex);
        }
        // current page is not finished and there is another element next
        currentIndex++;
        return currentPage.get(currentIndex);
    }

    @Override
    public Student reverseNext() {
        // edge case: if at the very first of a list that with only one page 
        if (list.getNumPages() == 1 && currentIndex == -1) {
            currentIndex = currentPage.size();
        }
        // there is an element before current element
        if (currentIndex > 0) {
            // get the previous element
            currentIndex--;
            return currentPage.get(currentIndex);
        }
        // if current page is finished and current page is not the last page
        if (currentIndex <= 0 && currentPageIndex < list.getNumPages() - 1) {
            // move to next page
            currentPageIndex++;
            loadPage(currentPageIndex);
            // set the current index to the last element of the page
            currentIndex = currentPage.size() - 1;
            return currentPage.get(currentIndex);
        }
        throw new NoSuchElementException();
    }
}
