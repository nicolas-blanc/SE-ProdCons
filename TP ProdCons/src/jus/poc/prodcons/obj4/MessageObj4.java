package jus.poc.prodcons.obj4;

import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.obj1.MessageX;

public class MessageObj4 extends MessageX {

	/**
	 * Permet de connaitre le producteur qui à créé le message ainsi que le
	 * nombre de message créé par celui-ci Et de savoir quel consommateur l'a
	 * récupéré
	 */
	private int nbExemplaire;

	public MessageObj4(_Producteur arg0, int arg1, int nbExemplaire) {
		super(arg0, arg1);
		this.nbExemplaire = nbExemplaire;
	}

	@Override
	public String toString() {
		return "Le message a été produit par : " + this.producteur + " // Avec le numéro : " + this.num + " // Il reste " + this.nbExemplaire + " exemplaire(s)";
	}

	public int getNbExemplaire() {
		return this.nbExemplaire;
	}
	
	public int getProducteur() {
		return this.producteur;
	}
	
	public void diminuerNbExemplaire() {
		this.nbExemplaire--;
	}
	
	

	
	
}
