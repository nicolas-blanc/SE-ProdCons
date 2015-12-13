package jus.poc.prodcons.obj4;

import java.util.Random;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int nbMessageProduire;
	private Tampon tampon;

	public Producteur(Tampon tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {

		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement,
				deviationTempsDeTraitement);
		
		this.tampon = tampon;
		this.nbMessageProduire = Aleatoire.valeur(
				this.moyenneTempsDeTraitement, this.deviationTempsDeTraitement);
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMessageProduire;
	}

	/**
	 * Méthode lancé à la création du thread de Producteur : Produis un
	 * certain nombre de message et les mets dans le buffer Si il a produitun
	 * nombre suffisant de message, il s'arrète.
	 */
	public void run() {
		for(int i = 0; i < nbMessageProduire; i++){
		
			Random random = new Random();
			int nr = random.nextInt(10);
			while(nr == 0)
			{
				nr = random.nextInt(10);
			}
			MessageX message = new MessageX(this,i, nr);
			try {
				tampon.put(this, message);
				System.out.println("Inserted message " + nr + " times with id" + message.getMessageId() );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
