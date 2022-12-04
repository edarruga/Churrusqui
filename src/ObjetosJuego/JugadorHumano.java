package ObjetosJuego;

import Matematica.Vector2D;

import java.awt.*;

public class JugadorHumano {
    private Card[] cartas;
    private Vector2D[] posiciones;
    private MazoDeRobo mazo;
    private boolean puedoRobar;

    public JugadorHumano(CartaSimple[] csv){
        this.posiciones=new Vector2D[4];
        //Posicion de carta 1
        this.posiciones[0].setX(Card.getAnchuraCarta()*(3.25));
        this.posiciones[0].setY(Card.getAlturaCarta()*(2.65));
        //Posicion de carta 2
        this.posiciones[1].setX(Card.getAnchuraCarta()*(4.75));
        this.posiciones[1].setY(Card.getAlturaCarta()*(2.65));
        //Posicion de carta 3
        this.posiciones[2].setX(Card.getAnchuraCarta()*(6.25));
        this.posiciones[2].setY(Card.getAlturaCarta()*(2.65));
        //Posicion de carta 4
        this.posiciones[3].setX(Card.getAnchuraCarta()*(7.75));
        this.posiciones[3].setY(Card.getAlturaCarta()*(2.65));
        //Posicion del mazo para robar
        this.posiciones[2].setX(Card.getAnchuraCarta()*(9.25));
        this.posiciones[2].setY(Card.getAlturaCarta()*(2.65));

        this.mazo=new MazoDeRobo(this.posiciones[4],csv);
        this.puedoRobar=false;
        this.cartas=new Card[3];

        this.cartas[0]=new Card(this.mazo.robarCata(),this.posiciones[0]);
        this.cartas[1]=new Card(this.mazo.robarCata(),this.posiciones[1]);
        this.cartas[2]=new Card(this.mazo.robarCata(),this.posiciones[2]);
        this.cartas[3]=new Card(this.mazo.robarCata(),this.posiciones[3]);
    }
    public void actualizar(){
        this.cartas[0].actualizar();
        this.cartas[1].actualizar();
        this.cartas[2].actualizar();
        this.cartas[3].actualizar();
        if(this.puedoRobar){
            if(this.cartas[0].getYaJugada() || this.cartas[1].getYaJugada() || this.cartas[2].getYaJugada() || this.cartas[3].getYaJugada()){
                this.puedoRobar=true;
            }
        }
        this.mazo.actualizar();
    }

    public void dibujar(Graphics g){
        this.cartas[0].dibujar(g);
        this.cartas[1].dibujar(g);
        this.cartas[2].dibujar(g);
        this.cartas[3].dibujar(g);
        this.mazo.dibujar(g);
    }
}
