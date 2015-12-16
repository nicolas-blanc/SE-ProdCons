package jus.poc.prodcons.obj6;

public class File {
	public File() {}
	
	public synchronized void attente() throws InterruptedException {
		wait();
	}
	
	public synchronized void reveiller() {
		notify();
	}
}
