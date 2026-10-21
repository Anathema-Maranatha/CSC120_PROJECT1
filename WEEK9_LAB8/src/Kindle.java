/**
 * Stores the information a Kindle keeps about a book a user is reading:
 * the number of pages in the book and the page the reader has reached.
 * The reader can turn one page or several pages at a time, but never
 * past the last page.
 *
 * @author Maxime Manuel Afonso
 * @version 1.1
 */
public class Kindle {

    /** The number of pages in the book. */
    private int numberOfPages;

    /** The page the reader is currently on (1 to numberOfPages). */
    private int pagesRead;

    /**
     * Receives the number of pages in the book and
     * sets the current page number to 1.
     *
     * @param numberOfPages the number of pages in the book
     */
    public Kindle(int numberOfPages) {
        this.numberOfPages = numberOfPages;
        pagesRead = 1;
    }

    /**
     * Returns the current page and the number of pages in the book as text,
     * in the form "Page   5 of  33.".
     *
     * @return the formatted current page and number of pages
     */
    public String toString() {
        return String.format("Page %3d of %3d.", pagesRead, numberOfPages);
    }

    /**
     * Increments the current page number by one, unless that would go
     * past the end of the book.
     */
    public void turnPages() {
        turnPages(1);
    }

    /**
     * Increases the current page number by the given number of pages.
     * If the increase would take the current page number past the end of
     * the book, an error message is issued and the current page number
     * is set to the last page instead.
     *
     * @param pagesTurned the number of pages to turn
     */
    public void turnPages(int pagesTurned) {
        if (pagesRead + pagesTurned > numberOfPages) {
            System.out.println("You were on                : " + this);
            System.out.println("Turning " + pagesTurned + " pages would take you past the last page.");
            pagesRead = numberOfPages;
            System.out.println("You are now on             : " + this);
        }
        else {
            pagesRead = pagesRead + pagesTurned;
        }
    }
}// end of the Kindle class