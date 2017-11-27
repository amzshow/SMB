package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import main.Game;

public class Sprite {
	
	public LinkedList<BufferedImage> sprites = new LinkedList<BufferedImage>();
	
	public Sprite(){
		init();
	}

	private void init(){
		try {
			BufferedImage image = ImageIO.read(Sprite.class.getResource("/images/blocks.png"));
			BufferedImage temp;
			for(int i = 0; i < 25; i++) {
				for(int j = 0; j < 21; j++) {
					temp = image.getSubimage(j * 32, i * 32, 32, 32);
					temp = imageToBufferedImage(temp.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
					replaceColor(temp, new Color(255,0,255), Game.transparent);
					sprites.add(temp);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage covertToARGB(BufferedImage old) {
		BufferedImage newImage = new BufferedImage(
			    old.getWidth(), old.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();
		g.drawImage(old, 0, 0, null);
		g.dispose();
		return newImage;
	}
	
	public static BufferedImage imageToBufferedImage(Image im) {
	     BufferedImage bi = new BufferedImage
	        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
	     Graphics bg = bi.getGraphics();
	     bg.drawImage(im, 0, 0, null);
	     bg.dispose();
	     return bi;
	}
	
	public static void replaceColor(BufferedImage image, Color oldColor, Color newColor)
	{
      for (int y=0; y<image.getHeight(); y++){
          for (int x=0; x<image.getWidth(); x++){
              int color = image.getRGB(x, y);
              
              if (color == oldColor.getRGB()){
                  image.setRGB(x, y, newColor.getRGB());
              }
          }
      }
	}
}
