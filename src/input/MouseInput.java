package input;

import ObjetosJuego.MazoDeApilar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.BUTTON1;

public class MouseInput extends MouseAdapter {
    public static int RatonX;//Posicion X del raton
    public static int RatonY;//Posicion Y del raton
    public static boolean botonIzquierdo;//Para saber si el boton izquierdo del raton esta pulsado
    public static boolean tengoCarta=false;
    public static boolean colocarCarta=false;
    public static boolean dentroDePila=false;


    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()== BUTTON1){
            botonIzquierdo=true;
            //System.out.println("Se ha clicado el boton izquierdo en la posicion X: "+e.getX()+" ,Y: "+e.getY());
        }

    }
    public void mouseReleased(MouseEvent e){
        if(e.getButton()==BUTTON1){
            if(MouseInput.tengoCarta && MouseInput.dentroDePila){
                MouseInput.colocarCarta=true;
            }
            botonIzquierdo=false;

            //System.out.println("Se ha desclicado el boton izquierdo en la posicion X: "+e.getX()+" ,Y: "+e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        RatonX=e.getX();
        RatonY=e.getY();
        //System.out.println("mouseDraggrd");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        RatonX=e.getX();
        RatonY=e.getY();
        //System.out.println("mouseMoved");
    }
}
