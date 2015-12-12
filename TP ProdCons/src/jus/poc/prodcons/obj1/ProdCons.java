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
	
	private File fc, fp;
	
	public ProdCons(int taille) {
		tailleBuffer = taille;
		
		nbvide = tailleBuffer;
		nbplein = 0;
		in = 0;
		out = 0;
		
		buffer = new Message[tailleBuffer];
		
		fc = new File();
		fp = new File();
	}

	@Override
	public int enAttente() {
		return nbplein;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		
		while(nbplein == 0) { fc.attente(); }
		
		nbplein--;
		
		Message m = buffer[out];
		out = (out + 1) % tailleBuffer;
		
		nbvide++;
		
		fp.reveiller();
		
		return m;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		while (nbvide == 0) { fp.attente(); }
		
		nbvide--;
		
		buffer[in] = arg1;
		in = (in + 1) % tailleBuffer;
		
		nbplein++;
		
		fc.reveiller();
		
	}

	@Override
	public int taille() {
		return buffer.length;
	}

}

