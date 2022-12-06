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
    private static MazoDeApilar mazoDeApilar1;
    private static MazoDeApilar mazoDeApilar2;
    private Mazo mazo;

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
        /*
        this.pila1=new Card(4,7,new Vector2D(abcisaPila1, ordenadaPilas));
        this.pila2=new Card(1,1,new Vector2D(abcisaPila2,ordenadaPilas));

        this.mazoRival=new Card(0,1,new Vector2D(abcisaMazoRival,ordenadaRival));
        this.pos1Rival=new Card(0,1,new Vector2D(abcisaPos1Jugadores,ordenadaRival));
        this.pos2Rival=new Card(0,1,new Vector2D(abcisaPos2Jugadores,ordenadaRival));
        this.pos3Rival=new Card(0,1,new Vector2D(abcisaPos3Jugadores,ordenadaRival));
        this.pos4Rival=new Card(0,1,new Vector2D(abcisaPos4Jugadores,ordenadaRival));

        this.mazoPropio=new Card(0,1,new Vector2D(abcisaMazoPropio,ordenadaPropia));
        this.pos1Propio=new Card(1,2,new Vector2D(abcisaPos1Jugadores,ordenadaPropia));
        this.pos2Propio=new Card(4,5,new Vector2D(abcisaPos2Jugadores,ordenadaPropia));
        this.pos3Propio=new Card(1,10,new Vector2D(abcisaPos3Jugadores,ordenadaPropia));
        this.pos4Propio=new Card(3,1,new Vector2D(abcisaPos4Jugadores,ordenadaPropia));
         */
        this.mazo=new Mazo();
        this.mazo.llenarMazo();
        //this.mazo.showmazo();
        //this.mazo.shuffle();
        CartaSimple[] cs1=new CartaSimple[20];
        CartaSimple[] cs2=new CartaSimple[20];
        for(int i=0;i<20;i++){
            cs1[i]=this.mazo.giveCard();
            cs1[i].showCard();
        }
        for(int i=0;i<20;i++){
            cs2[i]=this.mazo.giveCard();
            //cs2[i].showCard();
        }
        this.mazoDeApilar1=new MazoDeApilar1(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png",Card.getAnchuraCarta(),Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(4.5),Card.getAlturaCarta()*(1.4)));
        this.mazoDeApilar2=new MazoDeApilar2(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png",Card.getAnchuraCarta(),Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(6.5),Card.getAlturaCarta()*(1.4)));
        this.jugadorHumano=new JugadorHumano(cs1);
    }
    public static MazoDeApilar getMazoDeApilar1(){
        return mazoDeApilar1;
    }
    public static MazoDeApilar getMazoDeApilar2(){
        return mazoDeApilar2;
    }

    public void actualizar(){
        /*
        this.mazoPropio.actualizar();
        this.mazoRival.actualizar();

        this.pila1.actualizar();
        this.pila2.actualizar();

        this.pos1Rival.actualizar();
        this.pos2Rival.actualizar();
        this.pos3Rival.actualizar();
        this.pos4Rival.actualizar();

        this.pos1Propio.actualizar();
        this.pos2Propio.actualizar();
        this.pos3Propio.actualizar();
        this.pos4Propio.actualizar();
         */
        this.mazoDeApilar1.actualizar();
        this.mazoDeApilar2.actualizar();
        this.jugadorHumano.actualizar();
    }

    public void dibujar(Graphics g){
        /*
        pila1.dibujar(g);
        pila2.dibujar(g);

        mazoRival.dibujar(g);
        pos1Rival.dibujar(g);
        pos2Rival.dibujar(g);
        pos3Rival.dibujar(g);
        pos4Rival.dibujar(g);

        mazoPropio.dibujar(g);
        pos1Propio.dibujar(g);
        pos2Propio.dibujar(g);
        pos3Propio.dibujar(g);
        pos4Propio.dibujar(g);
         */
        this.mazoDeApilar1.dibujar(g);
        this.mazoDeApilar2.dibujar(g);
        this.jugadorHumano.dibujar(g);
    }

}
