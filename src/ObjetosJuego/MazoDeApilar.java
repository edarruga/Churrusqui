package ObjetosJuego;

import Matematica.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MazoDeApilar extends ObjetoJuego{
    private Mazo mazo;
    private Rectangle hitBox;

    public MazoDeApilar(BufferedImage t, Vector2D p) {
        super(t, p);
        this.mazo=new Mazo();
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
