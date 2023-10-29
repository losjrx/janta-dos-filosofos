import java.util.concurrent.Semaphore;

public class Jantar {
	private static final int NUM_FILOSOFOS = 5;
	
	public static void main(String[] args) {
		//Cada garfo com uma trava
		
		Semaphore[] garfos = new Semaphore[NUM_FILOSOFOS];
		
		for(int i = 0; i < NUM_FILOSOFOS; i++) {
			garfos[i] = new Semaphore(1);
		}
		
		Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
		
		for(int i = 0; i < NUM_FILOSOFOS; i++) {
			filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % NUM_FILOSOFOS]);
			new Thread(filosofos[i]).start();
		}
	}
}
