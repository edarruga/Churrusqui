package Componentes;

import Estados.Estado;
import Estados.EstadoJuego;
import Estados.EstadoMenu;
import Red.Buscador;

public class Accion3 {
    protected Buscador buscador;

    public Accion3(Buscador b){
        this.buscador=b;
    }

    public void hacerAccion(){
        this.buscador.finalizarBusqueda();
        Estado.cambiarEstado(new EstadoMenu());
    }
}
