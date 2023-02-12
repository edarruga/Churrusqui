package ObjetosJuego;

import Componentes.Accion;
import Componentes.Accion2;
import Componentes.Boton;
import Estados.EstadoJuego;
import Estados.EstadoJuegoOnline;
import Matematica.Vector2D;
import graficos.Assets;
import input.MouseInput;
import principal.Window;

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
    private EstadoJuego estadoJuego;
    protected boolean puedoRobar=false;

    protected boolean funcionando;
    private static CartaSimple cartaJugada=new CartaSimple();
    private boolean terminado=false;
    private static boolean churrusqui=false;
    private Boton boton;

    public JugadorHumano(JugadorSimple j, EstadoJuego es){
        this.posicionCarta1=new Vector2D(CardHumano.getAnchuraCarta()*(3.25), CardHumano.getAlturaCarta()*(2.65));

        //Posicion de carta 2
        this.posicionCarta2=new Vector2D(CardHumano.getAnchuraCarta()*(4.75), CardHumano.getAlturaCarta()*(2.65));

        //Posicion de carta 3
        this.posicionCarta3=new Vector2D(CardHumano.getAnchuraCarta()*(6.25), CardHumano.getAlturaCarta()*(2.65));

        //Posicion de carta 4
        this.posicionCarta4=new Vector2D(CardHumano.getAnchuraCarta()*(7.75), CardHumano.getAlturaCarta()*(2.65));

        //Posicion del mazo para robar
        this.posicionMazoDeRobo=new Vector2D(CardHumano.getAnchuraCarta()*(9.25), CardHumano.getAlturaCarta()*(2.65));

        this.mazo=new MazoDeRoboHumano(this.posicionMazoDeRobo,j.getMazo(),this);
        this.puedoRobar=false;

        this.carta1=new CardHumano(j.getCarta1(),this.posicionCarta1,this);
        this.carta2=new CardHumano(j.getCarta2(),this.posicionCarta2,this);
        this.carta3=new CardHumano(j.getCarta3(),this.posicionCarta3,this);
        this.carta4=new CardHumano(j.getCarta4(),this.posicionCarta4,this);

        this.estadoJuego=es;

        //this.estadoJuego.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
        this.boton=new Boton(Assets.BotonBlancoOut,
                Assets.BotonBlancoIn,
                (int)(CardHumano.getAnchuraCarta()*(0.75)),
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() * (6/2) + Assets.BotonBlancoIn.getHeight(),
                "CHURRUSQUI",
                new Accion2(this.estadoJuego)
        );
    }
    public JugadorHumano(CartaSimple[] csv,EstadoJuego es){
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

        this.carta1=new CardHumano(this.mazo.robarCata(),this.posicionCarta1,this);
        this.carta2=new CardHumano(this.mazo.robarCata(),this.posicionCarta2,this);
        this.carta3=new CardHumano(this.mazo.robarCata(),this.posicionCarta3,this);
        this.carta4=new CardHumano(this.mazo.robarCata(),this.posicionCarta4,this);

        this.estadoJuego=es;

        this.estadoJuego.getMazoDeApilar1().aniadirNuevaCartaAlaFuerza(this.mazo.robarCata());
        this.boton=new Boton(Assets.BotonBlancoOut,
                Assets.BotonBlancoIn,
                (int)(CardHumano.getAnchuraCarta()*(0.75)),
                Window.getAlturaVentana() / 2 + Assets.BotonBlancoIn.getHeight() * (6/2) + Assets.BotonBlancoIn.getHeight(),
                "CHURRUSQUI",
                new Accion2(this.estadoJuego)
        );
    }

    public void modificarEstado(JugadorSimple j){
        this.carta1.actualizarEstadoDeCarta(j.getCarta1());
        this.carta2.actualizarEstadoDeCarta(j.getCarta2());
        this.carta3.actualizarEstadoDeCarta(j.getCarta3());
        this.carta4.actualizarEstadoDeCarta(j.getCarta4());
        this.mazo.modificarEstado(j.getMazo());
    }

    public JugadorSimple pasarAJugadorSimple(){
        Mazo m=this.mazo.mazo;
        CartaSimple c1=this.carta1.CartaACartaSimpleParaEnvio();
        CartaSimple c2=this.carta2.CartaACartaSimpleParaEnvio();
        CartaSimple c3=this.carta3.CartaACartaSimpleParaEnvio();
        CartaSimple c4=this.carta4.CartaACartaSimpleParaEnvio();
        return new JugadorSimple(m,c1,c2,c3,c4);
    }
    public static CartaSimple getCartaJugada(){
        return JugadorHumano.cartaJugada;
    }
    public static void setCartaJugada(int s, int v){
        JugadorHumano.cartaJugada.setSuit(s);
        JugadorHumano.cartaJugada.setValue(v);
    }

    public synchronized void solicitarCurrusqui(){
        if(!this.estadoJuego.churrusqui){
            if(this.estadoJuego.solicitarChurrusqui()){
                if(!this.getEstadoJuego().getJugadorBot().getActivado()){
                    this.estadoJuego.hiceCurrusquiOnline=true;
                }
                JugadorHumano.churrusqui=true;
            }
        }
    }
    public boolean getChurrusqui(){
        return JugadorHumano.churrusqui;
    }
    public boolean puedoJugar(){
        if(!(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar1().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
        && !(this.estadoJuego.getMazoDeApilar2().esJugable(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
        && (!this.puedoRobar || (this.puedoRobar && this.mazo.estaVacio())))
        {
            return false;
        }else{
            return true;
        }
    }
    public boolean puedoJugarLocal(){
        if(!(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta1.CartaACartaSimple()) && !this.carta1.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta2.CartaACartaSimple()) && !this.carta2.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta3.CartaACartaSimple()) && !this.carta3.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar1().esJugableLocal(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
                && !(this.estadoJuego.getMazoDeApilar2().esJugableLocal(this.carta4.CartaACartaSimple()) && !this.carta4.getYaJugada())
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
                this.carta4.setMeTienen(false);
                this.carta4.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta4.CartaACartaSimple().getSuit(),this.carta4.CartaACartaSimple().getValue());
                this.carta4.setSuit(0);
                this.carta4.setValue(0);
                return cs;
            }else if(!this.carta3.getYaJugada()){
                this.carta3.setMeTienen(false);
                this.carta3.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta3.CartaACartaSimple().getSuit(),this.carta3.CartaACartaSimple().getValue());
                this.carta3.setSuit(0);
                this.carta3.setValue(0);
                return cs;
            }else if(!this.carta2.getYaJugada()){
                this.carta3.setMeTienen(false);
                this.carta2.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta2.CartaACartaSimple().getSuit(),this.carta2.CartaACartaSimple().getValue());
                this.carta2.setSuit(0);
                this.carta2.setValue(0);
                return cs;
            }else if(!this.carta1.getYaJugada()){
                this.carta1.setMeTienen(false);
                this.carta1.setYaJugada(true);
                CartaSimple cs=new CartaSimple(this.carta1.CartaACartaSimple().getSuit(),this.carta1.CartaACartaSimple().getValue());
                this.carta1.setSuit(0);
                this.carta1.setValue(0);
                return cs;
            }else{
                return new CartaSimple();
            }
        }
    }
    public MazoDeRobo getMazo(){
        return this.mazo;
    }
    public void setChurrusqui(boolean b){
        this.churrusqui=b;
    }
    public MazoDeApilar1 getMazoDeApilar1(){
        return this.estadoJuego.getMazoDeApilar1();
    }
    public MazoDeApilar2 getMazoDeApilar2(){
        return this.estadoJuego.getMazoDeApilar2();
    }
    public EstadoJuego getEstadoJuego(){
        return this.estadoJuego;
    }

    public void actualizar(){
        if(this.estadoJuego.finDeJuego){
            EstadoJuego.wait(5);
        }
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
        this.boton.actualizarV2();
        if(this.carta1.getYaJugada() && this.carta2.getYaJugada() && this.carta3.getYaJugada() && this.carta4.getYaJugada() && this.mazo.estaVacio()){
            this.terminado=true;
        }
    }
    public boolean getTerminado(){
        return this.terminado;
    }


    public void dibujar(Graphics g){
        this.mazo.dibujar(g);
        this.boton.dibujar(g);
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
