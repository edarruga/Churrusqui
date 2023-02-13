package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;

public class CardBot extends Card{

    public CardBot(CartaSimple cs,Vector2D v2d) {
        super(Loader.rotateImage(Loader.cargadorDeImagenes(CardHumano.buscarRutaTextura(cs.getSuit(),cs.getValue()), CardHumano.getAnchuraCarta(), CardHumano.getAlturaCarta())),v2d,cs);
    }

    public void actualizarEstadoDeCarta(CartaSimple cs){
        if(cs.getSuit()==0 && cs.getValue()==0){
            this.setYaJugada(true);
            super.textura=Loader.rotateImage(Loader.cargadorDeImagenes(Card.buscarRutaTextura(5,5),Card.getAnchuraCarta(),Card.getAlturaCarta()));
        }else{
            super.textura=Loader.rotateImage(Loader.cargadorDeImagenes(Card.buscarRutaTextura(cs.getSuit(),cs.getValue()),Card.getAnchuraCarta(),Card.getAlturaCarta()));
            this.setYaJugada(false);
        }
        this.setSuit(cs.getSuit());
        this.setValue(cs.getValue());

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
