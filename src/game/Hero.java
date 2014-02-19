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
	
	public void move(String dir, Labyrinth lab) {
		switch(dir){
		case "w":
			if (lab.cellIsFree(position.getX(), position.getY()-1))
				position.setY(position.getY()-1);
			else
				System.out.println("Jogada Inválida.");
			break;
		case "s":
			if (lab.cellIsFree(position.getX(), position.getY()+1))
				position.setY(position.getY()+1);
			else
				System.out.println("Jogada Inválida.");
			break;
		case "a":
			if (lab.cellIsFree(position.getX()-1, position.getY()))
				position.setX(position.getX()-1);
			else
				System.out.println("Jogada Inválida.");
			break;
		case "d":
			if (lab.cellIsFree(position.getX()+1, position.getY()))
				position.setX(position.getX()+1);
			else
				System.out.println("Jogada Inválida.");
			break;
		}
	}
}
