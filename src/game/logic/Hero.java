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
			getPosition().setX(r.nextInt(lab.getDimension() - 2) + 1);
		} while (getPosition().getX() % 2 == 0);
		do {
			getPosition().setY(r.nextInt(lab.getDimension() - 2) + 1);
		} while (getPosition().getY() % 2 == 0);
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
		}
	}

	public void move(Labyrinth lab) {
		boolean validMove = false;

		// resetting hasJustSentEagle value
		hasJustSentEagle = false;

		// opening scanner
		Scanner reader = new Scanner(System.in);

		// reading user input
		System.out.println();
		System.out.print("Type W/A/S/D/B to move: ");
		String dir = reader.next(".");

		// closing scanner
		// reader.close();
		// TODO why does the previous line cause a runtime exception?

		Direction direction = Direction.NONE;
		switch (dir) {
		// right
		case "d":
			direction = Direction.RIGHT;
			validMove = true;
			break;
		// down
		case "s":
			direction = Direction.DOWN;
			validMove = true;
			break;
		// left
		case "a":
			direction = Direction.LEFT;
			validMove = true;
			break;
		// up
		case "w":
			direction = Direction.UP;
			validMove = true;
			break;
		// hero sent eagle
		case "b":
			if (hasEagle && !hasSword) {
				sendEagle();

				validMove = true;
			}
			break;
		}

		if (direction != Direction.NONE && lab.heroCanWalk(direction, this))
			go(direction);

		if (!validMove)
			System.out.println("Invalid move.");
	}

	public void draw() {
		if (hasSword)
			System.out.print("A ");
		else
			System.out.print("H ");
	}
}
