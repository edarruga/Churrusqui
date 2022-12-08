package ObjetosJuego;

import Matematica.Vector2D;
import input.MouseInput;

import java.awt.*;

public class JugadorBot implements Runnable{
    private CardBot carta1;
    private CardBot carta2;
    private CardBot carta3;
    private CardBot carta4;
    private Vector2D posicionCarta1;
    private Vector2D posicionCarta2;
    private Vector2D posicionCarta3;
    private Vector2D posicionCarta4;
    private Vector2D posicionMazoDeRobo;
    private MazoDeRoboBot mazo;
    private boolean puedoRobar=false;
    private static CartaSimple cartaJugada=new CartaSimple();
    private boolean funcionando;

    public JugadorBot(CartaSimple[] csv){
        //Posicion de carta 1
        this.posicionCarta1=new Vector2D(Card.getAnchuraCarta()*(7.75),Card.getAlturaCarta()*(0.15));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(Card.getAnchuraCarta()*(6.25),Card.getAlturaCarta()*(0.15));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(Card.getAnchuraCarta()*(4.75),Card.getAlturaCarta()*(0.15));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(Card.getAnchuraCarta()*(3.25),Card.getAlturaCarta()*(0.15));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(Card.getAnchuraCarta()*(1.75),Card.getAlturaCarta()*(0.15));


        this.mazo=new MazoDeRoboBot(this.posicionMazoDeRobo,csv,this);
        this.puedoRobar=false;

        this.carta1=new CardBot(this.mazo.robarCata(),this.posicionCarta1);
        this.carta2=new CardBot(this.mazo.robarCata(),this.posicionCarta2);
        this.carta3=new CardBot(this.mazo.robarCata(),this.posicionCarta3);
        this.carta4=new CardBot(this.mazo.robarCata(),this.posicionCarta4);
        JugadorHumano.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
    }

    public void actualizar(){
        if(!MouseInput.tengoCarta){
            JugadorBot.cartaJugada.setSuit(0);
            JugadorBot.cartaJugada.setValue(0);
        }
        if(this.puedoRobar){
            if(this.carta1.getYaJugada() || this.carta2.getYaJugada() || this.carta3.getYaJugada() || this.carta4.getYaJugada()){
                this.puedoRobar=true;
            }
        }
        this.carta1.actualizar();
        this.carta2.actualizar();
        this.carta3.actualizar();
        this.carta4.actualizar();
        this.mazo.actualizar();
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
        while(funcionando){
            //Intentar arreglar esta chapuza
            this.actualizar();
        }
    }
}
