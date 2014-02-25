package game.logic;

import java.util.Vector;

public class Eagle extends LivingBeing {
	private boolean active = false;
	private boolean withHero = true;
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
	
	public void startFlight(Labyrinth lab, Sword sword) {
		// preparing variables
		active = true;
		withHero = false;
		state = FlightState.GOING;
		pathStep = 0;
		
		// calculating path
		// manual debug ATM
		path.clear();
		path.add(new Coord(position.getX(), position.getY()));
		path.add(new Coord(position.getX()+1, position.getY()));
		path.add(new Coord(position.getX()+2, position.getY()));
		
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
			if (pathStep >= path.size() - 1)
				state = FlightState.RETURNING;
			
			// if origin reached
			if (pathStep == 0 && state == FlightState.RETURNING)
				active = false;
			
			// updating eagle position
			getPosition().setX(path.get(pathStep).getX());
			getPosition().setY(path.get(pathStep).getY());
		}
	}

	public void draw() {
		if (active || !withHero)
			System.out.print("B ");
	}
}
