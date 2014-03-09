package game.logic;

import game.logic.Dragon.DragonBehavior;

public class GameConfig {
	int width, height;
	DragonBehavior dragonBehavior;
	int numDragons;

	public GameConfig() {
		width = 11;
		height = 11;
		dragonBehavior = DragonBehavior.NOTMOVING;
		numDragons = 5;
	}

	public void setGameConfig(int width, int height,
			DragonBehavior dragonBehavior, int numDragons) {
		this.width = width;
		this.height = height;
		this.dragonBehavior = dragonBehavior;
		this.numDragons = numDragons;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public DragonBehavior getDragonBehavior() {
		return dragonBehavior;
	}

	public void setDragonBehavior(DragonBehavior dragonBehavior) {
		this.dragonBehavior = dragonBehavior;
	}

	public int getNumDragons() {
		return numDragons;
	}

	public void setNumDragons(int numDragons) {
		this.numDragons = numDragons;
	}
}
