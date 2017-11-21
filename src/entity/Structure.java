package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.Mob;
import main.Game;

public class Structure extends Entity{

	public Structure(float x, float y) {
		super(x, y, entityID.Structure);
		try {
			image = Game.sprite.sprites.get(34);
			Sprite.replaceColor(image, new Color(255,0,255), new Color(0,0,0,0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void die() {
	}
	
	

}
