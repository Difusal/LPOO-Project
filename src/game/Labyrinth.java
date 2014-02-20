package game;

import java.util.Random;

public class Labyrinth {
	public char wallChar = 'X';
	public char exitChar = 'S';

	int dimension;
	private char[][] lab;
	private boolean[][] visitedCells;

	public Labyrinth(int dimension) {
		this.dimension = dimension;
		lab = new char[dimension][dimension];
		visitedCells = new boolean[dimension - 2][dimension - 2];

		// filling labyrinth with X
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				lab[i][j] = wallChar;

		// preparing matrix for maze generation
		for (int i = 0; i < visitedCells.length; i++)
			for (int j = 0; j < visitedCells[i].length; j++)
				visitedCells[i][j] = false;

		// generating exit
		Random r = new Random();
		int exitX, exitY, exitZ;
		do {
			exitZ = r.nextInt(dimension - 2) + 1;
		} while (exitZ % 2 == 0);
		int exitSide = r.nextInt(4);
		switch (exitSide) {
		// top
		case 0:
			exitX = exitZ;
			exitY = 0;
			break;
		// right
		case 1:
			exitX = dimension - 1;
			exitY = exitZ;
			break;
		// bottom
		case 2:
			exitX = exitZ;
			exitY = dimension - 1;
			break;
		// left
		case 3:
			exitX = 0;
			exitY = exitZ;
			break;
		default:
			exitX = 1;
			exitY = 0;
			break;
		}
		lab[exitY][exitX] = exitChar;

		// running maze generation algorithm

	}

	public void print(Hero hero, Sword sword, Dragon dragon) {
		System.out.println();
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				// printing hero
				if (hero.isOn(j, i))
					hero.print();
				// printing dragon
				else if (dragon.isOn(j, i) && !dragon.isDead()) {
					// if sword is under the dragon
					if (sword.isOn(j, i) && sword.isVisible())
						System.out.print("F ");
					else
						dragon.print();
				} else if (sword.isOn(j, i) && sword.isVisible())
					// printing sword
					sword.print();
				else
					System.out.print(lab[i][j] + " ");
			}

			System.out.println();
		}
	}

	public boolean heroCanWalkTo(int x, int y, Hero hero) {
		if (lab[y][x] == exitChar) {
			if (hero.hasKilledTheDragon())
				return true;
			else {
				System.out.println("You have to kill the Dragon to exit.");
				return false;
			}
		} else
			return lab[y][x] != wallChar;
	}

	public boolean dragonCanWalkTo(int x, int y) {
		return lab[y][x] == ' ';
	}

	public char[][] getLab() {
		return lab;
	}
}
