package game.logic;

import java.awt.event.KeyEvent;

import game.logic.Dragon.DragonBehavior;

public class GameConfig {
	private int width, height;
	private DragonBehavior dragonBehavior;
	private int numDragons;
	private int downKeyAssignment = KeyEvent.VK_S;
	private int leftKeyAssignment = KeyEvent.VK_A;
	private int rightKeyAssignment = KeyEvent.VK_D;
	private int upKeyAssignment = KeyEvent.VK_W;

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

	public int getDownKeyAssignment() {
		return downKeyAssignment;
	}

	public void setDownKeyAssignment(int downKeyAssignment) {
		this.downKeyAssignment = downKeyAssignment;
	}

	public int getLeftKeyAssignment() {
		return leftKeyAssignment;
	}

	public void setLeftKeyAssignment(int leftKeyAssignment) {
		this.leftKeyAssignment = leftKeyAssignment;
	}

	public int getRightKeyAssignment() {
		return rightKeyAssignment;
	}

	public void setRightKeyAssignment(int rightKeyAssignment) {
		this.rightKeyAssignment = rightKeyAssignment;
	}

	public int getUpKeyAssignment() {
		return upKeyAssignment;
	}

	public void setUpKeyAssignment(int upKeyAssignment) {
		this.upKeyAssignment = upKeyAssignment;
	}
}
