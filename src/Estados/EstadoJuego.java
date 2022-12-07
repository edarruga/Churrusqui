package Estados;

import Matematica.Vector2D;
import ObjetosJuego.*;
import graficos.Loader;
import input.MouseInput;
import principal.Window;

import java.awt.*;

public class EstadoJuego {
    private Card pila1;
    private Card pila2;

    private Card mazoRival;
    private Card pos1Rival;
    private Card pos2Rival;
    private Card pos3Rival;
    private Card pos4Rival;

    private Card mazoPropio;
    private Card pos1Propio;
    private Card pos2Propio;
    private Card pos3Propio;
    private Card pos4Propio;

    private JugadorHumano jugadorHumano;
    private Thread hilo1;
    private static MazoDeApilar mazoDeApilar1;
    private static MazoDeApilar mazoDeApilar2;
    private Mazo mazo;
    private static boolean actualiza=false;

    //--------------Posiciones de las Cartas en el juego---------------//
    private static double ordenadaRival=Card.getAlturaCarta()*(0.15);
    private static double ordenadaPropia=Card.getAlturaCarta()*(2.65);
    private static double abcisaMazoRival=Card.getAnchuraCarta()*(1.75);
    private static double abcisaMazoPropio=Card.getAnchuraCarta()*(9.25);
    private static double abcisaPos1Jugadores=Card.getAnchuraCarta()*(3.25);
    private static double abcisaPos2Jugadores=Card.getAnchuraCarta()*(4.75);
    private static double abcisaPos3Jugadores=Card.getAnchuraCarta()*(6.25);
    private static double abcisaPos4Jugadores=Card.getAnchuraCarta()*(7.75);
    private static double ordenadaPilas=Card.getAlturaCarta()*(1.4);
    private static double abcisaPila1=Card.getAnchuraCarta()*(4.5);
    private static double abcisaPila2=Card.getAnchuraCarta()*(6.5);

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
        this.mazoDeApilar1=new MazoDeApilar1(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png",Card.getAnchuraCarta(),Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(4.5),Card.getAlturaCarta()*(1.4)));
        this.mazoDeApilar2=new MazoDeApilar2(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png",Card.getAnchuraCarta(),Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(6.5),Card.getAlturaCarta()*(1.4)));
        this.jugadorHumano=new JugadorHumano(cs1);
        hilo1=new Thread(jugadorHumano);
        hilo1.start();

    }
    public static MazoDeApilar getMazoDeApilar1(){
        return mazoDeApilar1;
    }
    public static MazoDeApilar getMazoDeApilar2(){
        return mazoDeApilar2;
    }
    public static boolean getActualizar(){
        return EstadoJuego.actualiza;
    }

    public void actualizar(){
        this.actualiza=true;
        this.mazoDeApilar1.actualizar();
        this.mazoDeApilar2.actualizar();
        this.actualiza=false;


    }

    public void dibujar(Graphics g){
        this.mazoDeApilar1.dibujar(g);
        this.mazoDeApilar2.dibujar(g);
        this.jugadorHumano.dibujar(g);

    }

}
