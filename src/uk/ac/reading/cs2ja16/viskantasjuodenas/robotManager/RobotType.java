package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Arrays;
import java.util.Random;

/**
 * Class to store robot names and descriptions
 *
 */
public class RobotType {

	private static String[] robots = { "RobotOne", "RobotTwo", "RobotThree", "RobotFour", "RobotFive", "RobotSix", "RobotSeven"};
	private static String[] robotDescriptions = {
			"Robot changes direction clockwise. Takes a turn to change a direction.",
			"Robot changes direction clockwise. Changes direction instantly.",
			"Robot changes direction randomly. Changes direction instantly",
			"Robot changes direction clockwise, but can change at a random time. Changes direction instantly.",
			"Robot changes direction clockwise. Has extra charge. Takes a turn to change direction",
			"Robot changes direction clockwise. Removes traps. Takes a turn to change direction",
			"Robot changes direction clockwise, can steer towards a light. Changes direction instantly"};

	/**
	 * 
	 * @return count of different robot types
	 */
	public static int getCount() {
		return robots.length;
	}

	/**
	 * 
	 * @return random robot type
	 */
	public static String getRandom() {
		return robots[new Random().nextInt(robots.length)];
	}

	/**
	 * Get robot type by index
	 * @param index index of robot array
	 * @return robot type
	 */
	public static String get(int index) {
		return robots[index];
	}
	
	/**
	 * get robot description by index
	 * @param index	index for description array
	 * @return robot description
	 */
	public static String getDescription(int index) {
		return robotDescriptions[index];
	}

	/**
	 * get index of a robot type by name
	 * @param name	name of robot type
	 * @return index of robot type
	 */
	public static int getIndex(String name) {
		return Arrays.asList(robots).indexOf(name);
	}

	/**
	 * get a robot object by providing its name
	 * @param x	x coordinate
	 * @param y y coordinate
	 * @param direction robot direction
	 * @param type robot type
	 * @param robotArena robotArena object
	 * @return robot object
	 */
	public static Robot getRobotObject(int x, int y, Direction direction, String type, RobotArena robotArena) {
		switch (type) {
		default:
			return new RobotOne(x, y, direction, robotArena);
		case "RobotTwo":
			return new RobotTwo(x, y, direction, robotArena);
		case "RobotThree":
			return new RobotThree(x, y, direction, robotArena);
		case "RobotFour":
			return new RobotFour(x, y, direction, robotArena);
		case "RobotFive":
			return new RobotFive(x, y, direction, robotArena);
		case "RobotSix":
			return new RobotSix(x, y, direction, robotArena);
		case "RobotSeven":
			return new RobotSeven(x, y, direction, robotArena);
		}
	}
}
