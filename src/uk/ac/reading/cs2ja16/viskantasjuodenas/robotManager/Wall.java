package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import javafx.scene.image.Image;

public class Wall extends ArenaObject{
	
	Wall(int x, int y, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.robotArena = robotArena;
		this.image = new Image(getClass().getResourceAsStream("wall.png"));
		countId();
	}

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
}
