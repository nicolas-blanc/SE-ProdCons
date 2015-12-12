/**
 * 
 */
package jus.poc.prodcons.v;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

/**
 * @author nasheis
 *
 */
public class Buffer implements Tampon {
	
	// private Message[]; Creer un tableau de Message de Taille t
	
	public Buffer() {
		// Message[] = new Message[]
	}

	/* (non-Javadoc)
	 * @see jus.poc.prodcons.Tampon#enAttente()
	 */
	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jus.poc.prodcons.Tampon#get(jus.poc.prodcons._Consommateur)
	 */
	@Override
	public Message get(_Consommateur arg0) throws Exception,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jus.poc.prodcons.Tampon#put(jus.poc.prodcons._Producteur, jus.poc.prodcons.Message)
	 */
	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,
			InterruptedException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jus.poc.prodcons.Tampon#taille()
	 */
	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return 0;
		// return Message[].lenght();
	}

}
