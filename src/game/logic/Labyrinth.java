package game.logic;

public class Labyrinth {
	public char wallChar = 'X';
	public char exitChar = 'S';

	int dimension;
	private char[][] lab;

	public Labyrinth(int dimension, char[][] lab) {
		this.dimension = dimension;
		this.lab = lab;
	}

	public void draw(Hero hero, Sword sword, Dragon dragon) {
		System.out.println();
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				// printing hero
				if (hero.isOn(j, i))
					hero.draw();
				// printing dragon
				else if (dragon.isOn(j, i) && !dragon.isDead()) {
					// if sword is under the dragon
					if (sword.isOn(j, i) && sword.isVisible())
						System.out.print("F ");
					else
						dragon.draw();
				} else if (sword.isOn(j, i) && sword.isVisible())
					// printing sword
					sword.draw();
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

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
}
