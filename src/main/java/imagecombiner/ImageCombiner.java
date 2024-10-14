package imagecombiner;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
		
		saveImage(buf, path + outputFilename, outputFilename.substring(outputFilename.lastIndexOf(".") + 1));
		
		System.out.println("Done!");
    }
	
	private static void addImage(Graphics2D g, int x, int y, int pieceX, int pieceY, String path) {
		BufferedImage img = loadImage(path + "img_" + x + "-" + y + ".jpg", true);
		
		g.drawImage(img, x * pieceX, y * pieceY, null);
	}
	
	private static BufferedImage loadImage(String path, boolean absolutePath) {
		try {
			if (absolutePath) {
				return ImageIO.read(new File(path));
			} else {
				return loadImage(path);
			}
		} catch (IOException e) {
			System.out.println("IOException " + e + ", " + path);
		}
		
		return null;
	}
	
	private static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(ImageCombiner.class.getResource(path)); //If it doesnt work, might need to build the project
			//return ImageIO.read(getClass().getResource(path)); //works if non-static
			//return ImageIO.read(new File(path)); //dont work (has to be static)
		} catch (IOException e) {
			System.out.println("IOException " + e + ", " + path);
		} catch (NullPointerException e) {
			System.out.println("Couldn't get resource. Path was null " + e + ", " + path);
		} catch (IllegalArgumentException e) {
			System.out.println("Couldn't find the resource (resource was null) " + e + ", " + path);
		}

		return null;
	}
	
	private static void saveImage(BufferedImage img, String path, String ext) {
		try {
			File outputfile = new File(path);
			ImageIO.write(img, ext, outputfile);
		} catch (IOException e) {
			System.out.println("IOException " + e + ", " + path);
		}
	}
}
