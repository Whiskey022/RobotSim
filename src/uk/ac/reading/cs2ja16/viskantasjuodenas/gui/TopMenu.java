package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class TopMenu {

	private MenuBar menuBar;
	private RobotArena robotArena;

	/**
	 * MenuBar constructor, sets up MenuBar
	 */
	public TopMenu(RobotArena robotArena) {
		this.robotArena = robotArena;
		menuBar = setMenu();
	}

	/**
	 * Function to set up the MenuBar
	 * 
	 * @return returns MenuBar
	 */
	private MenuBar setMenu() {
		MenuBar menuBar = new MenuBar(); // create menu

		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0); // quit program
			}
		});
		mFile.getItems().addAll(mExit);

		Menu mObject = new Menu("Add Object");
		MenuItem miRobot = new MenuItem("Add custom Robot");
		miRobot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AddRobotDialog.open(robotArena); // Open Dialog
			}
		});
		MenuItem miObstacle = new MenuItem("Add custom Obstacle");
		miObstacle.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AddItemDialog.open(robotArena); // Open Dialog
			}
		});
		mObject.getItems().addAll(miRobot, miObstacle);

		Menu mHelp = new Menu("Help");
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
			}
		});
		MenuItem miHelp = new MenuItem("Help");
		miHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
			}
		});
		mHelp.getItems().addAll(mAbout, miHelp); // add sub-menus to Help

		menuBar.getMenus().addAll(mFile, mObject, mHelp);

		return menuBar;
	}

	/**
	 * @return menuBar
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
}
