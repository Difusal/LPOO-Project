package game.logic;

import game.logic.Labyrinth.Symbols;

import java.util.Random;

/**
 * A living being representing a hero.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class Hero extends LivingBeing {
	private static final long serialVersionUID = 1L;
	private boolean hasSword = false;
	private boolean hasEagle = true;
	private boolean hasJustSentEagle = false;
	private boolean hasKilledADragon = false;

	// change order according to sprite sheet
	public enum SpriteDirection {
		DOWN, LEFT, RIGHT, UP
	}

	/**
	 * Constructs and initializes a hero with the specified name.
	 * 
	 * @param name
	 *            the hero's name
	 */
	public Hero(String name) {
		this.name = name;
		this.type = Type.HERO;

		prepareSpriteData();
	}

	/**
	 * Constructs and initializes a hero with the specified name on the
	 * specified labyrinth.
	 * 
	 * @param name
	 *            the hero's name
	 * @param lab
	 *            the game labyrinth
	 */
	public Hero(String name, Labyrinth lab) {
		this.name = name;
		this.type = Type.HERO;

		Random r = new Random();
		do {
			position.setX(r.nextInt(lab.getWidth() - 2) + 1);
			position.setY(r.nextInt(lab.getHeight() - 2) + 1);
		} while (lab.getLab()[position.getY()][position.getX()] != Symbols.PATH);

		prepareSpriteData();
	}

	/**
	 * Constructs and initialized a hero.
	 * 
	 * @param name
	 *            this hero's name
	 * @param poition
	 *            this hero's position
	 */
	public Hero(String name, Coord position) {
		this.name = name;
		this.type = Type.HERO;
		this.position = position;

		prepareSpriteData();
	}

	/**
	 * Prepares sprite sheet data.
	 */
	private void prepareSpriteData() {
		// change this according to sprite sheet
		frames = 4;
		currentFrame = 0;
	}

	/**
	 * Moves this hero to the specified direction if possible.
	 * 
	 * @param lab
	 *            game labyrinth
	 * @param direction
	 *            direction in which to move hero
	 */
	public void move(Labyrinth lab, Direction direction) {
		// updating direction hero is facing
		if (direction != Direction.NONE)
			facingDir = direction;

		// if hero can go to direction, do go to that direction
		if (direction != Direction.NONE && lab.heroCanWalk(direction, this)) {
			switch (direction) {
			case RIGHT:
				position.setX(position.getX() + 1);
				break;
			case DOWN:
				position.setY(position.getY() + 1);
				break;
			case LEFT:
				position.setX(position.getX() - 1);
				break;
			case UP:
				position.setY(position.getY() - 1);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Prints hero to string.
	 */
	public String toString() {
		if (hasSword)
			return "A ";
		else
			return "H ";
	}

	/**
	 * Catches sword if possible.
	 * 
	 * @param sword
	 *            game sword
	 * @return <code>true</code> if sword was caught
	 */
	public boolean catchSwordIfPossible(Sword sword) {
		if (sword.isVisible() && sword.getPosition().equals(position)) {
			// hide sword from labyrinth
			sword.hide();

			// arm hero
			arm();

			return true;
		}

		return false;
	}

	/**
	 * Returns true if hero has sword.
	 * 
	 * @return <code>true</code> if hero has sword
	 */
	public boolean hasSword() {
		return hasSword;
	}

	/**
	 * Arms hero.
	 */
	public void arm() {
		hasSword = true;
	}

	/**
	 * Returns true if hero has eagle.
	 * 
	 * @return <code>true</code> if hero has eagle
	 */
	public boolean hasEagle() {
		return hasEagle;
	}

	/**
	 * Returns true if hero has just sent the eagle.
	 * 
	 * @return <code>true</code> if hero has just sent the eagle
	 */
	public boolean hasJustSentEagle() {
		return hasJustSentEagle;
	}

	/**
	 * Resets hasJustSentEagle to <code>false</code>
	 */
	public void resetHasJustSentEagleValue() {
		hasJustSentEagle = false;
	}

	/**
	 * Sends eagle.
	 */
	public void sendEagle() {
		hasEagle = false;
		hasJustSentEagle = true;
	}

	/**
	 * Catches the eagle.
	 * 
	 * @param eagle
	 *            the eagle
	 */
	public void catchEagle(Eagle eagle) {
		hasEagle = true;
		eagle.setWithHero(true);

		if (eagle.hasSword())
			arm();

		eagle.setHasSword(false);
	}

	/**
	 * Returns true if hero has killed at least one dragon.
	 * 
	 * @return <code>true</code> if hero has killed at least one dragon
	 */
	public boolean hasKilledADragon() {
		return hasKilledADragon;
	}

	/**
	 * Sets hasKilledADragon to <code>true</code>.
	 */
	public void killedADragon() {
		hasKilledADragon = true;
	}
}
