package input;

import ObjetosJuego.Card;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardInput implements KeyListener{
    private int[] codigo;
    private int num=0;
    private int[] codigoKonami={38,38,40,40,37,39,37,39,66,65};
    public KeyBoardInput(){
        this.codigo=new int[10];
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        if(!Card.getEstigma()){
            if(e.getKeyCode()==10){
                if(this.num==10){
                    Card.activarEstigma();
                    //System.out.println("Activado");
                }else{
                    for (int i=0;i<codigo.length;i++) {
                        codigo[i]=0;
                    }
                    this.num=0;
                }
            }else{
                if(e.getKeyCode()!=38 && e.getKeyCode()!=39 && e.getKeyCode()!=40 && e.getKeyCode()!=37 && e.getKeyCode()!=66 && e.getKeyCode()!=65){
                    for (int i=0;i<codigo.length;i++) {
                        codigo[i]=0;
                    }
                    this.num=0;
                }else{
                    if(this.codigoKonami[num]==e.getKeyCode()){
                        this.codigo[num]=e.getKeyCode();
                        num++;
                    }else{
                        for (int i=0;i<codigo.length;i++) {
                            codigo[i]=0;
                        }
                        this.num=0;
                    }
                }
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
