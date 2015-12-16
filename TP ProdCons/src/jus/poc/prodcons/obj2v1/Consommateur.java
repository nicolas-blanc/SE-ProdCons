package jus.poc.prodcons.obj2v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.obj1.MessageX;

public class Consommateur extends Acteur implements _Consommateur {

	private int nbMesssageLu;
	private Tampon tampon;
	public Consommateur(Tampon tampon, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement,
				deviationTempsDeTraitement);
		this.tampon = tampon;
		nbMesssageLu = jus.poc.prodcons.Aleatoire.valeur(
				moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.observateur = observateur;
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMesssageLu;
	}

	public void run() {
		int tempsTraitement = 0;
		MessageX message = null;
		for (int i = 0; i < nbMesssageLu; i++) {
			try {
				tempsTraitement = Aleatoire.valeur(moyenneTempsDeTraitement,
						deviationTempsDeTraitement);
				message = (MessageX) tampon.get(this);
				if(message !=null)
					System.out.println("Retrieved message : " + message.toString()
						+ "consumed by: " + this.identification());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(tempsTraitement);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
