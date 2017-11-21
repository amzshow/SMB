package gameState;

import map.Map;

public abstract class GameState {

	protected GameState() {
	}
	
	protected Map m = new Map();
	public String levelName;
	protected float startX = 0;
	protected float startY = 0;
	protected float toX[];
	protected float toY[];
	public int currentTo = 0;
	public boolean loading = true;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	
	public enum Type {OverWorld, UnderWorld, Castle};
	public static Type type = GameState.Type.OverWorld;
	public static final Type getWorldType() { return type; }
	public static final void setWorldType(Type type) { GameState.type = type; }
	public float getToX() { return toX[currentTo]; }
	public float getToY() { return toY[currentTo]; }
	public void nextTo() { currentTo++; }
	
}
