package game.logic;

import game.logic.Labyrinth.Symbols;

import java.util.Random;
import java.util.Vector;

public class Dragon extends LivingBeing {
	DragonBehavior behavior = DragonBehavior.MOVING;

	public enum DragonBehavior {
		NOTMOVING, MOVING, MOVINGANDSLEEPING
	}

	// change order according to sprite sheet
	public enum SpriteDirection {
		DOWN, LEFT, RIGHT, UP
	}

	public Dragon(DragonBehavior behavior, Coord coord) {
		this.type = Type.DRAGON;
		this.behavior = behavior;
		position = new Coord(coord);
		prepareSpriteData();
	}

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
		} while (lab.getLab()[position.getY()][position.getX()] != Symbols.PATH
				|| distanceTo(livingBeings.get(0)) < 2);

		prepareSpriteData();
	}

	private void prepareSpriteData() {
		// change this according to sprite sheet
		frames = 4;
		Random r = new Random();
		currentFrame = r.nextInt(frames);
	}

	public void move(Labyrinth lab, Direction direction) {
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

	public String drawToString() {
		if (isSleeping)
			return "d ";
		else
			return "D ";
	}
}
