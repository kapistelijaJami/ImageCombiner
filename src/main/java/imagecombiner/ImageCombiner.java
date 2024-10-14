package imagecombiner;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import uilibrary.animation.ImageLoader;

/**
 * Takes bunch of images named in format img_x-y.jpg and combines them to single large image.
 */
public class ImageCombiner {
    public static void main(String[] args) {
		
		//CHANGE THESE SETTINGS:
		
		String path = "C:\\Users\\Jami\\Downloads\\combine images\\images\\"; //Folder where the images are, and where the resulting image will be saved.
		String outputFilename = "combined.jpg";
		int nX = 64;		//Number of pieces in x direction
		int nY = 96;		//Number of pieces in y direction
		int pieceX = 256;	//Piece x size in pixels
		int pieceY = 256;	//Piece y size in pixels
		
		
		//DON'T TOUCH THESE:
		
		int w = pieceX * nX;
		int h = pieceY * nY;
		
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); //If you need alpha you can change this to TYPE_INT_ARGB
		Graphics2D g = (Graphics2D) buf.getGraphics();
		
		for (int y = 0; y < nY; y++) {
			System.out.println("Starting row y = " + y);
			for (int x = 0; x < nX; x++) {
				addImage(g, x, y, pieceX, pieceY, path);
			}
		}
		g.dispose();
		
		System.out.println("Done creating the image! Saving to file...");
		
		ImageLoader.saveImage(buf, path + outputFilename, outputFilename.substring(outputFilename.lastIndexOf(".") + 1));
		
		System.out.println("Done!");
    }
	
	private static void addImage(Graphics2D g, int x, int y, int pieceX, int pieceY, String path) {
		BufferedImage img = ImageLoader.loadImage(path + "img_" + x + "-" + y + ".jpg", true);
		
		g.drawImage(img, x * pieceX, y * pieceY, null);
	}
}
