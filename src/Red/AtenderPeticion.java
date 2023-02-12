package Red;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class AtenderPeticion implements Runnable{
	
	private Socket socket;
	private Buscador b;
	
	public AtenderPeticion(Socket s,Buscador b){
		this.b=b;
		this.socket=s;
	}
	@Override
	public void run() {
		String respuesta;
		try (DataInputStream dis=new DataInputStream(this.socket.getInputStream());
				PrintStream ps=new PrintStream(this.socket.getOutputStream());){
			
			//System.out.println("Empezo el Atender petición");
			if(!this.b.getEnlazado()) {
				respuesta=dis.readLine();
				//System.out.println("Continua:"+respuesta);
				if(respuesta.equals("BuscoRival")) {
					if(!this.b.getEnlazado()) {
						ps.print("OK\r\n");
						ps.print("BuscoRival\r\n");
						respuesta=dis.readLine();
						//System.out.println("Continua 2:"+respuesta);
						if(respuesta.equals("OK") && !this.b.getEnlazado()) {
							this.b.cliente=new Socket(this.socket.getInetAddress(),9988);
							this.b.setEnlazado(true);
							this.b.rival=this.socket.getInetAddress().getHostAddress();
							//System.out.println("Atender: "+this.b.rival);
							//=========================================
							this.b.mensajeAEnlazador();
							this.b.mensajeATrazador();
							//=========================================
						}else {
							this.b.setEnlazado(false);
						}
						
					}else {
						ps.print("YoNo\r\n");
					}
					
				}else {
					ps.print("ERROR\r\n");
				}
			}else {
				ps.print("ERROR\r\n");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(this.socket !=null) {
					this.socket.close();
					//System.out.println("Termino el Atender petición");
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
		
	}

	

}
