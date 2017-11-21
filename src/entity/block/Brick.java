package entity.block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.mob.Player;
import gameState.GameState;
import main.Game;

public class Brick extends Block {

	static final BufferedImage[] images = new BufferedImage[6];
	
	public Brick(float x, float y, boolean breakable) {
		super(x, y, true, breakable, false);
		init();
	} 
	
	private void init(){
		try {
			int i;
			for(i = 0; i < 6; i++) {
				images[i] = Game.sprite.sprites.get(i); 
			}
			i = 0;
			if(GameState.getWorldType() == GameState.Type.OverWorld)
				i = 0;
			else if(GameState.getWorldType() == GameState.Type.UnderWorld)
				i = 1;
			else
				i = 2;
			if(breakable)
				i+=3;
			image = images[i];
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);		
	}

	@Override
	public void die() {
		if(Player.powerLevel > 0 && breakable) {
			Game.hud.addScore(50);
			Game.handler.removeEntity(this);
		}
	}

}
