package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import graficos.Loader;
import input.MouseInput;
import principal.Window;


import java.awt.*;

public class CardHumano extends Card{
    private boolean mouseIn;//Para saber si el mouse se encuentra dentro de la hitbox de la carta
    private Rectangle hitBox;
    private boolean primeraVez=false;
    private int diferenciaX;
    private int diferenciaY;
    private boolean tocada=false;
    private boolean meTienen=false;




    public CardHumano(){
        //PRECONDITION:
        //POSTCONDITION: the card is initialized
        super(Loader.cargadorDeImagenes(Card.buscarRutaTextura(0,0),Card.getAnchuraCarta(),Card.getAlturaCarta()),new Vector2D(),new CartaSimple());
        this.hitBox=new Rectangle(0, 0,Card.getAnchuraCarta(),Card.getAlturaCarta());
    }
    public CardHumano(int s, int v, Vector2D v2d){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: creates the card with its own value and suit
        super(Loader.cargadorDeImagenes(Card.buscarRutaTextura(s,v),Card.getAnchuraCarta(),Card.getAlturaCarta()),v2d,new CartaSimple());
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),Card.getAnchuraCarta(),Card.getAlturaCarta());
        //System.out.println("Carta "+v+" de "+s+" el rectangulo empieza en X:"+(int) v2d.getX()+" ,Y: "+(int) v2d.getY()+", y la imagen mide "+anchuraCarta+" x "+alturaCarta);
    }
    public CardHumano(CartaSimple cs, Vector2D v2d){
        super(Loader.cargadorDeImagenes(Card.buscarRutaTextura(cs.getSuit(),cs.getValue()),Card.getAnchuraCarta(),Card.getAlturaCarta()),v2d,cs);
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),Card.getAnchuraCarta(),Card.getAlturaCarta());
    }
    public void actualizarEstadoDeCarta(CartaSimple cs){
        super.textura=Loader.cargadorDeImagenes(Card.buscarRutaTextura(cs.getSuit(),cs.getValue()),Card.getAnchuraCarta(),Card.getAlturaCarta());
        super.posicion.setX(this.getPosicionInicial().getX());
        super.posicion.setY(this.getPosicionInicial().getY());
        this.setSuit(cs.getSuit());
        this.setValue(cs.getValue());
        this.hitBox.x=(int)this.getPosicionInicial().getX();
        this.hitBox.y=(int)this.getPosicionInicial().getY();

    }
    public boolean getMeTienen(){
        return this.meTienen;
    }
    public void setMeTienen(boolean b){
        this.meTienen=b;
    }


    @Override
    public void actualizar() {
        if(this.hitBox.contains(MouseInput.RatonX,MouseInput.RatonY) && MouseInput.botonIzquierdo ){
            if( !MouseInput.tengoCarta && MouseInput.botonIzquierdo){
                this.primeraVez=true;
                MouseInput.tengoCarta=true;
                JugadorHumano.setCartaJugada(this.getSuit(),this.getValue());
            }
            if( MouseInput.tengoCarta && this.primeraVez ){
                this.meTienen=true;
                this.primeraVez=false;
                this.tocada=true;
                this.diferenciaX=MouseInput.RatonX-(int) this.posicion.getX();
                this.diferenciaY=MouseInput.RatonY-(int) this.posicion.getY();
            }
            if(this.meTienen && MouseInput.botonIzquierdo){
                MouseInput.tengoCarta=true;
                super.posicion.setX(MouseInput.RatonX-this.diferenciaX);
                super.posicion.setY(MouseInput.RatonY-this.diferenciaY);
                this.hitBox.x=MouseInput.RatonX-this.diferenciaX;
                this.hitBox.y=MouseInput.RatonY-this.diferenciaY;
            }

        }else{
            this.meTienen=false;
        }

        if(!MouseInput.botonIzquierdo && !this.meTienen && this.hitBox.contains(MouseInput.RatonX,MouseInput.RatonY)){
            if(JugadorHumano.getMazoDeApilar1().getPosibleJugada()){
                if(JugadorHumano.getMazoDeApilar1().esJugable(this.CartaACartaSimple())){
                    if(JugadorHumano.getMazoDeApilar1().aniadirNuevaCarta(this.CartaACartaSimple())){
                        this.setYaJugada(true);
                        this.posicion.setX(-1000);
                        this.posicion.setY(-1000);
                    }
                }
            }else if(JugadorHumano.getMazoDeApilar2().getPosibleJugada()){
                if(JugadorHumano.getMazoDeApilar2().esJugable(this.CartaACartaSimple())){
                    if(JugadorHumano.getMazoDeApilar2().aniadirNuevaCarta(this.CartaACartaSimple())){
                        this.setYaJugada(true);
                        this.posicion.setX(-1000);
                        this.posicion.setY(-1000);
                    }
                }
            }else{
                this.setYaJugada(false);
            }
        }
        if(!this.meTienen && !this.getYaJugada() && this.tocada ){
            this.posicion.setX(this.getPosicionInicial().getX());
            this.posicion.setY(this.getPosicionInicial().getY());
            this.hitBox.x=(int)this.getPosicionInicial().getX();
            this.hitBox.y=(int)this.getPosicionInicial().getY();
        }
        if(!this.meTienen && this.getYaJugada()){
            this.hitBox.x=-1000;
            this.hitBox.y=-1000;
        }



    }

    @Override
    public void dibujar(Graphics g) {
        if(!this.getYaJugada() ){
            if(!this.meTienen && this.tocada && EstadoJuego.getActualizar()){
                g.drawImage(super.textura,(int)this.getPosicionInicial().getX(),(int)this.getPosicionInicial().getY(),null);
            }else{
                g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
            }
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }

    }
}
