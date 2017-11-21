package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import entity.Camera;
import entity.Sprite;
import entity.entityID;
import main.Game;

public class Projectile extends Mob {

	public Projectile(float x, float y) {
		super(x, y, entityID.Projectile);
		
		try {
			BufferedImage[] sprites = new BufferedImage[4];
	
			for(int i = 0; i < 4; i++) {
				sprites[i] = ImageIO.read(new FileInputStream("resources/images/fireball_"+i+".png"));
				Sprite.replaceColor(sprites[i], new Color(255,0,255), Game.transparent);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(50);
			image = sprites[0];
		
			if(Game.player.dir == false)
				dx = -5;
			else
				dx = 5;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		
		move();
		animation.update();
		image = animation.getImage();
		if(x > -Camera.x + 32 * 16)
			die();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void die() {
		Game.handler.removeEntity(this);
	}
	
}
