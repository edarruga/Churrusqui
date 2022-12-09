package ObjetosJuego;

import Estados.EstadoJuego;
import Matematica.Vector2D;
import graficos.Loader;
import ObjetosJuego.ObjetoJuego;
import input.MouseInput;
import principal.Window;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLOutput;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class Card extends ObjetoJuego{
    private int suit;   //Oro=1,Copa=2,Espada=3,Basto=4

    private int value;  //The value is between 1-10, but our methods show the 8, 9 and 10 as 10, 11 and 12, respectically
    private static int anchuraCarta=Window.getAnchuraVentana()/12;
    private static int alturaCarta=Window.getAlturaVentana()/4;
    private static boolean estigma=false;
    private boolean mouseIn;//Para saber si el mouse se encuentra dentro de la hitbox de la carta
    private Rectangle hitBox;
    private boolean primeraVez=false;
    private int diferenciaX;
    private int diferenciaY;
    private boolean tocada=false;
    private boolean meTienen=false;
    private boolean yaJugada=false;
    private Vector2D posicionInicial;




    public Card(){
        //PRECONDITION:
        //POSTCONDITION: the card is initialized
        super(Loader.cargadorDeImagenes(buscarRutaTextura(0,0),anchuraCarta,alturaCarta),new Vector2D());
        this.hitBox=new Rectangle(0, 0,anchuraCarta,alturaCarta);
        this.suit=0;
        this.value=0;
    }
    public Card(int s,int v,Vector2D v2d){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: creates the card with its own value and suit
        super(Loader.cargadorDeImagenes(buscarRutaTextura(s,v),anchuraCarta,alturaCarta),v2d);
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),anchuraCarta,alturaCarta);
        //System.out.println("Carta "+v+" de "+s+" el rectangulo empieza en X:"+(int) v2d.getX()+" ,Y: "+(int) v2d.getY()+", y la imagen mide "+anchuraCarta+" x "+alturaCarta);
        this.suit=s;
        this.value=v;
        this.posicionInicial=new Vector2D(v2d.getX(),v2d.getY());
    }
    public Card(CartaSimple cs,Vector2D v2d){
        super(Loader.cargadorDeImagenes(buscarRutaTextura(cs.getSuit(),cs.getValue()),anchuraCarta,alturaCarta),v2d);
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),anchuraCarta,alturaCarta);
        this.suit=cs.getSuit();
        this.value=cs.getValue();
        this.posicionInicial=new Vector2D(v2d.getX(),v2d.getY());
    }
    public int getValue(){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: returns the value of the card
        return this.value;
    }
    public int getSuit(){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: returns the suit of the card
        return this.suit;
    }
    public void setValue(int v){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: set the value of the card to 'v'
        this.value=v;
    }
    public void setSuit(int s){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: set the suit of the card to 's'
        this.suit=s;
    }
    public boolean sameCard(Card c){
        //PRECONDITION:
        //POSTCONDITION: returns true if 2 cards have the same value and suit, false otherwise
        if(this.getSuit()==c.getSuit() && this.getValue()==c.getValue()){
            return true;
        }
        return false;
    }
    public boolean sameSuit(Card c){
        //PRECONDITION:
        //POSTCONDITION: returns true if the suit is the same, false otherwise
        return this.getSuit()==c.getSuit();
    }
    public boolean sameValue(Card c){
        //PRECONDITION:
        //POSTCONDITION: returns true if the value is the same, false otherwise
        if(this.getValue()==c.getValue()){
            return true;
        }
        return false;
    }
    public void showCard(){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: shows the card value and suit on screen
        if(this.getValue()!=0){
            int trueValue;
            String trueSuit = "Palo no definido";
            if(this.getValue()<=7){
                trueValue=this.getValue();
            }else{
                trueValue=this.getValue()+2;
            }
            if(this.getSuit()==1){
                trueSuit="Oros";
            }
            if(this.getSuit()==2){
                trueSuit="Copas";
            }
            if(this.getSuit()==3){
                trueSuit="Espadas";
            }
            if(this.getSuit()==4){
                trueSuit="Bastos";
            }
            System.out.println("[" + trueValue + " de "+trueSuit+"]");
        }
    }
    public static int getAnchuraCarta(){
        return Card.anchuraCarta;
    }
    public static int getAlturaCarta(){
        return Card.alturaCarta;
    }
    public boolean getYaJugada(){
        return this.yaJugada;
    }
    public void setYaJugada(boolean b){
        this.yaJugada=b;
    }
    public void actualizarEstadoDeCarta(CartaSimple cs){
        super.textura=Loader.cargadorDeImagenes(buscarRutaTextura(cs.getSuit(),cs.getValue()),anchuraCarta,alturaCarta);
        super.posicion.setX(this.posicionInicial.getX());
        super.posicion.setY(this.posicionInicial.getY());
        this.suit=cs.getSuit();
        this.value=cs.getValue();
        this.hitBox.x=(int)this.posicionInicial.getX();
        this.hitBox.y=(int)this.posicionInicial.getY();

    }


    public void copyCard(Card c){
        //PRECONDITION: the cards must be initialized
        //POSTCONDITION: copies the value and suit of 'c'
        this.setValue(c.getValue());
        this.setSuit(c.getSuit());
        super.textura=Loader.cargadorDeImagenes(buscarRutaTextura(c.getValue(),c.getSuit()),anchuraCarta,alturaCarta);
    }
    public CartaSimple CartaACartaSimple(){
        return new CartaSimple(this.getSuit(),this.getValue());
    }
    public static void activarEstigma(){
        estigma=true;
    }
    public boolean getMeTienen(){
        return this.meTienen;
    }

    public static String buscarRutaTextura(int s,int v){
        if(v==0 || s==0){
            if(estigma==false){
                return "recursos/Cartas/ParteTrasera.png";
            }else{
                return "recursos/Cartas/ParteTraseraEstigma.png";
            }
        }else{
            if(v>=8){
                v=v+2;
            }
            switch (s){
                case 1://Oro
                    return "recursos/Cartas/"+v+"-Oro.png";
                case 2://Copa
                    return "recursos/Cartas/"+v+"-Copa.png";
                case 3://Espada
                    return "recursos/Cartas/"+v+"-Espada.png";
                case 4://Basto
                    return "recursos/Cartas/"+v+"-Basto.png";
            }
            return "recursos/Cartas/Invisible.png";
        }
    }

    @Override
    public void actualizar() {
        if(this.hitBox.contains(MouseInput.RatonX,MouseInput.RatonY) && MouseInput.botonIzquierdo && !EstadoJuego.bloqueado){
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
                        this.yaJugada=true;
                        this.posicion.setX(-1000);
                        this.posicion.setY(-1000);
                    }
                }
            }else if(JugadorHumano.getMazoDeApilar2().getPosibleJugada()){
                if(JugadorHumano.getMazoDeApilar2().esJugable(this.CartaACartaSimple())){
                    if(JugadorHumano.getMazoDeApilar2().aniadirNuevaCarta(this.CartaACartaSimple())){
                        this.yaJugada=true;
                        this.posicion.setX(-1000);
                        this.posicion.setY(-1000);
                    }
                }
            }else{
                this.yaJugada=false;
            }
        }
        if(!this.meTienen && !this.yaJugada && this.tocada ){
            this.posicion.setX(this.posicionInicial.getX());
            this.posicion.setY(this.posicionInicial.getY());
            this.hitBox.x=(int)this.posicionInicial.getX();
            this.hitBox.y=(int)this.posicionInicial.getY();
        }
        if(!this.meTienen && this.yaJugada){
            this.hitBox.x=-1000;
            this.hitBox.y=-1000;
        }



    }

    @Override
    public void dibujar(Graphics g) {
        if(!this.getYaJugada() ){
            if(!this.meTienen && this.tocada && EstadoJuego.getActualizar()){
                g.drawImage(super.textura,(int)this.posicionInicial.getX(),(int)this.posicionInicial.getY(),null);
            }else{
                g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
            }
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }

    }
}
