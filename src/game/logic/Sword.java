package game.logic;

import game.logic.Labyrinth.Symbols;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a sword.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class Sword implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Coord position = new Coord(1, 1);
	private boolean show = true;

	/**
	 * Constructs and initializes a sword.
	 * 
	 * @param lab
	 *            the labyrinth
	 * @param hero
	 *            the hero
	 */
	public Sword(Labyrinth lab, LivingBeing hero) {
		Random r = new Random();
		do {
			do {
				position.setX(r.nextInt(lab.getWidth() - 2) + 1);
				position.setY(r.nextInt(lab.getHeight() - 2) + 1);
			} while (lab.getLab()[position.getY()][position.getX()] != Symbols.PATH);
		} while (position.getX() == hero.getPosition().getX()
				&& position.getY() == hero.getPosition().getY());
	}

	/**
	 * Constructs and initializes a sword at the specified coordinate.
	 * 
	 * @param x
	 *            the X coordinate of the sword
	 * @param y
	 *            the Y coordinate of the sword
	 */
	public Sword(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	/**
	 * Constructs and initializes a sword at the specified coordinate.
	 * 
	 * @param coord
	 *            the coordinate where to create this sword
	 */
	public Sword(Coord coord) {
		position = new Coord(coord);
	}

	public String drawToString() {
		return "E ";
	}

	/**
	 * Returns true if this sword is at the specified coordinate.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if this sword is at the specified coordinate.
	 */
	public boolean isOn(int x, int y) {
		return (x == position.getX()) && (y == position.getY());
	}

	/**
	 * Returns true if the sword is visible.
	 * 
	 * @return <code>true</code> if the sword is visible
	 */
	public boolean isVisible() {
		return show;
	}

	/**
	 * Shows this sword
	 */
	public void show() {
		show = true;
	}

	/**
	 * Hides this sword.
	 */
	public void hide() {
		show = false;
	}

	/**
	 * Gets this sword position.
	 * 
	 * @return this sword position
	 */
	public Coord getPosition() {
		return position;
	}

	/**
	 * Sets this sword position.
	 * 
	 * @param position
	 *            the new position of this sword
	 */
	public void setPosition(Coord position) {
		this.position = position;
	}
}
