package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;

public class MazoDeRoboBot extends MazoDeRobo{

    private JugadorBot jugadorBot;

    public MazoDeRoboBot(Vector2D v2d,CartaSimple[] csv,JugadorBot jb) {
        super(v2d,csv);
        this.jugadorBot=jb;
    }
    public MazoDeRoboBot(Vector2D v2d,Mazo m,JugadorBot jb) {
        super(v2d,m);
        this.jugadorBot=jb;

    }

    @Override
    public void actualizar() {
        if(Card.getEstigma()){
            if(super.estigma==false){
                super.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(0,1),Card.getAnchuraCarta(),Card.getAlturaCarta());
                super.estigma=true;
            }
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
