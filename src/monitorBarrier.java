import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class monitorBarrier implements Barrier
{
	/*
	 *	Private members and methods here
	 */
	enum threadState {arriving, leaving}//condition - barrier is reached at start and end of critical section

    private AtomicInteger count = new AtomicInteger(0); //AtomicInteger might be needed to count threads

   //private int count = 0; //count threads as they reach barrier
	private int max; //maximum amount of threads barrier will track
    private threadState[] state;

	public monitorBarrier(int N)
	{
	    max = N;
		state = new threadState[N]; //every thread will have its own state
		//throw new java.lang.UnsupportedOperationException("Monitor not supported yet.");
	}
	public void arriveAndWait()
	{
		initialize(count.incrementAndGet());
		/*
		 *	Your code here
		 */
		//throw new java.lang.UnsupportedOperationException("Monitor not supported yet.");
	}

	private void signal() { //Used when last thread reaches barrier
	    this.notifyAll(); //Notify all other threads
    }

	private void initialize(int n) {
	    state[n] = threadState.arriving; //all threads start as arriving on first arriveAndWait() call
	}

}
