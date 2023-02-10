package Red;

import Estados.EstadoJuego;
import Estados.EstadoJuegoOnline;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class RealizarPeticionPartida extends Thread{

    private EstadoJuego estadoJuego;
    private InetAddress rival;
    private int numeroDePeticion;

    public RealizarPeticionPartida(EstadoJuego ej, InetAddress rival, int num){
        this.estadoJuego=ej;
        this.rival=rival;
        this.numeroDePeticion=num;
    }

    @Override
    public void run() {
        try(Socket socket=new Socket(this.rival,9990);
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            PrintStream ps=new PrintStream(socket.getOutputStream())){

            switch (this.numeroDePeticion){
                case 0:
                    ps.println("ActualizoMiJugador");
                    ps.println(this.estadoJuego.getJugadorHumano().pasarAJugadorSimple().toString());
                    ps.flush();
                    break;
                case 1:
                    ps.println("ActualizoTuJugador");
                    ps.println(this.estadoJuego.getJugadorBot().pasarAJugadorSimple().toString());
                    ps.flush();
                    break;
                case 2:
                    ps.println("ActualizoMazo1");
                    ps.println(this.estadoJuego.getMazoDeApilar1().getMazo().toString());
                    ps.flush();
                    break;
                case 3:
                    ps.println("ActualizoMazo2");
                    ps.println(this.estadoJuego.getMazoDeApilar2().getMazo().toString());
                    ps.flush();
                    break;
                case 4:
                    ps.println("SolucionoBloqueo");
                    ps.println(this.estadoJuego.getJugadorHumano().pasarAJugadorSimple().toString());
                    ps.println(this.estadoJuego.getJugadorBot().pasarAJugadorSimple().toString());
                    ps.println(this.estadoJuego.getMazoDeApilar1().getMazo().toString());
                    ps.println(this.estadoJuego.getMazoDeApilar2().getMazo().toString());
                    ps.flush();
                    break;
                case 5:
                    ps.println("SolucionoChurrusqui");
                    ps.println(this.estadoJuego.getJugadorHumano().pasarAJugadorSimple().toString());
                    ps.println(this.estadoJuego.getJugadorBot().pasarAJugadorSimple().toString());
                    ps.println(this.estadoJuego.getMazoDeApilar1().getMazo().toString());
                    ps.println(this.estadoJuego.getMazoDeApilar2().getMazo().toString());
                    ps.flush();
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*@Override
    public void run() {
        try(Socket socket=new Socket(this.rival,9990);
                DataInputStream dis=new DataInputStream(socket.getInputStream());
                PrintStream ps=new PrintStream(socket.getOutputStream())){

            switch (this.numeroDePeticion){
                case 0:
                    try(MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                        ps.println("ActualizoMiJugador");
                        oos.writeObject(this.estadoJuego.getJugadorHumano().pasarAJugadorSimple());
                        oos.flush();
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 1:
                    try(MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                        ps.println("ActualizoTuJugador");
                        oos.writeObject(this.estadoJuego.getJugadorBot().pasarAJugadorSimple());
                        oos.flush();
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try(MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                        ps.println("ActualizoMazo1");
                        oos.writeObject(this.estadoJuego.getMazoDeApilar1().getMazo());
                        oos.flush();
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    try(MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                        ps.println("ActualizoMazo2");
                        oos.writeObject(this.estadoJuego.getMazoDeApilar2().getMazo());
                        oos.flush();
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    try(MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                        ps.println("SolucionoBloqueo");
                        oos.writeObject(this.estadoJuego.getJugadorHumano().pasarAJugadorSimple());
                        oos.writeObject(this.estadoJuego.getJugadorBot().pasarAJugadorSimple());
                        oos.writeObject(this.estadoJuego.getMazoDeApilar1().getMazo());
                        oos.writeObject(this.estadoJuego.getMazoDeApilar2().getMazo());
                        oos.flush();
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    try(MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                        ps.println("SolucionoChurrusqui");
                        oos.writeObject(this.estadoJuego.getJugadorHumano().pasarAJugadorSimple());
                        oos.writeObject(this.estadoJuego.getJugadorBot().pasarAJugadorSimple());
                        oos.writeObject(this.estadoJuego.getMazoDeApilar1().getMazo());
                        oos.writeObject(this.estadoJuego.getMazoDeApilar2().getMazo());
                        oos.flush();
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/
}
