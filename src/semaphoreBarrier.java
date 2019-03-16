import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class semaphoreBarrier implements Barrier {
	//Private members and methods here
	private AtomicInteger arrived = new AtomicInteger(0), cycle = new AtomicInteger(2); //
	private AtomicInteger leaving = new AtomicInteger(0);
	private int count = 0; // use this instead of AtomicInts
	private int max = 0; //maximum threads barrier will count
	private boolean isReturning = false;
	private final Semaphore mutex = new Semaphore(0),
			barrier1 = new Semaphore(1);

	public semaphoreBarrier(int N) {
		max = N;
		//throw new java.lang.UnsupportedOperationException("Semaphore not supported yet.");
	}

	public void arriveAndWait() {
		if(cycle.get()%2 == 0){
			try{
				mutex.drainPermits();
				//System.out.println("I got here!");
				barrier1.acquire();
				if(arrived.incrementAndGet()%max == 0){
					cycle.incrementAndGet();

					mutex.release();
					//System.out.println("I got here too!" + arrived.get() + " " + cycle.get());
				}
				//System.out.println(arrived.get());
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally{
				barrier1.release();
			}
			try{
				mutex.acquire();
				leaving.incrementAndGet();
				if(cycle.get()%2 == 0) count++;
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		} else {
			mutex.release();
			//mutex.drainPermits();
			if(leaving.get()%max == 0)
				cycle.incrementAndGet();
		}
	}
}