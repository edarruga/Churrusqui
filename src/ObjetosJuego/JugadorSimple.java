package ObjetosJuego;

import java.io.Serializable;

public class JugadorSimple implements Serializable {
    private static final long serialVersionUID=111L;
    private Mazo mazo;
    private CartaSimple carta1;
    private CartaSimple carta2;
    private CartaSimple carta3;
    private CartaSimple carta4;

    public JugadorSimple(JugadorSimple j){
        this.mazo=j.mazo;
        this.carta1=j.carta1;
        this.carta2=j.carta2;
        this.carta3=j.carta3;
        this.carta4=j.carta4;
    }
    public JugadorSimple(Mazo mazo,CartaSimple c1,CartaSimple c2,CartaSimple c3,CartaSimple c4){
        this.mazo=mazo;
        this.carta1=c1;
        this.carta2=c2;
        this.carta3=c3;
        this.carta4=c4;
    }
    public JugadorSimple(String s){
        String js[]=s.split("a");
        this.carta1=new CartaSimple(js[0]);
        this.carta2=new CartaSimple(js[1]);
        this.carta3=new CartaSimple(js[2]);
        this.carta4=new CartaSimple(js[3]);
        this.mazo=new Mazo(js[4]);
    }
    public JugadorSimple(CartaSimple[] csv){
        this.mazo=new Mazo();
        this.mazo.insertarCartasSimples(csv);
        CartaSimple c=this.mazo.giveCard();
        this.carta1=new CartaSimple(c.getSuit(),c.getValue());
        c=this.mazo.giveCard();
        this.carta2=new CartaSimple(c.getSuit(),c.getValue());
        c=this.mazo.giveCard();
        this.carta3=new CartaSimple(c.getSuit(),c.getValue());
        c=this.mazo.giveCard();
        this.carta4=new CartaSimple(c.getSuit(),c.getValue());
    }

    public Mazo getMazo() {
        return this.mazo;
    }

    public CartaSimple getCarta1() {
        return this.carta1;
    }

    public CartaSimple getCarta2() {
        return this.carta2;
    }

    public CartaSimple getCarta3() {
        return this.carta3;
    }

    public CartaSimple getCarta4() {
        return this.carta4;
    }

    public String toString(){
        return this.carta1.toString()+"a"+this.carta2.toString()+"a"+this.carta3.toString()+"a"+this.carta4.toString()+"a"+this.mazo.toString();
    }
}
