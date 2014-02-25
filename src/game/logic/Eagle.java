package game.logic;

import java.util.Vector;

public class Eagle extends LivingBeing {
	private boolean active = false;
	private boolean withHero = true;
	private boolean hasSword = false;
	private FlightState state;
	private int pathStep;
	Vector<Coord> path = new Vector<Coord>();

	public enum FlightState {
		GOING, RETURNING
	}

	public Eagle() {
		type = Type.EAGLE;
	}

	public boolean isFlying() {
		return active;
	}

	public boolean isWithHero() {
		return withHero;
	}

	public void setWithHero(boolean withHero) {
		this.withHero = withHero;
	}

	public boolean hasSword() {
		return hasSword;
	}

	public void startFlight(Labyrinth lab, Sword sword) {
		// preparing variables
		active = true;
		withHero = false;
		state = FlightState.GOING;
		pathStep = 0;

		// calculating path
		path.clear();

		// if dx > dy
		if (Math.abs(sword.position.getX() - position.getX()) > Math
				.abs(sword.position.getY() - position.getY())) {
			// calculating m
			double m;
			if (sword.position.getX() == position.getX())
				m = 0;
			else
				m = 1.0 * (sword.position.getY() - position.getY())
						/ (sword.position.getX() - position.getX());

			// calculating b
			double b = position.getY() - m * position.getX();

			System.out.println("y = " + m + "*x + " + b);

			// if eagleX < swordX
			if (position.getX() < sword.position.getX()) {
				for (int i = position.getX(); i <= sword.position.getX(); i++) {
					path.add(new Coord(i, (int) Math.round(m * i + b)));
				}
			}
			// if eagleX >= swordX
			else {
				for (int i = position.getX(); i >= sword.position.getX(); i--) {
					path.add(new Coord(i, (int) Math.round(m * i + b)));
				}
			}
		}
		// if dy >= dx
		else {
			// calculating m
			double m;
			if (sword.position.getY() == position.getY())
				m = 0;
			else
				m = 1.0 * (sword.position.getX() - position.getX())
						/ (sword.position.getY() - position.getY());

			// calculating b
			double b = position.getX() - m * position.getY();

			System.out.println("y = " + m + "*x + " + b);

			// if eagleY < swordY
			if (position.getY() < sword.position.getY()) {
				for (int i = position.getY(); i <= sword.position.getY(); i++) {
					path.add(new Coord((int) Math.round(m * i + b), i));
				}
			}
			// if eagleY >= swordY
			else {
				for (int i = position.getY(); i >= sword.position.getY(); i--) {
					path.add(new Coord((int) Math.round(m * i + b), i));
				}
			}
		}

		// moving eagle
		move(lab);
	}

	public void move(Labyrinth lab) {
		// if eagle moving
		if (active) {
			// updating pathStep
			if (state == FlightState.GOING)
				pathStep++;
			else
				pathStep--;

			// sword reached
			if (pathStep >= path.size() - 1) {
				// inverting flight direction
				state = FlightState.RETURNING;

				// catching sword
				hasSword = true;
			}

			// if origin reached
			if (pathStep == 0 && state == FlightState.RETURNING)
				active = false;

			// updating eagle position
			getPosition().setX(path.get(pathStep).getX());
			getPosition().setY(path.get(pathStep).getY());
		}
	}

	public void draw() {
		if (active || !withHero) {
			if (hasSword)
				System.out.print("BE");
			else
				System.out.print("B ");
		}
	}

	public boolean isCatchingSword() {
		return pathStep == path.size() - 1;
	}
}
