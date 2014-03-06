package game.logic;

import game.logic.Labyrinth.Symbols;

import java.util.Random;

public class Hero extends LivingBeing {
	private boolean hasSword = false;
	private boolean hasEagle = true;
	private boolean hasJustSentEagle = false;
	private boolean hasKilledADragon = false;

	public Hero(String name, Labyrinth lab) {
		this.name = name;
		this.type = Type.HERO;

		Random r = new Random();
		do {
			position.setX(r.nextInt(lab.getDimension() - 2) + 1);
			position.setY(r.nextInt(lab.getDimension() - 2) + 1);
		} while (lab.getLab()[position.getY()][position.getX()] != Symbols.PATH);
	}

	public Hero(String name, Coord position) {
		this.name = name;
		this.type = Type.HERO;
		this.position = position;
	}

	public void move(Labyrinth lab, Direction direction) {
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

	public String drawToString() {
		if (hasSword)
			return "A ";
		else
			return "H ";
	}

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

	public boolean hasSword() {
		return hasSword;
	}

	public void arm() {
		hasSword = true;
	}

	public boolean hasEagle() {
		return hasEagle;
	}

	public boolean hasJustSentEagle() {
		return hasJustSentEagle;
	}

	public void resetHasJustSentEagleValue() {
		hasJustSentEagle = false;
	}

	public void sendEagle() {
		hasEagle = false;
		hasJustSentEagle = true;
	}

	public void catchEagle(Eagle eagle) {
		hasEagle = true;
		eagle.setWithHero(true);

		if (eagle.hasSword())
			arm();

		eagle.setHasSword(false);
	}

	public boolean hasKilledADragon() {
		return hasKilledADragon;
	}

	public void killedADragon() {
		hasKilledADragon = true;
	}
}
