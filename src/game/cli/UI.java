package game.cli;

import game.logic.Direction;
import game.logic.Dragon.DragonBehavior;
import game.logic.Game;

import java.util.Scanner;

public class UI {
	private static Game game = null;

	private static int waitForUserInput(Scanner reader, String message,
			int min, int max) {
		int input;

		// getting input
		System.out.println();
		do {
			// displaying message
			System.out.println(message);
			System.out.print("> ");

			// reading input
			input = reader.nextInt();
		} while (input < min || input > max);

		return input;
	}

	private static void printGameToConsole() {
		System.out.println(game.getLabyrinth().drawToString(
				game.getLivingBeings(), game.getSword(), game.getEagle()));
	}

	private static void displayMainMenu() {
		// printing main menu
		System.out.println("--------------");
		System.out.println("Labyrinth Game");
		System.out.println("--------------");
		System.out.println();
		System.out.println("1. Play");
		System.out.println("2. Exit");
		System.out.println();
	}

	private static void createGameUI(Scanner reader) {
		String msg;

		// asking which maze to build
		msg = "Choose which maze to play: \n1. Random\n2. Demo";
		int mazeToPlay = waitForUserInput(reader, msg, 1, 2);

		// if random maze was chosen
		int dimension = 10;
		if (mazeToPlay == 1) {
			// asking maze dimension
			System.out.println();
			do {
				System.out.println("Insert an odd labyrinth size (>= 5): ");
				System.out.print("> ");
				dimension = reader.nextInt();
			} while (dimension % 2 == 0 || dimension < 5);
		}

		// asking dragon behavior
		msg = "Choose the dragon(s) behavior: \n1. Stopped\n2. Moving\n3. Moving/Sleeping";
		int behaviorInput = waitForUserInput(reader, msg, 1, 3);
		DragonBehavior dragonBehavior = DragonBehavior.values()[behaviorInput - 1];

		// asking number of dragons to summon
		msg = "How many dragons should appear?";
		int numDragons = waitForUserInput(reader, msg, 1, dimension - 2);

		// creating game
		if (mazeToPlay == 1)
			game = new Game(dimension, dragonBehavior, numDragons);
		else if (mazeToPlay == 2)
			game = new Game(dragonBehavior, numDragons);
	}

	private static Direction getHeroDirection(Scanner reader) {
		String dir;

		// reading user input
		do {
			System.out.print("Type W/A/S/D to move or B to send eagle: ");
			dir = reader.next();
		} while (!dir.equals("w") && !dir.equals("a") && !dir.equals("s")
				&& !dir.equals("d") && !dir.equals("b"));

		Direction direction = Direction.NONE;
		switch (dir) {
		case "d":
			direction = Direction.RIGHT;
			break;
		case "s":
			direction = Direction.DOWN;
			break;
		case "a":
			direction = Direction.LEFT;
			break;
		case "w":
			direction = Direction.UP;
			break;
		// hero sent eagle
		case "b":
			if (game.getHero().hasEagle() && !game.getHero().hasSword())
				game.getHero().sendEagle();
			break;
		default:
			break;
		}

		return direction;
	}

	private static void startGame(Scanner reader) {
		boolean done = false;

		// print labyrinth for the first time
		printGameToConsole();

		while (!done) {
			// getting user input and updating game
			done = game.updateGame(getHeroDirection(reader));

			// printing labyrinth
			printGameToConsole();
		}

		// displaying notification message
		if (game.getHero().isDead()) {
			System.out.println("----------");
			System.out.println("GAME OVER!");
			System.out.println("----------");
			System.out.println("You lost.");
		} else {
			System.out.println("----------------");
			System.out.println("CONGRATULATIONS!");
			System.out.println("----------------");
			System.out.println("You won the game.");
		}
		System.out.println();
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

			switch (input) {
			case 1:
				// getting info about game
				createGameUI(reader);

				// starting game
				startGame(reader);

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

		// closing scanner
		reader.close();
	}
}
