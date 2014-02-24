package game.cli;

import game.logic.Dragon.DragonBehavior;
import game.logic.Game;

import java.util.Scanner;

public class UI {
	public static void displayMainMenu() {
		// printing main menu
		System.out.println("--------------");
		System.out.println("Labyrinth Game");
		System.out.println("--------------");
		System.out.println();
		System.out.println("1. Play");
		System.out.println("2. Exit");
		System.out.println();
	}

	public static int waitForUserInput(String message, int min, int max) {
		int input;

		// opening scanner
		Scanner reader = new Scanner(System.in);

		// getting input
		do {
			// displaying message
			System.out.println(message);
			System.out.print("> ");

			// reading input
			input = reader.nextInt();
		} while (input < min || input > max);
		System.out.println();
		
		// closing scanner
		//reader.close();
		// TODO why does the previous line cause a runtime exception?

		return input;
	}

	public static void main(String[] args) {
		displayMainMenu();

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
				// reading maze dimension
				int dimension;
				do {
					System.out.println("Insert an odd labyrinth size (>= 5): ");
					System.out.print("> ");
					dimension = reader.nextInt();
				} while (dimension % 2 == 0 || dimension < 5);
				System.out.println();

				// reading dragon behavior
				int behaviorInput = waitForUserInput(
						"Choose the dragon(s) behavior: \n1. Stopped\n2. Moving\n3. Moving/Sleeping",
						1, 3);
				DragonBehavior dragonBehavior = DragonBehavior.values()[behaviorInput - 1];

				// starting game
				System.out.println("Dbeh: " + dragonBehavior);
				Game.startGame(dimension, dragonBehavior);
				
				done = true;
				break;
			case 2:
				System.out.println("Quitting game.");
				
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
