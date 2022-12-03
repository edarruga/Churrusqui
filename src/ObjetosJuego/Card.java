package ObjetosJuego;

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
    private double posicionInicialX;
    private double posicionInicialY;
    private boolean primeraVez=false;
    private int diferenciaX;
    private int diferenciaY;
    private boolean tocada=false;
    private boolean meTienen=false;



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
        System.out.println("Carta "+v+" de "+s+" el rectangulo empieza en X:"+(int) v2d.getX()+" ,Y: "+(int) v2d.getY()+", y la imagen mide "+anchuraCarta+" x "+alturaCarta);
        this.suit=s;
        this.value=v;

        //super.textura.getScaledInstance()
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
    public boolean ImmediatelyNext(Card c){//The card c is the one that is already on the table.
        //PRECONDITION: the cards must be initialized
        //POSTCONDITION: returns true if the card given is higher than
        // the instance card by one unit, false otherwise.
        if(this.sameSuit(c) && this.getValue()==c.getValue()+1){
            return true;
        }
        return false;
    }
    public boolean ImmediatelyPrevious(Card c){//La carta c es la que esta ya en la mesa
        //PRECONDITION: the cards must be initialized
        //POSTCONDITION: returns tre if the card given is lower than
        // the instance card by one unit, false otherwise.
        if(this.sameSuit(c) && this.getValue()==c.getValue()-1){
            return true;
        }
        return false;
    }
    public void copyCard(Card c){
        //PRECONDITION: the cards must be initialized
        //POSTCONDITION: copies the value and suit of 'c'
        this.setValue(c.getValue());
        this.setSuit(c.getSuit());
        super.textura=Loader.cargadorDeImagenes(buscarRutaTextura(c.getValue(),c.getSuit()),anchuraCarta,alturaCarta);
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
            return "recursos/Cartas/9-Basto.png";
        }
    }

    @Override
    public void actualizar() {
        //System.out.println(hitBox.contains(MouseInput.RatonX,MouseInput.RatonY));
        if(!MouseInput.tengoCarta || (MouseInput.tengoCarta && meTienen)){
            if(hitBox.contains(MouseInput.RatonX,MouseInput.RatonY)){
                mouseIn=true;
                //System.out.println("Entro");
            }else{
                mouseIn=false;
                primeraVez=false;

            }
            if(mouseIn && MouseInput.botonIzquierdo){
                if(!primeraVez){
                    if(!MouseInput.tengoCarta){
                        this.meTienen=true;
                        MouseInput.tengoCarta=true;
                    }
                    this.tocada=true;
                    this.posicionInicialX=this.posicion.getX();
                    this.posicionInicialY=this.posicion.getY();
                    this.diferenciaX=MouseInput.RatonX-(int)this.posicion.getX();
                    this.diferenciaY=MouseInput.RatonY-(int)this.posicion.getY();
                /*
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("La posicion de raton es X:"+MouseInput.RatonX+" ,Y: "+MouseInput.RatonY);
                System.out.println("La carta se encuentra en X:"+this.posicion.getX()+" ,Y: "+this.posicion.getY());
                System.out.println("La hitbox se encuentra en X:"+this.hitBox.x+" ,Y: "+this.hitBox.y);
                System.out.println("difernecia X: "+this.diferenciaX+" ,diferencia Y:"+this.diferenciaY);
                System.out.println("---------------------------------------------------------------------------------");
                */
                    primeraVez=true;
                }
                if(meTienen){
                    MouseInput.tengoCarta=true;
                    super.posicion.setX(MouseInput.RatonX-this.diferenciaX);
                    super.posicion.setY(MouseInput.RatonY-this.diferenciaY);
                    this.hitBox.x=MouseInput.RatonX-this.diferenciaX;
                    this.hitBox.y=MouseInput.RatonY-this.diferenciaY;
                }

            }else if(this.primeraVez ||(!this.primeraVez && this.tocada && !mouseIn)){//El raton habra soltado la carta

                MouseInput.tengoCarta=false;
                this.meTienen=false;
                super.posicion.setX(this.posicionInicialX);
                super.posicion.setY(this.posicionInicialY);
                this.hitBox.x=(int)this.posicionInicialX;
                this.hitBox.y=(int)this.posicionInicialY;
            }
        }


    }

    @Override
    public void dibujar(Graphics g) {
        g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
        //System.out.println("dibujado");
        //Graphics2D g2d=(Graphics2D) g;
        //g2d.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
    }
}
