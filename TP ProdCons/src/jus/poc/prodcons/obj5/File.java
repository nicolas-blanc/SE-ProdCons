package jus.poc.prodcons.obj5;

public class File {
	public File() {}
	
	public synchronized void attente() throws InterruptedException {
		wait();
	}
	
	public synchronized void reveiller() {
		notify();
	}
}
