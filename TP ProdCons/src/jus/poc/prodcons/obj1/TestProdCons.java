package jus.poc.prodcons.obj1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends Simulateur {
	
	protected int nbProd;
	protected int nbCons;
	protected int nbBuffer;
	protected int tempsMoyenProduction;
	protected int deviationTempsMoyenProduction;
	protected int tempsMoyenConsommation;
	protected int deviationTempsMoyenConsommation;
	protected int nombreMoyenDeProduction;
	protected int deviationNombreMoyenDeProduction;
	protected int nombreMoyenNbExemplaire;
	protected int deviationNombreMoyenNbExemplaire;

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
		try {
			init("jus/poc/prodcons/options/options.xml");
		} catch (IllegalArgumentException
				| IllegalAccessException | NoSuchFieldException
				| SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void createThread(Tampon tampon) throws ControlException {
		ArrayList<Runnable> producteur = new ArrayList<Runnable>();
		ArrayList<Runnable> consommateur = new ArrayList<Runnable>();
		ArrayList<Thread> threadProducteur = new ArrayList<Thread>();
		ArrayList<Thread> threadConsommateur = new ArrayList<Thread>();
		
		for (int i = 0; i < nbProd; i++) {
			producteur.add(new Producteur(tampon, observateur, tempsMoyenProduction, deviationTempsMoyenProduction, nombreMoyenDeProduction, deviationNombreMoyenDeProduction));
		}
		
		for (int i = 0; i < nbCons; i++) {
			consommateur.add(new Consommateur(tampon, observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation));
		}
		
		for (Runnable p : producteur) {
			threadProducteur.add(new Thread(p));
		}
		
		for (Runnable c : consommateur) {
			threadProducteur.add(new Thread(c));
		}
		
		for (Thread thread : threadProducteur) {
			thread.start();
		}
		
		for (Thread thread : threadConsommateur) {
			thread.start();
		}
	}

	/**
	 * Initialise selon le fichier option,
	 * puis créé le buffer, avec un certain nombre d'espace libre
	 * et enfin créé un certain nombre de thread producteur et consommateur
	 */
	protected void run() throws Exception {		
		Tampon tampon = new ProdCons((nbBuffer-1),nbProd,nbCons);
		
		this.createThread(tampon);

//		System.out.println("// ----- ----- \\ Fin de TestProdCons // ----- ----- \\");
	}

	public static void main(String[] args) {
//		System.out.println("// ----- ----- \\ Lancement programme : Obj1 // ----- ----- \\");
		new TestProdCons(new Observateur()).start();
		
	}
}