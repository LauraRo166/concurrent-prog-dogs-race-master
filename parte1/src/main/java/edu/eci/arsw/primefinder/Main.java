package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    public static void main(String[] args) throws IOException, InterruptedException {
        
        int start = 0;
        int end = 30000000;
        int range = (end - start) / 3;  

        PrimeFinderThread pft1 = new PrimeFinderThread(start, start + range);
        PrimeFinderThread pft2 = new PrimeFinderThread(start + range + 1, start + 2 * range);
        PrimeFinderThread pft3 = new PrimeFinderThread(start + 2 * range + 1, end);

        pft1.start();
        pft2.start();
        pft3.start();

        Thread.sleep(5000);

        pft1.pauseThread();
        pft2.pauseThread();
        pft3.pauseThread();

        int totalPrimes = pft1.getPrimes().size()
                        + pft2.getPrimes().size()
                        + pft3.getPrimes().size();

        System.out.println("\n==== Ejecución pausada ====");
        System.out.println("Primos encontrados hasta ahora: " + totalPrimes);
        System.out.println("Presione ENTER para continuar...");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();

        pft1.resumeThread();
        pft2.resumeThread();
        pft3.resumeThread();

        pft1.join();
        pft2.join();
        pft3.join();

        System.out.println("\n==== Ejecución terminada ====");
        System.out.println("Total de primos encontrados: " + (
                pft1.getPrimes().size() +
                pft2.getPrimes().size() +
                pft3.getPrimes().size()));
    
    }
}