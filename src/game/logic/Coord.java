package game.logic;

import java.io.Serializable;

/**
 * 
 * A coordinate representing a location in (x, y) point in space, specified in
 * integer precision.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class Coord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * The X coordinate of this Coord.
	 */
	private int x;

	/**
	 * The Y coordinate of this Coord.
	 */
	private int y;

	/**
	 * Constructs and initializes a coord at the origin (0, 0) of the coordinate
	 * space.
	 */
	public Coord() {
		x = 0;
		y = 0;
	}

	/**
	 * Constructs and initializes a coord at the specified (x,y) location in the
	 * coordinate space.
	 * 
	 * @param x
	 *            the X coordinate of the newly constructed Coord
	 * @param y
	 *            the Y coordinate of the newly constructed Coord
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs and initializes a coord with the same location as the
	 * specified Coord object.
	 * 
	 * @param position
	 *            a coord
	 */
	public Coord(Coord position) {
		x = position.getX();
		y = position.getY();
	}

	public boolean equals(Object other) {
		if (!(other instanceof Coord))
			return false;

		Coord that = (Coord) other;

		// Custom equality check here.
		return this.x == that.x && this.y == that.y;
	}

	/**
	 * Returns the X coordinate of this Coord.
	 * 
	 * @return the X coordinate of this Coord.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of this Coord.
	 * 
	 * @param x
	 *            the new X coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y coordinate of this Coord.
	 * 
	 * @return the Y coordinate of this Coord.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y coordinate of this Coord.
	 * 
	 * @param y
	 *            the new Y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
}
