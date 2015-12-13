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
	
	private Message[] buffer; //Creer un tableau de Message de Taille t
	
	private int tailleBuffer;
	private int in, out;
	private int nbplein, nbvide;
	
	public ProdCons(int taille) {
		tailleBuffer = taille;
		
		nbvide = tailleBuffer;
		nbplein = 0;
		in = 0;
		out = 0;
		
		buffer = new Message[tailleBuffer];
	}

	@Override
	public int enAttente() {
		return nbplein;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while(nbplein == 0) { wait(); }
		
		nbplein--;
		
		Message m = buffer[out];
		System.out.println("-----> Message Récupéré => " + m.toString() );
		out = (out + 1) % tailleBuffer;
		
		nbvide++;
		
		notifyAll();
		
		return m;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while (nbvide == 0) { wait(); }
		
		nbvide--;
		
		buffer[in] = arg1;
		System.out.println("-----> Message Inséré => " + arg1.toString() );
		in = (in + 1) % tailleBuffer;
		
		nbplein++;
		
		notifyAll();	
	}

	@Override
	public int taille() {
		return buffer.length;
	}

}

