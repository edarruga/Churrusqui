package Red;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Enlazador extends Thread{

	private Buscador b;
	
	public Enlazador(Buscador b) {
		this.b=b;
	}
	@Override
	public void run() {
		ExecutorService pool = Executors.newCachedThreadPool();
		//System.out.println("Empezó el enlazador");
		try (ServerSocket server = new ServerSocket(7777)){
			while (this.b.rival==null && !this.b.parado && !this.b.finalizado) {
				try {
					//System.out.println("-Esperando enlazada");
					Socket cliente = server.accept();
					//System.out.println("--Enlazada encontrada");
					AtenderPeticion ap = new AtenderPeticion(cliente, this.b);
					pool.execute(ap);
					//System.out.println("---Enlazada terminada");
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//System.out.println("Termino el enlazador");
			pool.shutdown();
		}
		
	}

}
