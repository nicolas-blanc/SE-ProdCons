/**
 * 
 */
package jus.poc.prodcons.obj5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdConsLock implements Tampon {
	
	private Message[] buffer; //Creer un tableau de Message de Taille t
	
	private final Lock lock = new ReentrantLock();
	private final Condition fullBuffer = lock.newCondition();// si le buffer est plein, cette condition va bloquer
	private final Condition emptyBuffer = lock.newCondition();
	private int tailleBuffer;
	private int in, out;
	int nbMessagesBuffer = 0;

	private Observateur observateur;
	
	public ProdConsLock(Observateur observateur, int taille) {
		tailleBuffer = taille;
		in = 0;
		out = 0;
		buffer = new Message[tailleBuffer];
		this.observateur = observateur;
	}

	@Override
	public synchronized int enAttente() {
		return nbMessagesBuffer;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		
		lock.lock();
		while (nbMessagesBuffer <= 0) {
			this.emptyBuffer.await();
		}
		Message m = buffer[out];
		out = (out + 1) % tailleBuffer;
		nbMessagesBuffer--;
		this.observateur.retraitMessage(arg0, m);
		this.fullBuffer.signal();
		lock.unlock();
		return m;
	}
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		lock.lock();
		while(nbMessagesBuffer >=tailleBuffer)
		{
			this.fullBuffer.await();
		}
		buffer[in] = arg1;
		in = (in + 1) % tailleBuffer;
		nbMessagesBuffer++;
		this.observateur.depotMessage(arg0, arg1);
		this.emptyBuffer.signal();
		lock.unlock();
}

	@Override
	public int taille() {
		int bufferLength;
		lock.lock();
		bufferLength = buffer.length;
		lock.unlock();
		return bufferLength;
		
	}

}

