package gameState;

import java.awt.Graphics2D;

import entity.Camera;
import entity.HUD;
import entity.Structure;
import entity.block.Pipe;
import main.Game;

public class Level1_1State extends GameState {
	
	public Level1_1State() {		
		levelName = "1-1";
		startX = 128 - 32;
		startY = Game.HEIGHT - 256;
		toX = new float[1];
		toY = new float[1];
		toX[0] = 211 * 32;
		toY[0] = 3 * 32;
	}
	
	public void init() {
		
		Game.player.setX(startX);
		Game.player.setY(startY);
		Game.player.setDx(0);
		Game.player.setDy(0);
		
		Game.background = Game.overWorldBG;
		
		m.loadMap(levelName);
		
		m.addItemFromMap();
		
		Game.handler.addEntity(new Pipe(32 * 20, Game.HEIGHT - 128, true));
		
		HUD.time = 400;
		currentTo = 0;
		HUD.start();
		Camera.x = 0;
	}

	public void update() {
		if((Game.player.getX() > 200 * 32 && Game.player.getX() < 208 * 32) || Game.player.getX() > 208 * 32)
			Camera.move = false;
		Game.handler.update();
	}

	public void draw(Graphics2D g) {
		Game.handler.draw(g);
	}
	
}
