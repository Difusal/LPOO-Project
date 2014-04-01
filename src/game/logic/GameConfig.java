package game.logic;

import java.awt.event.KeyEvent;

import game.logic.Dragon.DragonBehavior;

/**
 * A game configuration representing all the game settings.
 * 
 * @author henrique
 * 
 */
public class GameConfig {
	private int width, height;
	private DragonBehavior dragonBehavior;
	private int numDragons;
	private int downKeyAssignment = KeyEvent.VK_S;
	private int leftKeyAssignment = KeyEvent.VK_A;
	private int rightKeyAssignment = KeyEvent.VK_D;
	private int upKeyAssignment = KeyEvent.VK_W;
	private int sendEagleKeyAssignment = KeyEvent.VK_B;

	/**
	 * Constructs and initializes the default game configurations.
	 */
	public GameConfig() {
		width = 11;
		height = 11;
		dragonBehavior = DragonBehavior.NOTMOVING;
		numDragons = 5;
	}

	/**
	 * Changes the current game configuration with the specified width.
	 * 
	 * @param width
	 *            the width of this game configuration
	 * @param height
	 *            the height of this game configuration
	 * @param dragonBehavior
	 *            the dragonBehavior of this game configuration
	 * @param numDragons
	 *            the number of dragons of this game configuration
	 */
	public void setGameConfig(int width, int height,
			DragonBehavior dragonBehavior, int numDragons) {
		this.width = width;
		this.height = height;
		this.dragonBehavior = dragonBehavior;
		this.numDragons = numDragons;
	}

	/**
	 * Gets this game configuration width.
	 * 
	 * @return this game configuration width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets this game configuration width.
	 * 
	 * @param width
	 *            the width to be associated to this game configuration
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets this game configuration height.
	 * 
	 * @return this game configuration height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets this game configuration height.
	 * 
	 * @param height
	 *            the height to be associated to this game configuration
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets this game configuration dragonBehavior.
	 * 
	 * @return this game configuration dragon behavior
	 */
	public DragonBehavior getDragonBehavior() {
		return dragonBehavior;
	}

	/**
	 * Sets this game configuration dragon behavior.
	 * 
	 * @param dragonBehavior
	 *            the dragon behavior to be associated to this game
	 *            configuration
	 */
	public void setDragonBehavior(DragonBehavior dragonBehavior) {
		this.dragonBehavior = dragonBehavior;
	}

	/**
	 * Gets this game configuration number of dragons.
	 * 
	 * @return this game configuration number of dragons
	 */
	public int getNumDragons() {
		return numDragons;
	}

	/**
	 * Sets this game configuration number of dragons.
	 * 
	 * @param numDragons
	 *            the number of dragons to be associated to this game
	 *            configuration
	 */
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

	public int getSendEagleKeyAssignment() {
		return sendEagleKeyAssignment;
	}

	public void setSendEagleKeyAssignment(int sendEagleKeyAssignment) {
		this.sendEagleKeyAssignment = sendEagleKeyAssignment;
	}
}
