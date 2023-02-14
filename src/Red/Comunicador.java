package Red;

import Estados.EstadoJuegoOnline;
import ObjetosJuego.JugadorSimple;
import ObjetosJuego.Mazo;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Comunicador {

    //Solo usar para el decidirInicio
    private Socket cliente;
    //Solo usar para el decidirInicio
    private Socket servidor;
    private InetAddress rival;
    public boolean finDeComunicacion;
    private Servidor servidorDeComunicacion;
    private EstadoJuegoOnline estadoJuegoOnline;





    public Comunicador(Socket cliente, Socket servidor, EstadoJuegoOnline estadoJuegoOnline){

        this.cliente=cliente;
        this.servidor=servidor;
        this.rival=cliente.getInetAddress();
        this.finDeComunicacion=false;
        //System.out.println("Constructor finalizado");
        this.estadoJuegoOnline=estadoJuegoOnline;
        this.servidorDeComunicacion=new Servidor(this.estadoJuegoOnline);
        this.servidorDeComunicacion.start();

    }

    public boolean decidirInicio(){
        try (PrintStream psCliente =new PrintStream(this.cliente.getOutputStream());
             DataInputStream disServidor=new DataInputStream(this.servidor.getInputStream())){
            //System.out.println("Decidiendo el inicio");
            int numero=(int) (Math.random()*1000);
            //System.out.println("Mi numero es: "+numero);
            psCliente.println(numero+"\r\n");
            String s=disServidor.readLine();
            int otro=Integer.parseInt(s);
            //System.out.println("Su numero es: "+otro);
            while(otro==numero){
                numero=(int) (Math.random()*1000);
                //System.out.println("--Mi numero es: "+numero);
                psCliente.println(numero+"\r\n");
                s=disServidor.readLine();
                otro=Integer.parseInt(s);
                //System.out.println("--Su numero es: "+otro);
            }
            //System.out.println(this.cliente);
            //System.out.println(this.servidor);
            //System.out.println(this.rival);
            if(otro>numero){
                return false;
            }else{
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JugadorSimple recibirJugador(){
        try (ServerSocket serverSocket=new ServerSocket(9999);
             Socket socket=serverSocket.accept();
             DataInputStream dis=new DataInputStream(socket.getInputStream())){
            String s=dis.readLine();
            //System.out.println("Jugador: "+s);
            return new JugadorSimple(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public int recibirPrueba(){
        try (ServerSocket serverSocket=new ServerSocket(9999);
                Socket socket=serverSocket.accept();
                DataInputStream dis=new DataInputStream(socket.getInputStream())){
            String s=dis.readLine();
            //System.out.println(s);
            //System.out.println(socket);
            try{
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                //System.out.println("recivirPrueba");
                Object o= ois.readObject();
                //System.out.println(o);
                return (int)o;
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public InetAddress getRival() {
        return rival;
    }

    public void enviarPrueba(int i){
        try (Socket socket=new Socket(this.rival,9999);
                MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream());
                PrintStream ps=new PrintStream(socket.getOutputStream())){
            ps.print("funcionaPlis\r\n");
            //ystem.out.println(socket);
            //System.out.println("enviarPrueba");
            oos.writeObject(i);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Mazo recibirMazo(){
        try (ServerSocket serverSocket=new ServerSocket(9999);
             Socket socket=serverSocket.accept();
             DataInputStream dis=new DataInputStream(socket.getInputStream())){
            String s=dis.readLine();
            //System.out.println("Mazo: "+s);
            return new Mazo(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarJugador(JugadorSimple js){
        try (Socket socket=new Socket(this.rival,9999);
             PrintStream ps=new PrintStream(socket.getOutputStream())){
            ps.println(js.toString());
            ps.flush();
            //System.out.println("Jugador: "+js.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarMazo(Mazo m){
        try (Socket socket=new Socket(this.rival,9999);
             PrintStream ps=new PrintStream(socket.getOutputStream())){
            ps.println(m.toString());
            ps.flush();
            //System.out.println("Mazo: "+m.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
