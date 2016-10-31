package library;

public class SynchronizedQueue<T> {

	private Object[] queue;
	private int top;
	private int bot;
	private int size;
	private String name;
	
	public SynchronizedQueue(int capacity) {
	    queue = new Object[capacity];
	    top = 0;
	    bot = 0;
	    size = 0;
	}
	
	public SynchronizedQueue(String name, int capacity) {
		this.name = name;
	    queue = new Object[capacity];
	    top = 0;
	    bot = 0;
	    size = 0;
	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public int size() { return size; }
	
	synchronized public T remove() throws InterruptedException {
		while (size == 0) wait();
		top = (top + 1) % queue.length;
		size--;
		notifyAll();
		return (T) queue[top];
	}

	synchronized public void add(T obj) throws InterruptedException {
		while (size == queue.length) wait();
		bot = (bot + 1) % queue.length;
		queue[bot] = obj;
		size++;
		notifyAll();
	}
	
}