package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MazoDeRobo extends ObjetoJuego{
    private Mazo mazo;
    private Rectangle hitBox;
    public MazoDeRobo(Vector2D v2d,CartaSimple[] csv) {
        super(Loader.cargadorDeImagenes(Card.buscarRutaTextura(0,1),Card.getAnchuraCarta(),Card.getAlturaCarta()), v2d);
        this.mazo=new Mazo();
        this.mazo.insertarCartasSimples(csv);
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),Card.getAnchuraCarta(),Card.getAlturaCarta());
    }
    public CartaSimple robarCata(){
       return this.mazo.giveCard();
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
