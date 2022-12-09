package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;

import java.awt.*;

public class JugadorBot implements Runnable{
    private CardBot carta1;
    private CardBot carta2;
    private CardBot carta3;
    private CardBot carta4;
    private Vector2D posicionCarta1;
    private Vector2D posicionCarta2;
    private Vector2D posicionCarta3;
    private Vector2D posicionCarta4;
    private Vector2D posicionMazoDeRobo;
    private MazoDeRoboBot mazo;
    private boolean puedoRobar=false;
    private boolean puedoJugar=true;
    private boolean funcionando;

    public JugadorBot(CartaSimple[] csv){
        //Posicion de carta 1
        this.posicionCarta1=new Vector2D(Card.getAnchuraCarta()*(7.75),Card.getAlturaCarta()*(0.15));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(Card.getAnchuraCarta()*(6.25),Card.getAlturaCarta()*(0.15));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(Card.getAnchuraCarta()*(4.75),Card.getAlturaCarta()*(0.15));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(Card.getAnchuraCarta()*(3.25),Card.getAlturaCarta()*(0.15));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(Card.getAnchuraCarta()*(1.75),Card.getAlturaCarta()*(0.15));


        this.mazo=new MazoDeRoboBot(this.posicionMazoDeRobo,csv,this);
        this.puedoRobar=false;

        this.carta1=new CardBot(this.mazo.robarCata(),this.posicionCarta1);
        this.carta2=new CardBot(this.mazo.robarCata(),this.posicionCarta2);
        this.carta3=new CardBot(this.mazo.robarCata(),this.posicionCarta3);
        this.carta4=new CardBot(this.mazo.robarCata(),this.posicionCarta4);
        JugadorHumano.getMazoDeApilar2().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
    }
    public CardBot primeraCartaYaJugada(){
        if(carta1.getYaJugada()){
            carta1.setYaJugada(false);
            return carta1;
        }
        if(carta2.getYaJugada()){
            carta2.setYaJugada(false);
            return carta2;
        }
        if(carta3.getYaJugada()){
            carta3.setYaJugada(false);
            return carta3;
        }
        if(carta4.getYaJugada()){
            carta4.setYaJugada(false);
            return carta4;
        }
        return null;
    }
    public boolean getPuedoJugar(){
        return this.puedoJugar;
    }

    public void actualizar(){
        if(!EstadoJuego.bloqueado){
            if(EstadoJuego.getMazoDeApilar1().getUltimaCarta().getValue()==EstadoJuego.getMazoDeApilar2().getUltimaCarta().getValue()){
                //Hacer lo de Churrusqui
                System.out.println("Churrusqui");
            }
            while(this.puedoRobar && !this.mazo.estaVacio()){
                if(this.carta1.getYaJugada() || this.carta2.getYaJugada() || this.carta3.getYaJugada() || this.carta4.getYaJugada()){
                    this.primeraCartaYaJugada().actualizarEstadoDeCarta(this.mazo.robarCata());
                    this.wait(1);
                }else{
                    this.puedoRobar=false;
                }
            }
            if(EstadoJuego.getMazoDeApilar1().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada()){
                if(JugadorHumano.getMazoDeApilar1().aniadirNuevaCarta(this.carta1.CartaACartaSimple())){
                    this.carta1.setYaJugada(true);
                    this.puedoRobar=true;
                }
            } else if(EstadoJuego.getMazoDeApilar2().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada()) {
                if(JugadorHumano.getMazoDeApilar2().aniadirNuevaCarta(this.carta1.CartaACartaSimple())){
                    this.carta1.setYaJugada(true);
                    this.puedoRobar=true;
                }
            }else if(EstadoJuego.getMazoDeApilar1().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada()){
                if(JugadorHumano.getMazoDeApilar1().aniadirNuevaCarta(this.carta2.CartaACartaSimple())){
                    this.carta2.setYaJugada(true);
                    this.puedoRobar=true;
                }
            } else if(EstadoJuego.getMazoDeApilar2().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada()) {
                if(JugadorHumano.getMazoDeApilar2().aniadirNuevaCarta(this.carta2.CartaACartaSimple())){
                    this.carta2.setYaJugada(true);
                    this.puedoRobar=true;
                }
            }else if(EstadoJuego.getMazoDeApilar1().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada()){
                if(JugadorHumano.getMazoDeApilar1().aniadirNuevaCarta(this.carta3.CartaACartaSimple())){
                    this.carta3.setYaJugada(true);
                    this.puedoRobar=true;
                }
            } else if(EstadoJuego.getMazoDeApilar2().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada()) {
                if(JugadorHumano.getMazoDeApilar2().aniadirNuevaCarta(this.carta3.CartaACartaSimple())){
                    this.carta3.setYaJugada(true);
                    this.puedoRobar=true;
                }
            }else if(EstadoJuego.getMazoDeApilar1().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada()){
                if(JugadorHumano.getMazoDeApilar1().aniadirNuevaCarta(this.carta4.CartaACartaSimple())){
                    this.carta4.setYaJugada(true);
                    this.puedoRobar=true;
                }
            } else if(EstadoJuego.getMazoDeApilar2().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada()) {
                if(JugadorHumano.getMazoDeApilar2().aniadirNuevaCarta(this.carta4.CartaACartaSimple())){
                    this.carta4.setYaJugada(true);
                    this.puedoRobar=true;
                }
            }

            this.carta1.actualizar();
            this.carta2.actualizar();
            this.carta3.actualizar();
            this.carta4.actualizar();
            this.mazo.actualizar();
        }

    }
    public boolean puedoJugar(){
        if(!(EstadoJuego.getMazoDeApilar1().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar2().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar1().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar2().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar1().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar2().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar1().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && !(EstadoJuego.getMazoDeApilar2().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && (!this.puedoRobar || (this.puedoRobar && this.mazo.estaVacio())))
        {
            return false;
        }else{
            return true;
        }
    }
    public boolean prueba(){
        return (
                !(this.puedoRobar || (!this.puedoRobar && this.mazo.estaVacio()))
                );
    }

    public void dibujar(Graphics g){
        this.carta1.dibujar(g);
        this.carta2.dibujar(g);
        this.carta3.dibujar(g);
        this.carta4.dibujar(g);
        this.mazo.dibujar(g);
    }
    public CartaSimple solucionarBloqueo(){
        if(!this.mazo.estaVacio()){
            return this.mazo.robarCata();
        }else{
            if(!this.carta4.getYaJugada()){
                this.carta4.setYaJugada(true);
                return this.carta4.CartaACartaSimple();
            }else if(!this.carta3.getYaJugada()){
                this.carta3.setYaJugada(true);
                return this.carta3.CartaACartaSimple();
            }else if(!this.carta2.getYaJugada()){
                this.carta2.setYaJugada(true);
                return this.carta2.CartaACartaSimple();
            }else if(!this.carta1.getYaJugada()){
                this.carta1.setYaJugada(true);
                return this.carta1.CartaACartaSimple();
            }else{
                return new CartaSimple();
            }
        }
    }

    @Override
    public void run() {
        funcionando=true;
        this.wait(2);
        while(funcionando){
            //Intentar arreglar esta chapuza
            this.wait(2);
            this.actualizar();
        }
    }

    public void wait(int s){
        //PRECONDITION:
        //POSTCONDITION: the execution will wait for 's' secs
        try{
            Thread.sleep(s*1000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }
}
