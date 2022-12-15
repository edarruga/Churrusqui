package Componentes;

import Matematica.Vector2D;
import graficos.Assets;
import graficos.Texto;
import input.MouseInput;
import org.w3c.dom.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Boton {
    private BufferedImage mouseOutImg;
    private BufferedImage mouseInImg;
    private boolean mouseIn;
    private Rectangle hitbox;
    private String texto;
    private Accion accion;

    public Boton(BufferedImage out,BufferedImage in, int x,int y,String texto,Accion a){
        this.mouseInImg=in;
        this.mouseOutImg=out;
        this.texto=texto;
        this.hitbox=new Rectangle(x,y,in.getWidth(),out.getHeight());
        this.accion=a;
    }

    public void actualizar(){
        if(this.hitbox.contains(MouseInput.RatonX,MouseInput.RatonY)){
            mouseIn=true;
        }else{
            mouseIn=false;
        }
        if(mouseIn && MouseInput.botonIzquierdo && !MouseInput.arrastrando){
            this.accion.hacerAccion();
        }
    }
    public void dibujar(Graphics g){
        if(mouseIn){
            g.drawImage(mouseInImg,this.hitbox.x,this.hitbox.y,null);
            if(MouseInput.botonIzquierdo && !MouseInput.arrastrando){
                Texto.dibujarTexto(g,this.texto,new Vector2D(this.hitbox.x+this.hitbox.width/6,this.hitbox.y+this.hitbox.height*3/4),Color.RED, Assets.fuente);
            }else{
                Texto.dibujarTexto(g,this.texto,new Vector2D(this.hitbox.x+this.hitbox.width/6,this.hitbox.y+this.hitbox.height*3/4),Color.DARK_GRAY, Assets.fuente);
            }

        }else{
            g.drawImage(mouseOutImg,this.hitbox.x,this.hitbox.y,null);
            Texto.dibujarTexto(g,this.texto,new Vector2D(this.hitbox.x+this.hitbox.width/6,this.hitbox.y+this.hitbox.height*2/3),Color.BLACK, Assets.fuente);
        }

    }
}
