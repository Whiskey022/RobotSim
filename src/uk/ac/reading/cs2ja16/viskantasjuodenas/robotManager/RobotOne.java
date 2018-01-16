package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

/**
 *	RobotOne class, default robot type
 */
public class RobotOne extends Robot {

	/**
	 * Robot constructor, sets up his location, direction, robotArena, Id, and
	 * imageIndex
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param direction
	 *            robot's initial direction
	 * @param robotArena
	 *            robotArena the robot belongs tos
	 */
	public RobotOne(int x, int y, Direction direction, RobotArena robotArena) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getRobotImage(0);
		countId();
		type = "RobotOne";
	}
}
