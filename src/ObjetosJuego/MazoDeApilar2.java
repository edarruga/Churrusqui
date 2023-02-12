package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import Red.MiObjectOutputStream;
import Red.RealizarPeticionPartida;
import input.MouseInput;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MazoDeApilar2 extends MazoDeApilar{
    public MazoDeApilar2(BufferedImage t, Vector2D v2d, EstadoJuego es) {
        super(t, v2d,es);
    }
    public MazoDeApilar2(BufferedImage t, Vector2D v2d, EstadoJuego es,Mazo m) {
        super(t, v2d,es,m);
    }

    public synchronized boolean esJugable(CartaSimple cNueva){
        if(this.estadoJuego.getJugadorBot().getActivado()){
            if((this.getUltimaCarta().ImmediatelyNext(cNueva)||this.getUltimaCarta().ImmediatelyPrevious(cNueva)) && !this.estadoJuego.getChurrusqui()){
                return true;
            }
            return false;
        }else{//Caso de juego online
            //System.out.println("else");
            if(!this.estadoJuego.isMazodeApilar2SimpleModificado()){
                //System.out.println("elsee");
                if((this.getUltimaCarta().ImmediatelyNext(cNueva)||this.getUltimaCarta().ImmediatelyPrevious(cNueva)) && !this.estadoJuego.getChurrusqui()){
                    //System.out.println("elseee");
                    try(Socket socket=new Socket(this.estadoJuego.getComunicador().getRival(),9990);
                        DataInputStream dis=new DataInputStream(socket.getInputStream());
                        PrintStream ps=new PrintStream(socket.getOutputStream())){
                        //System.out.println("Try");
                        ps.println("IntroduzcoCartaEnMazo2");
                        ps.println(this.estadoJuego.getMazoDeApilar2().getMazo().toString());
                        ps.flush();
                        //System.out.println("Tryy");
                        String s=dis.readLine();
                        if(s.equals("OK")){
                            System.out.println("Ok");
                            return true;
                        }else{
                            System.out.println("No");
                            return false;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("1No elsee");
            return false;
        }

    }

    public synchronized boolean aniadirNuevaCarta(CartaSimple cs){
        if(this.esJugable(cs)){
            this.mazo.insertarCartaSimple(cs);
            this.seModifico=true;
            if(!this.estadoJuego.getJugadorBot().getActivado()){//Caso de juego online
                RealizarPeticionPartida info1=new RealizarPeticionPartida(this.estadoJuego,this.estadoJuego.getComunicador().getRival(),0);
                info1.start();
                RealizarPeticionPartida info2=new RealizarPeticionPartida(this.estadoJuego,this.estadoJuego.getComunicador().getRival(),3);
                info2.start();
            }
            return true;
        }
        return false;

    }

    @Override
    public void actualizar() {

        if(super.getHitBox().contains(MouseInput.RatonX,MouseInput.RatonY)){
            //System.out.println("Entro en 2");
            MouseInput.dentroDePila2=true;
            //if(MouseInput.colocarCarta2){//Esto hay que cambiarlo
                super.setPosibleJugada(true);
            //}
        }else{
            super.setPosibleJugada(false);
            MouseInput.dentroDePila2=false;
        }

    }
}
