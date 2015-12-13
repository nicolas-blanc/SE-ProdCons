/**
 * 
 */
package jus.poc.prodcons.obj4;


import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj2.SemaphorePropre;

public class ProdConsSemaphore implements Tampon {
	
	private Message[] buffer; //Creer un tableau de Message de Taille t
	
	private int tailleBuffer;
	private int in, out;
	SemaphorePropre semP = new SemaphorePropre(0); //un semaphore pour verifier le nombre de places occupees dans le buffer
	SemaphorePropre mutex = new SemaphorePropre(1); //un semaphore pour l'exclusion mutuelle
	SemaphorePropre semV;
	
	public ProdConsSemaphore(int taille) {
		tailleBuffer = taille;
		semV = new SemaphorePropre(tailleBuffer); //semaphore pour verifier le nombre de places libres dans le buffer
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
		MessageX m = (MessageX) buffer[out];
		
		if(m.getNumConsommable() >=2 ) { //S'il reste encore 2 ou plusieurs messages dans le buffer
			System.out.println("Nombre de messages de meme type restes :"+m.getNumConsommable());
			int num = m.getNumConsommable();
			m.setNumConsommable(num - 1); //on decremente le nombre de messages restes
			semP.release(); // pour faire un nouveau acquire
			//return m;
		} else {
			out = (out + 1) % tailleBuffer; //si on a un msg de meme type, on libere la place
			semV.release(); //on increment le nombre de places libres
		}
		mutex.release();
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

