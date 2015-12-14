/**
 * 
 */
package jus.poc.prodcons.obj2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj1.ProdCons;

public class ProdConsSemaphore extends ProdCons {
	
	private Sem semP = new Sem(0); //un semaphore pour verifier le nombre de places occupees dans le buffer
	private Sem mutex = new Sem(1); //un semaphore pour l'exclusion mutuelle
	private Sem semV;
	
	public ProdConsSemaphore(int taille) {
		super(taille);
		semV = new Sem(tailleBuffer); //semaphore pour verifier le nombre de places libres dans le buffer
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		semP.P(); //decrementer le nombre de places occupees
		mutex.P(); // pour l'exclusion mutuelle, semaphore binaire

		Message m = buffer[out];
		out = (out + 1) % tailleBuffer;
		
		nbplein--;
		
		mutex.V();
		semV.V(); //incrementer le nombre de places libres
		
		return m;
	}
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		semV.P(); //decrementer le nombre de places libres
		mutex.P();
		
		buffer[in] = arg1;
		in = (in + 1) % tailleBuffer;
		
		nbplein++;
		
		mutex.V();
		semP.V(); //incrementer le nombre de places occupees
	}
}

