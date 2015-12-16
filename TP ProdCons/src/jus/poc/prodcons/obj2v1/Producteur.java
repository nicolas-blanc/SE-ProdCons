package jus.poc.prodcons.obj2v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj1.MessageX;

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

	public void run() {
//		System.out.println("Producteur :" + this.identification() + "no messages: " + this.nombreDeMessages());
		int tempsProduction = 0;
		for(int i = 0; i < nbMessageProduire; i++){
		
			MessageX message = new MessageX(this, i);
			tempsProduction = Aleatoire.valeur(moyenneTempsDeTraitement(), deviationTempsDeTraitement()); 
//				System.out.println("Inserted message :" + message.toString() );
			try {
				Thread.sleep(tempsProduction);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				tampon.put(this, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
