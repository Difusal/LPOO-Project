package game;

import java.util.Scanner;

public class Game {
	public static void startGame() {
		// opening scanner
		Scanner reader = new Scanner(System.in);

		// reading user input
		int dimension;
		do {
			System.out.println("Insert an odd labyrinth size: ");
			System.out.print("> ");
			dimension = reader.nextInt();
		} while (dimension % 2 == 0);

		// initializing variables
		Labyrinth lab = new Labyrinth(dimension);
		Hero hero = new Hero("Hero");
		Sword sword = new Sword();
		Dragon dragon = new Dragon();

		boolean done = false;
		while (!done) {
			// printing labyrinth
			lab.print(hero, sword, dragon);

			// reading user input
			System.out.println();
			System.out.print("Type W/A/S/D to move: ");
			String dir = reader.next(".");

			// moving hero and dragon
			hero.move(dir, lab);
			dragon.move(lab);

			// checking if player got sword
			if (sword.isVisible()
					&& sword.getPosition().getX() == hero.position.getX()
					&& sword.getPosition().getY() == hero.position.getY()) {
				// hide sword from labyrinth
				sword.hide();

				// arm hero
				hero.arm();
			}

			// if hero is next to a dragon
			if (!dragon.isDead()) {
				if ((Math.abs(hero.position.getX() - dragon.position.getX()) <= 1)
						&& (Math.abs(hero.position.getY()
								- dragon.position.getY()) <= 1)) {
					// if hero has sword
					if (hero.hasSword()) {
						// kill the dragon
						dragon.setLife(0);

						hero.killedTheDragon();
					} else {
						// else kill hero
						hero.setLife(0);
						done = true;
					}
				}
			}

			// if hero is able to step on Exit, game is done
			if (lab.getLab()[hero.position.getY()][hero.position.getX()] == 'S')
				done = true;
		}
		// print labyrinth for the last time
		lab.print(hero, sword, dragon);

		// displaying notification message
		System.out.println();
		if (hero.isDead())
			System.out.println("GAME OVER! You lost.");
		else
			System.out.println("CONGRATULATIONS! You won the game.");

		// closing scanner
		reader.close();
	}

	public static void main(String[] args) {
		// printing main menu
		System.out.println("--------------");
		System.out.println("Labyrinth Game");
		System.out.println("--------------");
		System.out.println();
		System.out.println("1. Play");
		System.out.println("2. Exit");
		System.out.println();

		Scanner reader = new Scanner(System.in);

		boolean done = false;
		while (!done) {
			// reading user input
			System.out.println("Choose what to do:");
			System.out.print("> ");
			int input = reader.nextInt();
			System.out.println();

			switch (input) {
			case 1:
				startGame();
				done = true;
				break;
			case 2:
				System.out.println("Quitting game... Done.");
				done = true;
				break;
			default:
				System.out.println("Invalid input!");
				break;
			}
		}

		reader.close();
	}
}
