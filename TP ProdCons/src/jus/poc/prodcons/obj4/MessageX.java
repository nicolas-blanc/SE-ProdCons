package jus.poc.prodcons.obj4;

import java.util.UUID;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class MessageX implements Message {

	/**
	 * Permet de connaitre le producteur qui à créé le message ainsi que le
	 * nombre de message créé par celui-ci Et de savoir quel consommateur l'a
	 * récupéré
	 */
	private int producteur;
	private int consommateur;
	private int num;
	private int numConsommable;
	private String messageId;

	public MessageX(_Producteur arg0, int arg1, int numConsommable) {
		producteur = arg0.identification();
		num = arg1;
		consommateur = 0;
		this.numConsommable = numConsommable;
		
		messageId = UUID.randomUUID().toString();
	}

	public void setConsommateur(_Consommateur arg0) {
		consommateur = arg0.identification();
	}

	@Override
	public String toString() {
		return "Le message a été produit par : " + this.producteur
				+ " // Avec le numéro : " + this.num
				+ "\n Et récupéré par : " + this.consommateur;
	}

	public int getNumConsommable() {
		return numConsommable;
	}

	public void setNumConsommable(int numConsommable) {
		this.numConsommable = numConsommable;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	

	
	
}
