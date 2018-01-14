package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

public class RobotTwo extends Robot{

	/**
	 * Robot constructor, sets up his location, direction, robotArena, Id, and imageIndex
	 * @param	x	x coordinate
	 * @param	y	y coordinate
	 * @param	direction	robot's initial direction
	 * @param	robotArena	robotArena the robot belongs to	
	 * @param	imageIndex	robot's image index, stored to have a consistent image
	 */
	public RobotTwo(int x, int y, Direction direction, RobotArena robotArena) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getRobotImage(1);
		countId();
	}
	
	//Function to move
		@Override
		public boolean tryToMove() {
			for (int i=0; i<4; i++) {
				//Set next coordinates
				int nextX = x, nextY = y;
				switch (direction) {
				case NORTH:
					nextY--;
					break;
				case EAST:
					nextX++;
					break;
				case SOUTH:
					nextY++;
					break;
				case WEST:
					nextX--;
					break;
				}
				//If robot can move there, set next coordinates to current
				if (robotArena.canMoveHere(nextX, nextY)) {
					oldX = x;
					oldY = y;
					x = nextX;
					y = nextY;
					didMove = true;
					return true;
				}
				direction = direction.getNextDirection();
			}
			didMove = false;
			return false;
		}
}
