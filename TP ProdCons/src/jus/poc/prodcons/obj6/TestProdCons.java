package jus.poc.prodcons.obj6;

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
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.options.XmlReader;

public class TestProdCons extends Simulateur {

	private ObservateurPropre observateurPropre;
	private Observateur observateur;
	
	

	public TestProdCons(ObservateurPropre observateurPropre,Observateur observateur ) {
		super(observateur);
		this.observateur = observateur;
		this.observateurPropre = observateurPropre;
	}
	
	protected void run() throws Exception {
		System.out.println("Objective 6");
		
		Map<String, Integer> properties = XmlReader.readFromXml();
		Integer numberOfConsumers = properties.get("nbCons");
		Integer numberOfProducers = properties.get("nbProd");
		Integer numberMessagesBuffer = properties.get("nbBuffer");
		Integer tempsMoyenProduction = properties.get("tempsMoyenProduction");
		Integer deviationTempsMoyenProduction = properties.get("deviationTempsMoyenProduction");
		Integer tempsMoyenConsommation = properties.get("tempsMoyenConsommation");
		Integer deviationTempsMoyenConsommation = properties.get("deviationTempsMoyenConsommation");
		Integer nombreMoyenDeProduction = properties.get("nombreMoyenDeProduction");
		Integer deviationNombreMoyenDeProduction = properties.get("deviationNombreMoyenDeProduction");
		Integer nombreMoyenNbExemplaire = properties.get("nombreMoyenNbExemplaire");
	
		this.observateurPropre.init(numberOfProducers,numberOfConsumers, numberMessagesBuffer);
		Tampon tampon = new ProdConsSemaphore(this.observateurPropre,numberMessagesBuffer);

		ExecutorService executor = Executors.newFixedThreadPool(100);
		
		for(int i = 0; i < numberOfProducers; i++){
			
			Runnable producteur = new Producteur(tampon,observateurPropre, observateur, tempsMoyenProduction, deviationTempsMoyenProduction);
			this.observateurPropre.newProducteur((_Producteur) producteur);
			executor.submit(producteur);
		}
		
		for (int i = 0; i < numberOfConsumers; i++) {
			Consommateur consommateur = new Consommateur(tampon,observateurPropre, observateur,tempsMoyenConsommation,deviationTempsMoyenConsommation);
			this.observateurPropre.newConsommateur(consommateur);
			executor.submit(consommateur);
		}

		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
		System.out.println("All tasks finished");
	}

	public static void main(String[] args) {
		new TestProdCons(new ObservateurPropre(), new Observateur()).start();

	}
}