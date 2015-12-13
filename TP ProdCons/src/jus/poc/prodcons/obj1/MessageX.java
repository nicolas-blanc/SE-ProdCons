package jus.poc.prodcons.obj1;

import jus.poc.prodcons.Message;
	import jus.poc.prodcons._Producteur;

public class MessageX implements Message {

	/**
	 * Permet de connaitre le producteur qui à créé le message ainsi que le nombre de message créé par celui-ci
	 * Et de savoir quel consommateur l'a récupéré
	 */
	private int producteur;
	private int num;
	
	public MessageX(_Producteur arg0, int arg1) {
		producteur = arg0.identification();
		num = arg1;
	}

	@Override
	public String toString() {
		return "Le message a été produit par : " + this.producteur + " // Avec le numéro : " + this.num;
	}

	
}
