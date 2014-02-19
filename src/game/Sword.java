package game;

public class Sword {
	private Coord position = new Coord(1, 8);
	private boolean show = true;

	public Sword() {
	}

	public Sword(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void print() {
		System.out.print("E ");
	}

	public boolean isOn(int x, int y) {
		return (x == position.getX()) && (y == position.getY()); 
	}
	
	public boolean isVisible() { return show; }
	
	public void hide() {
		show = false;
	}
	
	public Coord getPosition() { return position; }
}
