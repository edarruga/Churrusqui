package Estados;

import Componentes.Accion;
import Componentes.Boton;
import graficos.Assets;
import input.MouseInput;
import principal.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EstadoFinDePartida extends Estado{
    private Boton volver;
    private BufferedImage resultado;


    public EstadoFinDePartida(boolean victoria){
        MouseInput.botonIzquierdo=false;
        if(victoria){
            this.resultado=Assets.Victoria;
        }else{
            this.resultado=Assets.Derrota;
        }
        this.volver=new Boton(Assets.BotonGrisOut,
                Assets.BotonGrisIn,
                Window.getAnchuraVentana() / 2 - Assets.BotonBlancoIn.getWidth() / 2,
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() * (6/2) + Assets.BotonBlancoIn.getHeight()/2,
                "      Volver",
                new Accion() {
                    @Override
                    public void hacerAccion() {
                        Estado.cambiarEstado(new EstadoMenu());
                    }
                }
        );
    }
    @Override
    public void actualizar() {
        this.volver.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        g.drawImage(this.resultado,0,0,null);
        this.volver.dibujar(g);
    }
}
