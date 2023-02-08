package Red;

import ObjetosJuego.JugadorSimple;
import ObjetosJuego.Mazo;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Comunicador {

    private InetAddress inetCliente;

    private InetAddress inetServidor;



    public Comunicador(InetAddress cliente,InetAddress servidor){

        this.inetCliente=cliente;
        this.inetServidor=servidor;

    }

    public boolean decidirInicio(){
        try (Socket socket1=new Socket(inetCliente,9999);
             Socket socket2=new Socket(inetServidor,9999);
                PrintStream psCliente =new PrintStream(socket1.getOutputStream());
        DataInputStream disServidor=new DataInputStream(socket2.getInputStream())){
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
        try (Socket socket=new Socket(this.inetServidor,9999);
             ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
            return (JugadorSimple)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int recivirPrueba(){
        try (Socket socket=new Socket(this.inetServidor,9999);
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
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
        try (Socket socket=new Socket(this.inetServidor,9999);
             ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
            return (Mazo)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarJugador(JugadorSimple js){
        try (Socket socket=new Socket(this.inetCliente,9999);
             MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
            oos.writeObject(js);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarMazo(Mazo m){
        try (Socket socket=new Socket(this.inetCliente,9999);
             MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
            oos.writeObject(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
