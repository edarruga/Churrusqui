package ObjetosJuego;

import Matematica.Vector2D;
import principal.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Card extends ObjetoJuego{
    private int suit;   //Oro=1,Copa=2,Espada=3,Basto=4
    private int value;  //The value is between 1-10, but our methods show the 8, 9 and 10 as 10, 11 and 12, respectically
    private Vector2D posicionInicial;
    private boolean yaJugada=false;
    private static boolean estigma=false;
    private static int anchuraCarta=Window.getAnchuraVentana()/12;
    private static int alturaCarta=Window.getAlturaVentana()/4;
    public Card(BufferedImage t, Vector2D v2d,CartaSimple cs) {
        super(t, v2d);
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
    public boolean getYaJugada(){
        return this.yaJugada;
    }
    public void setYaJugada(boolean b){
        this.yaJugada=b;
    }
    public static boolean getEstigma(){
        return Card.estigma;
    }
    public Vector2D getPosicionInicial(){
        return this.posicionInicial;
    }
    public static int getAnchuraCarta(){
        return Card.anchuraCarta;
    }
    public static int getAlturaCarta(){
        return Card.alturaCarta;
    }
    public CartaSimple CartaACartaSimple(){
        return new CartaSimple(this.getSuit(),this.getValue());
    }
    public boolean sameCard(CardHumano c){
        //PRECONDITION:
        //POSTCONDITION: returns true if 2 cards have the same value and suit, false otherwise
        if(this.getSuit()==c.getSuit() && this.getValue()==c.getValue()){
            return true;
        }
        return false;
    }
    public boolean sameSuit(CardHumano c){
        //PRECONDITION:
        //POSTCONDITION: returns true if the suit is the same, false otherwise
        return this.getSuit()==c.getSuit();
    }
    public boolean sameValue(CardHumano c){
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
    public static void activarEstigma(){
        estigma=true;
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
    public abstract void actualizarEstadoDeCarta(CartaSimple cs);
}
