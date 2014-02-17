package game;

public class Hero extends LivingBeing {
	private boolean hasSword = false;
		
	public Hero(String name) {
		this.name = name;
	}
	
	public Hero(String name, int x, int y) {
		this.name = name;
		this.position = new Coord(x, y);
	}
	
	public void print() {
		System.out.print("H ");
	}
	
	public boolean isOn(int x, int y) {
		return (x == position.getX()) && (y == position.getY()); 
	}
	
	public void move(Direction dir) {
		
	}
}
