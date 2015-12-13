package jus.poc.prodcons.obj1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private TestProdCons testProdCons;
	
	private int nbMessageProduire;
	private Tampon tampon;

	public Producteur(TestProdCons testProdCons, Tampon tampon, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, int nombreMoyenDeProduction, int deviationNombreMoyenDeProduction) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.testProdCons = testProdCons;
		
		this.tampon = tampon;
		this.nbMessageProduire = Aleatoire.valeur(nombreMoyenDeProduction, deviationNombreMoyenDeProduction);
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMessageProduire;
	}
	
	private void production(int numMessage) {
		MessageX message = new MessageX(this, numMessage);
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
		
		this.testProdCons.enleverProducteur();
		
		System.out.println("Fin de production de message -- Producteur : " + this.identification());
	}

}
