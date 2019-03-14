import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class semaphoreBarrier implements Barrier {
	//Private members and methods here
	private AtomicInteger arrived = new AtomicInteger(0); //
	//private AtomicInteger leaving = new AtomicInteger(0);
	//private int count = 0; // use this instead of AtomicInts
	private int max = 0; //maximum threads barrier will count
	private boolean isReturning = false;
	Semaphore mutex = new Semaphore(0), barrier = new Semaphore(1);

	public semaphoreBarrier(int N) {
		max = N;
		//throw new java.lang.UnsupportedOperationException("Semaphore not supported yet.");
	}

	public void arriveAndWait() {

		System.out.println(isReturning);
		System.out.println(max);
		if (isReturning) barrier.release();
		try {
			//arrived.getAndIncrement();
			// TODO set acquire limit to max then change boolean to true and release max
            /*if(mutex.getQueueLength() == max -1) {

                mutex.release(max);
            }*/
			mutex.release();
			mutex.acquire(max);
			if (!isReturning) {
				isReturning = true;
				mutex.release(max * max - max);
				System.out.println("One has made it through!");
			} else if (mutex.getQueueLength() == 0) isReturning = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("I got here!");
			System.out.println(mutex.availablePermits());
			barrier.acquire();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}