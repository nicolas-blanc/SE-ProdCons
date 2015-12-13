package jus.poc.prodcons.obj4;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends Simulateur {

	protected void init(String file) throws InvalidPropertiesFormatException,
			IOException, IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
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
	 * Initialise selon le fichier option, puis créé le buffer, avec un
	 * certain nombre d'espace libre et enfin créé un certain nombre de thread
	 * producteur et consommateur
	 */
	protected void run() throws Exception {
		// init("jus/poc/prodcons/options/options.xml");

		Tampon tampon = new ProdConsSemaphore(10);

		Runnable producteur = new Producteur(tampon, observateur, 2, 1);

		ExecutorService executor = Executors.newFixedThreadPool(10);

		executor.submit(producteur);
		for (int i = 0; i < 50; i++) {
			executor.submit(new Consommateur(tampon, observateur, 2, 1));
		}

		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
		System.out.println("All tasks finished");

	}

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();

	}
}