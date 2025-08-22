package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;


/**
 * Thread implementation that finds all prime numbers within a given range.
 *
 * @author sergio.bejarano-r
 * @author laura.rsanchez
 */
public class PrimeFinderThread extends Thread{

	
	int a,b;
	
	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	/**
     * Executes the prime number search in the given interval.
     * <p>
     * Every prime number found is added to {@link #primes} and also
     * printed to the standard output.
     * </p>
     */
    @Override
	public void run(){
		for (int i=a;i<=b;i++){						
			if (isPrime(i)){
				primes.add(i);
				System.out.println(i);
			}
		}
		
		
	}
	
	/**
     * Checks whether a given number is prime.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is prime, {@code false} otherwise
     */
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	/**
     * Returns the list of prime numbers found by this thread.
     *
     * @return a list containing the discovered prime numbers
     */
	public List<Integer> getPrimes() {
		return primes;
	}
	
	
	
	
}
