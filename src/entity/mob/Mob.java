package entity.mob;

import java.util.LinkedList;

import entity.Entity;
import entity.Handler;
import entity.entityID;
import entity.block.Block;
import main.Game;

public abstract class Mob extends Entity {
	
	protected Handler handler;
	protected Animation animation;
	
	protected Mob(float x, float y, entityID id) {
		super(x, y, id);
		this.handler = Game.handler;
	}

	protected boolean dir = false; //left = false, right = true
	protected float moveSpeed;
	protected boolean canJump = false;
	protected boolean jumping = false;
	protected static float gravity = 0.3f;
	protected static final float MAX_SPEED = 9f; 
	protected Entity lastCollision = null;
	
	protected void move() {
		
		if(this.y > 32 * 16)
			this.die();
		
		applyGravity();
		if(!verticalCollision(handler.getList()))
			y += dy;
		if(!horizontalCollision(handler.getList()))
			x += dx;
	}
	
	protected void applyGravity() {
		if(falling || jumping)
			dy += gravity;
		if(dy > MAX_SPEED)
			dy = MAX_SPEED;
	}
	
	protected boolean verticalCollision(LinkedList<Entity> entity) {
		
		for(int i = 0; i < entity.size(); i++) {
			Entity tempEntity = entity.get(i);
			
			if(!(tempEntity.getID() == entityID.Structure || tempEntity.getID() == entityID.Other || tempEntity.getID() == entityID.Coin)) {
				if(getBottom().intersects(tempEntity.getBounds()) && dy > 0) {
					falling = false;
					canJump = true;
					jumping = false;
					dy = 0;
					y = tempEntity.getY() - image.getHeight();
					lastCollision = tempEntity;
					if(tempEntity.getID() != entityID.Block && this.id != entityID.Block && this.id != entityID.Powerup && tempEntity.getID() != entityID.Powerup) {
						if(tempEntity.getID() != entityID.Powerup && this.id != entityID.Powerup) {
							tempEntity.isAlive = false;
							jumping = true;
							canJump = false;
							falling = true;
							dy -= 4;
						}
						if(this.id == entityID.Enemy && tempEntity.getID() == entityID.Player || this.id == entityID.Player && tempEntity.getID() == entityID.Enemy)
							tempEntity.die();
					}
					if(this.id == entityID.Projectile && tempEntity.getID() == entityID.Block)
						this.dy -= 4;
					return true;
				} else if(getTop().intersects(tempEntity.getBounds()) && dy < 0) {

					jumping = true;
					falling = true;
					canJump = false;
					dy = 0;
					if(this.id == entityID.Player && tempEntity.getID() == entityID.Powerup)
						return false;
					if(tempEntity.getID() == entityID.Player && this.id == entityID.Enemy)
						this.die();
					if(this.id == entityID.Player && tempEntity.getID() == entityID.Block)
						tempEntity.die();
					return true;
				}
			}
		}
		falling = true;
		return false;
	}
	
	protected boolean horizontalCollision(LinkedList<Entity> entity) {
		for(int i = 0; i < entity.size(); i++) {
			Entity tempEntity = entity.get(i);
			
			if((this.id == entityID.Player && tempEntity.getID() == entityID.Powerup) || (this.id == entityID.Powerup && tempEntity.getID() == entityID.Player))
				return false;
			
			if(tempEntity.getID() == entityID.Block  && !((Block)(tempEntity)).isSolid())
				return false;
				
			if(!(tempEntity.getID() == entityID.Structure || tempEntity.getID() == entityID.Other || tempEntity.getID() == entityID.Coin)) {
				if((getLeft().intersects(tempEntity.getBounds()) && dx < 0) || (getRight().intersects(tempEntity.getBounds())) && dx > 0){
					if(tempEntity.getID() == entityID.Block)
						dx = 0;

					if(tempEntity.getID() == entityID.Player) {
						if(this.id == entityID.Enemy)
							tempEntity.die();
					}
					if(this.id == entityID.Projectile && tempEntity.getID() == entityID.Enemy) {
						tempEntity.die();
						this.die();
					}
					if(this.id == entityID.Player && tempEntity.getID() == entityID.Enemy)
						this.die();
					if(this.id != entityID.Player && (tempEntity.getID() == entityID.Block || tempEntity.getID() == entityID.Enemy || tempEntity.getID() == entityID.Powerup))
						this.changeDir();
					if(this.id == entityID.Projectile && tempEntity.getID() == entityID.Block)
						this.die();
					return true;
				}
			}
		}
		return false;
	}
	
	public void stopAllAnimation() {
		for(int i = 0; i < handler.getList().size(); i++)
			animation.stopAnimation();
	}
	
	public void startAllAnimation() {
		for(int i = 0; i < handler.getList().size(); i++)
			animation.startAnimation();
	}
	
	public void changeDir() {
		dir = !dir;
	}
}
