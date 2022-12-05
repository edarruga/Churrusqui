package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;
import input.MouseInput;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MazoDeApilar extends ObjetoJuego{
    private Mazo mazo;
    private Rectangle hitBox;
    private boolean posibleJugada=false;
    private boolean seModifico=false;

    public MazoDeApilar(BufferedImage t, Vector2D v2d) {
        super(t, v2d);
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),Card.getAnchuraCarta(),Card.getAlturaCarta());
        this.mazo=new Mazo();
    }

    @Override
    public void actualizar() {

        if(this.hitBox.contains(MouseInput.RatonX,MouseInput.RatonY)){
            MouseInput.dentroDePila=true;
            if(MouseInput.colocarCarta){
                this.posibleJugada=true;
            }
        }else{
            this.posibleJugada=false;
            MouseInput.dentroDePila=false;
        }

    }
    public CartaSimple getUltimaCarta(){
        return this.mazo.getUltimaCarta();
    }
    public boolean getPosibleJugada(){
        return this.posibleJugada;
    }
    public synchronized boolean aniadirNuevaCarta(){
        if(this.esJugable(this.mazo.getUltimaCarta(),JugadorHumano.getCartaJugada())){
            this.mazo.insertarCartaSimple(JugadorHumano.getCartaJugada());
            this.seModifico=true;
            return true;
        }
        return false;
    }
    public void aniadirNuevaCartaAlaFuerza(CartaSimple cs){
        this.mazo.insertarCartaSimple(cs);
        this.seModifico=true;
    }
    public boolean esJugable(CartaSimple cMesa,CartaSimple cNueva){
        if(cMesa.ImmediatelyNext(cNueva)||cMesa.ImmediatelyPrevious(cNueva)){
            return true;
        }
        return false;
    }

    @Override
    public void dibujar(Graphics g) {
        if(this.mazo.getNum()!=0){
            if(this.seModifico){
                super.textura= Loader.cargadorDeImagenes(Card.buscarRutaTextura(this.mazo.getUltimaCarta().getSuit(),this.mazo.getUltimaCarta().getValue()),Card.getAnchuraCarta(),Card.getAlturaCarta());
                this.seModifico=false;
            }
            g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }
    }
}
