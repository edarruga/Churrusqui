package Estados;

import Componentes.Accion;
import Componentes.Boton;
import Matematica.Vector2D;
import graficos.Assets;
import graficos.Texto;
import principal.Window;

import java.awt.*;


public class EstadoBuscandoPartida extends Estado{

    private Boton volver;
    public static int contador =0;

    public EstadoBuscandoPartida(){

        this.volver=new Boton(Assets.BotonGrisOut,
                Assets.BotonGrisIn,
                principal.Window.getAnchuraVentana() / 2 - Assets.BotonBlancoIn.getWidth() / 2,
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
        if (EstadoBuscandoPartida.contador == 0) {
            Texto.dibujarTexto(g,"Buscando rival",new Vector2D(Window.getAnchuraVentana()/2.65,Window.getAlturaVentana()*2.5/5),Color.WHITE, Assets.fuenteMediana);
        } else if (EstadoBuscandoPartida.contador ==1) {
            Texto.dibujarTexto(g,"Buscando rival .",new Vector2D(Window.getAnchuraVentana()/2.65,Window.getAlturaVentana()*2.5/5),Color.WHITE, Assets.fuenteMediana);
        } else if (EstadoBuscandoPartida.contador ==2) {
            Texto.dibujarTexto(g,"Buscando rival . .",new Vector2D(Window.getAnchuraVentana()/2.65,Window.getAlturaVentana()*2.5/5),Color.WHITE, Assets.fuenteMediana);
        }else if(EstadoBuscandoPartida.contador ==3){
            Texto.dibujarTexto(g,"Buscando rival . . .",new Vector2D(Window.getAnchuraVentana()/2.65,Window.getAlturaVentana()*2.5/5),Color.WHITE, Assets.fuenteMediana);
        }

        this.volver.dibujar(g);
    }
}
