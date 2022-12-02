package ObjetosJuego;

import Matematica.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ObjetoJuego {
    protected BufferedImage textura;
    protected Vector2D posicion;

    public ObjetoJuego(BufferedImage t, Vector2D p){
        this.textura=t;
        this.posicion=p;
    }

    public BufferedImage getTextura() {
        return textura;
    }

    public Vector2D getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2D posicion) {
        this.posicion = posicion;
    }

    public void setTextura(BufferedImage textura) {
        this.textura = textura;
    }

    public void escalarTextura(int anchura,int altura){
        Image imagenEscalada=this.textura.getScaledInstance(anchura,altura,Image.SCALE_SMOOTH);
        this.textura= this.convertirABufferedImage(imagenEscalada);
    }

    private BufferedImage convertirABufferedImage(Image imagen){
        if(imagen instanceof BufferedImage){
            return (BufferedImage) imagen;
        }
        BufferedImage bi=new BufferedImage(imagen.getWidth(null),imagen.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics g=bi.getGraphics();
        g.drawImage(imagen,0,0,null);
        g.dispose();
        return bi;
    }


    public abstract void actualizar();
    public abstract void dibujar(Graphics g);
}
