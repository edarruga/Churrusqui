package Estados;

import Componentes.Accion;
import Componentes.Boton;
import Matematica.Vector2D;
import graficos.Assets;
import graficos.Texto;
import principal.Window;

import java.awt.*;

public class EstadoMenu extends Estado{

    private Boton online;
    private Boton local;
    private Boton salir;

    public EstadoMenu(){
        this.online=new Boton(Assets.BotonMarronOut,
                Assets.BotonMarronIn,
                Window.getAnchuraVentana()  / 2 - Assets.BotonBlancoIn.getWidth() / 2,
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() / 2,
                " Multijugador",
                new Accion() {
                    @Override
                    public void hacerAccion() {
                        Estado.cambiarEstado(new EstadoBuscandoPartida());
                    }
                }
        );
        this.local=new Boton(Assets.BotonCremaOut,
                Assets.BotonCremaIn,
                Window.getAnchuraVentana() / 2 - Assets.BotonBlancoIn.getWidth() / 2,
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() * 2,
                "  Un jugador",
                new Accion() {
                    @Override
                    public void hacerAccion() {
                        Estado.cambiarEstado(new EstadoJuego());
                    }
                }
        );
        this.salir=new Boton(Assets.BotonGrisOut,
                Assets.BotonGrisIn,
                Window.getAnchuraVentana() / 2 - Assets.BotonBlancoIn.getWidth() / 2,
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() * (6/2) + Assets.BotonBlancoIn.getHeight()*2,
                "       Salir",
                new Accion() {
                    @Override
                    public void hacerAccion() {
                        System.exit(0);
                    }
                }
        );
    }

    @Override
    public void actualizar() {
        this.online.actualizar();
        this.local.actualizar();
        this.salir.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        this.online.dibujar(g);
        this.local.dibujar(g);
        this.salir.dibujar(g);
        Texto.dibujarTexto(g,"CHURRUSQUI",new Vector2D(Window.getAnchuraVentana()/8,Window.getAlturaVentana()*2/5),Color.WHITE, Assets.fuenteTitulo);
    }
}
