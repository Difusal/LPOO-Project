package game.logic;

import java.util.Random;
import java.util.Scanner;

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
		} while (lab.getLab()[position.getY()][position.getX()] != ' ');
	}

	public boolean hasSword() {
		return hasSword;
	}

	public void arm() {
		hasSword = true;
		System.out.println("You have caught the sword.");
	}

	public boolean hasEagle() {
		return hasEagle;
	}

	public boolean hasJustSentEagle() {
		return hasJustSentEagle;
	}

	public void sendEagle() {
		System.out.println("Eagle was sent.");
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
		System.out.println("You have killed the Dragon.");
	}

	public void go(Direction dir) {
		switch (dir) {
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

	public void move(Labyrinth lab) {
		// resetting hasJustSentEagle value
		hasJustSentEagle = false;

		// opening scanner
		Scanner reader = new Scanner(System.in);

		// reading user input
		String dir;
		System.out.println();
		do {
			System.out.print("Type W/A/S/D/B to move/send eagle: ");
			dir = reader.next();
		} while (dir.length() != 1);

		// closing scanner
		// reader.close();
		// TODO why does the previous line cause a runtime exception?

		boolean validMove = true;
		Direction direction = Direction.NONE;

		switch (dir) {
		// right
		case "d":
			direction = Direction.RIGHT;
			break;
		// down
		case "s":
			direction = Direction.DOWN;
			break;
		// left
		case "a":
			direction = Direction.LEFT;
			break;
		// up
		case "w":
			direction = Direction.UP;
			break;
		// hero sent eagle
		case "b":
			if (hasEagle && !hasSword)
				sendEagle();
			break;
		default:
			validMove = false;
			break;
		}

		// if hero can go to direction, do go to that direction
		if (direction != Direction.NONE && lab.heroCanWalk(direction, this))
			go(direction);

		// if invalid input detected
		if (!validMove)
			System.out.println("Invalid input.");
	}

	public void draw() {
		if (hasSword)
			System.out.print("A ");
		else
			System.out.print("H ");
	}
}
