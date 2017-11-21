package entity.block;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Sprite;
import entity.mob.Animation;
import entity.mob.Mob;
import gameState.GameState;
import main.Game;

public class ContainerBlock extends Block {

	int type;
	Animation animation = new Animation();
	
	public ContainerBlock(float x, float y, int type) {
		super(x, y, true, false, true);
		
		BufferedImage[] images = {image,image,image,image};
		int i = 0;
		if(GameState.getWorldType() == GameState.Type.OverWorld)
			i = 0;
		else if(GameState.getWorldType() == GameState.Type.UnderWorld)
			i = 1;
		else
			i = 2;
		for(int j = 0; j < 4; j++) {
			images[j] = Game.sprite.sprites.get(6 + (3 * j) + i);
			Sprite.replaceColor(images[j], new Color(255,0,255), Game.transparent);
		}
		animation.setFrames(images);
		animation.setStartStop(1, 4);
		animation.setDelay(200);
		image = animation.getImage();
		this.type = type;
	}

	public void update() {
		if(interactable) {
			animation.update();
			image = animation.getImage();
		}
		else {
			animation.setStartStop(0, 1);
			image = animation.getImage();
		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void die() {
		if(!interactable)
			return;
		interactable = false;
		if(type == 0)
			Game.handler.addPowerup(x, y - 32);
	}

}
