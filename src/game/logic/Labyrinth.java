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

		System.out.println("Eagle dead: " + eagle.isDead());
		System.out.println("Eagle flying: " + eagle.isFlying());
		System.out.println("Eagle with hero: " + eagle.isWithHero());
		System.out.println("SWORD visible: " + sword.isVisible());

		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				boolean somethingWasDrawn = false;

				// drawing hero, dragon or sword
				for (LivingBeing k : livingBeings) {
					// if something is at this coords
					if (k.isOn(j, i)) {
						// drawing hero
						if (k.getType() == Type.HERO) {
							k.draw();
							somethingWasDrawn = true;
						}
						// drawing eagle
						if (k.getType() == Type.EAGLE && !eagle.isDead()
								&& (eagle.isFlying() || !eagle.isWithHero())) {
							k.draw();
							somethingWasDrawn = true;
						}
						// drawing dragon
						else if (k.getType() == Type.DRAGON && !k.isDead()) {
							// if sword is under the dragon
							if (sword.isOn(j, i) && sword.isVisible())
								System.out.print("F ");
							else
								k.draw();

							somethingWasDrawn = true;
						}

						break;
					}
				}

				if (!somethingWasDrawn) {
					if (sword.isOn(j, i) && sword.isVisible()) {
						// printing sword
						sword.draw();
						somethingWasDrawn = true;
					}
				}

				if (!somethingWasDrawn)
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
				System.out.println("You have to kill a Dragon to exit.");
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
