package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;

import java.awt.*;

public class MazoDeRoboBot extends MazoDeRobo{

    private JugadorBot jugadorBot;

    public MazoDeRoboBot(Vector2D v2d,CartaSimple[] csv,JugadorBot jb) {
        super(v2d,csv);
        this.jugadorBot=jb;
        this.mazo=new Mazo();
        this.mazo.insertarCartasSimples(csv);
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
