package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

/**
 * Button toolBar for GUI
 */
public class BottomToolbar {

	private HBox menuBox;
	private RobotArena robotArena;
	// Some icons for buttons
	private Image plusImage = new Image(getClass().getResourceAsStream("plus.png"));
	private Image arrowImage = new Image(getClass().getResourceAsStream("arrow.png"));
	private Image doubleArrowImage = new Image(getClass().getResourceAsStream("double_arrow.png"));
	private Image pauseImage = new Image(getClass().getResourceAsStream("pause.png"));
	private double defaultSpeed = 0.02;

	/**
	 * BottomMenu constructor, sets up an HBox of buttons
	 * 
	 * @param RobotArena
	 *            object
	 * @param ArenaCanvas
	 *            object to interact with
	 */
	public BottomToolbar(RobotArena robotArena, ArenaCanvas robotCanvas) {
		this.robotArena = robotArena;
		robotArena.setSpeed(defaultSpeed);

		menuBox = setButtons();
	}

	/**
	 * Create the bottom menu of buttons
	 * 
	 * @return a VBox of menu buttons
	 */
	private HBox setButtons() {
		HBox menuBox = new HBox();

		// "Button for adding a random robot
		Button randomRobotBtn = new Button("Random Robot");
		randomRobotBtn.setGraphic(new ImageView(plusImage));
		randomRobotBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addRobot();
			}
		});

		// "Button for adding a random robot
		Button randomItem = new Button("Random Item");
		randomItem.setGraphic(new ImageView(plusImage));
		randomItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addItem();
			}
		});

		// Button for moving robots only one position
		Button moveRobotsOnceBtn = new Button("Move Once");
		moveRobotsOnceBtn.setGraphic(new ImageView(arrowImage));
		moveRobotsOnceBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				robotArena.setStatus("move-once");
			}
		});

		// Button for moving robots continuously
		Button moveRobotsBtn = new Button("Move");
		moveRobotsBtn.setGraphic(new ImageView(doubleArrowImage));
		moveRobotsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				robotArena.setStatus("move-continuous");
			}
		});

		// Button to stop robots moving continuously
		Button stopRobotsBtn = new Button("Stop");
		stopRobotsBtn.setGraphic(new ImageView(pauseImage));
		stopRobotsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				robotArena.setStatus("stop-movement");
			}
		});

		// Button to speed up robots
		Button speedUpButton = new Button("Speed Up");
		speedUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				robotArena.setSpeed(robotArena.getSpeed() + defaultSpeed);
			}
		});

		// Button to reset speed
		Button resetSpeedButton = new Button("Reset speed");
		resetSpeedButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				robotArena.setSpeed(defaultSpeed);
			}
		});

		// Button to reset speed
		Button resetChargeButton = new Button("Reset charge");
		resetChargeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				robotArena.resetCharge();
			}
		});

		//Add all buttons to HBox
		menuBox.getChildren().addAll(randomRobotBtn, randomItem, moveRobotsOnceBtn, moveRobotsBtn, stopRobotsBtn,
				speedUpButton, resetSpeedButton, resetChargeButton);
		return menuBox;
	}

	/**
	 * Adding robot method, changes arena's status
	 */
	private void addRobot() {
		String addObjectOutput = robotArena.addRobot();
		if (addObjectOutput == "success") {
			robotArena.setStatus("not-drawn");
		} else {
			AlertMessage.show("Error", addObjectOutput, true);
		}
	}

	/**
	 * Adding item method, changes arena's status
	 */
	private void addItem() {
		String addObjectOutput = robotArena.addItem();
		if (addObjectOutput == "success") {
			robotArena.setStatus("not-drawn");
		} else {
			AlertMessage.show("Error", addObjectOutput, true);
		}
	}

	/**
	 * @return button toolbar
	 */
	public HBox getMenuBar() {
		return menuBox;
	}
}
