package Estados;

import Matematica.Vector2D;
import ObjetosJuego.*;
import Red.Comunicador;
import graficos.Loader;

import java.awt.*;

public class EstadoJuegoOnline extends EstadoJuego{
    private Comunicador comunicador;
    private JugadorSimple yoSimple;
    private JugadorSimple rivalSimple;
    private Mazo mazodeApilar1Simple;
    private Mazo mazodeApilar2Simple;

    public EstadoJuegoOnline(Comunicador comunicador){
        this.comunicador=comunicador;
        if(this.comunicador.decidirInicio()){
            Mazo mazo=new Mazo();
            mazo.llenarMazo();
            //this.mazo.showmazo();
            mazo.shuffle();
            CartaSimple[] cs1=new CartaSimple[19];
            CartaSimple[] cs2=new CartaSimple[19];
            for(int i=0;i<19;i++){
                cs1[i]=mazo.giveCard();
                //cs1[i].showCard();
            }
            this.mazodeApilar1Simple.insertarCartaSimple(mazo.giveCard());
            for(int i=0;i<19;i++){
                cs2[i]=mazo.giveCard();
                //cs2[i].showCard();
            }
            this.mazodeApilar2Simple.insertarCartaSimple(mazo.giveCard());

            this.yoSimple=new JugadorSimple(cs1);
            this.rivalSimple=new JugadorSimple(cs2);

            this.comunicador.enviarJugador(this.rivalSimple);
            this.comunicador.enviarJugador(this.yoSimple);
            this.comunicador.enviarMazo(this.mazodeApilar1Simple);
            this.comunicador.enviarMazo(this.mazodeApilar2Simple);
        }else{
            this.yoSimple=this.comunicador.recivirJugador();
            this.rivalSimple=this.comunicador.recivirJugador();
            this.mazodeApilar1Simple=this.comunicador.recivirMazo();
            this.mazodeApilar2Simple=this.comunicador.recivirMazo();
        }


        super.finDeJuego=false;
        super.mazoDeApilar1=new MazoDeApilar1(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(4.5), Card.getAlturaCarta()*(1.4)),this,this.mazodeApilar1Simple);
        super.mazoDeApilar2=new MazoDeApilar2(Loader.cargadorDeImagenes("recursos/Cartas/Invisible.png", Card.getAnchuraCarta(), Card.getAlturaCarta()),new Vector2D(Card.getAnchuraCarta()*(6.5), Card.getAlturaCarta()*(1.4)),this,this.mazodeApilar2Simple);
        super.jugadorHumano=new JugadorHumano(this.yoSimple,this);
        super.jugadorBot=new JugadorBot(this.rivalSimple,this);
        super.jugadorBot.desactivar();
        super.bloqueo=new Bloqueo(this);
        hiloBloqueo=new Thread(bloqueo);
        hilo1=new Thread(jugadorHumano);
        hilo2=new Thread(jugadorBot);
        hiloBloqueo.start();
        hilo1.start();
        hilo2.start();

    }
    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {

    }
}
