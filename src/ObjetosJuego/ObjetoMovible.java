package ObjetosJuego;

import Matematica.Vector2D;

import java.awt.image.BufferedImage;

public abstract class ObjetoMovible extends ObjetoJuego{
    protected Vector2D velocidad;

    public ObjetoMovible(BufferedImage t, Vector2D p,Vector2D velocidad) {
        super(t, p);
        this.velocidad=velocidad;
    }
}
