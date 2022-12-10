package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;

public class CardBot extends Card{

    public CardBot(CartaSimple cs,Vector2D v2d) {
        super(Loader.rotateImage(Loader.cargadorDeImagenes(CardHumano.buscarRutaTextura(cs.getSuit(),cs.getValue()), CardHumano.getAnchuraCarta(), CardHumano.getAlturaCarta())),v2d,cs);
    }

    public void actualizarEstadoDeCarta(CartaSimple cs){
        super.textura=Loader.rotateImage(Loader.cargadorDeImagenes(CardHumano.buscarRutaTextura(cs.getSuit(),cs.getValue()), CardHumano.getAnchuraCarta(), CardHumano.getAlturaCarta()));
        this.setSuit(cs.getSuit());
        this.setValue(cs.getValue());
        this.setYaJugada(false);
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {
        if(!this.getYaJugada() ){
            g.drawImage(super.textura,(int)this.getPosicionInicial().getX(),(int)this.getPosicionInicial().getY(),null);
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }
    }
}
