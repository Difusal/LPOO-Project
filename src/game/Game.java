package game;

import java.util.Scanner;

public class Game {
	public static void startGame() {
		Labyrinth lab = new Labyrinth();
		Hero hero = new Hero("Hero");

		Scanner reader = new Scanner(System.in);
		
		boolean done = false;
		while (!done) {
			lab.print(hero);
			
			// reading user input
			System.out.print("> ");
			String dir = reader.next(".");
			hero.move(dir, lab);
			if(lab.getLab()[hero.position.getY()][hero.position.getX()] == 'S')
				done = true;
		}
		
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
