package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import graficos.Loader;
import input.MouseInput;

import java.awt.*;

public class MazoDeRoboHumano extends MazoDeRobo{
    private Rectangle hitBox;
    private boolean desclico=true;
    private JugadorHumano jugadorHumano;
    public MazoDeRoboHumano(Vector2D v2d, CartaSimple[] csv, JugadorHumano jh) {
        super(v2d,csv);
        this.jugadorHumano=jh;
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(), CardHumano.getAnchuraCarta(), CardHumano.getAlturaCarta());
    }

    @Override
    public void actualizar() {

        if(this.hitBox.contains(MouseInput.RatonX,MouseInput.RatonY) && !EstadoJuego.getChurrusqui()){
            if(MouseInput.clickEnMazoDeRobo){
                CardHumano c=this.jugadorHumano.primeraCartaYaJugada();
                if(c!=null && !this.estaVacio()){
                    c.actualizarEstadoDeCarta(this.mazo.giveCard());
                    MouseInput.clickEnMazoDeRobo =false;
                }

            }
        }else{
            MouseInput.clickEnMazoDeRobo =false;
        }
    }

    @Override
    public void dibujar(Graphics g) {
        if(this.mazo.getNum()!=0){
            g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }
    }
}
