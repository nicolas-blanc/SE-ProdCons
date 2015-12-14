/**
 * 
 */
package jus.poc.prodcons.obj1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

/**
 * @author nasheis
 *
 */
public class ProdCons implements Tampon {
	
	protected Message[] buffer; //Creer un tableau de Message de Taille t
	
	protected int tailleBuffer;
	protected int in, out;
	protected int nbplein;
	
	protected boolean stop;
	
	private int nbvide;
	
	public ProdCons(int taille) {
		tailleBuffer = taille;
		
		nbvide = tailleBuffer;
		nbplein = 0;
		in = 0;
		out = 0;
		
		buffer = new Message[tailleBuffer];
		
		stop = false;
	}
	
	public synchronized void finProg() {
		stop = true;
		System.out.println("// ----- || -----\\ Changer variable stop // ----- || -----\\");
		notifyAll();
	}
	
	public boolean getStop(){
		return stop;
	}

	@Override
	public int enAttente() {
		return nbplein;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while(nbplein == 0 && !stop) { wait(); }
		
		if(!stop) {
			synchronized (buffer) {		
				nbplein--;
				
				Message m = buffer[out];
				System.out.println("-----> Message Récupéré => " + m.toString() );
				out = (out + 1) % tailleBuffer;
				
				nbvide++;
				
				notifyAll();
			
				return m;
			}
		} else {
			return null;
		}
			
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while (nbvide == 0 && !stop) { wait(); }

		if(!stop) {
			synchronized (buffer) {
				nbvide--;
				
				buffer[in] = arg1;
				System.out.println("-----> Message Inséré => " + arg1.toString() );
				in = (in + 1) % tailleBuffer;
			
				nbplein++;
				
				notifyAll();
			}
		}
	}

	@Override
	public int taille() {
		return buffer.length;
	}
}

