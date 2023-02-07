package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import input.MouseInput;

import java.awt.image.BufferedImage;

public class MazoDeApilar1 extends MazoDeApilar{
    public MazoDeApilar1(BufferedImage t, Vector2D v2d, EstadoJuego es) {
        super(t, v2d,es);
    }
    public MazoDeApilar1(BufferedImage t, Vector2D v2d, EstadoJuego es,Mazo m) {
        super(t, v2d,es,m);
    }

    @Override
    public void actualizar() {

        if(super.getHitBox().contains(MouseInput.RatonX,MouseInput.RatonY)){
            //System.out.println("Entro en 1");
            MouseInput.dentroDePila1=true;
            super.setPosibleJugada(true);
        }else{
            super.setPosibleJugada(false);
            MouseInput.dentroDePila1=false;
        }

    }
}
