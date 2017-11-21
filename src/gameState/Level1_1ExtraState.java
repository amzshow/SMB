package gameState;

import java.awt.Graphics2D;

import entity.Camera;
import entity.HUD;
import main.Game;

public class Level1_1ExtraState extends GameState {
	
	public Level1_1ExtraState() {		
		levelName = "1-1E";
		startX = 32 * 3;
		startY = 32 * 3;
	}
	
	public void init() {
		
		Game.player.setX(startX);
		Game.player.setY(startY);
		
		Game.background = Game.underWorldBG;
		
		type = Type.UnderWorld;
		
		m.loadMap(levelName);
		
		m.addItemFromMap();
		
		Camera.move = false;
		
		HUD.start();
		Camera.x = 0;
	}

	public void update() {
		
		Game.handler.update();
	}

	public void draw(Graphics2D g) {
		Game.handler.draw(g);
	}
	
}
