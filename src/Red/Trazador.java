package Red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Trazador extends Thread{
	
	private Buscador b;
	
	public Trazador(Buscador b) {
		this.b=b;
	}
	
	public void run() {
		ExecutorService pool = Executors.newCachedThreadPool();
		//System.out.println("Empezó el trazador");
		byte mensaje[]=this.b.mensaje.getBytes();
		try (DatagramSocket server=new DatagramSocket(6666);){
			while (this.b.rival==null && !this.b.parado && !this.b.finalizado) {
				try {
					DatagramPacket recibo=new DatagramPacket(mensaje,mensaje.length);
					//System.out.println("-Esperando Trazada");
					server.receive(recibo);
					//System.out.println("--Trazada encontrada");
					if(recibo.getAddress().getHostAddress().equals(this.b.ip)) {
						//System.out.println("Me mande un mensaje a mi mismo");
					}else {
						RealizarPeticion ap = new RealizarPeticion(recibo.getAddress().getHostAddress(), this.b);
						pool.execute(ap);
					}
					//System.out.println("---Trazada terminada");
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//System.out.println("Termino el trazador");
			pool.shutdown();
		}
	}

}
