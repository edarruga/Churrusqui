package Estados;

import java.awt.*;

public abstract class Estado {
    private static Estado estadoActual=null;
    public static Estado getEstadoActual(){
        return Estado.estadoActual;
    }
    public static void cambiarEstado(Estado estado){
        Estado.estadoActual=estado;
    }
    public abstract void actualizar();
    public abstract void dibujar(Graphics g);
}
