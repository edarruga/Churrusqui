package Estados;

import Matematica.Vector2D;
import ObjetosJuego.Card;
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

    //--------------Posiciones de las Cartas en el juego---------------//
    private static double ordenadaRival=Window.getAlturaVentana()*(0.15)/(4);
    private static double ordenadaPropia=Window.getAlturaVentana()*(2.65)/(4);
    private static double abcisaMazoRival=Window.getAnchuraVentana()*(1.75)/(12);
    private static double abcisaMazoPropio=Window.getAnchuraVentana()*(9.25)/(12);
    private static double abcisaPos1Jugadores=Window.getAnchuraVentana()*(3.25)/(12);
    private static double abcisaPos2Jugadores=Window.getAnchuraVentana()*(4.75)/(12);
    private static double abcisaPos3Jugadores=Window.getAnchuraVentana()*(6.25)/(12);
    private static double abcisaPos4Jugadores=Window.getAnchuraVentana()*(7.75)/(12);
    private static double ordenadaPilas=Window.getAlturaVentana()*(1.4)/(4);
    private static double abcisaPila1=Window.getAnchuraVentana()*(4.5)/(12);
    private static double abcisaPila2=Window.getAnchuraVentana()*(6.5)/(12);

    //-----------------------------------------------------------------//
    public EstadoJuego(){
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
    }

    public void actualizar(){
        this.mazoPropio.actualizar();
    }

    public void dibujar(Graphics g){
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
    }

}
