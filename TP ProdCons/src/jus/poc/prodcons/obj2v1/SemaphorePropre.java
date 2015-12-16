package jus.poc.prodcons.obj2v1;

public class SemaphorePropre {
	private int valeur;
    
    public SemaphorePropre(int valeur)
    {
        this.valeur=valeur;
    }
    
    public int availablePermits()
    {
        return valeur;
    }
    
    synchronized public void acquire()
    {
        while (valeur<1)
        {
            try 
            {
                wait();
            }
            catch(InterruptedException e)
            {
                System.out.println("Erreur de S�maphore");
            }
        }
        valeur=valeur-1;
    }
    
    synchronized public void release()
    {
        valeur=valeur+1;
        notify();
    }
}
