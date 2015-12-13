package jus.poc.prodcons.obj1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends Simulateur {

	protected  void init(String file) throws InvalidPropertiesFormatException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Properties properties = new Properties();
		properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
		String key;
		int value;
		Class<?> thisOne = getClass();
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			key = (String) entry.getKey();
			value = Integer.parseInt((String) entry.getValue());
			thisOne.getDeclaredField(key).set(this, value);
		}
	}

	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	/**
	 * Initialise selon le fichier option,
	 * puis créé le buffer, avec un certain nombre d'espace libre
	 * et enfin créé un certain nombre de thread producteur et consommateur
	 */
	protected void run() throws Exception {
		//init("jus/poc/prodcons/options/options.xml");
		
		Tampon tampon = new ProdConsSemaphore(10);
		
		Runnable producteur = new Producteur(tampon, observateur, 2, 1);
		
		
		Runnable consommateur = new jus.poc.prodcons.obj1.Consommateur(tampon, observateur, 
				2, 1);
		
		Thread thread1 = new Thread(producteur);
		Thread thread2 = new Thread(consommateur);
		thread1.start();
		thread2.start();
		
	}

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
		
	}
}