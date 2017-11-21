package entity.block;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Camera;
import gameState.GameStateManager;
import main.Game;

public class Pipe extends Block {

	float xTo;
	float yTo;
	
	public Pipe(float x, float y, boolean canTravel) {
		super(x, y, false, false, canTravel);
		image = new BufferedImage(32, 32, 1);
		if(canTravel) {
			xTo = Game.gsm.getCurrentState().getToX();
			yTo = Game.gsm.getCurrentState().getToY();
			Game.gsm.getCurrentState().nextTo();
		}
	}

	@Override
	public void update() {
		if(this.getBounds().intersects(Game.player.getBounds())) {
			
			Game.player.setX(32 * 3);
			Camera.x = -Game.player.getX() + (32 * 3);
			Game.player.setY(32 * 3);
			Game.gsm.setState(GameStateManager.LEVEL1_1Extra);
			Game.gsm.reset();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect((int)(x),(int)(y), image.getWidth(), image.getHeight());
		g.setColor(Game.background);
		
	}

	@Override
	public void die() {
		
	}
	
}
