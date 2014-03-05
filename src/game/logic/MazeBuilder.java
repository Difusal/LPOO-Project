package game.logic;

import java.util.Random;
import java.util.Stack;

public class MazeBuilder {
	private char[][] maze;
	private boolean[][] visitedCells;
	private Stack<Coord> pathHistory;
	private Coord guideCell;
	private Coord exit;

	public char wallChar = 'X';
	public char exitChar = 'S';

	// demo labyrinth builder
	public Labyrinth Build() {
		maze = new char[][] {
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		return new Labyrinth(10, maze);
	}

	// random labyrinth builder
	public Labyrinth Build(int dimension) {
		Random r = new Random();
		maze = new char[dimension][dimension];
		visitedCells = new boolean[dimension - 2][dimension - 2];

		// filling maze with 'X'
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (i % 2 != 0 && j % 2 != 0)
					maze[i][j] = ' ';
				else
					maze[i][j] = wallChar;
			}
		}

		// preparing visitedCells
		for (int i = 0; i < visitedCells.length; i++)
			for (int j = 0; j < visitedCells[i].length; j++)
				visitedCells[i][j] = false;

		generateExit(r);
		initializeGuideCell();
		markGuideCellPositionAsVisited();

		pathHistory = new Stack<Coord>();
		addGuideCellPositionToStack();

		// running maze generation algorithm
		while (!pathHistory.empty()) {
			while (!guideCellCanGoSomewhere()) {
				// pop cell from history
				pathHistory.pop();

				// if path history is empty, terminate algorithm
				if (pathHistory.empty())
					break;
				else
					guideCell = pathHistory.peek();
			}
			// if path history is empty, terminate algorithm
			if (pathHistory.empty())
				break;

			Direction direction;
			do {
				direction = Direction.values()[r.nextInt(4)];
			} while (!guideCellCanMove(direction));

			// updating maze
			switch (direction) {
			case UP:
				maze[guideCell.getY() * 2][guideCell.getX() * 2 + 1] = ' ';
				break;
			case RIGHT:
				maze[guideCell.getY() * 2 + 1][(guideCell.getX() + 1) * 2] = ' ';
				break;
			case DOWN:
				maze[(guideCell.getY() + 1) * 2][guideCell.getX() * 2 + 1] = ' ';
				break;
			case LEFT:
				maze[guideCell.getY() * 2 + 1][guideCell.getX() * 2] = ' ';
				break;
			case NONE:
				break;
			}

			moveGuideCell(direction);
			markGuideCellPositionAsVisited();
			addGuideCellPositionToStack();
		}

		// creating labyrinth
		return new Labyrinth(dimension, maze);
	}

	private void generateExit(Random r) {
		exit = new Coord();

		// generating exit distance to corner
		int exitZ;
		do {
			exitZ = r.nextInt(maze.length - 2) + 1;
		} while (exitZ % 2 == 0);

		// getting random border to place exit at
		switch (r.nextInt(4)) {
		// top
		case 0:
			exit.setX(exitZ);
			break;
		// right
		case 1:
			exit.setX(maze.length - 1);
			exit.setY(exitZ);
			break;
		// bottom
		case 2:
			exit.setX(exitZ);
			exit.setY(maze.length - 1);
			break;
		// left
		case 3:
			exit.setY(exitZ);
			break;
		default:
			exit.setX(1);
			break;
		}

		// placing exit character at exit
		maze[exit.getY()][exit.getX()] = exitChar;
	}

	private void initializeGuideCell() {
		// preparing data for maze generation
		Coord cellNextToExit = new Coord(exit.getX(), exit.getY());
		if (exit.getX() == 0)
			cellNextToExit.setX(cellNextToExit.getX() + 1);
		else if (exit.getX() == maze.length - 1)
			cellNextToExit.setX(cellNextToExit.getX() - 1);
		else if (exit.getY() == 0)
			cellNextToExit.setY(cellNextToExit.getY() + 1);
		else
			cellNextToExit.setY(cellNextToExit.getY() - 1);

		int guideCellX = (cellNextToExit.getX() - 1) / 2;
		int guideCellY = (cellNextToExit.getY() - 1) / 2;

		guideCell = new Coord(guideCellX, guideCellY);
	}

	private boolean cellNextToGuideCellHasBeenVisited(Direction direction) {
		switch (direction) {
		case RIGHT:
			return visitedCells[guideCell.getY()][guideCell.getX() + 1];
		case DOWN:
			return visitedCells[guideCell.getY() + 1][guideCell.getX()];
		case LEFT:
			return visitedCells[guideCell.getY()][guideCell.getX() - 1];
		case UP:
			return visitedCells[guideCell.getY() - 1][guideCell.getX()];
		default:
			break;
		}

		return false;
	}

	private boolean guideCellCanMove(Direction direction) {
		// checking if trying to move outside maze borders
		switch (direction) {
		case RIGHT:
			if (guideCell.getX() + 1 >= (maze.length - 1) / 2)
				return false;
			break;
		case DOWN:
			if (guideCell.getY() + 1 >= (maze.length - 1) / 2)
				return false;
			break;
		case LEFT:
			if (guideCell.getX() - 1 < 0)
				return false;
			break;
		case UP:
			if (guideCell.getY() - 1 < 0)
				return false;
			break;
		default:
			break;
		}

		return !cellNextToGuideCellHasBeenVisited(direction);
	}

	private boolean guideCellCanGoSomewhere() {
		boolean guideCellCanGoSomewhere = false;

		for (int i = 0; i < 4; i++)
			if (guideCellCanMove(Direction.values()[i]))
				guideCellCanGoSomewhere = true;

		return guideCellCanGoSomewhere;
	}

	private void moveGuideCell(Direction direction) {
		switch (direction) {
		case RIGHT:
			guideCell.setX(guideCell.getX() + 1);
			break;
		case DOWN:
			guideCell.setY(guideCell.getY() + 1);
			break;
		case LEFT:
			guideCell.setX(guideCell.getX() - 1);
			break;
		case UP:
			guideCell.setY(guideCell.getY() - 1);
			break;
		default:
			break;
		}
	}

	private void markGuideCellPositionAsVisited() {
		visitedCells[guideCell.getY()][guideCell.getX()] = true;
	}

	private void addGuideCellPositionToStack() {
		pathHistory.push(new Coord(guideCell.getX(), guideCell.getY()));
	}
}
