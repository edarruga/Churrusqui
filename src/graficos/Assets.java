package graficos;

import java.awt.image.BufferedImage;
import graficos.Loader;
import principal.Window;


public class Assets {

	public static BufferedImage fondo;
	
	public static void iniciar() {//Se llamará cuando el programa arranque
		fondo=Loader.cargadorDeImagenes("recursos/Fondos/Fondo3.png", Window.getAnchuraVentana(),Window.getAlturaVentana());//Carga el fondo
	}

}
