package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Random;
import javafx.scene.image.Image;

/**
 * Simple class to easily store and retrieve object images
 */
public class ArenaImage {

	private Image[] robotImages = { new Image(getClass().getResourceAsStream("robot_01.png")),
			new Image(getClass().getResourceAsStream("robot_02.png")),
			new Image(getClass().getResourceAsStream("robot_03.png")),
			new Image(getClass().getResourceAsStream("robot_04.png")),
			new Image(getClass().getResourceAsStream("robot_05.png")),
			new Image(getClass().getResourceAsStream("robot_06.png")),
			new Image(getClass().getResourceAsStream("robot_07.png")) };
	private Image wallImage = new Image(getClass().getResourceAsStream("wall.png"));
	private Image chargerImage = new Image(getClass().getResourceAsStream("charger.png"));
	private Image trapImage = new Image(getClass().getResourceAsStream("trap.png"));
	private Image lightImage = new Image(getClass().getResourceAsStream("light.png"));

	/**
	 * Get image with provided with item type
	 * 
	 * @param itemType
	 *            provide item type to get image for
	 * @return return item image
	 */
	public Image getByItemType(String itemType) {
		switch (itemType) {
		default:
			return wallImage;
		case "Charger":
			return chargerImage;
		case "Trap":
			return trapImage;
		case "Light":
			return lightImage;
		}
	}

	/**
	 * Get robot image with provided index
	 * 
	 * @param index
	 *            provide image index
	 * @return return robot image
	 */
	public Image getRobotImage(int index) {
		return robotImages[index];
	}

	/**
	 * @return Image of a wall
	 */
	public Image getWallImage() {
		return wallImage;
	}

	/**
	 * 
	 * @return Image of a charger
	 */
	public Image getChargerImage() {
		return chargerImage;
	}

	/**
	 * 
	 * @return Image of a trap
	 */
	public Image getTrapImage() {
		return trapImage;
	}

	/**
	 * 
	 * @return Image of a light
	 */
	public Image getLightImage() {
		return lightImage;
	}

	/**
	 * Get a random robot image
	 * 
	 * @return return robot image
	 */
	public Image getRandomRobotImage() {
		return robotImages[new Random().nextInt(robotImages.length)];
	}

	/**
	 * Get size of robot images array
	 * 
	 * @return size of robot images array
	 */
	public int getSize() {
		return robotImages.length;
	}
}
