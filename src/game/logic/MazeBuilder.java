package game.logic;

import java.util.Random;
import java.util.Stack;

public class MazeBuilder {
	public static char wallChar = 'X';
	public static char exitChar = 'S';

	public static Labyrinth Build(int dimension) {
		char[][] maze = new char[dimension][dimension];
		boolean[][] visitedCells = new boolean[dimension - 2][dimension - 2];

		// filling maze with 'X'
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (i % 2 != 0 && j % 2 != 0)
					maze[i][j] = ' ';
				else
					maze[i][j] = wallChar;
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
		maze[exitY][exitX] = exitChar;

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
					maze[guideCell.getY() * 2][guideCell.getX() * 2 + 1] = ' ';

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
					maze[guideCell.getY() * 2 + 1][(guideCell.getX() + 1) * 2] = ' ';

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
					maze[(guideCell.getY() + 1) * 2][guideCell.getX() * 2 + 1] = ' ';

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
					maze[guideCell.getY() * 2 + 1][guideCell.getX() * 2] = ' ';

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

		// creating labyrinth
		return new Labyrinth(dimension, maze);
	}
}
