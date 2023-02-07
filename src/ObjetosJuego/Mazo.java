package ObjetosJuego;

import Matematica.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Mazo {

    private CartaSimple [] cards;
    private int num;
    private Random r = new Random();

    public Mazo(){
        this.cards = new CartaSimple [40];
        num=0;
        for(int i=0;i<40;i++){
            this.cards[i] = new CartaSimple(0, 0);
        }
    }
    public void llenarMazo(){
        num=40;
        for(int i=0;i<40;i++){
            this.cards[i] = new CartaSimple(i/10+1, (i%10)+1);
        }
    }
    public void swap(int n, int n2){
        //PRECONDITION: the deck must be initialized, 0 <= n and n2 < 40
        //POSTCONDITION: the cards in the position n and n2 are swapped
        CartaSimple aux = this.cards[n2];
        this.cards[n2]=this.cards[n];
        this.cards[n]=aux;
    }
    public void setNum(int num){
        this.num=num;
    }

    public void shuffle(){
        //PRECONDITION: the deck must be initialized
        //POSTCONDITION: the deck swap 100 times randomly
        for(int i=0;i<100;i++){
            this.swap(r.nextInt(39),r.nextInt(39));
        }
    }

    public CartaSimple giveCard(){
        //PRECONDITION: the deck must be initialized
        //POSTCONDITION: returns the last card in the deck,
        // and the number of cards is decreased by one
        this.num--;
        CartaSimple cs=new CartaSimple(this.cards[this.num].getSuit(),this.cards[this.num].getValue());
        this.cards[this.num].setValue(0);
        this.cards[this.num].setSuit(0);
        return cs;
    }
    public void insertarCartaSimple(CartaSimple cs){
        this.cards[this.num].setSuit(cs.getSuit());
        this.cards[this.num].setValue(cs.getValue());
        this.num++;
    }
    public void insertarCartasSimples(CartaSimple[] csv){
        for(int i=0;i<csv.length;i++){
            this.insertarCartaSimple(csv[i]);
        }
    }

    public int getNum(){
        //PRECONDITION: the deck must be initialized
        //POSTCONDITION: returns the number of cards in the deck
        return this.num;
    }
    public void showmazo(){
        for(int i=0;i<num;i++){
            this.cards[i].showCard();
        }
    }
    public CartaSimple getUltimaCarta(){
        return this.cards[this.num-1];
    }

}
