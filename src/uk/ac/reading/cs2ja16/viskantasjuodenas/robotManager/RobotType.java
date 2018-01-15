package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Arrays;
import java.util.Random;

public class RobotType {

	private static String[] robots = { "RobotOne", "RobotTwo", "RobotThree", "RobotFour" };

	public static int getCount() {
		return robots.length;
	}

	public static String getRandom() {
		return robots[new Random().nextInt(robots.length)];
	}

	public static String get(int index) {
		return robots[index];
	}

	public static int getIndex(String name) {
		return Arrays.asList(robots).indexOf(name);
	}

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
		}
	}
}
