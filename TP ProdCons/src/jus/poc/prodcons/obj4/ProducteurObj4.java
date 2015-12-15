package jus.poc.prodcons.obj4;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons.obj1.ProdCons;
import jus.poc.prodcons.obj1.Producteur;

public class ProducteurObj4 extends Producteur {

	private int nombreMoyenNbExemplaire;
	private int deviationNombreMoyenNbExemplaire;
	
	public ProducteurObj4(Tampon tampon, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, int nombreMoyenDeProduction, int deviationNombreMoyenDeProduction, int nombreMoyenNbExemplaire, int deviationNombreMoyenNbExemplaire) throws ControlException {
		super(tampon, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement, nombreMoyenDeProduction, deviationNombreMoyenDeProduction);
		this.nombreMoyenNbExemplaire = nombreMoyenNbExemplaire;
		this.deviationNombreMoyenNbExemplaire = deviationNombreMoyenNbExemplaire;
	}
	
	private void production(int numMessage) {
		MessageObj4 message = new MessageObj4(this, numMessage, Aleatoire.valeur(this.nombreMoyenNbExemplaire, this.deviationNombreMoyenNbExemplaire));
		try {
			sleep(Aleatoire.valeur(this.moyenneTempsDeTraitement, this.deviationTempsDeTraitement) * 100);
			tampon.put(this, message);
			System.out.println("*** *** *** Inserted message : " + message.toString() + " *** *** ***");
		} catch (Exception e) {
			System.out.println("// ----- Nouvelle Exception : Exception IN Producteur ----- //");
			e.printStackTrace();
		}
	}

	/**
	 * Méthode lancé à la création du thread de Producteur : Produis un
	 * certain nombre de message et les mets dans le buffer Si il a produitun
	 * nombre suffisant de message, il s'arrète.
	 */
	public void run() {
		System.out.println("Producteur : " + this.identification() + " // nombre de message : " + this.nombreDeMessages());
		
		for(int i = 0; i < nbMessageProduire; i++){
			System.out.println("Production nouveau message -- Producteur : " + this.identification());
			production(i);
		}
		
		((ProdCons) this.tampon).enleverProducteur();
		
		System.out.println("Nombre de message restant dans le Tampon : " + this.tampon.enAttente() + " // Nombre de producteurs restant : " + ((ProdCons) this.tampon).getProd());
		System.out.println("Fin de production de message -- Producteur : " + this.identification());
	}

}
