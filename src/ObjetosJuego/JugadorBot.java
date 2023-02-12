package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class JugadorBot implements Runnable{

    private CardBot carta1;
    private CardBot carta2;
    private CardBot carta3;
    private CardBot carta4;
    protected Vector2D posicionCarta1;
    protected Vector2D posicionCarta2;
    protected Vector2D posicionCarta3;
    protected Vector2D posicionCarta4;
    protected Vector2D posicionMazoDeRobo;
    protected MazoDeRobo mazo;
    private EstadoJuego estadoJuego;
    protected boolean puedoRobar=false;
    protected boolean funcionando;
    private boolean puedoJugar=true;
    private boolean terminado=false;
    private boolean churrusqui=false;
    private boolean activado=true;

    public JugadorBot(CartaSimple[] csv,EstadoJuego es){
        //Posicion de carta 1
        this.posicionCarta1=new Vector2D(CardHumano.getAnchuraCarta()*(7.75), CardHumano.getAlturaCarta()*(0.15));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(CardHumano.getAnchuraCarta()*(6.25), CardHumano.getAlturaCarta()*(0.15));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(CardHumano.getAnchuraCarta()*(4.75), CardHumano.getAlturaCarta()*(0.15));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(CardHumano.getAnchuraCarta()*(3.25), CardHumano.getAlturaCarta()*(0.15));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(CardHumano.getAnchuraCarta()*(1.75), CardHumano.getAlturaCarta()*(0.15));

        this.estadoJuego=es;
        this.mazo=new MazoDeRoboBot(this.posicionMazoDeRobo,csv,this);
        this.puedoRobar=false;

        this.carta1=new CardBot(this.mazo.robarCata(),this.posicionCarta1);
        this.carta2=new CardBot(this.mazo.robarCata(),this.posicionCarta2);
        this.carta3=new CardBot(this.mazo.robarCata(),this.posicionCarta3);
        this.carta4=new CardBot(this.mazo.robarCata(),this.posicionCarta4);
        this.estadoJuego.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
    }
    public JugadorBot(JugadorSimple j,EstadoJuego es){
        //Posicion de carta 1
        this.posicionCarta1=new Vector2D(CardHumano.getAnchuraCarta()*(7.75), CardHumano.getAlturaCarta()*(0.15));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(CardHumano.getAnchuraCarta()*(6.25), CardHumano.getAlturaCarta()*(0.15));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(CardHumano.getAnchuraCarta()*(4.75), CardHumano.getAlturaCarta()*(0.15));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(CardHumano.getAnchuraCarta()*(3.25), CardHumano.getAlturaCarta()*(0.15));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(CardHumano.getAnchuraCarta()*(1.75), CardHumano.getAlturaCarta()*(0.15));

        this.estadoJuego=es;
        this.mazo=new MazoDeRoboBot(this.posicionMazoDeRobo,j.getMazo(),this);
        this.puedoRobar=false;

        this.carta1=new CardBot(j.getCarta1(),this.posicionCarta1);
        this.carta2=new CardBot(j.getCarta2(),this.posicionCarta2);
        this.carta3=new CardBot(j.getCarta3(),this.posicionCarta3);
        this.carta4=new CardBot(j.getCarta4(),this.posicionCarta4);
        //this.estadoJuego.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
    }

    public void modificarEstado(JugadorSimple j){
        this.carta1.actualizarEstadoDeCarta(j.getCarta1());
        this.carta2.actualizarEstadoDeCarta(j.getCarta2());
        this.carta3.actualizarEstadoDeCarta(j.getCarta3());
        this.carta4.actualizarEstadoDeCarta(j.getCarta4());
        this.mazo.modificarEstado(j.getMazo());
    }

    public JugadorSimple pasarAJugadorSimple(){
        Mazo m=this.mazo.mazo;
        CartaSimple c1=this.carta1.CartaACartaSimple();
        CartaSimple c2=this.carta2.CartaACartaSimple();
        CartaSimple c3=this.carta3.CartaACartaSimple();
        CartaSimple c4=this.carta4.CartaACartaSimple();
        return new JugadorSimple(m,c1,c2,c3,c4);
    }
    public boolean getPuedoJugar(){
        return this.puedoJugar;
    }
    public synchronized void solicitarCurrusqui(){
        if(!this.estadoJuego.churrusqui){
            if(this.estadoJuego.solicitarChurrusqui()){
                this.churrusqui=true;
            }
        }

    }
    public void activar(){
        this.activado=true;
    }
    public void desactivar(){
        this.activado=false;
    }
    public boolean getActivado(){
        return this.activado;
    }
    public boolean getChurrusqui(){
        if(this.activado){
            return this.churrusqui;
        }else{//En caso que este desactivado, es decir, que sea modo online
            try(Socket socket=new Socket(this.estadoJuego.getComunicador().getRival(),9990);
                DataInputStream dis=new DataInputStream(socket.getInputStream());
                PrintStream ps=new PrintStream(socket.getOutputStream())){
                ps.println("SolicitoGetChurrusqui");
                String s=dis.readLine();
                if(s.equals("True")){
                    return true;
                }else{
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void setChurrusqui(boolean b){
        this.churrusqui=b;
    }
    public CardBot primeraCartaYaJugada(){
        if(carta1.getYaJugada()){
            carta1.setYaJugada(false);
            return carta1;
        }
        if(carta2.getYaJugada()){
            carta2.setYaJugada(false);
            return carta2;
        }
        if(carta3.getYaJugada()){
            carta3.setYaJugada(false);
            return carta3;
        }
        if(carta4.getYaJugada()){
            carta4.setYaJugada(false);
            return carta4;
        }
        return null;
    }
    public MazoDeRobo getMazo(){
        return this.mazo;
    }
    public boolean puedoJugar(){
        if(!(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && (!this.puedoRobar || this.mazo.estaVacio()))
        {
            return false;
        }else{
            return true;
        }
    }
    public boolean puedoJugarLocal(){
        if(!(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && (!this.puedoRobar || this.mazo.estaVacio()))
        {
            return false;
        }else{
            return true;
        }
    }
    public CartaSimple solucionarBloqueo(){
        if(!this.mazo.estaVacio()){
            return this.mazo.robarCata();
        }else{
            if(!this.carta4.getYaJugada()){
                this.carta4.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta4.CartaACartaSimple().getSuit(),this.carta4.CartaACartaSimple().getValue());
                this.carta4.setSuit(0);
                this.carta4.setValue(0);
                return cs;
            }else if(!this.carta3.getYaJugada()){
                this.carta3.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta3.CartaACartaSimple().getSuit(),this.carta3.CartaACartaSimple().getValue());
                this.carta3.setSuit(0);
                this.carta3.setValue(0);
                return cs;
            }else if(!this.carta2.getYaJugada()){
                this.carta2.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta2.CartaACartaSimple().getSuit(),this.carta2.CartaACartaSimple().getValue());
                this.carta2.setSuit(0);
                this.carta2.setValue(0);
                return cs;
            }else if(!this.carta1.getYaJugada()){
                this.carta1.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta1.CartaACartaSimple().getSuit(),this.carta1.CartaACartaSimple().getValue());
                this.carta1.setSuit(0);
                this.carta1.setValue(0);
                return cs;
            }else{
                return new CartaSimple();
            }
        }
    }

    public void actualizar(){
        if(this.estadoJuego.finDeJuego){
            EstadoJuego.wait(5);
        }else{
            if(this.activado){
                if(!this.estadoJuego.bloqueado && !this.estadoJuego.getChurrusqui()){
                    if(this.estadoJuego.getMazoDeApilar1().getUltimaCarta().getValue()==this.estadoJuego.getMazoDeApilar2().getUltimaCarta().getValue()){
                        //Hacer lo de Churrusqui
                        System.out.println("Churrusqui");
                        this.solicitarCurrusqui();
                    }else{
                        while(this.puedoRobar && !this.mazo.estaVacio()){
                            if(this.carta1.getYaJugada() || this.carta2.getYaJugada() || this.carta3.getYaJugada() || this.carta4.getYaJugada()){
                                this.primeraCartaYaJugada().actualizarEstadoDeCarta(this.mazo.robarCata());
                                this.wait(1);
                            }else{
                                this.puedoRobar=false;
                            }
                        }
                        if(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada()){
                            if(this.estadoJuego.getMazoDeApilar1().aniadirNuevaCarta(this.carta1.CartaACartaSimple())){
                                this.carta1.setYaJugada(true);
                                this.carta1.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        } else if(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada()) {
                            if(this.estadoJuego.getMazoDeApilar2().aniadirNuevaCarta(this.carta1.CartaACartaSimple())){
                                this.carta1.setYaJugada(true);
                                this.carta1.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        }else if(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada()){
                            if(this.estadoJuego.getMazoDeApilar1().aniadirNuevaCarta(this.carta2.CartaACartaSimple())){
                                this.carta2.setYaJugada(true);
                                this.carta2.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        } else if(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada()) {
                            if(this.estadoJuego.getMazoDeApilar2().aniadirNuevaCarta(this.carta2.CartaACartaSimple())){
                                this.carta2.setYaJugada(true);
                                this.carta2.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        }else if(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada()){
                            if(this.estadoJuego.getMazoDeApilar1().aniadirNuevaCarta(this.carta3.CartaACartaSimple())){
                                this.carta3.setYaJugada(true);
                                this.carta3.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        } else if(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada()) {
                            if(this.estadoJuego.getMazoDeApilar2().aniadirNuevaCarta(this.carta3.CartaACartaSimple())){
                                this.carta3.setYaJugada(true);
                                this.carta3.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        }else if(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada()){
                            if(this.estadoJuego.getMazoDeApilar1().aniadirNuevaCarta(this.carta4.CartaACartaSimple())){
                                this.carta4.setYaJugada(true);
                                this.carta4.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        } else if(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada()) {
                            if(this.estadoJuego.getMazoDeApilar2().aniadirNuevaCarta(this.carta4.CartaACartaSimple())){
                                this.carta4.setYaJugada(true);
                                this.carta4.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta());
                                this.puedoRobar=true;
                            }
                        }
                    }

                    this.carta1.actualizar();
                    this.carta2.actualizar();
                    this.carta3.actualizar();
                    this.carta4.actualizar();
                    if(this.carta1.getYaJugada() && this.carta2.getYaJugada() && this.carta3.getYaJugada() && this.carta4.getYaJugada() && this.mazo.estaVacio()){
                        this.terminado=true;
                    }
                }
            }
        }
        this.mazo.actualizar();

    }

    public boolean getTerminado(){
        return this.terminado;
    }

    public boolean prueba(){
        return (
                !(this.puedoRobar || (!this.puedoRobar && this.mazo.estaVacio()))
                );
    }

    public void dibujar(Graphics g){
        this.carta1.dibujar(g);
        this.carta2.dibujar(g);
        this.carta3.dibujar(g);
        this.carta4.dibujar(g);
        this.mazo.dibujar(g);
    }

    @Override
    public void run() {
        funcionando=true;
        this.wait(2);
        while(funcionando){
            //Intentar arreglar esta chapuza
            this.wait(2);
            this.actualizar();
        }
    }

    public void wait(int s){
        //PRECONDITION:
        //POSTCONDITION: the execution will wait for 's' secs
        try{
            Thread.sleep(s*1000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }
}
