package graficos;

import java.awt.image.BufferedImage;
import graficos.Loader;

import static principal.Window.alturaVentana;
import static principal.Window.anchuraVentana;

public class Assets {

	public static BufferedImage fondo;
	public static BufferedImage prueba;
	
	public static void iniciar() {//Se llamará cuando el programa arranque
		prueba=Loader.cargadorDeImagenes("recursos/Cartas/7-BastoPrueba.png",160,248);
		fondo=Loader.cargadorDeImagenes("recursos/Fondos/Fondo3.png",anchuraVentana,alturaVentana);//Carga el fondo
	}

}
