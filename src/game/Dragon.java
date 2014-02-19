package game;

import java.util.Random;

public class Dragon extends LivingBeing {
	public Dragon() {
		this.position = new Coord(1, 3);
	}

	public Dragon(int x, int y) {
		this.position = new Coord(x, y);
	}
	
	public void print() {
		System.out.print("D ");
	}
	
	public void move(Labyrinth lab){
		Random r = new Random();
		int dir = r.nextInt(4);
		dir++;
		
		/*
		 * 1 - up
		 * 2 - right
		 * 3 - down
		 * 4 - left
		 */
		switch(dir){
		case 1:
			if(lab.dragonCanWalkTo(position.getX(), position.getY()-1)){
				position.setX(position.getX());
				position.setY(position.getY()-1);
			}
			break;
		case 2:
			if(lab.dragonCanWalkTo(position.getX()+1, position.getY())){
				position.setX(position.getX()+1);
				position.setY(position.getY());
			}
			break;
		case 3:
			if(lab.dragonCanWalkTo(position.getX(), position.getY()+1)){
				position.setX(position.getX());
				position.setY(position.getY()+1);
			}
			break;
		case 4:
			if(lab.dragonCanWalkTo(position.getX()-1, position.getY())){
				position.setX(position.getX()-1);
				position.setY(position.getY());
			}
			break;
		
		}
	}
}
