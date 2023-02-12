package ObjetosJuego;

import Estados.EstadoJuego;
import Red.RealizarPeticionPartida;
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
                    if(this.estadoJuego.getJugadorBot().getActivado()){
                        EstadoJuego.wait(4);
                        MouseInput.botonIzquierdo=false;
                        this.estadoJuego.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorHumano().solucionarBloqueo());
                        this.estadoJuego.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorBot().solucionarBloqueo());
                        this.estadoJuego.bloqueado=false;
                    }else{//Caso de juego online
                        if(this.estadoJuego.prioridad){
                            EstadoJuego.wait(4);
                            MouseInput.botonIzquierdo=false;
                            this.estadoJuego.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorHumano().solucionarBloqueo());
                            this.estadoJuego.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.estadoJuego.getJugadorBot().solucionarBloqueo());
                            this.estadoJuego.bloqueado=false;
                            RealizarPeticionPartida info=new RealizarPeticionPartida(this.estadoJuego,this.estadoJuego.getComunicador().getRival(),4);
                            info.start();

                        }
                    }

                }
                if(this.estadoJuego.getChurrusqui()){
                    //System.out.println("Se produce churrusqui");
                    if(this.estadoJuego.getJugadorBot().getActivado()){
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

                    }else{//Caso de juego online
                        if(this.estadoJuego.hiceCurrusquiOnline){
                            //System.out.println("Hice el churrusqui");
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

                            RealizarPeticionPartida info=new RealizarPeticionPartida(this.estadoJuego,this.estadoJuego.getComunicador().getRival(),5);
                            info.start();
                        }else{
                            //System.out.println("Me hicieron el churrusqui");
                            EstadoJuego.wait(4);
                        }
                    }
                }
            }

        }
    }
}
