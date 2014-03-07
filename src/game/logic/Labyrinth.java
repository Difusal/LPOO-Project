package game.logic;

import game.logic.LivingBeing.Type;

import java.util.Vector;

public class Labyrinth {
	private int width, height;
	private Symbols[][] lab;

	public enum Symbols {
		PATH, WALL, EXIT
	}

	public char getSymbolChar(Symbols symbol) {
		char res = '.';

		switch (symbol) {
		case PATH:
			res = ' ';
			break;
		case WALL:
			res = 'X';
			break;
		case EXIT:
			res = 'S';
			break;
		}

		return res;
	}

	public Labyrinth(int width, int height, Symbols[][] lab) {
		this.width = width;
		this.height = height;
		this.lab = lab;
	}

	public String drawToString() {
		String str = "\n";

		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++)
				str += getSymbolChar(lab[i][j]) + " ";

			str += "\n";
		}

		return str;
	}

	public String drawToString(Vector<LivingBeing> livingBeings, Sword sword,
			Eagle eagle) {
		String str = "\n\n";

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
							str += k.drawToString();
							somethingWasDrawn = true;
						}
						// drawing dragon
						else if (k.getType() == Type.DRAGON) {
							// if sword is under the dragon
							if (sword.isVisible() && sword.isOn(j, i)) {
								if (k.isSleeping)
									str += "f ";
								else
									str += "F ";
							} else
								str += k.drawToString();

							somethingWasDrawn = true;
						}

						break;
					}
				}

				// if nothing was drawn yet
				if (!somethingWasDrawn) {
					if (sword.isOn(j, i) && sword.isVisible()) {
						// draw sword
						str += sword.drawToString();
						somethingWasDrawn = true;
					}
				}

				// if nothing was drawn yet
				if (!somethingWasDrawn)
					// draw maze
					str += getSymbolChar(lab[i][j]) + " ";
			}

			str += "\n";
		}

		return str;
	}

	public boolean heroCanWalk(Direction dir, Hero hero) {
		int x = hero.getPosition().getX();
		int y = hero.getPosition().getY();

		switch (dir) {
		// scanning cell to the right
		case RIGHT:
			if (lab[y][x + 1] != Symbols.EXIT)
				return lab[y][x + 1] != Symbols.WALL;
			break;
		// scanning cell to the south
		case DOWN:
			if (lab[y + 1][x] != Symbols.EXIT)
				return lab[y + 1][x] != Symbols.WALL;
			break;
		// scanning cell to the left
		case LEFT:
			if (lab[y][x - 1] != Symbols.EXIT)
				return lab[y][x - 1] != Symbols.WALL;
			break;
		// scanning cell to the north
		case UP:
			if (lab[y - 1][x] != Symbols.EXIT)
				return lab[y - 1][x] != Symbols.WALL;
			break;
		default:
			break;
		}

		// if hero is trying to walk to maze exit
		if (hero.hasKilledADragon())
			return true;
		else
			return false;
	}

	public boolean dragonCanWalkTo(int x, int y) {
		return lab[y][x] == Symbols.PATH;
	}

	public Symbols[][] getLab() {
		return lab;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
