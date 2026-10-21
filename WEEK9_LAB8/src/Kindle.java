import jdk.swing.interop.SwingInterOpUtils;

public class Kindle {

    private int numberOfPages;
    private int pagesRead;

    public Kindle(int numberOfPages) {
        this.numberOfPages = numberOfPages;
        pagesRead = 1;
    }
    public String toString() {
        return String.format("Page %3d of %3d.", pagesRead, numberOfPages);
        }
    public void turnPages() {
        turnPages(1);
    }
    public void turnPages(int pagesTurned){
        if (pagesRead + pagesTurned > numberOfPages) {
            System.out.println("You were on                : " + this);
            System.out.println("Turning " + pagesTurned + " pages would take you past the last page.");
            pagesRead = numberOfPages;
            System.out.println("You are now on             : " + this);
        }
        else {
            pagesRead = pagesRead + pagesTurned;;
        }
    }
}// end of the Kindle class
