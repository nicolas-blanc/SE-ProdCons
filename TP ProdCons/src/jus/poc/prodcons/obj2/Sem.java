package jus.poc.prodcons.obj2;

public class Sem {
	
	private int valeur;
	private int limite;
	
	public Sem(int limite) {
		this.valeur=limite;
		this.limite=limite;
	}
	
	public int getValeur() {
		return valeur;
	}
	
	synchronized public void P() {
		while (valeur < 1) {
			try  {
				wait();
			}
			catch(InterruptedException e) {
				System.out.println("Erreur de Sémaphore");
			}
		}
		
		valeur = valeur-1;
	}
	
	synchronized public void V() {
		valeur = valeur + 1;
		notify();
	}
}
