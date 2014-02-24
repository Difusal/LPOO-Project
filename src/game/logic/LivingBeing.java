package game.logic;

public abstract class LivingBeing {
	protected String name;
	private Coord position = new Coord();
	protected int life = 100;

	public boolean isOn(int x, int y) {
		return (x == getPosition().getX()) && (y == getPosition().getY());
	}
	
	public abstract void draw();

	public boolean isDead(){
		return life == 0;
	}

	public void setLife(int life){
		this.life = life;
	}
	
	public Coord getPosition() {
		return position;
	}

	public void setPosition(Coord position) {
		this.position = position;
	}
}