import java.util.Random;
import java.util.concurrent.Semaphore;

public class Filosofo implements Runnable {
	
	private Random numero = new Random();
	private int prato = 2;
	private int id;
	private Semaphore garfoEsquerdo;
	private Semaphore garfoDireito;
	
	public Filosofo(int id, Semaphore garfoEsquerdo, Semaphore garfoDireito) {
		this.id = id;
		this.garfoEsquerdo = garfoEsquerdo;
		this.garfoDireito = garfoDireito;
	}
	
	private void pensar() throws InterruptedException {
		System.out.println("Fil�sofo " + (id + 1) + " est� PENSANDO...\n");
		System.out.flush();
		Thread.sleep(numero.nextInt(5000));
	}
	
	private void pegarGarfos() throws InterruptedException {
		while(true) {
			if(garfoEsquerdo.availablePermits() == 0) {
				Thread.sleep(1000);
				continue;
			} else {
				garfoEsquerdo.acquire();
				System.out.println("Fil�sofo " + (id + 1) + " PEGOU o garfo esquerdo...\n");
				System.out.flush();
				
				if(garfoDireito.availablePermits() == 0) {
					garfoEsquerdo.release();
					System.out.println("Fil�sofo " + (id + 1) + " DEVOLVEU o garfo esquerdo (direito indispon�vel)\n");
					Thread.sleep(1000);
					continue;
				} else {
					garfoDireito.acquire();
					System.out.println("Fil�sofo " + (id + 1) + " PEGOU o garfo direito...\n");
					System.out.flush();
					return;
				}
			}
		}
		
    }
	
//	private void pegarGarfoEsquerdo() throws InterruptedException {
//		if(garfoEsquerdo.availablePermits() == 0) {
//			System.out.println("Fil�sofo " + (id + 1) + " est� ESPERANDO pelo garfo esquerdo...\n");
//			System.out.flush();
//		}
//		
//		garfoEsquerdo.acquire();
//		System.out.println("Fil�sofo " + (id + 1) + " PEGOU o garfo esquerdo...\n");
//		System.out.flush();
//	}
//	
//	private void pegarGarfoDireito() throws InterruptedException {
//		if(garfoDireito.availablePermits() == 0) {
//			System.out.println("Fil�sofo " + (id + 1) + " est� ESPERANDO pelo garfo direito...\n");
//			System.out.flush();
//		}
//		
//		garfoDireito.acquire();
//		System.out.println("Fil�sofo " + (id + 1) + " PEGOU o garfo direito...\n");
//		System.out.flush();
//	}
	
	private void comer() throws InterruptedException {
		System.out.println("Fil�sofo " + (id + 1) + " est� COMENDO...\n");
		System.out.flush();
		Thread.sleep(numero.nextInt(5000));
	}
	
	private void devolverGarfos() {
		garfoEsquerdo.release();
		garfoDireito.release();
		prato--;
	}

	@Override
	public void run() {
		try {
			while(prato != 0) {
				pensar();
				pegarGarfos();
				comer();
				devolverGarfos();
			}
			
			System.out.println("Fil�sofo " + (id + 1) + " terminou de comer e est� PENSANDO...\n");
			System.out.flush();
			
		} catch (InterruptedException e) {
			System.out.println("Fil�sofo " + (id + 1) + " foi INTERROMPIDO...\n");
			System.out.flush();
		}
	}
	
}
