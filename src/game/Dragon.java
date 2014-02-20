package game;

import java.util.Random;

public class Dragon extends LivingBeing {
	public Dragon() {
		this.position = new Coord(1, 3);
	}

	public Dragon(int x, int y) {
		this.position = new Coord(x, y);
	}

	public void print() {
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
			if (lab.dragonCanWalkTo(position.getX(), position.getY() - 1))
				position.setY(position.getY() - 1);
			break;
		// right
		case 1:
			if (lab.dragonCanWalkTo(position.getX() + 1, position.getY()))
				position.setX(position.getX() + 1);
			break;
		// down
		case 2:
			if (lab.dragonCanWalkTo(position.getX(), position.getY() + 1))
				position.setY(position.getY() + 1);
			break;
		// left
		case 3:
			if (lab.dragonCanWalkTo(position.getX() - 1, position.getY()))
				position.setX(position.getX() - 1);
			break;
		}
	}
}
