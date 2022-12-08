package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MazoDeRoboBot extends ObjetoJuego{

    private Mazo mazo;
    private JugadorBot jugadorBot;

    public MazoDeRoboBot(Vector2D v2d,CartaSimple[] csv,JugadorBot jb) {
        super(Loader.cargadorDeImagenes(Card.buscarRutaTextura(0,1),Card.getAnchuraCarta(),Card.getAlturaCarta()), v2d);
        this.jugadorBot=jb;
        this.mazo=new Mazo();
        this.mazo.insertarCartasSimples(csv);
    }
    public CartaSimple robarCata(){
        return this.mazo.giveCard();
    }
    public boolean quedanCartas(){
        if(this.mazo.getNum()==0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void actualizar() {

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
