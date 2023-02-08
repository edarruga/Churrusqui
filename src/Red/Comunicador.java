package Red;

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




    public Comunicador(Socket cliente,Socket servidor){

        this.cliente=cliente;
        this.servidor=servidor;
        this.rival=cliente.getInetAddress();
        System.out.println("Constructor finalizado");

    }

    public boolean decidirInicio(){
        try (PrintStream psCliente =new PrintStream(this.cliente.getOutputStream());
             DataInputStream disServidor=new DataInputStream(this.servidor.getInputStream())){
            System.out.println("Decidiendo el inicio");
            int numero=(int) (Math.random()*1000);
            System.out.println("Mi numero es: "+numero);
            psCliente.println(numero+"\r\n");
            String s=disServidor.readLine();
            int otro=Integer.parseInt(s);
            System.out.println("Su numero es: "+otro);
            while(otro==numero){
                numero=(int) (Math.random()*1000);
                System.out.println("--Mi numero es: "+numero);
                psCliente.println(numero+"\r\n");
                s=disServidor.readLine();
                otro=Integer.parseInt(s);
                System.out.println("--Su numero es: "+otro);
            }
            if(otro>numero){
                return false;
            }else{
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JugadorSimple recivirJugador(){
        try (Socket socket=new Socket(this.rival,9999);
             ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
            return (JugadorSimple)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int recivirPrueba(){
        try (ServerSocket serverSocket=new ServerSocket(9999);
                Socket socket=serverSocket.accept();
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
            Object o= ois.readObject();
            System.out.println(o);
            return (int)o;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarPrueba(int i){
        try (Socket socket=new Socket(this.rival,9999);
                MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
            oos.writeObject(i);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Mazo recivirMazo(){
        try (Socket socket=new Socket(this.rival,9999);
             ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
            return (Mazo)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarJugador(JugadorSimple js){
        try (Socket socket=new Socket(this.rival,9999);
             MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
            oos.writeObject(js);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarMazo(Mazo m){
        try (Socket socket=new Socket(this.rival,9999);
             MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
            oos.writeObject(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
