package game.logic;

import game.logic.Labyrinth.Symbols;

import java.util.Random;
import java.util.Vector;

/**
 * 
 * Represents a Dragon.
 * 
 * @author Henrique Ferrolho
 * @see LivingBeing
 * 
 */
public class Dragon extends LivingBeing {
	private static final long serialVersionUID = 1L;
	DragonBehavior behavior = DragonBehavior.MOVING;

	/**
	 * Represents a {@link Dragon} behavior
	 * 
	 * @author Henrique Ferrolho
	 * 
	 */
	public enum DragonBehavior {
		NOTMOVING, MOVING, MOVINGANDSLEEPING
	}

	public enum SpriteDirection {
		// change order according to sprite sheet
		DOWN, LEFT, RIGHT, UP
	}

	/**
	 * Constructs and initializes a dragon at the specified position with the
	 * chosen behavior.
	 * 
	 * @param behavior
	 *            the behavior of the Dragon
	 * @param coord
	 *            the position of the Dragon
	 * @see {@link Coord}, {@link DragonBehavior}
	 */
	public Dragon(DragonBehavior behavior, Coord coord) {
		this.type = Type.DRAGON;
		this.behavior = behavior;
		position = new Coord(coord);
		prepareSpriteData();
	}

	/**
	 * Constructs and initializes a dragon somewhere on the labyrinth.
	 * 
	 * @param behavior
	 * @param lab
	 * @param livingBeings
	 * @param sword
	 */
	public Dragon(DragonBehavior behavior, Labyrinth lab,
			Vector<LivingBeing> livingBeings, Sword sword) {
		this.type = Type.DRAGON;
		this.behavior = behavior;

		Random r = new Random();
		do {
			position.setX(r.nextInt(lab.getWidth() - 2) + 1);
			position.setY(r.nextInt(lab.getHeight() - 2) + 1);

			// while dragon is not generated on a free cell
			// or while distance to hero is < 2
			// or while distance to sword is < 2 if behavior is NOTMOVING
		} while (lab.getLab()[position.getY()][position.getX()] != Symbols.PATH
				|| distanceTo(livingBeings.get(0)) < 2
				|| (behavior == DragonBehavior.NOTMOVING && distanceTo(sword) < 2));

		prepareSpriteData();
	}

	/**
	 * Prepares sprite sheet data.
	 */
	private void prepareSpriteData() {
		// change this according to sprite sheet
		frames = 4;
		Random r = new Random();
		currentFrame = r.nextInt(frames);
	}

	/**
	 * Moves dragon in a certain direction.
	 * 
	 * @param lab
	 *            game labyrinth
	 * @param direction
	 *            direction in which dragon should move
	 */
	public void move(Labyrinth lab, Direction direction) {
		// updating facing direction
		facingDir = direction;

		if (behavior != DragonBehavior.NOTMOVING && !isSleeping) {
			// moving dragon randomly
			switch (direction) {
			case UP:
				if (lab.dragonCanWalkTo(getPosition().getX(), getPosition()
						.getY() - 1))
					getPosition().setY(getPosition().getY() - 1);
				break;
			case RIGHT:
				if (lab.dragonCanWalkTo(getPosition().getX() + 1, getPosition()
						.getY()))
					getPosition().setX(getPosition().getX() + 1);
				break;
			case DOWN:
				if (lab.dragonCanWalkTo(getPosition().getX(), getPosition()
						.getY() + 1))
					getPosition().setY(getPosition().getY() + 1);
				break;
			case LEFT:
				if (lab.dragonCanWalkTo(getPosition().getX() - 1, getPosition()
						.getY()))
					getPosition().setX(getPosition().getX() - 1);
				break;
			case NONE:
				break;
			}
		}

		if (behavior == DragonBehavior.MOVINGANDSLEEPING) {
			Random r = new Random();

			// making dragon sleep sometimes
			int sleep = r.nextInt(2);

			if (sleep == 1)
				sleep();
			else
				wakeUp();
		}
	}

	/**
	 * Draws dragon to string.
	 */
	public String drawToString() {
		if (isSleeping)
			return "d ";
		else
			return "D ";
	}
}
