package jus.poc.prodcons.v;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur{
	
	private int nbMesssageLu;

	protected Consommateur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int nombreDeMessages() {
		return this.nbMesssageLu;
	}
	
	/**
	 * Méthode lancé à la création du thread de Concommateur :
	 * Récupère un message dans le buffer quand il y en a un, si il n'y en pas, et qu'il n'y a plus de producteur, le thread s'arrète
	 * le traite pendant un temps aléatoire,
	 * puis récupère un nouveau message
	 */
	public void run() {
		
	}

}
