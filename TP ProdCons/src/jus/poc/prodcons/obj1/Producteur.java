package jus.poc.prodcons.v;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{
	
	private int nbMessageProduire;

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		
		this.nbMessageProduire = Aleatoire.valeur(this.moyenneTempsDeTraitement, this.deviationTempsDeTraitement);
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMessageProduire;
	}
	
	
	/**
	 * Méthode lancé à la création du thread de Producteur :
	 * Produis un certain nombre de message et les mets dans le buffer
	 * Si il a produitun nombre suffisant de message, il s'arrète.
	 */
	public void run() {
		
	}

}
