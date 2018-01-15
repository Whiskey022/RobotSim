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
	private ArenaCanvas arenaCanvas;
	private FileBrowser fileBrowser = new FileBrowser();

	/**
	 * MenuBar constructor, sets up MenuBar
	 */
	public TopMenu(RobotArena robotArena, ArenaCanvas arenaCanvas) {
		this.robotArena = robotArena;
		this.arenaCanvas = arenaCanvas;
		menuBar = setMenu();
	}

	/**
	 * Function to set up the MenuBar
	 * 
	 * @return returns MenuBar
	 */
	private MenuBar setMenu() {
		MenuBar menuBar = new MenuBar(); // create menu

		Menu mArena = new Menu("Arena");
		MenuItem miNew = new MenuItem("New Arena");
		miNew.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				NewArenaDialog.open(arenaCanvas);
			}
		});
		MenuItem miSave = new MenuItem("Save Arena");
		miSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				fileBrowser.browse(FileBrowser.BrowserMode.SAVE);
				fileBrowser.saveFile(robotArena.getDetails());
			}
		});
		MenuItem miLoad = new MenuItem("Load Arena");
		miLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				fileBrowser.browse(FileBrowser.BrowserMode.LOAD);
				String loadedData = fileBrowser.loadFile();
				if (loadedData.length() > 0) {
					String[] lines = loadedData.split("\\|");
					// Extract arena details
					String[] values = lines[0].split(":");
					arenaCanvas.newArena(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					robotArena.loadArena(loadedData);
				}
			}
		});
		MenuItem miShowGrid = new MenuItem();
		if (arenaCanvas.getShowGrid()) {
			miShowGrid.setText("Hide Grid");
		} else {
			miShowGrid.setText("Show Grid");
		}
		miShowGrid.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				arenaCanvas.setShowGrid(!arenaCanvas.getShowGrid());
				if (arenaCanvas.getShowGrid()) {
					miShowGrid.setText("Hide Grid");
				} else {
					miShowGrid.setText("Show Grid");
				}
			}
		});
		mArena.getItems().addAll(miNew, miSave, miLoad, miShowGrid);

		Menu mObject = new Menu("Add Object");
		MenuItem miRobot = new MenuItem("Add custom Robot");
		miRobot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AddRobotDialog.open(robotArena); // Open Dialog
			}
		});
		MenuItem miItem = new MenuItem("Add custom Item");
		miItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AddItemDialog.open(robotArena); // Open Dialog
			}
		});
		mObject.getItems().addAll(miRobot, miItem);

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

		menuBar.getMenus().addAll(mArena, mObject, mHelp);

		return menuBar;
	}

	/**
	 * @return menuBar
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
}
