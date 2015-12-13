package jus.poc.prodcons.obj2;

public class SemaphorePropre {
	private int permits; // la valeur initiale des permis disponibles, le nombre de threads qui vont acceder
						//a la ressource en meme temps; 
	
	public SemaphorePropre(int permits)
	{
		this.permits = permits; 
	}
	
	public synchronized void acquire() throws InterruptedException {
		//Obtenitr un permis, si le permis est plus grand que 0, cette methode va
		//decrementer le nombre de permits avec 1;
        if(permits > 0){
               permits--;
        }
        //S'il n'y a pas de permit disponible, le thread va attendre et puis va etre annonce et
        //le nombre de permits decremente.(c'est possible avec l'appel de release d'un autre thread)
        else{
               this.wait();
               permits--;
        }
	}
 /** Libere un permis et incremente le nombre des permis disponible avec 1. 
 */
 public synchronized void release() {
        permits++;
        //Si le nombre de permis est plus grand que 0, on va annoncer tous les threads qui sont en attente
        if(permits > 0)
               this.notifyAll();
 }
 public synchronized int availablePermits()
 {
	 return this.permits;
 }
}
