package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import entity.Sprite;
import entity.entityID;
import gameState.GameState;
import main.Game;

public class Koopa extends Mob {

	private int state = 1;
	
	public Koopa(float x, float y) {
		super(x, y, entityID.Enemy);
		init();		
	}
	
	private void init() {
		try {
			moveSpeed = -1;
			String p = "";
			if(GameState.getWorldType() == GameState.Type.UnderWorld)
				p = "1";
			else if(GameState.getWorldType() == GameState.Type.Castle)
				p = "2";
			BufferedImage sprites[] = new BufferedImage[3];
			for(int i = 0; i < 3; i++) {
				if(i < 2)
					sprites[i] = ImageIO.read(new FileInputStream("resources/images/koopa"+p+"_"+i+".png"));
				else
					sprites[i] = ImageIO.read(new FileInputStream("resources/images/koopa"+p+"_ded.png"));
				sprites[i] = Sprite.imageToBufferedImage(sprites[i].getScaledInstance(sprites[i].getWidth(), sprites[i].getHeight(), Image.SCALE_DEFAULT));
				Sprite.replaceColor(sprites[i], new Color(255,0,255), Game.transparent);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(200);
			animation.setStartStop(0, 2);
			image = sprites[0];
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update() {
		dx = moveSpeed;
		move();
		animation.update();
		image = animation.getImage();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)this.x, (int)this.y, null);
	}

	@Override
	public void die() {
		state--;
		animation.setStartStop(2, 3);
		if(state == 0)
			if(moveSpeed == -1)
				moveSpeed = 0;
			else
				moveSpeed = -1;
		if(state < 0)
			Game.handler.removeEntity(this);
	}

}
