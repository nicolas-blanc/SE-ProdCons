package jus.poc.prodcons.obj6;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {

	private int nbMesssageLu;
	private Tampon tampon;
	private Observateur observateur;
	private ObservateurPropre observateurPropre;

	public Consommateur(Tampon tampon,ObservateurPropre observateurPropre, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement,
				deviationTempsDeTraitement);
		this.tampon = tampon;
		nbMesssageLu = jus.poc.prodcons.Aleatoire.valeur(
				moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.observateur = observateur;
		this.observateurPropre = observateurPropre;
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMesssageLu;
	}

	/**
	 * Méthode lancé à la création du thread de Concommateur : Récupère un
	 * message dans le buffer quand il y en a un, si il n'y en pas, et qu'il n'y
	 * a plus de producteur, le thread s'arrète le traite pendant un temps
	 * aléatoire, puis récupère un nouveau message
	 */
	public void run() {
		int tempsTraitement = 0;
		MessageX message = null;
		for (int i = 0; i < nbMesssageLu; i++) {
			try {
				tempsTraitement = Aleatoire.valeur(moyenneTempsDeTraitement,
						deviationTempsDeTraitement);
				message = (MessageX) tampon.get(this);
				System.out.println("Retrieve message : " + message.toString()
						+ "\n Recupere par : " + this.identification());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.observateurPropre.consommationMessage(this, message,
						tempsTraitement);
			} catch (ControlException e) {
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
