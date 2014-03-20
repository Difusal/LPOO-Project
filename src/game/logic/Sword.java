package game.logic;

import game.logic.Labyrinth.Symbols;

import java.io.Serializable;
import java.util.Random;

public class Sword implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Coord position = new Coord(1, 1);
	private boolean show = true;

	public Sword(Labyrinth lab, LivingBeing hero) {
		Random r = new Random();
		do {
			do {
				position.setX(r.nextInt(lab.getWidth() - 2) + 1);
				position.setY(r.nextInt(lab.getHeight() - 2) + 1);
			} while (lab.getLab()[position.getY()][position.getX()] != Symbols.PATH);
		} while (position.getX() == hero.getPosition().getX()
				&& position.getY() == hero.getPosition().getY());
	}

	public Sword(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public Sword(Coord coord) {
		position = new Coord(coord);
	}

	public String drawToString() {
		return "E ";
	}

	public boolean isOn(int x, int y) {
		return (x == position.getX()) && (y == position.getY());
	}

	public boolean isVisible() {
		return show;
	}

	public void show() {
		show = true;
	}

	public void hide() {
		show = false;
	}

	public Coord getPosition() {
		return position;
	}

	public void setPosition(Coord position) {
		this.position = position;
	}
}
