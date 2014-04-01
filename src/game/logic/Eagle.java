package game.logic;

import java.util.Random;
import java.util.Vector;

/**
 * 
 * Represents a Eagle.
 * 
 * @author Henrique Ferrolho
 * @see LivingBeing
 * 
 */
public class Eagle extends LivingBeing {
	private static final long serialVersionUID = 1L;
	private boolean flying = false;
	private boolean withHero = true;
	private boolean hasSword = false;
	private FlightState state;
	private Coord src, dest;

	/**
	 * Represents an eagle flight state.
	 * 
	 */
	public enum FlightState {
		GOING, RETURNING
	}

	// change order according to sprite sheet
	public enum SpriteDirection {
		DOWN, LEFT, RIGHT, UP
	}

	/**
	 * Constructs and initializes an eagle.
	 */
	public Eagle() {
		type = Type.EAGLE;
		prepareSpriteData();
	}

	private void prepareSpriteData() {
		// change this according to sprite sheet
		frames = 4;
		Random r = new Random();
		currentFrame = r.nextInt(frames);
	}

	/**
	 * Starts eagle flight and calculates shortest path to catch sword.
	 * 
	 * @param lab
	 * @param sword
	 */
	public void startFlight(Labyrinth lab, Sword sword) {
		// preparing variables
		flying = true;
		withHero = false;
		state = FlightState.GOING;

		src = new Coord(position);
		dest = new Coord(sword.getPosition());

		// moving eagle
		move(lab, Direction.NONE);
	}

	/**
	 * Moves eagle in a certain direction or along the shortest path to catch
	 * the sword.
	 * 
	 * @param lab
	 *            labyrinth
	 * @param direction
	 *            to move eagle
	 * 
	 */
	public void move(Labyrinth lab, Direction direction) {
		// if eagle is moving
		if (flying) {
			// saving previous position to know which direction is facing
			int prevX = position.getX();
			int prevY = position.getY();

			Coord goal = src;
			if (state == FlightState.GOING) {
				goal = dest;
			}

			// if dx > dy
			if (Math.abs(goal.getX() - position.getX()) > Math.abs(goal.getY()
					- position.getY())) {
				// if eagleX < swordX
				if (position.getX() < goal.getX())
					position.setX(position.getX() + 1);
				// if eagleX >= swordX
				else
					position.setX(position.getX() - 1);
			}
			// if dy >= dx
			else {
				// if eagleY < swordY
				if (position.getY() < goal.getY())
					position.setY(position.getY() + 1);
				// if eagleY >= swordY
				else
					position.setY(position.getY() - 1);
			}

			// if sword reached
			if (state == FlightState.GOING && dest.equals(position)) {
				// inverting flight direction
				state = FlightState.RETURNING;

				// catching sword
				hasSword = true;
			}

			// if origin reached
			if (state == FlightState.RETURNING && position.equals(src))
				flying = false;

			// updating sprite
			if (position.getY() > prevY)
				facingDir = Direction.DOWN;
			else if (position.getY() < prevY)
				facingDir = Direction.UP;
			else if (position.getX() > prevX)
				facingDir = Direction.RIGHT;
			else if (position.getX() < prevX)
				facingDir = Direction.LEFT;
		}
	}

	/**
	 * Updates eagle: checks if sword was caught, if eagle was killed by any
	 * dragon or if eagle was caught by hero.
	 * 
	 * @param lab
	 *            game labyrinth
	 * @param livingBeings
	 *            container with game living beings
	 * @param hero
	 *            game hero
	 * @param sword
	 *            game sword
	 */
	public void update(Labyrinth lab, Vector<LivingBeing> livingBeings,
			Hero hero, Sword sword) {
		// if eagle got the sword
		if (!isDead() && sword.isVisible() && hasSword())
			// hide sword from labyrinth
			sword.hide();

		// if dragon nearby when waiting for hero or catching sword
		if (!isDead() && ((!flying && !withHero) || isCatchingSword())) {
			for (LivingBeing i : livingBeings) {
				// skipping if comparing to something other than a dragon
				if (i.getType() != Type.DRAGON)
					continue;

				// if dragon is not dead nor sleeping
				if (!i.isDead() && !i.isSleeping) {
					if (distanceTo(i) <= 1) {
						// sword is dropped
						sword.setPosition(new Coord(getPosition()));
						sword.show();

						// eagle dies
						setLife(0);
					}
				}
			}
		}

		// updating eagle position if it is on hero's shoulder
		if (hero.hasEagle()) {
			setPosition(new Coord(hero.getPosition()));
			facingDir = hero.getFacingDir();
		}

		// if hero sent eagle
		if (hero.hasJustSentEagle()) {
			startFlight(lab, sword);
			hero.resetHasJustSentEagleValue();
		}

		// checking if hero caught eagle
		if (hero.getPosition().equals(position) && !hero.hasEagle()
				&& !isFlying() && !isDead())
			hero.catchEagle(this);
	}

	/**
	 * Draws eagle to string.
	 */
	@Override
	public String toString() {
		if (flying || !withHero) {
			if (hasSword)
				return "BE";
			else
				return "B ";
		}

		return "";
	}

	/**
	 * Returns true if eagle is flying and false otherwise.
	 * 
	 * @return <code>true<code> if eagle is flying, <code>false<code> otherwise.
	 */
	public boolean isFlying() {
		return flying;
	}

	/**
	 * Returns true if eagle is with hero; false otherwise.
	 * 
	 * @return <code>true<code> if eagle is with hero; <code>false<code> otherwise.
	 */
	public boolean isWithHero() {
		return withHero;
	}

	public void setWithHero(boolean withHero) {
		this.withHero = withHero;
	}

	/**
	 * Returns true if eagle has sword; false otherwise.
	 * 
	 * @return <code>true<code> if eagle has sword; <code>false<code> otherwise.
	 */
	public boolean hasSword() {
		return hasSword;
	}

	public void setHasSword(boolean hasSword) {
		this.hasSword = hasSword;
	}

	/**
	 * Returns true if eagle is catching the sword; false otherwise.
	 * 
	 * @return <code>true<code> if eagle is catching the sword; <code>false<code> otherwise.
	 */
	public boolean isCatchingSword() {
		return flying && dest.equals(position);
	}
}
