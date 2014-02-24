package game.logic;

import java.util.Scanner;

public class Game {
	public static void startGame(int dimension) {
		// opening scanner
		Scanner reader = new Scanner(System.in);

		// initializing variables
		Labyrinth lab = MazeBuilder.Build(dimension);
		Hero hero = new Hero("Hero", lab);
		Sword sword = new Sword(lab, hero);
		Dragon dragon = new Dragon(lab, hero, sword);

		boolean done = false;
		while (!done) {
			// printing labyrinth
			lab.draw(hero, sword, dragon);

			// reading user input
			System.out.println();
			System.out.print("Type W/A/S/D to move: ");
			String dir = reader.next(".");

			// moving hero and dragon
			hero.move(dir, lab);
			dragon.move(lab);

			// checking if player got sword
			if (sword.isVisible()
					&& sword.getPosition().getX() == hero.getPosition().getX()
					&& sword.getPosition().getY() == hero.getPosition().getY()) {
				// hide sword from labyrinth
				sword.hide();

				// arm hero
				hero.arm();
			}

			// if hero is next to a dragon
			if (!dragon.isDead()) {
				if ((Math.abs(hero.getPosition().getX() - dragon.getPosition().getX()) <= 1)
						&& (Math.abs(hero.getPosition().getY()
								- dragon.getPosition().getY()) <= 1)) {
					// if hero has sword
					if (hero.hasSword()) {
						// kill the dragon
						dragon.setLife(0);
						hero.killedTheDragon();
					} else if (!dragon.isSleeping()) {
						// else kill hero
						hero.setLife(0);
						done = true;
					}
				}
			}

			// if hero is able to step on Exit, game is done
			if (lab.getLab()[hero.getPosition().getY()][hero.getPosition().getX()] == 'S')
				done = true;
		}
		// print labyrinth for the last time
		lab.draw(hero, sword, dragon);

		// displaying notification message
		System.out.println();
		if (hero.isDead())
			System.out.println("GAME OVER! You lost.");
		else
			System.out.println("CONGRATULATIONS! You won the game.");

		// closing scanner
		reader.close();
	}
}
