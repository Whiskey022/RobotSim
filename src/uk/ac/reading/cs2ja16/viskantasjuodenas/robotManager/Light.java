package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

/**
 * Class for Light object
 */
public class Light extends ArenaObject {

	public Light(int x, int y, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getLightImage();
		countId();
		type = "Light";
	}

	@Override
	public boolean isLight() {
		return true;
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

	@Override
	public boolean isRobot() {
		// TODO Auto-generated method stub
		return false;
	}
}
