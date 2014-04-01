package game.logic;

import java.io.Serializable;

/**
 * Represents a living being. A living being has a name, a type, a position and
 * life. There are also flags to indicate whether the being is moving and
 * sleeping. Furthermore, each being also has an indication of the direction it
 * is facing.
 * 
 * @author Henrique Ferrolho
 * 
 */
public abstract class LivingBeing implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected Type type;
	protected Coord position = new Coord();
	protected boolean moving = false;
	protected int life = 100;
	protected boolean isSleeping = false;
	protected Direction facingDir = Direction.DOWN;
	protected int frames, currentFrame;

	public enum Type {
		HERO, EAGLE, DRAGON
	}

	/**
	 * Moves a being in the specified direction.
	 * 
	 * @param lab
	 *            a labyrinth
	 * @param direction
	 *            a direction
	 */
	public abstract void move(Labyrinth lab, Direction direction);

	public abstract String toString();

	/**
	 * Gets this living being type.
	 * 
	 * @return this living being type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets this living being type.
	 * 
	 * @param type
	 *            the type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns true if this living being is on the specified position.
	 * 
	 * @param x
	 *            the X coordinate of the specified position
	 * @param y
	 *            the Y coordinate of the specified position
	 * @return <code>true</code> if this living being is on the specified
	 *         position
	 */
	public boolean isOn(int x, int y) {
		return (x == getPosition().getX()) && (y == getPosition().getY());
	}

	/**
	 * Gets this living being position.
	 * 
	 * @return this living being position
	 */
	public Coord getPosition() {
		return position;
	}

	/**
	 * Sets this living being position.
	 * 
	 * @param position
	 *            the new position
	 */
	public void setPosition(Coord position) {
		this.position = position;
	}

	/**
	 * Returns the distance from this living being to the specified living
	 * being.
	 * 
	 * @param livingBeing
	 *            the other living being
	 * @return the distance between the living beings
	 */
	public int distanceTo(LivingBeing livingBeing) {
		int dx = Math.abs(getPosition().getX()
				- livingBeing.getPosition().getX());
		int dy = Math.abs(getPosition().getY()
				- livingBeing.getPosition().getY());

		return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
	}

	/**
	 * Returns the distance between this living being and the specified sword.
	 * 
	 * @param sword
	 *            the sword
	 * @return the distance between this living being and the specified sword
	 */
	public int distanceTo(Sword sword) {
		int dx = Math.abs(getPosition().getX() - sword.getPosition().getX());
		int dy = Math.abs(getPosition().getY() - sword.getPosition().getY());

		return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
	}

	/**
	 * Returns true if being is moving.
	 * 
	 * @return <code>true</code> if being is moving
	 */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * Sets this being moving state
	 * 
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	/**
	 * Returns true if this living being has no life left.
	 * 
	 * @return <code>true</code> if this living being is dead
	 */
	public boolean isDead() {
		return life == 0;
	}

	/**
	 * Kills this living being.
	 */
	public void kill() {
		this.life = 0;
	}

	/**
	 * Sets this living being's life.
	 * 
	 * @param life
	 *            the life to be set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * Returns true if this living being is sleeping.
	 * 
	 * @return <code>true</code> if this living being is sleeping
	 */
	public boolean isSleeping() {
		return isSleeping;
	}

	/**
	 * Makes this living being go to sleep.
	 */
	public void sleep() {
		isSleeping = true;
	}

	/**
	 * Wakes up this living being
	 */
	public void wakeUp() {
		isSleeping = false;
	}

	/**
	 * Gets this living being sprite's number of frames.
	 * 
	 * @return the number of frames
	 */
	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public void nextFrame() {
		this.currentFrame++;

		if (this.currentFrame >= this.frames) {
			this.currentFrame = 0;
			this.moving = false;
		}
	}

	/**
	 * Gets the direction in which this being is currently facing.
	 * 
	 * @return the direction this being is facing
	 */
	public Direction getFacingDir() {
		return facingDir;
	}

	public void setFacingDir(Direction facingDir) {
		this.facingDir = facingDir;
	}
}