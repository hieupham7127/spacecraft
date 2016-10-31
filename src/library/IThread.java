package library;

public abstract class IThread extends Thread {
	protected long time = 0;
	protected boolean isRun = true; 
	protected boolean isPaused = false;
	protected SynchronizedList<Object> list = null;
	protected SynchronizedQueue<Object> queue = null;
	
	public IThread() { 
		start();
	}
	
	public IThread(SynchronizedQueue<Object> queue) {
		this.queue = queue;
		start();
	}

	public IThread(SynchronizedQueue<Object> queue, long millis) { 
		this.queue = queue;
		this.time = millis;	
		start();
	}
	
	public IThread(SynchronizedList<Object> list) {
		this.list = list;
		start();
	}
	
	public IThread(SynchronizedList<Object> list, long millis) {
		this.list = list;
		this.time = millis;
		start();
	}

	public void setWait(long millis) { time = millis; }
	public long getWait() { return time; }
	
	public void Stop() { isRun = false; }
	
	public void Pause() { isPaused = true; }
	public boolean isPaused() { return isPaused; }
	synchronized public void Resume() {
		isPaused = false;
		this.notify();
	}
	
	protected void pretasks() {} 
	abstract protected void tasks() throws InterruptedException;
	protected void finaltasks() {}
	
	@Override
	public void run() {
		pretasks();
		while (isRun) {
			try {
				synchronized(this) { while (isPaused) wait(); }
				tasks();	
				if (time > 0) sleep(time);
			} 
			catch (Exception e) {
				//System.out.println(this.getName());
				e.printStackTrace(System.out);
			} 
		} // ends loop
		finaltasks();
	}

}
