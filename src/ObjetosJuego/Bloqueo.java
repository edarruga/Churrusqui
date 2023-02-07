package ObjetosJuego;

import Estados.EstadoJuego;
import input.MouseInput;

public class Bloqueo implements Runnable{

    private EstadoJuego estadoJuego;

    public Bloqueo(EstadoJuego es){
        this.estadoJuego=es;
    }
    @Override
    public void run() {
        while(true){
            //System.out.println("Que paso");
            //System.out.println("Desde el run: "+EstadoJuego.bloqueado);
            System.out.print("");
            if(this.estadoJuego.finDeJuego){
                EstadoJuego.wait(5);
            }else{
                if(this.estadoJuego.bloqueado){
                    EstadoJuego.wait(4);
                    MouseInput.botonIzquierdo=false;
                    this.estadoJuego.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorHumano().solucionarBloqueo());
                    this.estadoJuego.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorBot().solucionarBloqueo());
                    this.estadoJuego.bloqueado=false;
                }
                if(this.estadoJuego.getChurrusqui()){
                    EstadoJuego.wait(4);
                    if(this.estadoJuego.getJugadorHumano().getChurrusqui()){
                        if(this.estadoJuego.esCorrectoElChurrusqui()){
                            this.estadoJuego.getJugadorBot().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar1().darCartas());
                            this.estadoJuego.getJugadorBot().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar2().darCartas());
                        }else{
                            this.estadoJuego.getJugadorHumano().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar1().darCartas());
                            this.estadoJuego.getJugadorHumano().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar2().darCartas());
                        }
                    }else{
                        if(this.estadoJuego.esCorrectoElChurrusqui()){
                            this.estadoJuego.getJugadorHumano().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar1().darCartas());
                            this.estadoJuego.getJugadorHumano().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar2().darCartas());
                        }else{
                            this.estadoJuego.getJugadorBot().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar1().darCartas());
                            this.estadoJuego.getJugadorBot().getMazo().aniadirCartas(this.estadoJuego.getMazoDeApilar2().darCartas());
                        }
                    }
                    this.estadoJuego.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorHumano().solucionarBloqueo());
                    this.estadoJuego.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorBot().solucionarBloqueo());
                    this.estadoJuego.getJugadorBot().setChurrusqui(false);
                    this.estadoJuego.getJugadorHumano().setChurrusqui(false);
                    this.estadoJuego.churrusqui=false;

                }
            }

        }
    }
}
