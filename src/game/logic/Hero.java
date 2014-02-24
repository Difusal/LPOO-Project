package game.logic;

import java.util.Random;
import java.util.Scanner;

public class Hero extends LivingBeing {
	private boolean hasSword = false;
	private boolean hasKilledTheDragon = false;

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

	public void draw() {
		if (hasSword)
			System.out.print("A ");
		else
			System.out.print("H ");
	}

	public boolean hasSword() {
		return hasSword;
	}

	public void arm() {
		hasSword = true;
		System.out.println("You have caught the sword.");
	}

	public boolean hasKilledTheDragon() {
		return hasKilledTheDragon;
	}

	public void killedTheDragon() {
		hasKilledTheDragon = true;
		System.out.println("You have killed the Dragon.");
	}

	public void move(Labyrinth lab) {
		boolean validMove = false;
		
		// opening scanner
		Scanner reader = new Scanner(System.in);
		
		// reading user input
		System.out.println();
		System.out.print("Type W/A/S/D to move: ");
		String dir = reader.next(".");
		
		// closing scanner
		//reader.close();
		// TODO why does the previous line cause a runtime exception?

		switch (dir) {
		case "w":
			if (lab.heroCanWalkTo(getPosition().getX(), getPosition().getY() - 1, this)) {
				getPosition().setY(getPosition().getY() - 1);
				validMove = true;
			}
			break;
		case "s":
			if (lab.heroCanWalkTo(getPosition().getX(), getPosition().getY() + 1, this)) {
				getPosition().setY(getPosition().getY() + 1);
				validMove = true;
			}
			break;
		case "a":
			if (lab.heroCanWalkTo(getPosition().getX() - 1, getPosition().getY(), this)) {
				getPosition().setX(getPosition().getX() - 1);
				validMove = true;
			}
			break;
		case "d":
			if (lab.heroCanWalkTo(getPosition().getX() + 1, getPosition().getY(), this)) {
				getPosition().setX(getPosition().getX() + 1);
				validMove = true;
			}
			break;
		}

		if (!validMove)
			System.out.println("Invalid move.");
	}
}
