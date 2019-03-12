import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class semaphoreBarrier implements Barrier
{
	//Private members and methods here
	private AtomicInteger arrived = new AtomicInteger(0); //
	private AtomicInteger leaving = new AtomicInteger(0);
	private int max = 0; //maximum threads barrier will count
	private boolean firstStart = true;

	public semaphoreBarrier(int N)
	{
		max = N;
		//throw new java.lang.UnsupportedOperationException("Semaphore not supported yet.");
	}
	public void arriveAndWait()
	{

		//		/****************************START OF LOOP****************************/
		//		mutex.wait()
		//		count += 1
		//		if (count == n) { //If all threads have been counted
		//			Barrier2.arriveAndWait() //lock second barrier
		//			Barrier1.signal() //unlock first barrier
		//		}
		//		mutex.signal()
		//
		//		Barrier1.arriveAndWait()
		////BARRIER 1 ----------------------------------------
		//		Barrier1.signal() //unblock other threads
		//
		////CRITICAL SECTION
		//
		//		mutex.wait()
		//		count -= 1;
		//		if (count == 0) { //If all threads have exited critical section
		//			Barrier1.arriveAndWait() //lock first barrier
		//			Barrier2.signal() //unlock second barrier
		//		}
		//		mutex.signal()
		//
		//		Barrier2.arriveAndWait()
		////BARRIER 2 ---------------------------------
		//		Barrier2.signal(); //unblock other threads
		//
		///******************************END OF LOOP******************************/

		if(firstStart) {
			arrived.incrementAndGet();
			System.out.println("Arrived: " + arrived.get() + " = " + max + "?");
			if (arrived.get() == max) { //If all threads have been counted
				lock2();//Barrier 2 lock
				unlock1();//SignalAll -- Barrier 1 unlock
				firstStart = false;
			}
			else {
				while(arrived.get() > 0)
					//System.out.println("Still " + arrived.get());
					;//Wait at barrier until signaled
			}
		}

		else {
			leaving.decrementAndGet();
			System.out.println("Leaving:" + leaving.get() + " = " + 0 + "?");
			if(leaving.get() == 0) {
				lock1();
				unlock2();
				firstStart = true;
			}
			else {
				while(leaving.get() < max)
					; //Wait
			}
		}
		if(firstStart) {
			System.out.println("EXIT BARRIER");
		}
		//throw new java.lang.UnsupportedOperationException("Semaphore not supported yet.");
	}

	private void lock1() { //Lock Barrier1, all threads left
	    arrived.set(0);
	    System.out.println("LOCK 1");
	}

	private void lock2() { //Lock Barrier2, all threads arrived
		leaving.set(max);
		System.out.println("LOCK 2");
	}

	private void unlock1() { //Unlock if all threads arrive
        arrived.set(0); //Unlock first barrier
		System.out.println("ALL ARRIVED: UNLOCK 1");
	}

	private void unlock2() { //Unlock if all threads leave
		leaving.set(0);
		System.out.println("ALL ARRIVED: UNLOCK 2");
	}
}
