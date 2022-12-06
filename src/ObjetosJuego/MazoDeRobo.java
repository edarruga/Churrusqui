package ObjetosJuego;

import Matematica.Vector2D;
import graficos.Loader;
import input.MouseInput;

import java.awt.*;

public class MazoDeRobo extends ObjetoJuego{
    private Mazo mazo;
    private Rectangle hitBox;
    private boolean desclico=true;
    private JugadorHumano jugadorHumano;
    public MazoDeRobo(Vector2D v2d,CartaSimple[] csv,JugadorHumano jh) {
        super(Loader.cargadorDeImagenes(Card.buscarRutaTextura(0,1),Card.getAnchuraCarta(),Card.getAlturaCarta()), v2d);
        this.jugadorHumano=jh;
        this.mazo=new Mazo();
        this.mazo.insertarCartasSimples(csv);
        System.out.println("===============");
        this.mazo.showmazo();
        this.hitBox=new Rectangle((int) v2d.getX(), (int) v2d.getY(),Card.getAnchuraCarta(),Card.getAlturaCarta());
    }
    public CartaSimple robarCata(){
       return this.mazo.giveCard();
    }

    @Override
    public void actualizar() {

        if(this.hitBox.contains(MouseInput.RatonX,MouseInput.RatonY)){
            if(MouseInput.clickEnMazoDeRobo){
                Card c=this.jugadorHumano.primeraCartaYaJugada();
                if(c!=null){
                    c.actualizarEstadoDeCarta(this.mazo.giveCard());
                    MouseInput.clickEnMazoDeRobo =false;
                }

            }
        }else{
            MouseInput.clickEnMazoDeRobo =false;
        }
    }

    @Override
    public void dibujar(Graphics g) {
        if(this.mazo.getNum()!=0){
            g.drawImage(super.textura,(int)super.posicion.getX(),(int)super.posicion.getY(),null);
        }else{
            g.drawImage(super.textura,-1000,-1000,null);
        }
    }
}
