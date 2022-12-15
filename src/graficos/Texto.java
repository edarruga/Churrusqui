package graficos;

import Matematica.Vector2D;

import java.awt.*;

public class Texto {
    public static void dibujarTexto(Graphics g, String texto, Vector2D posicion,Color color,Font fuente){
        g.setColor(color);
        g.setFont(fuente);
        Vector2D pos=new Vector2D(posicion.getX(), posicion.getY());
        g.drawString(texto,(int)pos.getX(),(int)pos.getY());
    }
}
