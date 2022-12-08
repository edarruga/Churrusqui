package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CardBot extends ObjetoJuego{

    private int suit;   //Oro=1,Copa=2,Espada=3,Basto=4

    private int value;  //The value is between 1-10, but our methods show the 8, 9 and 10 as 10, 11 and 12, respectically
    private Vector2D posicionInicial;
    private boolean yaJugada=false;

    public CardBot(CartaSimple cs,Vector2D v2d) {
        super(Loader.rotateImage(Loader.cargadorDeImagenes(Card.buscarRutaTextura(cs.getSuit(),cs.getValue()),Card.getAnchuraCarta(),Card.getAlturaCarta())),v2d);
        this.suit=cs.getSuit();
        this.value=cs.getValue();
        this.posicionInicial=new Vector2D(v2d.getX(),v2d.getY());
    }
    public boolean getYaJugada(){
        return this.yaJugada;
    }
    public void setYaJugada(boolean b){
        this.yaJugada=b;
    }
    public void actualizarEstadoDeCarta(CartaSimple cs){
        super.textura=Loader.rotateImage(Loader.cargadorDeImagenes(Card.buscarRutaTextura(cs.getSuit(),cs.getValue()),Card.getAnchuraCarta(),Card.getAlturaCarta()));
        super.posicion.setX(this.posicionInicial.getX());
        super.posicion.setY(this.posicionInicial.getY());
        this.suit=cs.getSuit();
        this.value=cs.getValue();
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {
        if(!this.getYaJugada() ){
            g.drawImage(super.textura,(int)this.posicionInicial.getX(),(int)this.posicionInicial.getY(),null);
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }
    }
}
