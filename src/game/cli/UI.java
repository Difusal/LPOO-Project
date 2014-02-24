package game.cli;

import game.logic.Game;

import java.util.Scanner;

public class UI {
	public enum DragonBehavior {
		notMoving, Moving, MovingAndSleeping
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
				// reading maze dimension
				int dimension;
				do {
					System.out.println("Insert an odd labyrinth size (>= 5): ");
					System.out.print("> ");
					dimension = reader.nextInt();
				} while (dimension % 2 == 0 || dimension < 5);
				
				// reading dragon behavior
				/*
				int dragonBehavior;
				do {
					System.out.println("Insert an odd labyrinth size (>= 5): ");
					System.out.print("> ");
					dimension = reader.nextInt();
				} while (dimension % 2 == 0 || dimension < 5);
				*/

				Game.startGame(dimension);
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
