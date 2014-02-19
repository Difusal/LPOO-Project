package game;

public class Labyrinth {
	int dimension = 10;
	private char[][] lab;

	public Labyrinth() {
		System.out.println("Initializing labyrinth...");
		System.out.println();

		lab = new char[dimension][dimension];

		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				lab[i][j] = ' ';

		// initializing borders
		for (int i = 0; i < dimension; i++) {
			lab[0][i] = 'x';
			lab[i][0] = 'x';
			lab[dimension - 1][i] = 'x';
			lab[i][dimension - 1] = 'x';
		}

		// placing exit
		lab[5][dimension - 1] = 'S';

		// initializing walls
		for (int i = 2; i < 5; i++) {
			for (int j = 2; j < 6; j++) {
				if (j == 4)
					continue;

				lab[i][j] = 'x';
			}
		}
		for (int i = 6; i < 9; i++) {
			for (int j = 2; j < 4; j++) {
				lab[i][j] = 'x';
			}
		}
		for (int i = 6; i < 8; i++) {
			lab[i][5] = 'x';
		}
		for (int i = 2; i < 8; i++) {
			lab[i][7] = 'x';
		}
	}

	public void print(Hero hero, Sword sword) {
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				// printing hero
				if (hero.isOn(j, i))
					hero.print();
				else if (sword.isOn(j, i) && sword.isVisible())
					sword.print();
				else
					System.out.print(lab[i][j] + " ");
			}

			System.out.println();
		}
	}

	public boolean heroCanWalkTo(int x, int y, Hero hero) {
		if (lab[y][x] == 'S') {
			if (hero.hasSword())
				return true;
			else {
				System.out.println("You have to get the Sword to Exit.");
				return false;
			}
		} else
			return lab[y][x] != 'x';
	}

	public char[][] getLab() {
		return lab;
	}
}
