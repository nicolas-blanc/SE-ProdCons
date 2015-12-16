/**
 * 
 */
package jus.poc.prodcons.obj4;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj2.Sem;

public class ProdConsSemaphore extends jus.poc.prodcons.obj2.ProdConsSem {
	
	Sem[] semProd;
	Sem mutex = new Sem(1);
	
	public ProdConsSemaphore(int taille, int nbProd, int nbCons) {
		super(taille, nbProd, nbCons);
		
		this.semProd = new Sem[nbProd];
		for (int i = 0; i < semProd.length; i++) {
			semProd[i] = new Sem(1);
		}
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		if(!stop && nbvide == 0) {
			semP.V();
		}
		
		while(nbplein == 0 && (!stop || nbvide != 0)) { semC.P(); }
		
		if(!stop || nbvide != 0) {
			
			mutex.P();
			
			MessageObj4 m = (MessageObj4) buffer[out];

			m.diminuerNbExemplaire();
			
			if (m.getNbExemplaire() <= 0) {
				out = (out + 1) % tailleBuffer;
				semProd[m.getProducteur() - 1].V();
				
				nbplein--;
			}
			
			nbvide++;

			if (m.getNbExemplaire() <= 0) {
				semP.V();
			} else {
				semC.V();
			}

			mutex.V();
			
			return m;
		} else {
			return null;
		}
	}
	
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while(nbvide == 0  && !stop) { semP.P(); }
		
		if(!stop || nbvide != 0) {
			nbvide--;
			
			buffer[in] = arg1;
			in = (in + 1) % tailleBuffer;
	
			semProd[((MessageObj4) arg1).getProducteur() - 1].P();
			
			nbplein++;
			
			semC.V();
		}
	}
}

