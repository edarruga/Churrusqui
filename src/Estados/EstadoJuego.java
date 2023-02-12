package Estados;

import Matematica.Vector2D;
import ObjetosJuego.*;
import Red.Comunicador;
import graficos.Assets;
import graficos.Loader;
import principal.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EstadoJuego extends Estado{
    protected CardHumano pila1;
    protected CardHumano pila2;

    protected CardHumano mazoRival;
    protected CardHumano pos1Rival;
    protected CardHumano pos2Rival;
    protected CardHumano pos3Rival;
    protected CardHumano pos4Rival;

    protected CardHumano mazoPropio;
    protected CardHumano pos1Propio;
    protected CardHumano pos2Propio;
    protected CardHumano pos3Propio;
    protected CardHumano pos4Propio;

    protected JugadorHumano jugadorHumano;
    protected JugadorBot jugadorBot;
    protected Bloqueo bloqueo;
    protected Thread hilo1;
    protected Thread hilo2;
    protected Thread hiloBloqueo;
    protected MazoDeApilar1 mazoDeApilar1;
    protected MazoDeApilar2 mazoDeApilar2;
    protected Mazo mazo;
    protected boolean actualiza=false;
    public boolean bloqueado=false;
    public boolean churrusqui=false;
    public boolean finDeJuego=false;
    public boolean prioridad=true;
    protected Comunicador comunicador;//Solo es necesario para el estado de juego online pero lo necesito poner aqui para el getChurrrusqui de jugadorBot
    protected boolean mazodeApilar1SimpleModificado;
    protected Mazo mazodeApilar1Simple;

    protected boolean mazodeApilar2SimpleModificado;
    protected Mazo mazodeApilar2Simple;
    public boolean hiceCurrusquiOnline;

    //--------------Posiciones de las Cartas en el juego---------------//
    protected static double ordenadaRival= CardHumano.getAlturaCarta()*(0.15);
    protected static double ordenadaPropia= CardHumano.getAlturaCarta()*(2.65);
    protected static double abcisaMazoRival= CardHumano.getAnchuraCarta()*(1.75);
    protected static double abcisaMazoPropio= CardHumano.getAnchuraCarta()*(9.25);
    protected static double abcisaPos1Jugadores= CardHumano.getAnchuraCarta()*(3.25);
    protected static double abcisaPos2Jugadores= CardHumano.getAnchuraCarta()*(4.75);
    protected static double abcisaPos3Jugadores= CardHumano.getAnchuraCarta()*(6.25);
    protected static double abcisaPos4Jugadores= CardHumano.getAnchuraCarta()*(7.75);
    protected static double ordenadaPilas= CardHumano.getAlturaCarta()*(1.4);
    protected static double abcisaPila1= CardHumano.getAnchuraCarta()*(4.5);
    protected static double abcisaPila2= CardHumano.getAnchuraCarta()*(6.5);

    //-----------------------------------------------------------------//
    public EstadoJuego(JugadorSimple yo, JugadorSimple rival,Mazo mazo1,Mazo mazo2){
        this.mazoDeApilar1=new MazoDeApilar1(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(4.5), Card.getAlturaCarta()*(1.4)),this,mazo1);
        this.mazoDeApilar2=new MazoDeApilar2(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(6.5), Card.getAlturaCarta()*(1.4)),this,mazo2);
        this.jugadorHumano=new JugadorHumano(yo,this);
        this.jugadorBot=new JugadorBot(rival,this);
        this.bloqueo=new Bloqueo(this);
        hiloBloqueo=new Thread(bloqueo);
        hilo1=new Thread(jugadorHumano);
        hilo2=new Thread(jugadorBot);
        hiloBloqueo.start();
        hilo1.start();
        hilo2.start();
    }
    public EstadoJuego(){

    }
    public EstadoJuego(int n){
        //System.out.println("Me llaman");
        this.finDeJuego=false;
        this.mazo=new Mazo();
        this.mazo.llenarMazo();
        //this.mazo.showmazo();
        this.mazo.shuffle();
        CartaSimple[] cs1=new CartaSimple[n];
        CartaSimple[] cs2=new CartaSimple[n];
        for(int i=0;i<n;i++){
            cs1[i]=this.mazo.giveCard();
            //cs1[i].showCard();
        }
        for(int i=0;i<n;i++){
            cs2[i]=this.mazo.giveCard();
            //cs2[i].showCard();
        }
        this.mazoDeApilar1=new MazoDeApilar1(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(4.5), Card.getAlturaCarta()*(1.4)),this);
        this.mazoDeApilar2=new MazoDeApilar2(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(6.5), Card.getAlturaCarta()*(1.4)),this);
        this.jugadorHumano=new JugadorHumano(cs1,this);
        this.jugadorBot=new JugadorBot(cs2,this);
        this.bloqueo=new Bloqueo(this);
        hiloBloqueo=new Thread(bloqueo);
        hilo1=new Thread(jugadorHumano);
        hilo2=new Thread(jugadorBot);
        hiloBloqueo.start();
        hilo1.start();
        hilo2.start();

    }





    public Comunicador getComunicador() {
        return this.comunicador;
    }

    public void setComunicador(Comunicador comunicador) {
        this.comunicador = comunicador;
    }

    public boolean isMazodeApilar1SimpleModificado() {
        return mazodeApilar1SimpleModificado;
    }

    public void setMazodeApilar1SimpleModificado(boolean mazodeApilar1SimpleModificado) {
        this.mazodeApilar1SimpleModificado = mazodeApilar1SimpleModificado;
    }

    public Mazo getMazodeApilar1Simple() {
        return mazodeApilar1Simple;
    }

    public void setMazodeApilar1Simple(Mazo mazodeApilar1Simple) {
        this.mazodeApilar1Simple = mazodeApilar1Simple;
    }

    public boolean isMazodeApilar2SimpleModificado() {
        return mazodeApilar2SimpleModificado;
    }

    public void setMazodeApilar2SimpleModificado(boolean mazodeApilar2SimpleModificado) {
        this.mazodeApilar2SimpleModificado = mazodeApilar2SimpleModificado;
    }

    public Mazo getMazodeApilar2Simple() {
        return mazodeApilar2Simple;
    }

    public void setMazodeApilar2Simple(Mazo mazodeApilar2Simple) {
        this.mazodeApilar2Simple = mazodeApilar2Simple;
    }







    public MazoDeApilar1 getMazoDeApilar1(){
        return this.mazoDeApilar1;
    }
    public MazoDeApilar2 getMazoDeApilar2(){
        return this.mazoDeApilar2;
    }
    public JugadorHumano getJugadorHumano(){
        return this.jugadorHumano;
    }
    public JugadorBot getJugadorBot(){
        return this.jugadorBot;
    }

    public boolean getActualizar() {
        return this.actualiza;
    }
    public boolean getChurrusqui(){
        return this.churrusqui;
    }
    public synchronized boolean solicitarChurrusqui(){
        if(!this.jugadorBot.getChurrusqui() && !this.jugadorHumano.getChurrusqui()){
            return true;
        }else{
            return false;
        }
    }
    public boolean esCorrectoElChurrusqui(){
        if(this.mazoDeApilar1.getUltimaCarta().getValue()==this.mazoDeApilar2.getUltimaCarta().getValue()){
            return true;
        }else{
            return false;
        }
    }
    public static void wait(int s){
        try{
            Thread.sleep(s*1000);
        }catch (InterruptedException e){
            //System.out.println(e);
        }
    }


    public void actualizar(){
        if(this.jugadorHumano.getTerminado() || this.jugadorBot.getTerminado()){
            this.finDeJuego=true;
            EstadoJuego.wait(1);
            hilo1.interrupt();
            hilo2.interrupt();
            hiloBloqueo.interrupt();
            Estado.cambiarEstado(new EstadoFinDePartida(this.jugadorHumano.getTerminado()));//Termina la partida

        }else{
            this.actualiza=true;
            this.mazoDeApilar1.actualizar();
            this.mazoDeApilar2.actualizar();
            if(this.jugadorHumano.getChurrusqui() || this.jugadorBot.getChurrusqui()){
                this.churrusqui=true;
            }
            this.actualiza=false;
            //System.out.println("Bot: "+this.jugadorBot.puedoJugar()+" ,Humano:"+this.jugadorHumano.puedoJugar());
            //System.out.println(EstadoJuego.bloqueado);
            if(!this.churrusqui){
                if(!jugadorBot.puedoJugar() && !jugadorHumano.puedoJugar() && !this.bloqueado){
                    //System.out.println(this.jugadorBot.prueba());
                    //this.dibujar(g);
                    this.bloqueado=true;
                    //System.out.println("Ce bloqueo");

                }
            }


        }

    }

    public void dibujar(Graphics g){
        if(this.bloqueado && !this.getChurrusqui()){
            g.drawImage(Assets.Bloqueo,((Window.getAnchuraVentana()/2)-(CardHumano.getAnchuraCarta()/2)),((Window.getAlturaVentana()/2)-(CardHumano.getAnchuraCarta()/2)),null);
        }
        this.mazoDeApilar1.dibujar(g);
        this.mazoDeApilar2.dibujar(g);
        if(this.getChurrusqui()){
            g.drawImage(Assets.Churrusqui,0,0,null);
        }
        this.jugadorBot.dibujar(g);
        this.jugadorHumano.dibujar(g);

    }

}
