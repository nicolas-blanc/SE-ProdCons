/**
 * 
 */
package jus.poc.prodcons.obj2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj1.ProdCons;

public class ProdConsSemaphore extends ProdCons {
	
	protected Sem semP = new Sem(0); //un semaphore pour verifier le nombre de places occupees dans le buffer
	protected Sem mutex = new Sem(1); //un semaphore pour l'exclusion mutuelle
	protected Sem semV;
	
	public ProdConsSemaphore(int taille, int nbProd, int nbCons) {
		super(taille, nbProd, nbCons);
		semV = new Sem(tailleBuffer); //semaphore pour verifier le nombre de places libres dans le buffer
	}
	
	public void enleverProducteur() {
		this.nbProd--;
		if (nbProd == 0) {
			System.out.println("// ----- ----- \\ Fin du programme : nbprod = " + nbProd + "// ----- ----- \\");
			this.finProg();
		}
	}
	
	private synchronized void finProg() {
		stop = true;
		System.out.println("// ----- || -----\\ Changer variable stop || mutex : " + mutex.getValeur() + " || semV : " + semV.getValeur() + " || semP : " + semP.getValeur() + " // ----- || -----\\");
		System.out.println("// ----- || -----\\ ProdConsSemaphore // ----- || -----\\ ");
		while (nbCons != 0) {
			semP.V();
			semV.V();
			mutex.V();
		}
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		semP.P(); //decrementer le nombre de places occupees
		mutex.P(); // pour l'exclusion mutuelle, semaphore binaire
		if(!stop) {
			synchronized (buffer) {	
				Message m = buffer[out];
				out = (out + 1) % tailleBuffer;
				
				nbplein--;
				
				mutex.V();
				semV.V(); //incrementer le nombre de places libres
				
				return m;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		semV.P(); //decrementer le nombre de places libres
		mutex.P();

		if(!stop) {
			synchronized (buffer) {
				buffer[in] = arg1;
				in = (in + 1) % tailleBuffer;
				
				nbplein++;
				
				mutex.V();
				semP.V(); //incrementer le nombre de places occupees
			}
		}
	}
}

