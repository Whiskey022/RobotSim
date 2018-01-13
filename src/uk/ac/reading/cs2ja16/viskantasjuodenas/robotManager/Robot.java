package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import javafx.scene.image.Image;

public class Robot extends ArenaObject {

	protected int initialX, initialY;		//To keep starting positions
	protected int oldX, oldY;
	protected static int robotsCount = 0; // Robots counter, to help calculate IDs
	protected Direction direction;
	protected RobotArena robotArena;
	protected boolean didMove = false;

	/**
	 * Robot constructor, sets up his location, direction, robotArena, Id, and imageIndex
	 * @param	x	x coordinate
	 * @param	y	y coordinate
	 * @param	direction	robot's initial direction
	 * @param	robotArena	robotArena the robot belongs to	
	 * @param	imageIndex	robot's image index, stored to have a consistent image
	 */
	Robot(int x, int y, Direction direction, RobotArena robotArena, Image image) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = image;
		countId();
	}

	//Function to move
	@Override
	public boolean tryToMove() {
		int nextX = x, nextY = y;
		//Set next coordinates
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
		} else {	//Otherwise, set direction to random
			direction = direction.getNextDirection();
			didMove = false;
			return false;
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(int robotId) {
		this.id = robotId;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setRobotArena(RobotArena robotArena) {
		this.robotArena = robotArena;
	}
	
	public static void setRobotsCount(int count) {
		robotsCount = count;
	}
	
	public int getOldX() {
		return oldX;
	}

	public int getOldY() {
		return oldY;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public RobotArena getRobotArena() {
		return robotArena;
	}
	
	public boolean getDidMove() {
		return didMove;
	}
}
