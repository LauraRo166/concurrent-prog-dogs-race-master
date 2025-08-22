package edu.eci.arsw.primefinder;

/**
 * Main program for concurrent prime number calculation.
 *
 * @author sergio.bejarano-r
 * @author laura.rsanchez
 */
public class Main {

    /**
     * Main method that splits the task into three concurrent threads.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        
        int start = 0;
        int end = 30000000;
        int range = (end - start) / 3;  

        PrimeFinderThread pft1 = new PrimeFinderThread(start, start + range);
        PrimeFinderThread pft2 = new PrimeFinderThread(start + range + 1, start + 2 * range);
        PrimeFinderThread pft3 = new PrimeFinderThread(start + 2 * range + 1, end);
A
        pft1.start();
        pft2.start();
        pft3.start();
    }
}