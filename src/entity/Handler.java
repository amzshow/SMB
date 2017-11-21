package entity;

import java.awt.Graphics2D;
import java.util.LinkedList;

import entity.block.Brick;
import entity.block.ContainerBlock;
import entity.mob.Coin;
import entity.mob.Goomba;
import entity.mob.Powerup;

public class Handler {

	LinkedList<Entity> entity = new LinkedList<Entity>();
	private Entity tempEntity;
	
	public void update() {
		for(int i = 0; i < entity.size();i++) {			
			tempEntity = entity.get(i);
			if(!(tempEntity.x < -Camera.x - 32 || tempEntity.x > (-Camera.x + (32 * 16))))
				tempEntity.update();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < entity.size();i++) {
			tempEntity = entity.get(i);
			if(!(tempEntity.x < -Camera.x - 32 || tempEntity.x > (-Camera.x + (32 * 16))))
				tempEntity.draw(g);
		}
	}
	
	public void addEntity(Entity entity) {
		this.entity.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		this.entity.remove(entity);
	}
	
	public LinkedList<Entity> getList() {
		return entity;
	}
	
	public void addGoomba(float x, float y) {
		addEntity(new Goomba(x, y));
	}
	
	public void addBrick(float x, float y, boolean breakable) {
		addEntity(new Brick(x, y, breakable));
	}
	
	public void addPowerup(float x, float y) {
		addEntity(new Powerup(x, y));
	}
	
	public void addContainer(float x, float y, int type) {
		addEntity(new ContainerBlock(x, y, type));
	}
	
	public void addCoin(float x, float y) {
		addEntity(new Coin(x, y));
	}
	
}
