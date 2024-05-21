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

    // The StudentList object represents student data.
    private StudentList list;

    // The maximum number of retries
    private int retries;

    // The current page number of student data
    private int currentPage;

    // The current batch of students fetched from the API.
    private List<Student> currentBatch;

    // The index of the current student within the current batch.
    private int currentIndex;

    /**
     * Construct an iterator over the given {@link StudentList} with the
     * specified retry quota.
     *
     * @param list The API interface.
     * @param retries The number of times to retry a query after getting {@link
     *     QueryTimedOutException} before declaring the API unreachable and throwing
     * an {@link ApiUnreachableException}.
     */
    public StudentListIterator(StudentList list, int retries) {
        this.list = list;           // Initialize the API interface.
        this.retries = retries;     // Set the retry quota.
        this.currentPage = 0;       // Start with the first page.
        this.currentBatch = new ArrayList<>(); // Initialize an empty batch.
        this.currentIndex = 0;      // Start with the first student in the batch.
        loadNextBatch();            // Load the first batch of students.
    }

    /**
     * Construct an iterator over the given {@link StudentList} with a default
     * retry quota of 3.
     *
     * @param list The API interface.
     */
    public StudentListIterator(StudentList list) {
        this(list, 3);  // Use the default retry quota of 3.
    }

    /**
     * Load the next batch of students from the API and check if the batch is empty.
     *
     * @return true if the batch is not empty, false otherwise.
     */
    private boolean loadNextBatch() {
        int attempts = 0;  // Initialize the number of attempts to fetch data.
        while (attempts < retries) {
            try {
                // Try to get the next page of students and reset the index to the start of the batch.
                currentBatch = Arrays.asList(list.getPage(currentPage));
                currentPage++;     // Move to the next page.
                currentIndex = 0;  // Reset the index within the new batch.
                return !currentBatch.isEmpty();  // Return true if the batch is not empty.
            } catch (QueryTimedOutException e) {
                attempts++;  // Increment the number of attempts if a timeout occurs.
            }
        }
        // Throw an exception if all retry attempts fail.
        throw new ApiUnreachableException();
    }

    /**
     * Check if there are more students available in the iterator.
     *
     * @return true if there are more students, false otherwise.
     */
    @Override
    public boolean hasNext() {
        // Check if there are more students in the current batch or load the next batch.
        return currentIndex < currentBatch.size() || loadNextBatch();
    }

    /**
     * Get the next student in the iterator.
     *
     * @return The next student.
     * @throws NoSuchElementException if there are no more students.
     */
    @Override
    public Student next() {
        if (!hasNext()) {
            // Throw an exception if there are no more students.
            throw new NoSuchElementException("No more students");
        }
        // Return the current student and move the index to the next student.
        return currentBatch.get(currentIndex++);
    }

    /**
     * Get the previous student in the iterator.
     *
     * @return The previous student.
     * @throws NoSuchElementException if there are no more students in reverse.
     */
    @Override
    public Student reverseNext() {
        if (currentIndex == 0 && currentPage > 1) {
            // If at the start of the batch and not on the first page, load the previous batch.
            currentPage -= 2;  // Decrement the currentPage by 2 to move to the previous batch.
            loadNextBatch();   // Load the batch.
            currentIndex = currentBatch.size();  // Set the index to the end of the batch.
        }
        if (currentIndex == 0) {
            // Throw an exception if there are no more students in reverse.
            throw new NoSuchElementException("No more students in reverse");
        }
        // Return the previous student and move the index back.
        return currentBatch.get(--currentIndex);
    }
}
