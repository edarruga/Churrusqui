package Red;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RealizarPeticion implements Runnable{

	
	private String rival;
	private Buscador b;
	
	public RealizarPeticion(String rival,Buscador b) {
		this.b=b;
		this.rival=rival;
	}
	
	
	@Override
	public void run() {
		String respuesta;
		try(Socket socket=new Socket(this.rival, 7777);
				DataInputStream dis=new DataInputStream(socket.getInputStream());
				PrintStream ps=new PrintStream(socket.getOutputStream());){
			//System.out.println("Empezó el Realizar petición");
			if(!this.b.getEnlazado()) {
				ps.print("BuscoRival\r\n");
				respuesta=dis.readLine();
				if(respuesta.equals("OK")) {
					respuesta=dis.readLine();
					if(respuesta.equals("BuscoRival")) {
						this.b.cliente=new Socket(socket.getInetAddress(),9988);
						this.b.setEnlazado(true);
						ps.print("OK\r\n");
						this.b.rival=socket.getInetAddress().getHostAddress();
						//System.out.println("Realizar: "+this.b.rival);
						//=========================================
						this.b.mensajeAEnlazador();
						this.b.mensajeATrazador();
						//=========================================
						
					}else {
						this.b.setEnlazado(false);
						ps.print("ERROR\r\n");
					}
					
				}else {
					ps.print("ERROR\r\n");
				}
			}else {
				ps.print("ERROR\r\n");
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Terminó el Realizar petición");
		
		
	}

}
