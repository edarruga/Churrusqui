package ObjetosJuego;

import Matematica.Vector2D;
import input.MouseInput;

import java.awt.image.BufferedImage;

public class MazoDeApilar1 extends MazoDeApilar{
    public MazoDeApilar1(BufferedImage t, Vector2D v2d) {
        super(t, v2d);
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
