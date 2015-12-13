/**
 * 
 */
package jus.poc.prodcons.obj1;

import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdConsSemaphore implements Tampon {
	
	private Message[] buffer; //Creer un tableau de Message de Taille t
	
	private int tailleBuffer;
	private int in, out;
	Semaphore semP = new Semaphore(0); //un semaphore pour verifier le nombre de places occupees dans le buffer
	Semaphore mutex = new Semaphore(1); //un semaphore pour l'exclusion mutuelle
	Semaphore semV;
	
	public ProdConsSemaphore(int taille) {
		tailleBuffer = taille;
		semV = new Semaphore(tailleBuffer); //semaphore pour verifier le nombre de places libres dans le buffer
		in = 0;
		out = 0;
		buffer = new Message[tailleBuffer];
	}

	@Override
	public int enAttente() {
		return semP.availablePermits();
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		
		semP.acquire(); //decrementer le nombre de places occupees
		mutex.acquire(); // pour l'exclusion mutuelle, semaphore binaire
		Message m = buffer[out];
		out = (out + 1) % tailleBuffer;
		mutex.release();
		semV.release(); //incrementer le nombre de places libres
		return m;
	}
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		semV.acquire(); //decrementer le nombre de places libres
		mutex.acquire();
		buffer[in] = arg1;
		in = (in + 1) % tailleBuffer;
		mutex.release();
		semP.release(); //incrementer le nombre de places occupees
}

	@Override
	public int taille() {
		return buffer.length;
	}

}

