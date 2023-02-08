package Red;

import ObjetosJuego.JugadorSimple;
import ObjetosJuego.Mazo;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Comunicador {

    private Socket cliente;
    private InetAddress inetCliente;
    private PrintStream psCliente;
    private ObjectOutputStream oosCliente;

    private Socket servidor;
    private InetAddress inetServidor;
    private DataInputStream disServidor;
    private ObjectInputStream oisServidor;


    public Comunicador(Socket cliente,Socket servidor){

        try {
            this.cliente=cliente;
            this.servidor=servidor;
            this.inetCliente=this.cliente.getInetAddress();
            this.inetServidor=this.servidor.getInetAddress();
            this.psCliente=new PrintStream(this.cliente.getOutputStream());
            //this.oosCliente=new ObjectOutputStream(this.cliente.getOutputStream());
            this.disServidor=new DataInputStream(this.servidor.getInputStream());
            //this.oisServidor=new ObjectInputStream(this.servidor.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean decidirInicio(){
        try (PrintStream psCliente =new PrintStream(this.cliente.getOutputStream());
        DataInputStream disServidor=new DataInputStream(this.servidor.getInputStream())){
            int numero=(int) (Math.random()*1000);
            psCliente.println(numero+"\r\n");
            String s=disServidor.readLine();
            int otro=Integer.parseInt(s);
            while(otro==numero){
                numero=(int) (Math.random()*1000);
                psCliente.println(numero+"\r\n");
                s=disServidor.readLine();
                otro=Integer.parseInt(s);
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
        try {
            return (JugadorSimple)this.oisServidor.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int recivirPrueba(){
        try (Socket socket=new Socket(this.inetServidor,9999);
                ObjectInputStream ois=new ObjectInputStream(this.servidor.getInputStream())){
            return (int)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarPrueba(int i){
        try (Socket socket=new Socket(this.inetCliente,9999);
                MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
            oos.writeObject(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Mazo recivirMazo(){
        try {
            return (Mazo)this.oisServidor.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarJugador(JugadorSimple js){
        try {
            this.oosCliente.writeObject(js);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarMazo(Mazo m){
        try {
            this.oosCliente.writeObject(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
