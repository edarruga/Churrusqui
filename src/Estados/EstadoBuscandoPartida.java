package Estados;

import Componentes.Accion;
import Componentes.Accion3;
import Componentes.Boton;
import Matematica.Vector2D;
import Red.Buscador;
import graficos.Assets;
import graficos.Texto;
import input.MouseInput;
import principal.Window;

import java.awt.*;


public class EstadoBuscandoPartida extends Estado{

    private Boton volver;
    private Buscador buscador;
    public static int contador =0;

    public EstadoBuscandoPartida(){
        this.buscador=new Buscador();
        MouseInput.botonIzquierdo=false;
        this.buscador.start();
        this.volver=new Boton(Assets.BotonGrisOut,
                Assets.BotonGrisIn,
                principal.Window.getAnchuraVentana() / 2 - Assets.BotonBlancoIn.getWidth() / 2,
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() * (6/2) + Assets.BotonBlancoIn.getHeight()/2,
                "      Volver",
                new Accion3(this.buscador)
        );
    }

    @Override
    public void actualizar() {
        this.volver.actualizarV3();
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
