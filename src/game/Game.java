package game;

import java.util.Scanner;

public class Game {
	public static void startGame() {
		Labyrinth lab = new Labyrinth();
		Hero hero = new Hero("Hero");
		Sword sword = new Sword();
		Dragon dragon = new Dragon();

		Scanner reader = new Scanner(System.in);

		boolean done = false;
		while (!done) {
			lab.print(hero, sword, dragon);

			// reading user input
			System.out.print("> ");
			String dir = reader.next(".");
			System.out.println();

			// moving hero and dragon
			hero.move(dir, lab);
			dragon.move(lab);

			// checking if player got sword
			if (sword.isVisible()
					&& sword.getPosition().getX() == hero.position.getX()
					&& sword.getPosition().getY() == hero.position.getY()) {
				sword.hide();
				hero.arm();
			}

			// if player is next to dragon
			if ((Math.abs(hero.position.getX() - dragon.position.getX()) <= 1)
					&& (Math.abs(hero.position.getY() - dragon.position.getY()) <= 1)) {
				// if player has sword
				if (hero.hasSword())
					dragon.setLife(0);
				else {
					hero.setLife(0);
					done = true;
				}
			}

			if (lab.getLab()[hero.position.getY()][hero.position.getX()] == 'S')
				done = true;
		}
		lab.print(hero, sword, dragon);

		if (hero.isDead())
			System.out.println("You lost.");
		else
			System.out.println("PARABÉNS! Ganhou o jogo.");
	}

	public static void main(String[] args) {
		System.out.println("--------------");
		System.out.println("Labyrinth Game");
		System.out.println("--------------");
		System.out.println();
		System.out.println("1. Play");
		System.out.println("2. Exit");
		System.out.println();

		boolean done = false;
		while (!done) {
			// reading user input
			Scanner reader = new Scanner(System.in);
			System.out.print("> ");
			int input = reader.nextInt();

			switch (input) {
			case 1:
				System.out.println();
				System.out.println("Loading game...");
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
	}
}
