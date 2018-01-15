package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

public class Charger extends ArenaObject {

	public Charger(int x, int y, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getChargerImage();
		countId();
		type = "Charger";
	}

	public boolean isCharger() {
		return true;
	}

	@Override
	public boolean tryToMove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRobot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDidMove() {
		// TODO Auto-generated method stub
		return false;
	}

}
