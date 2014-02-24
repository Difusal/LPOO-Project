package game.logic;

import java.util.Random;

public class Dragon extends LivingBeing {
	public Dragon(Labyrinth lab, Hero hero, Sword sword) {
		Random r = new Random();
		do {
			do {
				getPosition().setX(r.nextInt(lab.getDimension() - 2) + 1);
			} while (getPosition().getX() % 2 == 0);
			do {
				getPosition().setY(r.nextInt(lab.getDimension() - 2) + 1);
			} while (getPosition().getY() % 2 == 0);
		} while ((getPosition().getX() == hero.getPosition().getX() && getPosition().getY() == hero.getPosition()
				.getY())
				|| (getPosition().getX() == sword.position.getX() && getPosition().getY() == sword.position
						.getY()));
	}

	public Dragon(int x, int y) {
		this.setPosition(new Coord(x, y));
	}

	public void draw() {
		System.out.print("D ");
	}

	public void move(Labyrinth lab) {
		Random r = new Random();
		int dir = r.nextInt(4);

		/*
		 * Legend: 0 - up; 1 - right; 2 - down; 3 - left.
		 */

		switch (dir) {
		// up
		case 0:
			if (lab.dragonCanWalkTo(getPosition().getX(), getPosition().getY() - 1))
				getPosition().setY(getPosition().getY() - 1);
			break;
		// right
		case 1:
			if (lab.dragonCanWalkTo(getPosition().getX() + 1, getPosition().getY()))
				getPosition().setX(getPosition().getX() + 1);
			break;
		// down
		case 2:
			if (lab.dragonCanWalkTo(getPosition().getX(), getPosition().getY() + 1))
				getPosition().setY(getPosition().getY() + 1);
			break;
		// left
		case 3:
			if (lab.dragonCanWalkTo(getPosition().getX() - 1, getPosition().getY()))
				getPosition().setX(getPosition().getX() - 1);
			break;
		}
	}
}
