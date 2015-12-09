package jus.poc.prodcons.v;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur{

	protected Consommateur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int nombreDeMessages() {

		return Aleatoire.valeur(this.moyenneTempsDeTraitement,this.deviationTempsDeTraitement);
	}

}
