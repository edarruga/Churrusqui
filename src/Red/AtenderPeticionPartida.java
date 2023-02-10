package Red;

import Estados.EstadoJuegoOnline;
import ObjetosJuego.CartaSimple;
import ObjetosJuego.JugadorSimple;
import ObjetosJuego.Mazo;
import input.MouseInput;

import java.io.*;
import java.net.Socket;

public class AtenderPeticionPartida implements Runnable{

    private EstadoJuegoOnline estadoJuegoOnline;
    private Socket socket;
    public AtenderPeticionPartida(EstadoJuegoOnline ejo, Socket s){
        this.estadoJuegoOnline=ejo;
        this.socket=s;
    }
    @Override
    public void run() {
        try(DataInputStream dis=new DataInputStream(this.socket.getInputStream());
            PrintStream ps=new PrintStream(this.socket.getOutputStream())){
            String tipoPeticion=dis.readLine();
            if(tipoPeticion.equals("ActualizoMiJugador")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    this.estadoJuegoOnline.setRivalSimple((JugadorSimple) ois.readObject());
                    this.estadoJuegoOnline.setRivalSimpleModificado(true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("ActualizoTuJugador")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    this.estadoJuegoOnline.setYoSimple((JugadorSimple) ois.readObject());
                    this.estadoJuegoOnline.setYoSimpleModificado(true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("ActualizoMazo1")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    this.estadoJuegoOnline.setMazodeApilar1Simple((Mazo) ois.readObject());
                    this.estadoJuegoOnline.setMazodeApilar1SimpleModificado(true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("ActualizoMazo2")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    this.estadoJuegoOnline.setMazodeApilar2Simple((Mazo) ois.readObject());
                    this.estadoJuegoOnline.setMazodeApilar2SimpleModificado(true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("IntroduzcoCartaEnMazo1")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    Mazo mazo=(Mazo) ois.readObject();
                    Mazo mazoMi=new Mazo(this.estadoJuegoOnline.getMazoDeApilar1().getMazo());
                    if(mazoMi.equals(mazo)){
                        ps.println("OK");
                    }else{
                        ps.println("NoPudoSer");
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("IntroduzcoCartaEnMazo2")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    Mazo mazo=(Mazo) ois.readObject();
                    Mazo mazoMi=new Mazo(this.estadoJuegoOnline.getMazoDeApilar2().getMazo());
                    if(mazoMi.equals(mazo)){
                        ps.println("OK");
                    }else{
                        ps.println("NoPudoSer");
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("SolicitoGetChurrusqui")){
                if(!this.estadoJuegoOnline.getJugadorHumano().getChurrusqui()){
                    ps.println("False");
                }else{
                    ps.println("True");
                }
            }
            if(tipoPeticion.equals("Churrusqui")){
                this.estadoJuegoOnline.hiceCurrusquiOnline=false;
                this.estadoJuegoOnline.getJugadorBot().setChurrusqui(true);

            }
            if(tipoPeticion.equals("SolucionoBloqueo")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    this.estadoJuegoOnline.setRivalSimple((JugadorSimple) ois.readObject());
                    this.estadoJuegoOnline.setRivalSimpleModificado(true);
                    this.estadoJuegoOnline.setYoSimple((JugadorSimple) ois.readObject());
                    this.estadoJuegoOnline.setYoSimpleModificado(true);
                    this.estadoJuegoOnline.setMazodeApilar1Simple((Mazo) ois.readObject());
                    this.estadoJuegoOnline.setMazodeApilar1SimpleModificado(true);
                    this.estadoJuegoOnline.setMazodeApilar2Simple((Mazo) ois.readObject());
                    this.estadoJuegoOnline.setMazodeApilar2SimpleModificado(true);
                    MouseInput.botonIzquierdo=false;
                    this.estadoJuegoOnline.bloqueado=false;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(tipoPeticion.equals("SolucionoChurrusqui")){
                try(ObjectInputStream ois=new ObjectInputStream(this.socket.getInputStream())){
                    this.estadoJuegoOnline.setRivalSimple((JugadorSimple) ois.readObject());
                    this.estadoJuegoOnline.setRivalSimpleModificado(true);
                    this.estadoJuegoOnline.setYoSimple((JugadorSimple) ois.readObject());
                    this.estadoJuegoOnline.setYoSimpleModificado(true);
                    this.estadoJuegoOnline.setMazodeApilar1Simple((Mazo) ois.readObject());
                    this.estadoJuegoOnline.setMazodeApilar1SimpleModificado(true);
                    this.estadoJuegoOnline.setMazodeApilar2Simple((Mazo) ois.readObject());
                    this.estadoJuegoOnline.setMazodeApilar2SimpleModificado(true);
                    this.estadoJuegoOnline.getJugadorBot().setChurrusqui(false);
                    this.estadoJuegoOnline.getJugadorHumano().setChurrusqui(false);
                    this.estadoJuegoOnline.churrusqui=false;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            ps.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
