package game.logic;

import java.util.Random;

public class Dragon extends LivingBeing {
	DragonBehavior behavior = DragonBehavior.Moving;
	private boolean isSleeping = false;

	public enum DragonBehavior {
		notMoving, Moving, MovingAndSleeping
	}

	public Dragon(DragonBehavior behavior, Labyrinth lab, Hero hero, Sword sword) {
		this.behavior = behavior;

		Random r = new Random();
		do {
			do {
				getPosition().setX(r.nextInt(lab.getDimension() - 2) + 1);
			} while (getPosition().getX() % 2 == 0);
			do {
				getPosition().setY(r.nextInt(lab.getDimension() - 2) + 1);
			} while (getPosition().getY() % 2 == 0);
		} while ((getPosition().getX() == hero.getPosition().getX() && getPosition()
				.getY() == hero.getPosition().getY())
				|| (getPosition().getX() == sword.position.getX() && getPosition()
						.getY() == sword.position.getY()));
	}

	public Dragon(int x, int y) {
		this.setPosition(new Coord(x, y));
	}

	public void move(Labyrinth lab) {
		Random r = new Random();

		if (behavior != DragonBehavior.notMoving && !isSleeping) {
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

		if (behavior == DragonBehavior.MovingAndSleeping) {
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

	public boolean isSleeping() {
		return isSleeping;
	}
}
