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
    public boolean ImmediatelyNext(CartaSimple c){//La carta c es la que NO esta en la mesa
        //PRECONDITION: the cards must be initialized
        //POSTCONDITION: returns true if the card given is higher than
        // the instance card by one unit, false otherwise.
        if(this.getValue()==c.getValue()+1 || this.getValue()==c.getValue()-9){
            return true;
        }
        return false;
    }
    public boolean ImmediatelyPrevious(CartaSimple c){//La carta c es la que NO esta en la mesa
        //PRECONDITION: the cards must be initialized
        //POSTCONDITION: returns tre if the card given is lower than
        // the instance card by one unit, false otherwise.
        if(this.getValue()==c.getValue()-1 || this.getValue()==c.getValue()+9){
            return true;
        }
        return false;
    }

    public boolean sameCard(Card c){
        //PRECONDITION:
        //POSTCONDITION: returns true if 2 cards have the same value and suit, false otherwise
        if(this.getSuit()==c.getSuit() && this.getValue()==c.getValue()){
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

}
