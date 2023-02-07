package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import input.MouseInput;

import java.awt.image.BufferedImage;

public class MazoDeApilar2 extends MazoDeApilar{
    public MazoDeApilar2(BufferedImage t, Vector2D v2d, EstadoJuego es) {
        super(t, v2d,es);
    }
    public MazoDeApilar2(BufferedImage t, Vector2D v2d, EstadoJuego es,Mazo m) {
        super(t, v2d,es,m);
    }

    @Override
    public void actualizar() {

        if(super.getHitBox().contains(MouseInput.RatonX,MouseInput.RatonY)){
            //System.out.println("Entro en 2");
            MouseInput.dentroDePila2=true;
            //if(MouseInput.colocarCarta2){//Esto hay que cambiarlo
                super.setPosibleJugada(true);
            //}
        }else{
            super.setPosibleJugada(false);
            MouseInput.dentroDePila2=false;
        }

    }
}
