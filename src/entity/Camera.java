package entity;

import main.Game;

public class Camera {

	public static float x,y;
	public static Boolean move = true;
	
	public Camera(float x, float y) {
		Camera.x = x;
		Camera.y = y;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public Boolean getMove() { return move; }
	public void setX(float x) { Camera.x = x; }
	public void setY(float y) { Camera.y = y; }
	public void setMove(Boolean move) { Camera.move = move; }

	public void update(Entity entity) {
		
		if(-entity.getX()+Game.WIDTH/2 < x && move)
			x = -entity.getX() + Game.WIDTH / 2;
		if(entity.getX() < -x)
			entity.x = -x;
		if(entity.getX() > -x + (32 * 15))
			entity.x = -x + (32 * 15);
		Game.hud.x = -x;
	}
	
}
