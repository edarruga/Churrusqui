package Red;

import Estados.Estado;
import Estados.EstadoJuego;
import Estados.EstadoJuegoOnline;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;

public class Buscador extends Thread{

	private boolean enlazado;
	public String ip;
	public String rival;
	public String broadcast;
	public boolean parado;
	public boolean finalizado;
	public String mensaje;
	public Socket cliente;
	public ServerSocket servidor;
	
	public Buscador() {
		this.enlazado=false;
		this.rival=null;
		this.parado=false;
		this.finalizado=false;
		this.cliente=null;
		this.mensaje="BuscandoContrincanteChurrusqui";
	}
	
	public void run(){
		try{
			this.servidor=new ServerSocket(9988);
			System.out.println("1");
			File file=new File("prueba.txt");
			String ruta = null;
			ruta = file.getAbsolutePath();
			//System.out.println(ruta);
			DataInputStream dis=null;
			byte buff[]=this.mensaje.getBytes();
			String broadcast="255.255.255.255";
			System.out.println("2");
			try {
				Runtime.getRuntime().exec("cmd /c start cmd.exe /C \" ipconfig > "+ruta);
				Thread.sleep(1000);//Tiene que esperar hasta que se cree el fichero
				dis=new DataInputStream(new FileInputStream(ruta));
				String linea=dis.readLine();
				while(!linea.contains("IPv4")) {
					linea=dis.readLine();
				}
				System.out.println("3");
				String ip=linea.split(": ")[1];
				this.ip=ip;
				linea=dis.readLine();
				String mascara=linea.split(": ")[1];
				String red=Buscador.calculadorRed(ip, mascara);
				this.broadcast=Buscador.calculadorBroadcast(red, mascara);
				System.out.println("4");
				//System.out.println(ip);
				//System.out.println(mascara);
				//System.out.println(red);
				//System.out.println(broadcast);
				//=========================================================

			}catch(IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					dis.close();
					file.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("5");
			Enlazador enlazador=new Enlazador(this);
			enlazador.start();
			System.out.println("6");
			Trazador trazador=new Trazador(this);
			trazador.start();
			System.out.println("7");
			this.mandarBroadcast();
			System.out.println("8");
			try {
				trazador.join();
				enlazador.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("9");
			System.out.println("finalizado= "+this.finalizado+" | rival= "+this.rival+" | cliente= "+this.cliente);
			if(!this.finalizado && this.rival!=null && this.cliente!=null){
				System.out.println("Entro");
				//System.out.println(this.cliente.getInetAddress()+", "+this.cliente.getPort());
				//Socket s=this.servidor.accept();
				//System.out.println(s.getInetAddress()+", "+this.cliente.getPort());

				Estado.cambiarEstado(new EstadoJuegoOnline(new Comunicador(this.cliente.getInetAddress(),this.servidor.accept().getInetAddress())));
			}else{
				System.out.println("No entro");
				if(this.cliente!=null){
					try {
						this.cliente.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			if(this.servidor!=null){
				try {
					this.servidor.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			if(this.cliente!=null){
				try {
					this.cliente.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

	
	}
	
	public void finalizarBusqueda() {
		this.finalizado=true;
		this.mensajeAEnlazador();
		this.mensajeATrazador();
	}
	
	public synchronized boolean getEnlazado() {
		return this.enlazado;
	}
	public synchronized void setEnlazado(boolean b) {
		this.enlazado=b;
	}
	public void mensajeAEnlazador() {
		try(Socket socket=new Socket(this.ip, 7777);
				PrintStream ps=new PrintStream(socket.getOutputStream());){
			ps.println("CERRAR");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void mensajeATrazador() {
		InetAddress i;
		try {
			i = InetAddress.getByName(this.ip);
			DatagramPacket udp =new DatagramPacket(this.mensaje.getBytes(),this.mensaje.getBytes().length,i,6666);
			DatagramSocket cliente=new DatagramSocket();
			cliente.send(udp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mandarBroadcast() {
		
		InetAddress i;
		try {
			i = InetAddress.getByName(this.broadcast);
			DatagramPacket udp =new DatagramPacket(this.mensaje.getBytes(),this.mensaje.getBytes().length,i,6666);
			DatagramSocket cliente=new DatagramSocket();
			cliente.send(udp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String decimalABinario(int decimal) {
	    String binario = "";
	    while (decimal > 0) {
	        binario = decimal % 2 + binario;
	        decimal = decimal / 2;
	    }
	    while(binario.length()<8) {
	    	binario="0"+binario;
	    }
	    return binario;
	}
	
	public static int binarioADecimal(int binario) {
	    int decimal = 0;
	    int potencia = 0;
	    // Ciclo infinito hasta que binario sea 0
	    while (true) {
	        if (binario == 0) {
	            break;
	        } else {
	            int temp = binario % 10;
	            decimal += temp * Math.pow(2, potencia);
	            binario = binario / 10;
	            potencia++;
	        }
	    }
	    return decimal;
	}
	public static String direccionABinario(String direccion) {
		String direccionTroceada[]=direccion.split("\\.");
		int direccionInt[]= {Integer.parseInt(direccionTroceada[0]),Integer.parseInt(direccionTroceada[1]),Integer.parseInt(direccionTroceada[2]),Integer.parseInt(direccionTroceada[3])};
		return Buscador.decimalABinario(direccionInt[0])+"."+Buscador.decimalABinario(direccionInt[1])+"."+Buscador.decimalABinario(direccionInt[2])+"."+Buscador.decimalABinario(direccionInt[3]);
	}
	
	public static String calculadorRed(String ip,String mascara) {
		String ipTroceada[]=ip.split("\\.");
		int ipInt[]= {Integer.parseInt(ipTroceada[0]),Integer.parseInt(ipTroceada[1]),Integer.parseInt(ipTroceada[2]),Integer.parseInt(ipTroceada[3])};
		String mascaraTroceada[]=mascara.split("\\.");
		int mascaraInt[]= {Integer.parseInt(mascaraTroceada[0]),Integer.parseInt(mascaraTroceada[1]),Integer.parseInt(mascaraTroceada[2]),Integer.parseInt(mascaraTroceada[3])};
		int redInt[]= {mascaraInt[0]&ipInt[0],mascaraInt[1]&ipInt[1],mascaraInt[2]&ipInt[2],mascaraInt[3]&ipInt[3]};
		String red=redInt[0]+"."+redInt[1]+"."+redInt[2]+"."+redInt[3];
		
		return red;
	}
	
	public static String calculadorBroadcast(String red,String mascara) {
		String redTroceada[]=red.split("\\.");
		int redInt[]= {Integer.parseInt(redTroceada[0]),Integer.parseInt(redTroceada[1]),Integer.parseInt(redTroceada[2]),Integer.parseInt(redTroceada[3])};
		String mascaraTroceada[]=mascara.split("\\.");
		int mascaraInt[]= {Integer.parseInt(mascaraTroceada[0]),Integer.parseInt(mascaraTroceada[1]),Integer.parseInt(mascaraTroceada[2]),Integer.parseInt(mascaraTroceada[3])};
		int mascaraInvertida[]= {mascaraInt[0]^255,mascaraInt[1]^255,mascaraInt[2]^255,mascaraInt[3]^255};
		int broadcastInt[]= {mascaraInvertida[0]|redInt[0],mascaraInvertida[1]|redInt[1],mascaraInvertida[2]|redInt[2],mascaraInvertida[3]|redInt[3]};
		String broadcast=broadcastInt[0]+"."+broadcastInt[1]+"."+broadcastInt[2]+"."+broadcastInt[3];
		
		return broadcast;
	}

}
