package Estados;

import Matematica.Vector2D;
import ObjetosJuego.Card;

import java.awt.*;

public class EstadoJuego {
    private Card carta;
    private Card carta2;
    public EstadoJuego(){
        this.carta=new Card(4,7,new Vector2D(40,50));
        this.carta2=new Card(0,1,new Vector2D(120,50));
    }

    public void actualizar(){

    }

    public void dibujar(Graphics g){
        carta.dibujar(g);
        carta2.dibujar(g);
    }

}
