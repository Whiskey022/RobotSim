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
import javafx.scene.image.Image;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.Wall;

/**
 * Class to open "Add Custom Robot" dialog
 */
public class AddObstacleDialog {

	private static GraphicsContext gc;
	private static int x;
	private static int y;
	private static String objectType;
	private static RobotArena robotArena;

	/**
	 * Open "Add Custom Robot" dialog
	 */
	public static void open(RobotArena arena) {
		robotArena = arena;
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add a custom obstacle");

		// Set the button types.
		ButtonType addBtnType = new ButtonType("Add", ButtonData.APPLY);
		dialog.getDialogPane().getButtonTypes().addAll(addBtnType, ButtonType.CANCEL);

		// Set parent grid pane
		GridPane parentPane = new GridPane();
		parentPane.setHgap(10);
		parentPane.setVgap(10);
		parentPane.setPadding(new Insets(20, 150, 10, 10));

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

		// Set up object selection box
		GridPane objectPane = new GridPane();
		ChoiceBox<String> objectBox = new ChoiceBox<String>();
		// Create a list of directions
		ObservableList<String> objectList = FXCollections.observableArrayList();
		objectList.add("Random");
		objectList.add("Wall");
		objectBox.setItems(objectList);
		objectBox.setValue("Random");
		// https://stackoverflow.com/questions/14522680/javafx-choicebox-events
		objectBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable,
				String oldValue, String newValue) -> drawIt(newValue, 40, 20, 40));
		// Set up Group object to draw robot image
		Group root = new Group();
		Canvas canvas = new Canvas(90, 50);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		// Add direction box to direction grid pane
		objectPane.add(new Label("Select object type:    "), 0, 0);
		objectPane.add(objectBox, 1, 0);
		objectPane.add(root, 2, 0);

		// Add all grid panes to the parent pane
		parentPane.add(new Label("Input obstacle coordinates"), 0, 0);
		parentPane.add(positionPane, 0, 1);
		parentPane.add(objectPane, 0, 2);

		dialog.getDialogPane().setContent(parentPane);

		// Request focus on the username field by default.
		Platform.runLater(() -> xField.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addBtnType) {
				setResults(xField.getText(), yField.getText(), objectBox.getValue());
				addRobot();
				robotArena.setStatus("not-drawn");
			}
			return null;
		});

		dialog.showAndWait();
	}

	private static void addRobot() {
		String addRobotOutput = robotArena.addObstacle(x, y, objectType);
		if (addRobotOutput == "success") {
			robotArena.setStatus("not-drawn");
		} else {
			AlertMessage alertMsg = new AlertMessageError(addRobotOutput);
			alertMsg.show();
		}
	}

	/**
	 * Convert dialog results from string to values Robot object can accept
	 * 
	 * @param xVal
	 *            x coordinate
	 * @param yVal
	 *            y coordinate
	 * @param type
	 *            object type
	 */
	private static void setResults(String xVal, String yVal, String type) {
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
		// Set object type
		objectType = type;
	}

	/**
	 * drawIt ... draws object defined by given image at position and size
	 * 
	 * @param objectType
	 *            object type to get image
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param sz
	 *            size
	 */
	private static void drawIt(String objectType, double x, double y, double sz) {
		gc.clearRect(0, 0, 90, 50); // clear canvas
		if (objectType != "Random") {
			// to draw centred at x,y, give top left position and x,y size
			gc.drawImage(getImage(objectType), x - sz / 2, y - sz / 2, sz, sz);
		}
	}
	
	private static Image getImage(String objectType) {
		switch(objectType) {
		default:
			return new Wall().getImage();
		}
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
