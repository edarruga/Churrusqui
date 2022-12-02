package graficos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Loader {//No crearemos ningún objeto de esta clase solo proporcionará algunos metodos
	public static BufferedImage cargadorDeImagenes(String ruta,int anchura,int altura) {
		//File archivo =new File(ruta);
		ImageIcon ic =new ImageIcon(ruta);//Carga la imagen especificada en la ruta
		ImageIcon ic2 =new ImageIcon(ic.getImage().getScaledInstance(anchura,altura,Image.SCALE_SMOOTH));//Escala la imagen a un tamaño apropiado
		//return ImageIO.read(archivo);//Carga la imagen especificada en la ruta
		Image image=(Image)ic2.getImage();

		BufferedImage bi = new BufferedImage(anchura, altura,BufferedImage.SCALE_SMOOTH);
		bi.getGraphics().drawImage(image, 0, 0 , null);
		return  bi;

	}
}
