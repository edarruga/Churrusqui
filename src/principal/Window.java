package principal;

import Estados.Estado;
import Estados.EstadoJuego;
import Estados.EstadoMenu;
import graficos.Assets;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable{

    private static final int anchuraVentana = Toolkit.getDefaultToolkit().getScreenSize().width; //anchura de la ventana
    private static final int alturaVentana = Toolkit.getDefaultToolkit().getScreenSize().height; //altura de la ventana
    private Canvas canvas;//Lienzo de dibujado
    private Thread hilo; //Hilo encargado de mostrar la ventana
    private boolean funcionando = false;//Controlar si el hilo tiene que estar funcionando o tine que parar
    private BufferStrategy bs;//Para organizar la memoria
    private static Graphics g;//permiten que una aplicación se dibuje en componentes que se realizan en varios dispositivos, así como en imágenes fuera de pantalla
    private final int FPS=6000;//Numero maximo de fotogramas por segundo que mostrará la aplicación
    private double targettime=1000000000/FPS;//Objetivo para fijar los fotogramas, lo definimos en nanosegundos para ser lo más precisos posibles
    private double tiempoTranscurrido=0;//Almacenamos el tiempo que transcurra en el programa
    private int promedioFPS=FPS;//Nos permitirá conocer a cuantos FPS esta funcionado la aplicación

    private MouseInput mouseinput;
    public Window(){
        setTitle("Churrusqui");//Titulo de la ventana
        setSize(anchuraVentana,alturaVentana);//Definimos las dimensiones de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//La ventana se cerrará al pulsar la "X"
        setResizable(false);//la ventana no se podrá redimensionar en tiempo de ejecución
        setLocationRelativeTo(null);//La ventana aparecerá en el centro de la pantalla al inciar el programa
        setVisible(true);//Hacer la ventana visible

        canvas=new Canvas();//Creamos el lienzo
        mouseinput=new MouseInput();
        canvas.setPreferredSize(new Dimension(anchuraVentana,alturaVentana));
        canvas.setMaximumSize(new Dimension(anchuraVentana,alturaVentana));
        canvas.setMinimumSize(new Dimension(anchuraVentana,alturaVentana));
        canvas.setFocusable(true); //Para poder recivir entradas por parte del teclado

        add(canvas);//añadimos el canvas
        canvas.addMouseListener(mouseinput);
        canvas.addMouseMotionListener(mouseinput);
    }
    public static void main(String[] args){
        new Window().empezar();
    }

    private void actualizar(){
        Estado.getEstadoActual().actualizar();

    }
    public static Graphics getGraficos(){
        return Window.g;
    }

    private void dibujar(){
        bs=canvas.getBufferStrategy();
        if(bs==null){//Si es la primera vez que se dibuja será null
            canvas.createBufferStrategy(3); //Creamos el bufferStrategy
            return;//Para evitar errores
        }
        g=bs.getDrawGraphics();
        //----Empieza a dibujar----
        g.setColor(Color.white);
        g.fillRect(0,0,anchuraVentana,alturaVentana);

        g.drawImage(Assets.fondo,0,0,null);
        Estado.getEstadoActual().dibujar(g);
        g.setColor(Color.white);
        g.setFont(Assets.propiedades);
        g.drawString("FPS: "+promedioFPS,10,15); //Dibuja el contador de fotogramas
        g.drawString("Anchura pantalla: "+Toolkit.getDefaultToolkit().getScreenSize().width,10,27);
        g.drawString("Altura pantalla: "+Toolkit.getDefaultToolkit().getScreenSize().height,10,42);
        //----Termina de dibujar----
        g.dispose();
        bs.show();
    }
    
    public void iniciar() {
    	Assets.iniciar();
        Estado.cambiarEstado(new EstadoMenu());
    }

    @Override
    public void run() {
        long ahora=0;//Registro del tiempo que transcurra
        long ultimoTiempo=System.nanoTime();//Almacenamos la hora actual del sistema en nanosegundos
        int frames=0;//Numero de fotoframas
        long time=0;

        iniciar();
        
        while(funcionando){//Ciclo de dibujado de la pantalla
            ahora=System.nanoTime();
            tiempoTranscurrido += (ahora - ultimoTiempo)/targettime;
            time +=ahora - ultimoTiempo;
            ultimoTiempo=ahora;
            if(tiempoTranscurrido>=1){//Si ya ha transcurrrido el tiempo para poder realizar un fotograma generará el nuevo fotograma
                actualizar(); //Primero se actualizan las posiciones de todos los elementos
                dibujar(); //Luego se dibujan los elementos en pantalla
                tiempoTranscurrido --;
                frames ++;
            }
            if(time>=1000000000){//Si ya ha pasado un segundo pasamos los fotogamas realizados en este tiempo a la variable promedioFPS y los reiniciamos
                promedioFPS=frames;
                frames=0;
                time=0;
            }


        }

        terminar();
    }


    private void empezar(){
        hilo=new Thread(this);
        this.hilo.start();
        funcionando=true;//Permitimos que el hilo pueda funcionar
    }

    private void terminar() {
        try {
            hilo.join();
            funcionando=false;//Detenemos al hilo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getAnchuraVentana(){
        return anchuraVentana;
    }
    public static int getAlturaVentana(){
        return alturaVentana;
    }
}
