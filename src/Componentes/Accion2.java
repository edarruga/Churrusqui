package Componentes;

import Estados.EstadoJuego;

public class Accion2 {
    protected EstadoJuego estadoJuego;
    public Accion2(EstadoJuego es){
        this.estadoJuego=es;
    }
    public void hacerAccion(){
        if(!this.estadoJuego.churrusqui){
            if(!this.estadoJuego.bloqueado){
                this.estadoJuego.getJugadorHumano().solicitarCurrusqui();
            }
        }
    }
}
