import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class monitorBarrier implements Barrier
{
	enum threadState {arriving, leaving}//condition - barrier is reached at start and end of critical section

    private AtomicInteger count = new AtomicInteger(0); //AtomicInteger might be needed to count threads

   //private int count = 0; //count threads as they reach barrier
	private int max; //maximum amount of threads barrier will track
    //private threadState[] state;
	private threadState state;

	public monitorBarrier(int N)
	{
	    max = N;
	    state = threadState.arriving;
//		state = new threadState[max]; //every thread will have its own state
//		for(int i = 0; i < max; i++) { //for each thread
//			state[i] = threadState.arriving;
//		}
		//throw new java.lang.UnsupportedOperationException("Monitor not supported yet.");
	}
	public synchronized void arriveAndWait()
	{
		//threadState current = state[count.incrementAndGet()]; //State of thread calling arriveAndWait()
		switch(state) {
			case arriving: //First time arriveAndWait() is called
				if (count.incrementAndGet() == max) {//if all threads have reached critical section
					this.signalAll(); //Last thread will wake all other threads
					state = threadState.leaving;
				}
				else { //if some threads have not reached barrier
					threadWait();
				}
				return; //All threads have been signaled, enter critical section

			case leaving: //Second time arriveAndWait() is called
				if(count.decrementAndGet() == 0) { //All threads have left critical section
					this.signalAll(); //Wake all threads
				}
				else {
					threadWait(); //Wait until signal
				}
				break;
			default:
				System.out.println("ERROR: UNKNOWN THREAD STATE");
				return;
			//throw new java.lang.UnsupportedOperationException("Monitor not supported yet.");
		} //END OF switch(state)

		initialize(); //re-initialize thread for re-use
	}

	private synchronized void threadWait() { //Thread will wait
		System.out.println("WAIT: Count = " + count.get());
	    try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void signalAll() { //Used when last thread reaches barrier
		System.out.println("Signal All");
		this.notifyAll(); //Notify all other threads
    }

	private void initialize() {
	    System.out.println("--EXIT--");
	    count.set(0);
	    state = threadState.arriving;
	}

}
