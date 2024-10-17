package imagecombiner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Takes bunch of images in some filename format where x and y will be replaced by their
 * grid position index (default format: img_x-y.jpg) and combines them to a single large image.
 */
public class ImageCombiner {
    public static void main(String[] args) {
		//createTestPieces(1024, 1024, 3, 3, "G:\\Lataukset\\Tiedostot\\parts\\");
		
		
		//CHANGE THESE SETTINGS:
		
		String path = "G:\\Lataukset\\Tiedostot\\parts\\"; //Folder where the images are, and where the resulting image will be saved.
		String inputFileFormat = "img_x-y.jpg";	//Default: "img_x-y.jpg". x and y will be replaced by numbers in code.
		String outputFilename = "combined.jpg";
		int nX = 3;		//Number of pieces in x direction
		int nY = 3;		//Number of pieces in y direction
		int pieceX = 1024;	//Piece x size in pixels
		int pieceY = 1024;	//Piece y size in pixels
		
		
		//DON'T TOUCH THESE:
		
		int w = pieceX * nX;
		int h = pieceY * nY;
		
		if (!path.endsWith("/") && !path.endsWith("\\")) {
			path += "\\";
		}
		
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); //If you need alpha you can change this to TYPE_INT_ARGB
		Graphics2D g = (Graphics2D) buf.getGraphics();
		
		for (int y = 0; y < nY; y++) {
			System.out.println("Starting row y = " + y);
			for (int x = 0; x < nX; x++) {
				addImage(g, x, y, pieceX, pieceY, inputFileFormat, path);
			}
		}
		g.dispose();
		
		System.out.println("Done creating the image. Saving to file...");
		
		saveImage(buf, path + outputFilename);
		
		System.out.println("Done!");
    }
	
	private static void addImage(Graphics2D g, int x, int y, int pieceX, int pieceY, String inputFormat, String path) {
		BufferedImage img = loadImage(path + parseFormatToString(inputFormat, x, y), true);
		
		g.drawImage(img, x * pieceX, y * pieceY, null);
	}
	
	private static String parseFormatToString(String inputFormat, int x, int y) {
		String[] parts = inputFormat.split("((?<=[xy])|(?=[xy]))", 0); //Splits around x and y while keeping them in the array too.
		
		String res = "";
		for (String part : parts) {
			if (part.equals("x")) {
				res += x;
			} else if (part.equals("y")) {
				res += y;
			} else {
				res += part;
			}
		}
		return res;
		
		/*Old way:
		String[] parts = inputFormat.split("[xy]", -1); //Will have parts separated by x's and y's, and empty strings for x and y.
		String s = inputFormat.replaceAll("[^xy]", ""); //Records the order of x's and y's so we can replace the empty string with the correct variable.
		
		String res = "";
		int i = 0;
		for (String part : parts) {
			if (part.isBlank()) {
				char c = s.charAt(i);
				if (c == 'x') {
					res += x;
				} else if (c == 'y') {
					res += y;
				}
				i++;
			} else {
				res += part;
			}
		}
		return res;*/
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
	
	private static void saveImage(BufferedImage img, String path) {
		try {
			String ext = path.substring(path.lastIndexOf(".") + 1);
			File outputfile = new File(path);
			ImageIO.write(img, ext, outputfile);
		} catch (IOException e) {
			System.out.println("IOException " + e + ", " + path);
		}
	}
	
	private static void createTestImage(int w, int h, Color c, String filePath) {
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) buf.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, w, h);
		saveImage(buf, filePath);
	}
	
	private static void createTestPieces(int w, int h, int nX, int nY, String folderPath) {
		if (!folderPath.endsWith("/") && !folderPath.endsWith("\\")) {
			folderPath += "\\";
		}
		Random r = new Random();
		for (int y = 0; y < nY; y++) {
			for (int x = 0; x < nX; x++) {
				createTestImage(w, h, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()), folderPath + "img_" + x + "-" + y + ".jpg");
			}
		}
	}
}
