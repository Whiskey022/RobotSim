package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Scanner;

public class Robot {

	private int x, y;
	private static int robotsCount = 0; // Robots counter, to help calculate IDs
	private int robotId;
	private Direction direction;
	private RobotArena robotArena;

	Robot(int x, int y, Direction direction, RobotArena robotArena) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		// Setting robot ID
		robotsCount++;
		robotId = robotsCount;
	}

	Robot() {
		x = 5;
		y = 5;
		// Setting robot ID
		robotsCount++;
		robotId = robotsCount;
	}

	public String toString() {
		return "Robot ID: " + robotId + ". Current position x: " + x + ", y: " + y + ". Current direction: "
				+ direction;
	}

	public boolean isHere(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		} else {
			return false;
		}
	}

	public void displayRobot(RobotInterface r) {
		r.showRobot(x, y); // just send details of robot to interface
	}

	//Function to move
	public void tryToMove() {
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
			x = nextX;
			y = nextY;
		} else {	//Otherwise, set direction to random
			direction = direction.getNextDirection();
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(int robotId) {
		this.robotId = robotId;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return robotId;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public RobotArena getRobotArena() {
		return robotArena;
	}

	public static void main(String[] args) {
		// function used to test the robot code
		Scanner s = new Scanner(System.in);
		System.out.print("How many robots in your world ?");
		int numRobots = s.nextInt();
		Robot[] allRobots = new Robot[numRobots];
		int rx, ry;
		System.out.println("Now enter position of each robot in turn (as x y) >");
		for (int ct = 0; ct < numRobots; ct++) {
			System.out.print("Enter x,y position for " + ct + "th robot >");
			rx = s.nextInt();
			ry = s.nextInt();
			allRobots[ct] = new Robot(rx, ry, null, null);
		}
		for (int ct = 0; ct < numRobots; ct++)
			System.out.println(allRobots[ct].toString());

		System.out.println("Enter position to check if any robots are there (as x y) >");
		rx = s.nextInt();
		ry = s.nextInt();
		boolean robotFound = false;
		for (int i = 0; i < numRobots; i++) {
			if (allRobots[i].isHere(rx, ry)) {
				System.out.println("Robot found. Its ID: " + allRobots[i].getId());
				robotFound = true;
			}
		}

		if (!robotFound) {
			System.out.println("No robots found at provided position.");
		}

		s.close();
	}
}
