package ObjetosJuego;

public class CartaSimple {
    private int suit;   //Oro=1,Copa=2,Espada=3,Basto=4
    private int value;  //The value is between 1-10, but our methods show the 8, 9 and 10 as 10, 11 and 12, respectically
    public CartaSimple(){
        //PRECONDITION:
        //POSTCONDITION: the card is initialized
        this.suit=0;
        this.value=0;
    }
    public CartaSimple(int s,int v){
        //PRECONDITION: the card must be initialized
        //POSTCONDITION: creates the card with its own value and suit
        this.suit=s;
        this.value=v;
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

}
