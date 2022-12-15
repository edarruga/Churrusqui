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

    private static JugadorHumano jugadorHumano;
    private static JugadorBot jugadorBot;
    private Thread hilo1;
    private Thread hilo2;
    private Thread hiloBloqueo;
    private static MazoDeApilar mazoDeApilar1;
    private static MazoDeApilar mazoDeApilar2;
    private Mazo mazo;
    private static boolean actualiza=false;
    public static boolean bloqueado=false;
    private static boolean churrusqui=false;

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
        this.mazoDeApilar1=new MazoDeApilar1(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(4.5), Card.getAlturaCarta()*(1.4)));
        this.mazoDeApilar2=new MazoDeApilar2(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(6.5), Card.getAlturaCarta()*(1.4)));
        this.jugadorHumano=new JugadorHumano(cs1);
        this.jugadorBot=new JugadorBot(cs2);
        hiloBloqueo=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //System.out.println("Que paso");
                    //System.out.println("Desde el run: "+EstadoJuego.bloqueado);
                    System.out.print("");
                    if(EstadoJuego.bloqueado){
                        this.wait(4);
                        EstadoJuego.mazoDeApilar1.aniadirNuevaCartaAlaFuerza(EstadoJuego.jugadorHumano.solucionarBloqueo());
                        EstadoJuego.mazoDeApilar2.aniadirNuevaCartaAlaFuerza(EstadoJuego.jugadorBot.solucionarBloqueo());
                        EstadoJuego.bloqueado=false;
                    }
                    if(EstadoJuego.getChurrusqui()){
                        this.wait(4);
                        if(EstadoJuego.jugadorHumano.getChurrusqui()){
                            if(EstadoJuego.esCorrectoElChurrusqui()){
                                EstadoJuego.jugadorBot.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar1().darCartas());
                                EstadoJuego.jugadorBot.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar2().darCartas());
                            }else{
                                EstadoJuego.jugadorHumano.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar1().darCartas());
                                EstadoJuego.jugadorHumano.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar2().darCartas());
                            }
                        }else{
                            if(EstadoJuego.esCorrectoElChurrusqui()){
                                EstadoJuego.jugadorHumano.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar1().darCartas());
                                EstadoJuego.jugadorHumano.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar2().darCartas());
                            }else{
                                EstadoJuego.jugadorBot.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar1().darCartas());
                                EstadoJuego.jugadorBot.getMazo().aniadirCartas(EstadoJuego.getMazoDeApilar2().darCartas());
                            }
                        }
                        EstadoJuego.mazoDeApilar1.aniadirNuevaCartaAlaFuerza(EstadoJuego.jugadorHumano.solucionarBloqueo());
                        EstadoJuego.mazoDeApilar2.aniadirNuevaCartaAlaFuerza(EstadoJuego.jugadorBot.solucionarBloqueo());
                        EstadoJuego.jugadorBot.setChurrusqui(false);
                        EstadoJuego.jugadorHumano.setChurrusqui(false);
                        EstadoJuego.churrusqui=false;

                    }
                }
            }

            public void wait(int s){
                try{
                    Thread.sleep(s*1000);
                }catch (InterruptedException e){
                    System.out.println(e);
                }
            }
        });
        hilo1=new Thread(jugadorHumano);
        hilo2=new Thread(jugadorBot);
        hiloBloqueo.start();
        hilo1.start();
        hilo2.start();

    }
    public static MazoDeApilar getMazoDeApilar1(){
        return mazoDeApilar1;
    }
    public static MazoDeApilar getMazoDeApilar2(){
        return mazoDeApilar2;
    }

    public static boolean getActualizar() {
        return EstadoJuego.actualiza;
    }
    public static boolean getChurrusqui(){
        return EstadoJuego.churrusqui;
    }
    public synchronized static boolean solicitarChurrusqui(){
        if(!EstadoJuego.jugadorBot.getChurrusqui() && !EstadoJuego.jugadorHumano.getChurrusqui()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean esCorrectoElChurrusqui(){
        if(EstadoJuego.mazoDeApilar1.getUltimaCarta().getValue()==EstadoJuego.mazoDeApilar2.getUltimaCarta().getValue()){
            return true;
        }else{
            return false;
        }
    }


    public void actualizar(){
        if(EstadoJuego.jugadorHumano.getTerminado() || EstadoJuego.jugadorBot.getTerminado()){
            Estado.cambiarEstado(new EstadoFinDePartida(EstadoJuego.jugadorHumano.getTerminado()));//Termina la partida
        }else{
            this.actualiza=true;
            this.mazoDeApilar1.actualizar();
            this.mazoDeApilar2.actualizar();
            if(EstadoJuego.jugadorHumano.getChurrusqui() || EstadoJuego.jugadorBot.getChurrusqui()){
                EstadoJuego.churrusqui=true;
            }
            this.actualiza=false;
            //System.out.println("Bot: "+this.jugadorBot.puedoJugar()+" ,Humano:"+this.jugadorHumano.puedoJugar());
            //System.out.println(EstadoJuego.bloqueado);
            if(!EstadoJuego.churrusqui){
                if(!jugadorBot.puedoJugar() && !jugadorHumano.puedoJugar() && !EstadoJuego.bloqueado){
                    //System.out.println(this.jugadorBot.prueba());
                    //this.dibujar(g);
                    EstadoJuego.bloqueado=true;
                    //System.out.println("Ce bloqueo");

                }
            }


        }

    }

    public void dibujar(Graphics g){
        if(EstadoJuego.bloqueado && !EstadoJuego.getChurrusqui()){
            g.drawImage(Assets.Bloqueo,((Window.getAnchuraVentana()/2)-(CardHumano.getAnchuraCarta()/2)),((Window.getAlturaVentana()/2)-(CardHumano.getAnchuraCarta()/2)),null);
        }
        this.mazoDeApilar1.dibujar(g);
        this.mazoDeApilar2.dibujar(g);
        if(EstadoJuego.getChurrusqui()){
            g.drawImage(Assets.Churrusqui,0,0,null);
        }
        this.jugadorBot.dibujar(g);
        this.jugadorHumano.dibujar(g);

    }

}
