package jus.poc.prodcons.obj6;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ObservateurPropre {

	private int numberProductors;
	private int numberConsumers;
	private int numberMessagesBuffer;

	private Set<_Producteur> productorsSet;
	private Set<_Consommateur> consumerSet;
	private Set<Message> messagesBuffer;

	private Hashtable<_Producteur, Message> messagesToPut;
	private Hashtable<_Consommateur, Message> messagesToGet;

	public void init(int numberProductors, int numberConsumers, int numberMessagesBuffer)
			throws ControlException {
		this.numberProductors = numberProductors;
		this.numberConsumers = numberConsumers;
		this.numberMessagesBuffer = numberMessagesBuffer;
		
		productorsSet = new HashSet<>();
		consumerSet = new HashSet<>();
		messagesBuffer = new HashSet<>();
		messagesToGet = new Hashtable<>();
		messagesToPut = new Hashtable<>();
	}
	
	public void consommationMessage(_Consommateur c, Message m,
			int tempsDeTraitement) throws ControlException {
		if(c == null ||!(consumerSet.contains(c)))
		{
			throw new ControlException(c.getClass(), "consommationMessage_Consumer");
		}
		if(m == null)
		{
			throw new ControlException(c.getClass(), "consommationMessage_nullMessage");
		}
		if(tempsDeTraitement<=0)
		{
			throw new ControlException(c.getClass(), "consommationMessage_Temps_Traitement_Negative");
		}
		if(messagesToGet.remove(c,m)==false){
			throw new ControlException(c.getClass(), "consommationMessage");
		}
	}

	public void depotMessage(_Producteur p, Message m) throws ControlException {
		if (!productorsSet.contains(p) || p== null) {
			throw new ControlException(p.getClass(), "depotMessage_Producteur");
		}
		if(m == null)
		{
			throw new ControlException(p.getClass(), "depotMessage_nullMessage");
		}
		if(messagesToPut.remove(p)==null)
		{
			throw new ControlException(p.getClass(), "depotMessage");
		}

		if (messagesBuffer.size() > numberMessagesBuffer) {
			throw new ControlException(p.getClass(), "depotMessage");
		}

		this.messagesBuffer.add(m);

	}

	public void newConsommateur(_Consommateur c) throws ControlException {
		if(c== null)
		{
			throw new ControlException(this.getClass(), "newConsommateur_nullConsumer");
		}
		consumerSet.add(c);
		if (consumerSet.size() > numberConsumers) {
			throw new ControlException(this.getClass(), "newConsommateur");
		}
	}
	public void newProducteur(_Producteur p) throws ControlException {
		if(p== null)
		{
			throw new ControlException(this.getClass(), "newProducteur_nullProducer");
		}
		productorsSet.add(p);
		if (productorsSet.size() > numberProductors) {
			throw new ControlException(this.getClass(), "newProducteur");
		}
	}

	public void productionMessage(_Producteur p, Message m,
			int tempsDeTraitement) throws ControlException {
		if(p == null)
		{
			throw new ControlException(p.getClass(), "productionMessage_nullProducer");
		}
		if(m == null)
		{
			throw new ControlException(p.getClass(), "productionMessage_nullMessage");
		}
		if(tempsDeTraitement <=0)
		{
			throw new ControlException(p.getClass(), "productionMessage_TempsTraitementNegative");
		}
		if (!productorsSet.contains(p)) {
			throw new ControlException(p.getClass(), "productionMessage");
		}
		if (messagesToPut.containsKey(p)) {
			throw new ControlException(p.getClass(), "productionMessage");
		}
		messagesToPut.put(p, m);
	}

	public void retraitMessage(_Consommateur c, Message m)
			throws ControlException {
		if (!consumerSet.contains(c) || c==null || m==null) {
			throw new ControlException(c.getClass(), "retraitMessage");
		}
		if(messagesBuffer.remove(m)==false)
		{
			throw new ControlException(c.getClass(), "retraitMessage");
		}
		if (messagesToGet.containsKey(c)) {
			throw new ControlException(c.getClass(), "retraitMessage");
		}
		messagesToGet.put(c, m);

	}

}