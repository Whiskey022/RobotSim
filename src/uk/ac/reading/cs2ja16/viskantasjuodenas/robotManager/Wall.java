package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import javafx.scene.image.Image;

public class Wall extends ArenaObject{
	
	private Image wallImage = new Image(getClass().getResourceAsStream("wall.png"));
	
	public Wall(int x, int y, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.robotArena = robotArena;
		this.image = wallImage;
		countId();
	}

	public Wall(){}
	
	@Override
	public boolean tryToMove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDidMove() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Image getImage() {
		return wallImage;
	}
}
