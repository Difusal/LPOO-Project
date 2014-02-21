package game;

import java.util.Random;
import java.util.Stack;

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
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (i % 2 != 0 && j % 2 != 0)
					lab[i][j] = ' ';
				else
					lab[i][j] = wallChar;
			}
		}

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

		// preparing matrix for maze generation
		for (int i = 0; i < visitedCells.length; i++)
			for (int j = 0; j < visitedCells[i].length; j++)
				visitedCells[i][j] = false;

		// preparing data for maze generation
		Coord cellNextToExit = new Coord(exitX, exitY);
		if (exitX == 0)
			cellNextToExit.setX(cellNextToExit.getX() + 1);
		else if (exitX == dimension - 1)
			cellNextToExit.setX(cellNextToExit.getX() - 1);
		else if (exitY == 0)
			cellNextToExit.setY(cellNextToExit.getY() + 1);
		else
			cellNextToExit.setY(cellNextToExit.getY() - 1);

		int guideCellX = (cellNextToExit.getX() - 1) / 2;
		int guideCellY = (cellNextToExit.getY() - 1) / 2;
		Coord guideCell = new Coord(guideCellX, guideCellY);

		visitedCells[guideCell.getY()][guideCell.getX()] = true;
		Stack<Coord> pathHistory = new Stack<Coord>();
		pathHistory.push(new Coord(guideCell.getX(), guideCell.getY()));

		// running maze generation algorithm
		while (!pathHistory.empty()) {
			// if all neighbors of guideCell have been visited
			if ((guideCell.getX() + 1 >= (dimension - 1) / 2 || visitedCells[guideCell
					.getY()][guideCell.getX() + 1] == true)
					&& (guideCell.getX() - 1 < 0 || visitedCells[guideCell
							.getY()][guideCell.getX() - 1] == true)
					&& (guideCell.getY() + 1 >= (dimension - 1) / 2 || visitedCells[guideCell
							.getY() + 1][guideCell.getX()] == true)
					&& (guideCell.getY() - 1 < 0 || visitedCells[guideCell
							.getY() - 1][guideCell.getX()] == true)) {
				// pop cell from history
				pathHistory.pop();

				// if path history is empty, terminate algorithm
				if (pathHistory.empty())
					break;
				else
					guideCell = pathHistory.peek();
			}

			// generating a new path
			switch (r.nextInt(4)) {
			// up
			case 0:
				if (guideCell.getY() - 1 >= 0
						&& visitedCells[guideCell.getY() - 1][guideCell.getX()] == false) {
					// updating maze
					lab[guideCell.getY() * 2][guideCell.getX() * 2 + 1] = ' ';

					// updating guideCell position
					guideCell.setY(guideCell.getY() - 1);
					pathHistory.push(new Coord(guideCell.getX(), guideCell
							.getY()));

					// marking cell as visited
					visitedCells[guideCell.getY()][guideCell.getX()] = true;
				}
				break;
			// right
			case 1:
				if (guideCell.getX() + 1 < (dimension - 1) / 2
						&& visitedCells[guideCell.getY()][guideCell.getX() + 1] == false) {
					// updating maze
					lab[guideCell.getY() * 2 + 1][(guideCell.getX() + 1) * 2] = ' ';

					// updating guideCell position
					guideCell.setX(guideCell.getX() + 1);
					pathHistory.push(new Coord(guideCell.getX(), guideCell
							.getY()));

					// marking cell as visited
					visitedCells[guideCell.getY()][guideCell.getX()] = true;
				}
				break;
			// down
			case 2:
				if (guideCell.getY() + 1 < (dimension - 1) / 2
						&& visitedCells[guideCell.getY() + 1][guideCell.getX()] == false) {
					// updating maze
					lab[(guideCell.getY() + 1) * 2][guideCell.getX() * 2 + 1] = ' ';

					// updating guideCell position
					guideCell.setY(guideCell.getY() + 1);
					pathHistory.push(new Coord(guideCell.getX(), guideCell
							.getY()));

					// marking cell as visited
					visitedCells[guideCell.getY()][guideCell.getX()] = true;
				}
				break;
			// left
			case 3:
				if (guideCell.getX() - 1 >= 0
						&& visitedCells[guideCell.getY()][guideCell.getX() - 1] == false) {
					// updating maze
					lab[guideCell.getY() * 2 + 1][guideCell.getX() * 2] = ' ';

					// updating guideCell position
					guideCell.setX(guideCell.getX() - 1);
					pathHistory.push(new Coord(guideCell.getX(), guideCell
							.getY()));

					// marking cell as visited
					visitedCells[guideCell.getY()][guideCell.getX()] = true;
				}
				break;
			}
		}
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

	public int getDimension() {
		return dimension;
	}
}
