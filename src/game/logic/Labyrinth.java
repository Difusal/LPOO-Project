package game.logic;

import game.logic.LivingBeing.Type;

import java.io.Serializable;
import java.util.Vector;

/**
 * Represents a Labyrinth.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class Labyrinth implements Serializable {
	private static final long serialVersionUID = 1L;
	private int width, height;
	private Symbols[][] lab;

	public enum Symbols {
		PATH, WALL, EXIT
	}

	/**
	 * Gets char associated with the symbol specified.
	 * 
	 * @param symbol
	 *            the symbol
	 * @return the <code>char</code> associated to the symbol specified
	 */
	public char getSymbolChar(Symbols symbol) {
		char res = '.';

		switch (symbol) {
		case PATH:
			res = ' ';
			break;
		case WALL:
			res = 'X';
			break;
		case EXIT:
			res = 'S';
			break;
		}

		return res;
	}

	/**
	 * Constructs and initializes a labyrinth.
	 * 
	 * @param width
	 *            the labyrinth width
	 * @param height
	 *            the labyrinth height
	 * @param lab
	 *            the representation of the labyrinth
	 */
	public Labyrinth(int width, int height, Symbols[][] lab) {
		this.width = width;
		this.height = height;
		this.lab = lab;
	}

	@Override
	public String toString() {
		String str = "\n";

		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++)
				str += getSymbolChar(lab[i][j]) + " ";

			str += "\n";
		}

		return str;
	}

	public String drawToString(Vector<LivingBeing> livingBeings, Sword sword,
			Eagle eagle) {
		String str = "\n\n";

		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				boolean somethingWasDrawn = false;

				// drawing hero, dragon or sword
				for (LivingBeing k : livingBeings) {
					// if something is at this position
					if (!k.isDead() && k.isOn(j, i)) {
						// drawing hero or eagle
						if (k.getType() == Type.HERO
								|| (k.getType() == Type.EAGLE && !eagle
										.isWithHero())) {
							str += k.toString();
							somethingWasDrawn = true;
						}
						// drawing dragon
						else if (k.getType() == Type.DRAGON) {
							// if sword is under the dragon
							if (sword.isVisible() && sword.isOn(j, i)) {
								if (k.isSleeping)
									str += "f ";
								else
									str += "F ";
							} else
								str += k.toString();

							somethingWasDrawn = true;
						}

						break;
					}
				}

				// if nothing was drawn yet
				if (!somethingWasDrawn) {
					if (sword.isOn(j, i) && sword.isVisible()) {
						// draw sword
						str += sword.drawToString();
						somethingWasDrawn = true;
					}
				}

				// if nothing was drawn yet
				if (!somethingWasDrawn)
					// draw maze
					str += getSymbolChar(lab[i][j]) + " ";
			}

			str += "\n";
		}

		return str;
	}

	/**
	 * Returns true if hero can walk in the specified direction.
	 * 
	 * @param dir
	 *            the direction in which to move hero
	 * @param hero
	 *            the hero
	 * @return <code>true</code> if hero can walk in the specified direction
	 */
	public boolean heroCanWalk(Direction dir, Hero hero) {
		int x = hero.getPosition().getX();
		int y = hero.getPosition().getY();

		switch (dir) {
		// scanning cell to the right
		case RIGHT:
			if (lab[y][x + 1] != Symbols.EXIT)
				return lab[y][x + 1] != Symbols.WALL;
			break;
		// scanning cell to the south
		case DOWN:
			if (lab[y + 1][x] != Symbols.EXIT)
				return lab[y + 1][x] != Symbols.WALL;
			break;
		// scanning cell to the left
		case LEFT:
			if (lab[y][x - 1] != Symbols.EXIT)
				return lab[y][x - 1] != Symbols.WALL;
			break;
		// scanning cell to the north
		case UP:
			if (lab[y - 1][x] != Symbols.EXIT)
				return lab[y - 1][x] != Symbols.WALL;
			break;
		default:
			break;
		}

		// if hero is trying to walk to maze exit
		if (hero.hasKilledADragon())
			return true;
		else
			return false;
	}

	/**
	 * Returns true if dragon can walk to the specified direction.
	 * 
	 * @param x
	 *            the dragon X coordinate
	 * @param y
	 *            the dragon Y coordinate
	 * @return <code>true</code> if dragon can walk in the specified direction.
	 */
	public boolean dragonCanWalkTo(int x, int y) {
		return lab[y][x] == Symbols.PATH;
	}

	/**
	 * Gets this labyrinth representation.
	 * 
	 * @return this labyrinth representation
	 */
	public Symbols[][] getLab() {
		return lab;
	}

	/**
	 * Returns this labyrinth width.
	 * 
	 * @return this labyrinth width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets this labyrinth width
	 * 
	 * @param width
	 *            the width to be used
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the height of this labyrinth.
	 * 
	 * @return the height of this labyrinth
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of this labyrinth.
	 * 
	 * @param height
	 *            the height of this labyrinth
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
