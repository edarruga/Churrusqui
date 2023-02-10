package Estados;

import Matematica.Vector2D;
import ObjetosJuego.*;
import Red.Comunicador;
import Red.MiObjectOutputStream;
import graficos.Assets;
import graficos.Loader;
import principal.Window;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EstadoJuegoOnline extends EstadoJuego{

    private boolean yoSimpleModificado;
    private JugadorSimple yoSimple;

    private boolean rivalSimpleModificado;
    private JugadorSimple rivalSimple;
    private Comunicador comunicador;



    public EstadoJuegoOnline(Socket cliente, Socket servidor){
        this.comunicador=new Comunicador(cliente,servidor,this);
        System.out.println("Empiza el estado online");
        this.comunicador=comunicador;
        System.out.println("Comunicador asignado");
        if(this.comunicador.decidirInicio()){
            System.out.println("Lo mando yo");
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
            this.mazodeApilar1Simple=new Mazo();
            this.mazodeApilar1Simple.insertarCartaSimple(mazo.giveCard());
            for(int i=0;i<19;i++){
                cs2[i]=mazo.giveCard();
                //cs2[i].showCard();
            }
            this.mazodeApilar2Simple=new Mazo();
            this.mazodeApilar2Simple.insertarCartaSimple(mazo.giveCard());

            this.yoSimple=new JugadorSimple(cs1);
            this.rivalSimple=new JugadorSimple(cs2);
            /*
            try(Socket socket=new Socket(this.comunicador.cliente.getInetAddress(),9998);
            MiObjectOutputStream oos=new MiObjectOutputStream(socket.getOutputStream())){
                int num=777;
                oos.writeObject(num);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            */
            //this.comunicador.enviarPrueba(777);
            this.comunicador.enviarJugador(this.rivalSimple);
            this.comunicador.enviarJugador(this.yoSimple);
            this.comunicador.enviarMazo(this.mazodeApilar1Simple);
            this.comunicador.enviarMazo(this.mazodeApilar2Simple);
        }else{
            System.out.println("Me lo mandan");
            /*
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try (ServerSocket serverSocket=new ServerSocket(9998);
                 Socket socket=serverSocket.accept();
                 ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
                Object o=ois.readObject();
                int num=(int)o;

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }*/

            //System.out.println(this.comunicador.recivirPrueba());
            this.yoSimple=this.comunicador.recivirJugador();
            this.rivalSimple=this.comunicador.recivirJugador();
            this.mazodeApilar1Simple=this.comunicador.recivirMazo();
            this.mazodeApilar2Simple=this.comunicador.recivirMazo();

            this.prioridad=false;
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

    public Comunicador getComunicador() {
        return this.comunicador;
    }

    public void setComunicador(Comunicador comunicador) {
        this.comunicador = comunicador;
    }


    public boolean isYoSimpleModificado() {
        return yoSimpleModificado;
    }

    public void setYoSimpleModificado(boolean yoSimpleModificado) {
        this.yoSimpleModificado = yoSimpleModificado;
    }

    public JugadorSimple getYoSimple() {
        return yoSimple;
    }

    public void setYoSimple(JugadorSimple yoSimple) {
        this.yoSimple = yoSimple;
    }

    public boolean isRivalSimpleModificado() {
        return rivalSimpleModificado;
    }

    public void setRivalSimpleModificado(boolean rivalSimpleModificado) {
        this.rivalSimpleModificado = rivalSimpleModificado;
    }

    public JugadorSimple getRivalSimple() {
        return rivalSimple;
    }

    public void setRivalSimple(JugadorSimple rivalSimple) {
        this.rivalSimple = rivalSimple;
    }


    public synchronized boolean solicitarChurrusqui(){

        if(!this.jugadorBot.getChurrusqui() && !this.jugadorHumano.getChurrusqui()){
            try(Socket socket=new Socket(this.comunicador.getRival(),9990);
                PrintStream ps=new PrintStream(socket.getOutputStream())){

                ps.println("Churrusqui");
                return true;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            return false;
        }
    }
    @Override
    public void actualizar() {
        if(this.isYoSimpleModificado()){
            this.jugadorHumano.modificarEstado(this.getYoSimple());
            this.setYoSimpleModificado(false);
        }
        if(this.isRivalSimpleModificado()){
            this.jugadorBot.modificarEstado(this.getRivalSimple());
            this.setRivalSimpleModificado(false);
        }
        if(this.isMazodeApilar1SimpleModificado()){
            this.mazoDeApilar1.modificarEstado(this.getMazodeApilar1Simple());
            this.setMazodeApilar1SimpleModificado(false);
        }
        if(this.isMazodeApilar2SimpleModificado()){
            this.mazoDeApilar2.modificarEstado(this.getMazodeApilar2Simple());
            this.setMazodeApilar2SimpleModificado(false);
        }
        if(this.jugadorHumano.getTerminado() || this.jugadorBot.getTerminado()){
            this.finDeJuego=true;
            EstadoJuego.wait(1);
            hilo1.interrupt();
            hilo2.interrupt();
            hiloBloqueo.interrupt();
            Estado.cambiarEstado(new EstadoFinDePartida(this.jugadorHumano.getTerminado()));//Termina la partida

        }else{
            this.actualiza=true;
            this.mazoDeApilar1.actualizar();
            this.mazoDeApilar2.actualizar();
            if(this.jugadorHumano.getChurrusqui() || this.jugadorBot.getChurrusqui()){
                this.churrusqui=true;
            }
            this.actualiza=false;
            //System.out.println("Bot: "+this.jugadorBot.puedoJugar()+" ,Humano:"+this.jugadorHumano.puedoJugar());
            //System.out.println(EstadoJuego.bloqueado);
            if(!this.churrusqui){
                if(!jugadorBot.puedoJugar() && !jugadorHumano.puedoJugar() && !this.bloqueado){
                    //System.out.println(this.jugadorBot.prueba());
                    //this.dibujar(g);
                    this.bloqueado=true;
                    //System.out.println("Ce bloqueo");

                }
            }


        }
    }

    @Override
    public void dibujar(Graphics g) {
        if(this.bloqueado && !this.getChurrusqui()){
            g.drawImage(Assets.Bloqueo,((principal.Window.getAnchuraVentana()/2)-(CardHumano.getAnchuraCarta()/2)),((Window.getAlturaVentana()/2)-(CardHumano.getAnchuraCarta()/2)),null);
        }
        this.mazoDeApilar1.dibujar(g);
        this.mazoDeApilar2.dibujar(g);
        if(this.getChurrusqui()){
            g.drawImage(Assets.Churrusqui,0,0,null);
        }
        this.jugadorBot.dibujar(g);
        this.jugadorHumano.dibujar(g);
    }
}
