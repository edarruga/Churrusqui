package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.image.BufferedImage;

public abstract class MazoDeRobo extends ObjetoJuego{
    protected Mazo mazo;


    public MazoDeRobo(Vector2D v2d,CartaSimple[] csv) {
        super(Loader.cargadorDeImagenes(CardHumano.buscarRutaTextura(0,1), Card.getAnchuraCarta(), Card.getAlturaCarta()), v2d);
        this.mazo=new Mazo();
        this.mazo.insertarCartasSimples(csv);
    }
    public CartaSimple robarCata(){
        return this.mazo.giveCard();
    }
    public boolean estaVacio(){
        if(this.mazo.getNum()==0){
            return true;
        }else{
            return false;
        }
    }
}
