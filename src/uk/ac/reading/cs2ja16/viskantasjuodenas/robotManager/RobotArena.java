package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class RobotArena {

	private int x, y;
	private int maxRobots;
	private ArrayList<ArenaObject> objects;
	private int robotsCounter = 0;
	private String status = "stop";

	/**
	 * RobotArena constructor
	 * 
	 * @param x
	 *            arena width
	 * @param y
	 *            arena length
	 * @param maxRobots
	 *            robots count limit
	 */
	public RobotArena(int x, int y, int maxRobots) {
		// If maxRobots is higher than arena can fit, throw error
		if (x * y < maxRobots) {
			System.out.println("ERROR: robots limit bigger than the size of arena");
		} else {
			this.x = x;
			this.y = y;
			this.maxRobots = maxRobots;
			objects = new ArrayList<ArenaObject>();
		}
	}

	/**
	 * Adds a new robot at random location with random image index
	 * 
	 * @param robotImagesCount
	 *            Provide the count of how many robot images there are available to
	 *            calculate a random image index
	 */
	public String addRobot() {
		if (robotsCounter >= maxRobots) {
			System.out.println("ERROR: robots count has already reached max capacity");
			return "Robot count already reached max capacity";
		} else {
			// Initialise some values for adding a robot
			Random rand = new Random();
			int rx, ry;

			// Loop to find a position for a robot, and add him to arena
			// Attempts a certain count of times, so it doesn't get stuck on the loop
			for (int j = 0; j < x * y * 10; j++) {
				// Create random coordinates
				rx = rand.nextInt(x) + 1;
				ry = rand.nextInt(y) + 1;
				boolean posTaken = false;

				// Loop to check if position taken
				for (int i = 0; i < robotsCounter; i++) {
					if (objectIsHere(y, x)) {
						posTaken = true;
						break;
					}
				}

				// If position not taken, add robot there
				if (!posTaken) {
					ArenaObject newRobot = new Robot(rx, ry, Direction.getRandomDirection(), this,
							new RobotImages().getRandomImage());
					objects.add(newRobot);
					robotsCounter++;
					return "success";
				}
			}
			return "Failed to add a robot";
		}
	}

	public String addRobot(int x, int y, Direction direction, Image image) {
		if (robotsCounter >= maxRobots) {
			System.out.println("ERROR: robots count has already reached max capacity");
			return "Robot count already reached max capacity";
		} else {

			boolean posTaken = false;

			// Loop to check if position taken
			for (int i = 0; i < robotsCounter; i++) {
				if (objectIsHere(x, y)) {
					posTaken = true;
					break;
				}
			}

			// If position not taken, add robot there
			if (!posTaken) {
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
				robotsCounter++;
				return "success";
			} else {
				System.out.println("ERROR: position already taken");
				return "Position already taken";
			}
		}
	}

	public String addObstacle() {
		// Initialise some values for adding a robot
		Random rand = new Random();
		int rx, ry;

		// Loop to find a position for an object, and add it to arena
		// Attempts a certain count of times, so it doesn't get stuck on the loop
		for (int j = 0; j < x * y * 10; j++) {
			// Create random coordinates
			rx = rand.nextInt(x) + 1;
			ry = rand.nextInt(y) + 1;
			boolean posTaken = false;

			// Loop to check if position taken
			for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
				if (objectIsHere(y, x)) {
					posTaken = true;
					break;
				}
			}

			// If position not taken, add robot there
			if (!posTaken) {
				ArenaObject newRobot = randomObstacle(rx, ry);
				objects.add(newRobot);
				robotsCounter++;
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addObstacle(int y, int x, String type) {
		if (robotsCounter >= maxRobots) {
			System.out.println("ERROR: objects count has already reached max capacity");
			return "Objects count already reached max capacity";
		} else {

			boolean posTaken = false;

			// Loop to check if position taken
			for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
				if (objectIsHere(x, y)) {
					posTaken = true;
					break;
				}
			}

			// If position not taken, add robot there
			if (!posTaken) {
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
			} else {
				System.out.println("ERROR: position already taken");
				return "Position already taken";
			}
		}
	}

	public String toString() {
		String res = "";

		for (int i = 0; i < robotsCounter; i++) {
			res += "\n";
		}

		return res;
	}

	// Function to check if robot can mvove into position
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
		for (int i = 0; i < robotsCounter; i++) {
			if (objects.get(i).isHere(x, y)) {
				return true;
			}
		}
		return false;
	}

	// Function to check if robot is at position
	private boolean objectIsBlocking(int x, int y) {
		for (int i = 0; i < robotsCounter; i++) {
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
			for (int i = 0; i < robotsCounter; i++) {
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

	public void setMaxRobots(int maxRobots) {
		this.maxRobots = maxRobots;
	}

	public void setRobotsCounter(int robotsCounter) {
		this.robotsCounter = robotsCounter;
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

	public int getMaxRobots() {
		return maxRobots;
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
		details += x + ":" + y + ":" + maxRobots + "|";

		// Robots details
		for (int i = 0; i < robotsCounter; i++) {
			details += objects.get(i).getId() + ":" + objects.get(i).getX() + ":" + objects.get(i).getY() + "|";
		}

		return details;
	}
}
