/**
 * 
 */
package jus.poc.prodcons.obj4;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj2.Sem;

public class ProdConsSemaphore extends jus.poc.prodcons.obj2.ProdConsSemaphore {
	
	Sem[] semProd;
	
	public ProdConsSemaphore(int taille, int nbProd, int nbCons) {
		super(taille, nbProd, nbCons);
		semV = new Sem(tailleBuffer); //semaphore pour verifier le nombre de places libres dans le buffer
		
		this.semProd = new Sem[nbProd];
		for (int i = 0; i < semProd.length; i++) {
			semProd[i] = new Sem(1);
		}
	}
	
	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		semP.P(); //decrementer le nombre de places occupees
		mutex.P(); // pour l'exclusion mutuelle, semaphore binaire
		if(!stop) {
			synchronized (buffer) {	
				MessageObj4 m = (MessageObj4) buffer[out];
				m.diminuerNbExemplaire();
				
				if (m.getNbExemplaire() == 0) {
					out = (out + 1) % tailleBuffer;
					semProd[m.getProducteur() - 1].V();
					
					nbplein--;
				}
				
				mutex.V();
				if (m.getNbExemplaire() == 0) {
					semV.V(); //incrementer le nombre de places libres
				} else {
					semP.V();
				}
				
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
				
				semProd[((MessageObj4) arg1).getProducteur() - 1].P();
				
				nbplein++;
				
				mutex.V();
				semP.V(); //incrementer le nombre de places occupees
			}
		}
	}
}

