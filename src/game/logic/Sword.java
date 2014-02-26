package game.logic;

import java.util.Random;

public class Sword {
	protected Coord position = new Coord(1, 1);
	private boolean show = true;

	public Sword(Labyrinth lab, LivingBeing hero) {
		Random r = new Random();
		do {
			do {
				position.setX(r.nextInt(lab.getDimension() - 2) + 1);
				position.setY(r.nextInt(lab.getDimension() - 2) + 1);
			} while (lab.getLab()[position.getY()][position.getX()] != ' ');
		} while (position.getX() == hero.getPosition().getX()
				&& position.getY() == hero.getPosition().getY());
	}

	public Sword(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void draw() {
		System.out.print("E ");
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
