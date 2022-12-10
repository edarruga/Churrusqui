package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import input.MouseInput;

import java.awt.*;

public class JugadorHumano implements Runnable{
    private CardHumano carta1;
    private CardHumano carta2;
    private CardHumano carta3;
    private CardHumano carta4;
    protected Vector2D posicionCarta1;
    protected Vector2D posicionCarta2;
    protected Vector2D posicionCarta3;
    protected Vector2D posicionCarta4;
    protected Vector2D posicionMazoDeRobo;
    protected MazoDeRobo mazo;
    protected boolean puedoRobar=false;

    protected boolean funcionando;
    private static CartaSimple cartaJugada=new CartaSimple();

    public JugadorHumano(CartaSimple[] csv){
        //Posicion de carta 1
        this.posicionCarta1=new Vector2D(CardHumano.getAnchuraCarta()*(3.25), CardHumano.getAlturaCarta()*(2.65));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(CardHumano.getAnchuraCarta()*(4.75), CardHumano.getAlturaCarta()*(2.65));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(CardHumano.getAnchuraCarta()*(6.25), CardHumano.getAlturaCarta()*(2.65));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(CardHumano.getAnchuraCarta()*(7.75), CardHumano.getAlturaCarta()*(2.65));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(CardHumano.getAnchuraCarta()*(9.25), CardHumano.getAlturaCarta()*(2.65));


        this.mazo=new MazoDeRoboHumano(this.posicionMazoDeRobo,csv,this);
        this.puedoRobar=false;

        this.carta1=new CardHumano(this.mazo.robarCata(),this.posicionCarta1);
        this.carta2=new CardHumano(this.mazo.robarCata(),this.posicionCarta2);
        this.carta3=new CardHumano(this.mazo.robarCata(),this.posicionCarta3);
        this.carta4=new CardHumano(this.mazo.robarCata(),this.posicionCarta4);
        JugadorHumano.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
    }
    public static CartaSimple getCartaJugada(){
        return JugadorHumano.cartaJugada;
    }
    public static void setCartaJugada(int s, int v){
        JugadorHumano.cartaJugada.setSuit(s);
        JugadorHumano.cartaJugada.setValue(v);
    }

    public static MazoDeApilar getMazoDeApilar1() {
        return EstadoJuego.getMazoDeApilar1();
    }
    public static MazoDeApilar getMazoDeApilar2(){
        return EstadoJuego.getMazoDeApilar2();
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
    public CardHumano primeraCartaYaJugada(){
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
    public CartaSimple solucionarBloqueo(){
        if(!this.mazo.estaVacio()){
            return this.mazo.robarCata();
        }else{
            if(!this.carta4.getYaJugada()){
                this.carta4.setYaJugada(true);
                this.carta4.setMeTienen(false);
                return this.carta4.CartaACartaSimple();
            }else if(!this.carta3.getYaJugada()){
                this.carta3.setYaJugada(true);
                this.carta3.setMeTienen(false);
                return this.carta3.CartaACartaSimple();
            }else if(!this.carta2.getYaJugada()){
                this.carta2.setYaJugada(true);
                this.carta2.setMeTienen(false);
                return this.carta2.CartaACartaSimple();
            }else if(!this.carta1.getYaJugada()){
                this.carta1.setMeTienen(false);
                this.carta1.setYaJugada(true);
                return this.carta1.CartaACartaSimple();
            }else{
                return new CartaSimple();
            }
        }
    }

    public void actualizar(){
        if(!MouseInput.tengoCarta){
            JugadorHumano.cartaJugada.setSuit(0);
            JugadorHumano.cartaJugada.setValue(0);
        }
        if(this.carta1.getYaJugada() || this.carta2.getYaJugada() || this.carta3.getYaJugada() || this.carta4.getYaJugada()){
            this.puedoRobar=true;
        }else{
            this.puedoRobar=false;
        }
        this.carta1.actualizar();
        this.carta2.actualizar();
        this.carta3.actualizar();
        this.carta4.actualizar();
        this.mazo.actualizar();
    }


    public void dibujar(Graphics g){
        this.mazo.dibujar(g);
        if(this.carta1.getMeTienen()){
            this.carta2.dibujar(g);
            this.carta3.dibujar(g);
            this.carta4.dibujar(g);
            this.carta1.dibujar(g);
        }else if(this.carta2.getMeTienen()){
            this.carta1.dibujar(g);
            this.carta3.dibujar(g);
            this.carta4.dibujar(g);
            this.carta2.dibujar(g);
        }else if(this.carta3.getMeTienen()){
            this.carta1.dibujar(g);
            this.carta2.dibujar(g);
            this.carta4.dibujar(g);
            this.carta3.dibujar(g);
        }else if(this.carta4.getMeTienen()){
            this.carta1.dibujar(g);
            this.carta2.dibujar(g);
            this.carta3.dibujar(g);
            this.carta4.dibujar(g);
        }else{
            this.carta1.dibujar(g);
            this.carta2.dibujar(g);
            this.carta3.dibujar(g);
            this.carta4.dibujar(g);
        }

    }

    @Override
    public void run() {
        funcionando=true;
        while(funcionando){
            //Intentar arreglar esta chapuza

            //if(EstadoJuego.getActualizar()){
                this.actualizar();
            //}

        }

    }

}
