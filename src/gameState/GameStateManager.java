package gameState;

import java.util.ArrayList;

import main.Game;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	public static int currentState;
	public static String currentLevelName;
	
	public static final int GAMEOVER = 3;
	public static final int MENU = 0;
	public static final int LEVEL1_1 = 1;
	public static final int LEVEL1_1Extra = 2;
	public static final int LEVEL1_2 = 3;
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		gameStates.add(new MenuState());
		gameStates.add(new Level1_1State());
		gameStates.add(new Level1_1ExtraState());
		gameStates.add(new GameOverState());
		currentState = MENU;
		
	}
	
	public GameState getCurrentState() {
		return this.gameStates.get(currentState);
	}
	
	public void setState(int state) {
		gameStates.get(currentState).loading = true;
		currentState = state;
		gameStates.get(currentState).loading = true;
		Game.gsm.reset();
		gameStates.get(currentState).loading = false;
	}
	
	public void update() {
		if(gameStates.get(currentState).loading)
			return;
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(gameStates.get(currentState).loading)
			return;
		gameStates.get(currentState).draw(g);
	}
	
	public void reset() {
		while(Game.handler.getList().size() > 2) {
			Game.handler.getList().removeLast();
		}
		this.gameStates.get(currentState).init();
	}
	
}
