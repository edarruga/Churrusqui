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
                this.estadoJuegoOnline.setRivalSimple(new JugadorSimple(dis.readLine()));
                this.estadoJuegoOnline.setRivalSimpleModificado(true);
            }
            if(tipoPeticion.equals("ActualizoTuJugador")){
                this.estadoJuegoOnline.setYoSimple(new JugadorSimple(dis.readLine()));
                this.estadoJuegoOnline.setYoSimpleModificado(true);
            }
            if(tipoPeticion.equals("ActualizoMazo1")){
                this.estadoJuegoOnline.setMazodeApilar1Simple(new Mazo(dis.readLine()));
                this.estadoJuegoOnline.setMazodeApilar1SimpleModificado(true);
            }
            if(tipoPeticion.equals("ActualizoMazo2")){
                this.estadoJuegoOnline.setMazodeApilar2Simple(new Mazo(dis.readLine()));
                this.estadoJuegoOnline.setMazodeApilar2SimpleModificado(true);
            }
            if(tipoPeticion.equals("IntroduzcoCartaEnMazo1")){
                Mazo mazo=new Mazo(dis.readLine());
                Mazo mazoMi=new Mazo(this.estadoJuegoOnline.getMazoDeApilar1().getMazo());
                if(mazoMi.equals(mazo)){
                    ps.println("OK");
                }else{
                    ps.println("NoPudoSer");
                }
            }
            if(tipoPeticion.equals("IntroduzcoCartaEnMazo2")){
                Mazo mazo=new Mazo(dis.readLine());
                Mazo mazoMi=new Mazo(this.estadoJuegoOnline.getMazoDeApilar2().getMazo());
                System.out.println(mazoMi.toString());
                System.out.println(mazo.toString());
                if(mazoMi.equals(mazo)){
                    ps.println("OK");
                }else{
                    ps.println("NoPudoSer");
                }
            }
            if(tipoPeticion.equals("SolicitoGetChurrusqui")){
                if(this.estadoJuegoOnline.getJugadorHumano()!=null){
                    if(!this.estadoJuegoOnline.getJugadorHumano().getChurrusqui()){
                        ps.println("False");
                    }else{
                        ps.println("True");
                    }
                }else{
                    System.out.println("Nulo");
                    ps.println("False");
                }

            }
            if(tipoPeticion.equals("Churrusqui")){
                this.estadoJuegoOnline.hiceCurrusquiOnline=false;
                this.estadoJuegoOnline.getJugadorBot().setChurrusqui(true);

            }
            if(tipoPeticion.equals("SolucionoBloqueo")){
                this.estadoJuegoOnline.setRivalSimple(new JugadorSimple(dis.readLine()));
                this.estadoJuegoOnline.setRivalSimpleModificado(true);
                System.out.println("Jugador: "+this.estadoJuegoOnline.getRivalSimple().toString());
                System.out.println("===============");
                this.estadoJuegoOnline.setYoSimple(new JugadorSimple(dis.readLine()));
                this.estadoJuegoOnline.setYoSimpleModificado(true);
                System.out.println("Jugador: "+this.estadoJuegoOnline.getYoSimple().toString());
                System.out.println("===============");
                this.estadoJuegoOnline.setMazodeApilar1Simple(new Mazo(dis.readLine()));
                this.estadoJuegoOnline.setMazodeApilar1SimpleModificado(true);
                System.out.println("Mazo 1: "+this.estadoJuegoOnline.getMazodeApilar1Simple().toString());
                System.out.println("===============");
                this.estadoJuegoOnline.setMazodeApilar2Simple(new Mazo(dis.readLine()));
                this.estadoJuegoOnline.setMazodeApilar2SimpleModificado(true);
                System.out.println("Mazo 2: "+this.estadoJuegoOnline.getMazodeApilar2Simple().toString());
                System.out.println("===============");
                MouseInput.botonIzquierdo=false;
                this.estadoJuegoOnline.bloqueado=false;
            }
            if(tipoPeticion.equals("SolucionoChurrusqui")){
                this.estadoJuegoOnline.setRivalSimple(new JugadorSimple(dis.readLine()));
                this.estadoJuegoOnline.setRivalSimpleModificado(true);
                this.estadoJuegoOnline.setYoSimple(new JugadorSimple(dis.readLine()));
                this.estadoJuegoOnline.setYoSimpleModificado(true);
                this.estadoJuegoOnline.setMazodeApilar1Simple(new Mazo(dis.readLine()));
                this.estadoJuegoOnline.setMazodeApilar1SimpleModificado(true);
                this.estadoJuegoOnline.setMazodeApilar2Simple(new Mazo(dis.readLine()));
                this.estadoJuegoOnline.setMazodeApilar2SimpleModificado(true);
                this.estadoJuegoOnline.getJugadorBot().setChurrusqui(false);
                this.estadoJuegoOnline.getJugadorHumano().setChurrusqui(false);
                this.estadoJuegoOnline.churrusqui=false;
            }
            ps.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* @Override
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
    }*/
}
