package jus.poc.prodcons.obj1;

import jus.poc.prodcons.Message;

public class MessageX implements Message {

	/**
	 * Permet de connaitre le producteur qui à créé le message ainsi que le nombre de message créé par celui-ci
	 * Et de savoir quel consommateur l'a récupéré
	 */
	private int producteur;
	private int consommateur;
	private int num;

	@Override
	public String toString() {
		return "Le message a été produit par : " + this.producteur + " // Avec le numéro : " + this.num + "\n Et récupéré par : " + this.consommateur;
	}

	
}
