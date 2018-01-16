package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

/**
 * Wall to block the path
 *
 */
public class Wall extends ArenaObject {

	/**
	 * Constructor
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param robotArena
	 *            arena to place wall to
	 */
	public Wall(int x, int y, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getWallImage();
		countId();
		type = "Wall";
	}

	@Override
	public boolean isWall() {
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
