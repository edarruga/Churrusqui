package graficos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*;


public class Loader {//No crearemos ningún objeto de esta clase solo proporcionará algunos metodos
	public static BufferedImage cargadorDeImagenes(String ruta,int anchura,int altura) {
		//File archivo =new File(ruta);
		ImageIcon ic =new ImageIcon(ruta);//Carga la imagen especificada en la ruta
		ImageIcon ic2 =new ImageIcon(ic.getImage().getScaledInstance(anchura,altura,Image.SCALE_SMOOTH));//Escala la imagen a un tamaño apropiado
		//return ImageIO.read(archivo);//Carga la imagen especificada en la ruta
		Image image=(Image)ic2.getImage();

		BufferedImage bi = new BufferedImage(anchura, altura,BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(image, 0, 0 , null);
		return  bi;

	}
	public static BufferedImage rotateImage(BufferedImage imageToRotate) {
		int widthOfImage = imageToRotate.getWidth();
		int heightOfImage = imageToRotate.getHeight();
		int typeOfImage = imageToRotate.getType();

		BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

		Graphics2D graphics2D = newImageFromBuffer.createGraphics();

		graphics2D.rotate(Math.toRadians(180), widthOfImage / 2, heightOfImage / 2);
		graphics2D.drawImage(imageToRotate, null, 0, 0);

		return newImageFromBuffer;
	}
	public static Font loadFont(String ruta,double tamaño){
		try {
			//return new Font(ruta,Font.PLAIN,tamaño);
			InputStream is = new FileInputStream(ruta);
			Font fuente = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, (float) tamaño);
			return fuente;
			//return Font.createFont(Font.TRUETYPE_FONT,Loader.class.getResourceAsStream(ruta)).deriveFont(Font.PLAIN,tamaño);
		} catch (FontFormatException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
