package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class RobotArena {

	private int x, y;
	private ArrayList<ArenaObject> objects;
	private String status = "stop";

	/**
	 * RobotArena constructor
	 * 
	 * @param x
	 *            arena width
	 * @param y
	 *            arena length
	 */
	public RobotArena(int x, int y) {
		this.x = x;
		this.y = y;
		objects = new ArrayList<ArenaObject>();
	}

	/**
	 * Adds a new robot at random location with random image index
	 * 
	 * @param robotImagesCount
	 *            Provide the count of how many robot images there are available to
	 *            calculate a random image index
	 */
	public String addRobot() {

		// Initialise some values for adding a robot
		Random rand = new Random();
		int x, y;

		// Loop to find a position for a robot, and add him to arena
		// Attempts a certain count of times, so it doesn't get stuck on the loop
		for (int j = 0; j < this.x * this.y * 100; j++) {
			// Create random coordinates
			x = rand.nextInt(this.x) + 1;
			y = rand.nextInt(this.y) + 1;

			// If position not taken, add robot there
			if (!objectIsHere(x, y)) {
				objects.add(new Robot(x, y, Direction.getRandomDirection(), this, new RobotImages().getRandomImage()));
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addRobot(int x, int y, Direction direction, Image image) {
		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			// Check if x value valid, change if necessary
			if (x > this.x) {
				x = this.x;
			} else if (x < 1) {
				x = 1;
			}
			// Check if y value valid, change if necessary
			if (y > this.y) {
				y = this.y;
			} else if (y < 1) {
				y = 1;
			}
			objects.add(new Robot(x, y, direction, this, image));
			return "success";
		}
		System.out.println("ERROR: position already taken");
		return "Position already taken";
	}

	public String addObstacle() {
		// Initialise some values for adding a robot
		Random rand = new Random();
		int x, y;

		// Loop to find a position for an object, and add it to arena
		// Attempts a certain count of times, so it doesn't get stuck on the loop
		for (int j = 0; j < this.x * this.y * 10; j++) {
			// Create random coordinates
			x = rand.nextInt(this.x) + 1;
			y = rand.nextInt(this.y) + 1;

			// If position not taken, add robot there
			if (!objectIsHere(x, y)) {
				ArenaObject newRobot = randomObstacle(x, y);
				objects.add(newRobot);
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addObstacle(int y, int x, String type) {
		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			// Check if x value valid, change if necessary
			if (x > this.x) {
				x = this.x;
			} else if (x < 1) {
				x = 1;
			}
			// Check if y value valid, change if necessary
			if (y > this.y) {
				y = this.y;
			} else if (y < 1) {
				y = 1;
			}
			objects.add(newObstacle(x, y, type));
			return "success";
		}
		System.out.println("ERROR: position already taken");
		return "Position already taken";
	}

	public String toString() {
		String res = "";

		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			res += "\n";
		}

		return res;
	}

	// Function to check if robot can move into position
	public boolean canMoveHere(int x, int y) {
		// Check if its a wall in front
		if (x > this.x || y > this.y || x <= 0 || y <= 0) {
			return false;
			// Check if a robot is in front
		} else if (objectIsBlocking(x, y)) {
			return false;
		}
		return true;
	}

	// Function to check if robot is at position
	private boolean objectIsHere(int x, int y) {
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (objects.get(i).isHere(x, y)) {
				return true;
			}
		}
		return false;
	}

	// Function to check if robot is at position
	private boolean objectIsBlocking(int x, int y) {
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (objects.get(i).isHere(x, y) && (isRobot(objects.get(i)) || isWall(objects.get(i)))) {
				return true;
			}
		}
		return false;
	}

	// Try to move every robot
	public void moveAllRobots() {
		int countOfRobotsMoved = 0;
		while (countOfRobotsMoved == 0) {
			for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
				if (isRobot(objects.get(i)) && objects.get(i).tryToMove()) {
					countOfRobotsMoved++;
				}
			}
		}
	}

	private ArenaObject randomObstacle(int x, int y) {
		switch (new Random().nextInt() % 1) {
		default:
			return new Wall(x, y, this);
		}
	}

	private ArenaObject newObstacle(int x, int y, String type) {
		switch (type) {
		case "wall":
			return new Wall(x, y, this);
		default:
			return randomObstacle(x, y);
		}
	}

	private boolean isRobot(ArenaObject object) {
		return object.getClass() == Robot.class;
	}

	private boolean isWall(ArenaObject object) {
		return object.getClass() == Wall.class;
	}

	public void setXSize(int x) {
		this.x = x;
	}

	public void setYSize(int y) {
		this.y = y;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public int getXSize() {
		return x;
	}

	public int getYSize() {
		return y;
	}

	public ArrayList<ArenaObject> getObjects() {
		return objects;
	}

	public int getObjectCount() {
		return ArenaObject.getObjectsCount();
	}

	public String getStatus() {
		return status;
	}

	// Convert all arena details to a string for saving into file
	public String getDetails() {
		String details = "";

		// Arena details
		details += x + ":" + y + "|";

		// Robots details
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			details += objects.get(i).getId() + ":" + objects.get(i).getX() + ":" + objects.get(i).getY() + "|";
		}

		return details;
	}
}
