package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import java.util.Random;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.Direction;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotType;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.ArenaImage;

/**
 * Class to open "Add Custom Robot" dialog
 */
public class AddRobotDialog {

	private static GraphicsContext gc;
	private static int x;
	private static int y;
	private static Direction direction;
	private static String robotSelection;
	private static RobotArena robotArena;
	private static Label descLabel = new Label("Description: ");

	/**
	 * Open "Add Custom Robot" dialog
	 * @param arena provide RobotArena to which robots should be added
	 */
	public static void open(RobotArena arena) {
		robotArena = arena;
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add a custom Robot");

		// Set the button types.
		ButtonType addBtnType = new ButtonType("Add", ButtonData.APPLY);
		dialog.getDialogPane().getButtonTypes().addAll(addBtnType, ButtonType.CANCEL);

		// Set parent grid pane
		GridPane parentPane = new GridPane();
		parentPane.setHgap(10);
		parentPane.setVgap(10);
		parentPane.setMinWidth(550);
		parentPane.setPadding(new Insets(20, 0, 10, 10));

		// Grid pane for coordinates
		GridPane positionPane = new GridPane();
		TextField xField = new TextField();
		xField.setPromptText("X coordinate");
		xField.setMaxWidth(100);
		xField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if (!newValue.matches("\\d*")) {
						xField.setText(newValue.replaceAll("[^\\d]", ""));
					}
				});
		TextField yField = new TextField();
		yField.setPromptText("Y coordinate");
		yField.setMaxWidth(100);
		yField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if (!newValue.matches("\\d*")) {
						xField.setText(newValue.replaceAll("[^\\d]", ""));
					}
				});
		// Add text fields to the grid pane
		positionPane.add(new Label("X:  "), 0, 0);
		positionPane.add(xField, 1, 0);
		positionPane.add(new Label("   Y:  "), 2, 0);
		positionPane.add(yField, 3, 0);

		// Set up direction selection box
		GridPane directionPane = new GridPane();
		ChoiceBox<String> dirBox = new ChoiceBox<String>();
		// Create a list of directions
		ObservableList<String> dirList = FXCollections.observableArrayList();
		dirList.add("Random");
		for (int i = 0; i < Direction.values().length; i++) {
			dirList.add(Direction.values()[i].toString());
		}
		dirBox.setItems(dirList);
		dirBox.setValue("Random");
		// Add direction box to direction grid pane
		directionPane.add(new Label("Select robot's direction:    "), 0, 0);
		directionPane.add(dirBox, 1, 0);

		// Set up a grid pane of robot selection
		GridPane robotPane = new GridPane();
		ChoiceBox<String> robotBox = new ChoiceBox<String>();
		// Create a list of images indexes
		ObservableList<String> robotList = FXCollections.observableArrayList();
		robotList.add("Random");
		for (int i=0; i<RobotType.getCount(); i++) {
			robotList.add(RobotType.get(i));
		}
		robotBox.setItems(robotList);
		robotBox.setValue("Random");
		// https://stackoverflow.com/questions/14522680/javafx-choicebox-events
		robotBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable,
				String oldValue, String newValue) -> {drawIt(newValue, 40, 20, 40); setDescription(newValue);});
		// Set up Group object to draw robot image
		Group root = new Group();
		Canvas canvas = new Canvas(90, 50);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		// Add image box to images grid pane
		robotPane.add(new Label("Select robot image index: "), 0, 0);
		robotPane.add(robotBox, 1, 0);
		robotPane.add(root, 2, 0);
		
		//Description grid pane
		GridPane descPane = new GridPane();
		descLabel.setWrapText(true);
		GridPane.setHgrow(descLabel, Priority.ALWAYS);
		descPane.add(descLabel, 0, 0);

		// Add all grid panes to the parent pane
		parentPane.add(new Label("Input robot coordinates"), 0, 0);
		parentPane.add(positionPane, 0, 1);
		parentPane.add(directionPane, 0, 2);
		parentPane.add(robotPane, 0, 3);
		parentPane.add(descLabel, 0, 4);

		dialog.getDialogPane().setContent(parentPane);

		// Request focus on the username field by default.
		Platform.runLater(() -> xField.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addBtnType) {
				setResults(xField.getText(), yField.getText(), dirBox.getValue(), robotBox.getValue());
				addRobot();
				robotArena.setStatus("not-drawn");
			}
			return null;
		});

		dialog.showAndWait();
	}

	/**
	 * Add robot to arena
	 */
	private static void addRobot() {
		String addRobotOutput = robotArena.addRobot(x, y, direction, robotSelection);
		if (addRobotOutput == "success") {
			robotArena.setStatus("not-drawn");
		} else {
			AlertMessage.show("Error", addRobotOutput, true);
		}
	}

	/**
	 * Convert dialog results from string to values Robot object can accept
	 * 
	 * @param xVal
	 *            x coordinate
	 * @param yVal
	 *            y coordinate
	 * @param dir
	 *            direction
	 * @param typeSelection
	 *            type of robot
	 */
	private static void setResults(String xVal, String yVal, String dir, String typeSelection) {
		// Set x
		if (!isNumeric(xVal)) {
			x = new Random().nextInt(robotArena.getXSize());
		} else {
			x = Integer.parseInt(xVal);
		}
		// Set y
		if (!isNumeric(yVal)) {
			y = new Random().nextInt(robotArena.getXSize());
		} else {
			y = Integer.parseInt(yVal);
		}
		// Set direction
		if (dir == "Random") {
			direction = Direction.getRandomDirection();
		} else {
			direction = Direction.valueOf(dir);
		}
		// Set robot type selection
		if (typeSelection == "Random") {
			robotSelection = RobotType.getRandom();
		} else {
			robotSelection = typeSelection;
		}
	}

	/**
	 * drawIt ... draws object defined by given image at position and size
	 * 
	 * @param i
	 *            image
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param sz
	 *            size
	 */
	private static void drawIt(String robotName, double x, double y, double sz) {
		gc.clearRect(0, 0, 90, 50); // clear canvas
		if (robotName != "Random") {
			// to draw centred at x,y, give top left position and x,y size

			gc.drawImage(new ArenaImage().getRobotImage(RobotType.getIndex(robotName)), x - sz / 2, y - sz / 2, sz, sz);
		}
	}
	
	/**
	 * Set robot's description
	 * @param robotName	for which robot to get description
	 */
	private static void setDescription(String robotName) {
		descLabel.setText(RobotType.getDescription(RobotType.getIndex(robotName)));
	}

	/**
	 * Checks if string is numeric
	 * https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	 * 
	 * @param str
	 *            String to check
	 * @return if numeric, true
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
