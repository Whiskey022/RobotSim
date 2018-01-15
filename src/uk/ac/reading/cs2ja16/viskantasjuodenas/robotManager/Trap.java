package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

public class Trap extends ArenaObject{
		
	public Trap(int x, int y, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getTrapImage();
		countId();
		type = "Trap";
	}
	
	@Override
	public boolean isTrap() {
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
