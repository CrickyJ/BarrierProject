public class monitorBarrier implements Barrier
{
	/*
	 *	Private members and methods here
	 */
	enum state {arriving, leaving}//condition - barrier is reached at start and end of critical section

	public monitorBarrier(int N)
	{
		state[] s = new state[N]; //every thread will have its own state
		//throw new java.lang.UnsupportedOperationException("Monitor not supported yet.");
	}
	public void arriveAndWait()
	{
		initialize();
		/*
		 *	Your code here
		 */
		//throw new java.lang.UnsupportedOperationException("Monitor not supported yet.");
	}

	private void initialize() {
		//Initialize Monitor?
	}

}
