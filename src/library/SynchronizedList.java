package library;

import java.util.ArrayList;

public class SynchronizedList<T> {

	private ArrayList<Object> list;
	//private SynchronizedQueue<Object> queue;
	private String name;
	
	public SynchronizedList(String name) {
	    this.name = name;
	    list = new ArrayList<Object>();
	}

	/*public SynchronizedList(String name, int capacity) {
	    this.name = name;
	    queue = new SynchronizedQueue(capacity);
	}*/
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public int size() { return list.size(); }
	
	synchronized public T remove() throws InterruptedException {
		while (list.size() == 0) wait();
		notifyAll();
		return (T) list.remove(0);
	}

	synchronized public void add(T obj) throws InterruptedException {
		list.add(obj);
		notifyAll();
	}
	
}