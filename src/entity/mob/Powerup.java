package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import entity.Sprite;
import entity.entityID;
import main.Game;

public class Powerup extends Mob {

	public Powerup(float x, float y) {
		super(x, y,entityID.Powerup);
		init();
	}
	
	BufferedImage tt(BufferedImage old) {
		BufferedImage newImage = new BufferedImage(
			    old.getWidth(), old.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();
		g.drawImage(old, 0, 0, null);
		g.dispose();
		return newImage;
	}
	
	private void init() {
		dir = true;
		try {
			BufferedImage[] sprites = 	{ 
					ImageIO.read(new FileInputStream("resources/images/mushroom.png")),
					ImageIO.read(new FileInputStream("resources/images/flower0.png")),
				};
			
			for(int i = 0; i < 2; i++) {
				sprites[i] = tt(sprites[i]);
				Sprite.replaceColor(sprites[i], new Color(255,0,255), Game.transparent);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.stopAnimation();
			if(Player.powerLevel == 0) {
				animation.setCurrentFrame(0);
				moveSpeed = 1;
			}
			else {
				animation.setCurrentFrame(1);
				moveSpeed = 0;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if(dir)
			dx = moveSpeed;
		else
			dx = -moveSpeed;
		if(this.getBounds().intersects(Game.player.getBounds()))
			die();
		animation.update();
		image = animation.getImage();
		move();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void die() {
		Game.player.powerup();
		Game.player.animation.setStartStop(14, 15);
		Game.hud.addScore(1000);
		handler.removeEntity(this);
	}
	
}
