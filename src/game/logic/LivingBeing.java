package game.logic;

public class LivingBeing {
	protected String name;
	private Coord position = new Coord(1, 1);
	protected int life = 100;

	public boolean isOn(int x, int y) {
		return (x == getPosition().getX()) && (y == getPosition().getY());
	}
	
	public void setLife(int life){
		this.life = life;
	}
	
	public boolean isDead(){
		return life == 0;
	}

	public Coord getPosition() {
		return position;
	}

	public void setPosition(Coord position) {
		this.position = position;
	}
}