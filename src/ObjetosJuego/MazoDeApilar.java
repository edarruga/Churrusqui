package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MazoDeApilar extends ObjetoJuego{
    private Mazo mazo;
    private Rectangle hitBox;
    protected EstadoJuego estadoJuego;
    private boolean posibleJugada=false;
    private boolean seModifico=false;

    public MazoDeApilar(BufferedImage t, Vector2D v2d,EstadoJuego es) {
        super(t, v2d);
        this.estadoJuego=es;
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(), CardHumano.getAnchuraCarta(), CardHumano.getAlturaCarta());
        this.mazo=new Mazo();
    }

    public synchronized CartaSimple getUltimaCarta(){
        return this.mazo.getUltimaCarta();
    }
    public synchronized boolean getPosibleJugada(){
        return this.posibleJugada;
    }
    public void setPosibleJugada(boolean b){
        this.posibleJugada=b;
    }
    public Rectangle getHitBox(){
        return this.hitBox;
    }
    public synchronized boolean aniadirNuevaCarta(CartaSimple cs){
        if(this.esJugable(cs)){
            this.mazo.insertarCartaSimple(cs);
            this.seModifico=true;
            return true;
        }
        return false;
    }
    public CartaSimple[] darCartas(){
        CartaSimple[] cartasSimples=new CartaSimple[this.mazo.getNum()];
        int i=0;
        while (this.mazo.getNum()!=0){
            cartasSimples[i]=this.mazo.giveCard();
            i++;
        }
        return cartasSimples;
    }
    public void aniadirNuevaCartaAlaFuerza(CartaSimple cs){
        this.mazo.insertarCartaSimple(cs);
        this.seModifico=true;
    }
    public synchronized boolean esJugable(CartaSimple cNueva){
        if((this.getUltimaCarta().ImmediatelyNext(cNueva)||this.getUltimaCarta().ImmediatelyPrevious(cNueva)) && !this.estadoJuego.getChurrusqui()){
            return true;
        }
        return false;
    }

    @Override
    public void dibujar(Graphics g) {
        if(this.mazo.getNum()!=0){
            if(this.seModifico){
                super.textura= Loader.cargadorDeImagenes(CardHumano.buscarRutaTextura(this.mazo.getUltimaCarta().getSuit(),this.mazo.getUltimaCarta().getValue()), CardHumano.getAnchuraCarta(), CardHumano.getAlturaCarta());
                this.seModifico=false;
            }
            g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }
    }
}
