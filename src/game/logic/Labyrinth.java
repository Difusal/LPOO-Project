package game.logic;

import game.logic.LivingBeing.Type;

import java.util.Vector;

public class Labyrinth {
	public char wallChar = 'X';
	public char exitChar = 'S';

	int dimension;
	private char[][] lab;

	public Labyrinth(int dimension, char[][] lab) {
		this.dimension = dimension;
		this.lab = lab;
	}

	public void draw(Vector<LivingBeing> livingBeings, Sword sword, Eagle eagle) {
		System.out.println();

		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				boolean somethingWasDrawn = false;

				// drawing hero, dragon or sword
				for (LivingBeing k : livingBeings) {
					// if something is at this position
					if (!k.isDead() && k.isOn(j, i)) {
						// drawing hero or eagle
						if (k.getType() == Type.HERO
								|| (k.getType() == Type.EAGLE && !eagle
										.isWithHero())) {
							k.draw();
							somethingWasDrawn = true;
						}
						// drawing dragon
						else if (k.getType() == Type.DRAGON) {
							// if sword is under the dragon
							if (sword.isVisible() && sword.isOn(j, i))
								System.out.print("F ");
							else
								k.draw();

							somethingWasDrawn = true;
						}

						break;
					}
				}

				// if nothing was drawn yet
				if (!somethingWasDrawn) {
					if (sword.isOn(j, i) && sword.isVisible()) {
						// draw sword
						sword.draw();
						somethingWasDrawn = true;
					}
				}

				// if nothing was drawn yet
				if (!somethingWasDrawn)
					// draw maze
					System.out.print(lab[i][j] + " ");
			}

			System.out.println();
		}
	}

	public boolean heroCanWalk(Direction dir, Hero hero) {
		int x = hero.getPosition().getX();
		int y = hero.getPosition().getY();

		switch (dir) {
		// scanning cell to the right
		case RIGHT:
			if (lab[y][x + 1] != exitChar)
				return lab[y][x + 1] != wallChar;
			break;
		// scanning cell to the south
		case DOWN:
			if (lab[y + 1][x] != exitChar)
				return lab[y + 1][x] != wallChar;
			break;
		// scanning cell to the left
		case LEFT:
			if (lab[y][x - 1] != exitChar)
				return lab[y][x - 1] != wallChar;
			break;
		// scanning cell to the north
		case UP:
			if (lab[y - 1][x] != exitChar)
				return lab[y - 1][x] != wallChar;
			break;
		default:
			break;
		}

		// if hero is trying to walk to maze exit
		if (hero.hasKilledADragon())
			return true;
		else {
			System.out.println("You have to kill a Dragon to exit.");
			return false;
		}
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

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
}
