package game.logic;

import java.util.Random;
import java.util.Vector;

public class Dragon extends LivingBeing {
	DragonBehavior behavior = DragonBehavior.MOVING;

	public enum DragonBehavior {
		NOTMOVING, MOVING, MOVINGANDSLEEPING
	}

	public Dragon(DragonBehavior behavior, Labyrinth lab,
			Vector<LivingBeing> livingBeings, Sword sword) {
		this.type = Type.DRAGON;
		this.behavior = behavior;

		Random r = new Random();
		do {
			position.setX(r.nextInt(lab.getDimension() - 2) + 1);
			position.setY(r.nextInt(lab.getDimension() - 2) + 1);

			// while dragon is not generated on a free cell
			// or while distance to hero is < 2
		} while (lab.getLab()[position.getY()][position.getX()] != ' '
				|| distanceTo(livingBeings.get(0)) < 2);
	}

	public void move(Labyrinth lab) {
		Random r = new Random();

		if (behavior != DragonBehavior.NOTMOVING && !isSleeping) {
			/*
			 * Legend: 0 - up; 1 - right; 2 - down; 3 - left.
			 */

			int dir = r.nextInt(4);
			switch (dir) {
			// up
			case 0:
				if (lab.dragonCanWalkTo(getPosition().getX(), getPosition()
						.getY() - 1))
					getPosition().setY(getPosition().getY() - 1);
				break;
			// right
			case 1:
				if (lab.dragonCanWalkTo(getPosition().getX() + 1, getPosition()
						.getY()))
					getPosition().setX(getPosition().getX() + 1);
				break;
			// down
			case 2:
				if (lab.dragonCanWalkTo(getPosition().getX(), getPosition()
						.getY() + 1))
					getPosition().setY(getPosition().getY() + 1);
				break;
			// left
			case 3:
				if (lab.dragonCanWalkTo(getPosition().getX() - 1, getPosition()
						.getY()))
					getPosition().setX(getPosition().getX() - 1);
				break;
			}
		}

		if (behavior == DragonBehavior.MOVINGANDSLEEPING) {
			// making dragon sleep sometimes
			int sleep = r.nextInt(2);
			if (sleep == 1)
				isSleeping = true;
			else
				isSleeping = false;
		}
	}

	public void draw() {
		if (isSleeping)
			System.out.print("d ");
		else
			System.out.print("D ");
	}
}
