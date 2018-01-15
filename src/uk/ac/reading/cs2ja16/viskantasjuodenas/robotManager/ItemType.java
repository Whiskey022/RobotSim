package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Arrays;
import java.util.Random;

public class ItemType {

	private static String[] items = { "Wall", "Charger", "Trap", "Light" };

	public static int getCount() {
		return items.length;
	}

	public static String getRandom() {
		int rnd = new Random().nextInt(100);
		if (rnd < 40) {
			return items[0];			//Wall
		}
		if (rnd < 70) {
			return items[1];			//Charger
		}
		if (rnd < 80) {
			return items[2];			//Trap
		}
		return items[3];				//Light
	}

	public static String get(int index) {
		return items[index];
	}

	public static int getIndex(String name) {
		return Arrays.asList(items).indexOf(name);
	}

	public static ArenaObject getItemObject(int x, int y, String type, RobotArena robotArena) {
		switch (type) {
		default:
			return new Wall(x, y, robotArena);
		case "Charger":
			return new Charger(x, y, robotArena);
		case "Trap":
			return new Trap(x, y, robotArena);
		case "Light":
			return new Light(x, y, robotArena);
		}

	}
}
