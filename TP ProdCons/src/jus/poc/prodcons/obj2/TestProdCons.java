package jus.poc.prodcons.obj2;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends jus.poc.prodcons.obj1.TestProdCons{
	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	protected void run() throws ControlException {
		Tampon tampon = new ProdConsSemaphore(3);
		
		this.createThread(tampon);
		
		System.out.println("// ----- ----- \\ Fin de TestProdCons // ----- ----- \\");
	}
	
	public static void main(String[] args) {
		System.out.println("// ----- ----- \\ Lancement programme : Obj2 // ----- ----- \\");
		new TestProdCons(new Observateur()).start();
		
	}
}