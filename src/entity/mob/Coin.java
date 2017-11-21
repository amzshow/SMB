package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Sprite;
import entity.entityID;
import gameState.GameState;
import main.Game;

public class Coin extends Mob {

	public Coin(float x, float y) {
		super(x, y, entityID.Coin);
		try {
			BufferedImage[] sprites = new BufferedImage[3];
			String s = "0";
			
			if(GameState.getWorldType() == GameState.Type.UnderWorld)
				s = "2";
			else if (GameState.getWorldType() == GameState.Type.Castle)
				s = "2";
			
			sprites[0] = ImageIO.read(Goomba.class.getResource("/images/coin_use" + s + "0.bmp"));
			sprites[1] = ImageIO.read(Goomba.class.getResource("/images/coin_use" + s + ".bmp"));
			sprites[2] = ImageIO.read(Goomba.class.getResource("/images/coin_use" + s + "1.bmp"));
			
			Sprite.replaceColor(sprites[0], new Color(255,0,255), Game.background);
			Sprite.replaceColor(sprites[1], new Color(255,0,255), Game.background);
			Sprite.replaceColor(sprites[2], new Color(255,0,255), Game.background);
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(200);
			image = sprites[0];
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update() {
		if(this.getBounds().intersects(Game.player.getBounds()))
			die();
		animation.update();
		image = animation.getImage();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)(x), (int)(y), null);
	}

	@Override
	public void die() {
		Game.hud.addScore(200);
		Game.handler.removeEntity(this);
		Game.hud.addCoin();
	}

}
