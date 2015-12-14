package jus.poc.prodcons.obj1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur{
	
	private int nbMesssageLu;
	private Tampon tampon;

	public Consommateur(TestProdCons testProdCons, Tampon tampon, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.tampon = tampon;
		nbMesssageLu = 0;
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMesssageLu;
	}
	
	private void traiterMessage() {
		try {
			MessageX message = (MessageX) tampon.get(this);
			if (message != null) {
				System.out.println("*** *** *** Retrieve message : " + message.toString() + " *** *** ***\n*** *** *** Récupéré par : " + this.identification());
				sleep(Aleatoire.valeur(this.moyenneTempsDeTraitement, this.deviationTempsDeTraitement) * 100);				
			}
		} catch (InterruptedException e) {
			if (isInterrupted()) {
				Thread.currentThread().interrupt(); // réinterruption sur soi-même
				System.out.println("~~~~~~~~~~> Interruption dans traiterMessage()");
			}
			else {
				System.out.println("// ----- Nouvelle Exception : InterruptedException IN Consommateur ----- //");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("// ----- Nouvelle Exception : Exception IN Consommateur ----- //");
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode lancé à la création du thread de Concommateur :
	 * Récupère un message dans le buffer quand il y en a un, si il n'y en pas, et qu'il n'y a plus de producteur, le thread s'arrète
	 * le traite pendant un temps aléatoire,
	 * puis récupère un nouveau message
	 */
	public void run() {
		System.out.println("Nouveau Consommateur : " + this.identification());
		
		while (!((ProdCons) this.tampon).getStop() || this.tampon.enAttente() != 0) {
				System.out.println("Récupération nouveau message -- Consommateur : " + this.identification());
				this.traiterMessage();				
		}
		
		if (isInterrupted()) {
			Thread.currentThread().interrupt();
			System.out.println("~~~~~~~~~~> Interruption dans run()");
		}
		
		System.out.println("Nombre de message restant dans le Tampon : " + this.tampon.enAttente());
		System.out.println("Fin de récupération de message -- Consommateur : " + this.identification());
	}

}
