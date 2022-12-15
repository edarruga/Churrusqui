package graficos;

import java.awt.*;
import java.awt.image.BufferedImage;

import ObjetosJuego.Card;
import principal.Window;


public class Assets {

	public static BufferedImage fondo;
	public static Font fuenteTitulo;
	public static Font fuente;
	public static Font propiedades;
	public static BufferedImage BotonGrisIn;
	public static BufferedImage BotonGrisOut;
	public static BufferedImage BotonMarronIn;
	public static BufferedImage BotonMarronOut;
	public static BufferedImage BotonCremaIn;
	public static BufferedImage BotonCremaOut;
	public static BufferedImage BotonBlancoIn;
	public static BufferedImage BotonBlancoOut;
	public static BufferedImage Victoria;
	public static BufferedImage Derrota;
	public static BufferedImage Churrusqui;
	public static BufferedImage Bloqueo;
	
	public static void iniciar() {//Se llamará cuando el programa arranque
		fondo=Loader.cargadorDeImagenes("recursos/Fondos/Fondo3.png", Window.getAnchuraVentana(),Window.getAlturaVentana());//Carga el fondo
		fuenteTitulo=Loader.loadFont("recursos/Fuentes/Puzzle-SolvedFilled.ttf",Window.getAnchuraVentana()*(0.078125));
		fuente=Loader.loadFont("recursos/Fuentes/coolvetica rg.otf",Window.getAnchuraVentana()*(0.016276));
		propiedades=Loader.loadFont("recursos/Fuentes/coolvetica rg.otf",Window.getAnchuraVentana()*(0.0097656));
		BotonBlancoIn=Loader.cargadorDeImagenes("recursos/Botones/BlancoIn.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonBlancoOut=Loader.cargadorDeImagenes("recursos/Botones/BlancoOut.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonCremaIn=Loader.cargadorDeImagenes("recursos/Botones/CremaIn.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonCremaOut=Loader.cargadorDeImagenes("recursos/Botones/CremaOut.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonGrisIn=Loader.cargadorDeImagenes("recursos/Botones/GrisIn.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonGrisOut=Loader.cargadorDeImagenes("recursos/Botones/GrisOut.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonMarronIn=Loader.cargadorDeImagenes("recursos/Botones/MarronIn.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		BotonMarronOut=Loader.cargadorDeImagenes("recursos/Botones/MarronOut.png",(int)(Window.getAnchuraVentana()/7.5),(int)(Window.getAlturaVentana()/17));
		Victoria=Loader.cargadorDeImagenes("recursos/Otros/Victoria.png",Window.getAnchuraVentana(),Window.getAlturaVentana());
		Derrota=Loader.cargadorDeImagenes("recursos/Otros/Derrota.png",Window.getAnchuraVentana(),Window.getAlturaVentana());
		Churrusqui=Loader.cargadorDeImagenes("recursos/Otros/Churrusqui.png",Window.getAnchuraVentana(),Window.getAlturaVentana());
		Bloqueo=Loader.cargadorDeImagenes("recursos/Otros/Bloqueo.png", Card.getAnchuraCarta(), Card.getAnchuraCarta());

	}

}
