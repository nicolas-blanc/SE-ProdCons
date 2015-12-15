package jus.poc.prodcons.obj2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj1.ProdCons;

public class ProdConsSem extends ProdCons {
	
	protected Sem semP = new Sem(1);
	protected Sem semC = new Sem(1);
	
	public ProdConsSem(int taille, int nbProd, int nbCons) {
		super(taille, nbProd, nbCons);
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
		System.out.println("// ----- || -----\\ Changer variable stop || semP : " + semP.getValeur() + " || semC : " + semC.getValeur() + " // ----- || -----\\");
		System.out.println("// ----- || -----\\ ProdConsSemaphore // ----- || -----\\ ");
		while (nbCons != 0) {
			semC.V();
		}
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while(nbplein == 0 && !stop) { semC.P(); }
		
		if(!stop) {
			nbplein--;
			
			Message m = buffer[out];
			out = (out + 1) % tailleBuffer;
			
			nbvide++;
			
			semP.V();
			
			return m;
		} else {
			return null;
		}
	}
	
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while(nbvide == 0  && !stop) { semP.P(); }
		
		if(!stop) {
			nbvide--;
			
			buffer[in] = arg1;
			in = (in + 1) % tailleBuffer;
			
			nbplein++;
			
			semC.V();
		}
	}
}
