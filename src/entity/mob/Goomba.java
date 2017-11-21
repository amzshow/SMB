package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import entity.Sprite;
import entity.entityID;
import main.Game;

public class Goomba extends Mob {

	public Goomba(float x, float y) {
		super(x, y, entityID.Enemy);
		init();
	}
	
	private void init() {
		
		try {
			BufferedImage[] sprites = 	{ 
											ImageIO.read(new FileInputStream("resources/images/goombas_0.png")),
											ImageIO.read(new FileInputStream("resources/images/goombas_1.png")),
											ImageIO.read(new FileInputStream("resources/images/goombas_ded.png")),
										};

			Sprite.replaceColor(sprites[0], new Color(255,0,255), new Color(0,0,0,255));
			Sprite.replaceColor(sprites[1], new Color(255,0,255), new Color(0,0,0,255));
			Sprite.replaceColor(sprites[2], new Color(255,0,255), new Color(0,0,0,255));
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(200);
			animation.setStartStop(0, 2);
			image = sprites[0];

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if(dir)
			dx = 1;
		else
			dx = -1;
		animation.update();
		image = animation.getImage();
		move();
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void die() {
		this.x = this.x + this.image.getHeight();
		animation.setCurrentFrame(2);
		animation.setDelay(-1);
		handler.removeEntity(this);
		Game.hud.addScore(200);
	}

}
