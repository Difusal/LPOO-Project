package game;

public class LivingBeing {
	protected String name;
	protected Coord position = new Coord(1, 1);
	protected int life = 100;

	public boolean isOn(int x, int y) {
		return (x == position.getX()) && (y == position.getY());
	}
	
	public void setLife(int life){
		this.life = life;
	}
	
	public boolean isDead(){
		return life == 0;
	}
}