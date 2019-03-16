import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class semaphoreBarrier implements Barrier { //TODO: UPDATE semaphoreBarrier
	//Private members and methods here
	private AtomicInteger count = new AtomicInteger(0); //
	//private AtomicInteger leaving = new AtomicInteger(0);
	//private int count = 0; // use this instead of AtomicInts
	private int max = 0; //maximum threads barrier will count
	private boolean isReturning = false; //true on second call of arriveAndWait()
	Semaphore mutex = new Semaphore(1), barrier = new Semaphore(0); //mutex starts locked, barrier starts unlocked

	public semaphoreBarrier(int N) {
		max = N;
		//throw new java.lang.UnsupportedOperationException("Semaphore not supported yet.");
	}

	public void arriveAndWait() {

		//System.out.println(isReturning);
		//System.out.println(max);
//		if (isReturning) { //on second call
//			//System.out.println("RELEASE");
//			//barrier.release();
//			//System.out.println(barrier.availablePermits());
//		}
		try {
			mutex.acquire();
			//System.out.println("Count = " + count.incrementAndGet());
			//if(max == count.incrementAndGet()) {
			if(count.get() == max) { //All threads counted
				//System.out.println("PASS -- RELEASE BARRIER");
				isReturning = false;
				count.set(0);
				barrier.release();
			}
			mutex.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try { //LEAVE BARRIER
			barrier.acquire(); //Wait until last thread
			barrier.release();
			if(count.get() != 0) {
				System.out.println("WHY LEAVE? " + count.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void printMutex() {
		System.out.println("MUTEX: " + mutex.getQueueLength());
	}

	private void printBarrier() {
		System.out.println("BARRIER: " + barrier.getQueueLength());
	}
}