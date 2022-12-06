package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import input.MouseInput;

import java.awt.*;

public class JugadorHumano {
    private Card carta1;
    private Card carta2;
    private Card carta3;
    private Card carta4;
    private Vector2D posicionCarta1;
    private Vector2D posicionCarta2;
    private Vector2D posicionCarta3;
    private Vector2D posicionCarta4;
    private Vector2D posicionMazoDeRobo;
    private MazoDeRobo mazo;
    private boolean puedoRobar=false;
    private static CartaSimple cartaJugada=new CartaSimple();

    public JugadorHumano(CartaSimple[] csv){
        //Posicion de carta 1
        this.posicionCarta1=new Vector2D(Card.getAnchuraCarta()*(3.25),Card.getAlturaCarta()*(2.65));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(Card.getAnchuraCarta()*(4.75),Card.getAlturaCarta()*(2.65));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(Card.getAnchuraCarta()*(6.25),Card.getAlturaCarta()*(2.65));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(Card.getAnchuraCarta()*(7.75),Card.getAlturaCarta()*(2.65));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(Card.getAnchuraCarta()*(9.25),Card.getAlturaCarta()*(2.65));


        this.mazo=new MazoDeRobo(this.posicionMazoDeRobo,csv,this);
        this.puedoRobar=false;

        this.carta1=new Card(this.mazo.robarCata(),this.posicionCarta1);
        this.carta2=new Card(this.mazo.robarCata(),this.posicionCarta2);
        this.carta3=new Card(this.mazo.robarCata(),this.posicionCarta3);
        this.carta4=new Card(this.mazo.robarCata(),this.posicionCarta4);
        JugadorHumano.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
        JugadorHumano.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
    }
    public boolean getPuedoRobar(){
        return this.puedoRobar;
    }
    public static CartaSimple getCartaJugada(){
        return JugadorHumano.cartaJugada;
    }
    public static void setCartaJugada(int s, int v){
        JugadorHumano.cartaJugada.setSuit(s);
        JugadorHumano.cartaJugada.setValue(v);
    }
    public Card primeraCartaYaJugada(){
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

    public static MazoDeApilar getMazoDeApilar1() {
        return EstadoJuego.getMazoDeApilar1();
    }
    public static MazoDeApilar getMazoDeApilar2(){
        return EstadoJuego.getMazoDeApilar2();
    }

    public void actualizar(){
        if(!MouseInput.tengoCarta){
            JugadorHumano.cartaJugada.setSuit(0);
            JugadorHumano.cartaJugada.setValue(0);
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
}
