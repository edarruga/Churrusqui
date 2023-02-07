package Estados;

import Matematica.Vector2D;
import ObjetosJuego.*;
import graficos.Assets;
import graficos.Loader;
import principal.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EstadoJuego extends Estado{
    private CardHumano pila1;
    private CardHumano pila2;

    private CardHumano mazoRival;
    private CardHumano pos1Rival;
    private CardHumano pos2Rival;
    private CardHumano pos3Rival;
    private CardHumano pos4Rival;

    private CardHumano mazoPropio;
    private CardHumano pos1Propio;
    private CardHumano pos2Propio;
    private CardHumano pos3Propio;
    private CardHumano pos4Propio;

    private JugadorHumano jugadorHumano;
    private JugadorBot jugadorBot;
    private Bloqueo bloqueo;
    private Thread hilo1;
    private Thread hilo2;
    private Thread hiloBloqueo;
    private MazoDeApilar mazoDeApilar1;
    private MazoDeApilar mazoDeApilar2;
    private Mazo mazo;
    private boolean actualiza=false;
    public boolean bloqueado=false;
    public boolean churrusqui=false;
    public boolean finDeJuego=false;

    //--------------Posiciones de las Cartas en el juego---------------//
    private static double ordenadaRival= CardHumano.getAlturaCarta()*(0.15);
    private static double ordenadaPropia= CardHumano.getAlturaCarta()*(2.65);
    private static double abcisaMazoRival= CardHumano.getAnchuraCarta()*(1.75);
    private static double abcisaMazoPropio= CardHumano.getAnchuraCarta()*(9.25);
    private static double abcisaPos1Jugadores= CardHumano.getAnchuraCarta()*(3.25);
    private static double abcisaPos2Jugadores= CardHumano.getAnchuraCarta()*(4.75);
    private static double abcisaPos3Jugadores= CardHumano.getAnchuraCarta()*(6.25);
    private static double abcisaPos4Jugadores= CardHumano.getAnchuraCarta()*(7.75);
    private static double ordenadaPilas= CardHumano.getAlturaCarta()*(1.4);
    private static double abcisaPila1= CardHumano.getAnchuraCarta()*(4.5);
    private static double abcisaPila2= CardHumano.getAnchuraCarta()*(6.5);

    //-----------------------------------------------------------------//
    public EstadoJuego(){
        this.finDeJuego=false;
        this.mazo=new Mazo();
        this.mazo.llenarMazo();
        //this.mazo.showmazo();
        this.mazo.shuffle();
        CartaSimple[] cs1=new CartaSimple[20];
        CartaSimple[] cs2=new CartaSimple[20];
        for(int i=0;i<20;i++){
            cs1[i]=this.mazo.giveCard();
            //cs1[i].showCard();
        }
        for(int i=0;i<20;i++){
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
    public MazoDeApilar getMazoDeApilar1(){
        return this.mazoDeApilar1;
    }
    public MazoDeApilar getMazoDeApilar2(){
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
            System.out.println(e);
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
