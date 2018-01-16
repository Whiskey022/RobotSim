package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

/**
 * Creates the top menu for GUI
 */
public class TopMenu {

	private static RobotArena robotArena;
	private static ArenaCanvas arenaCanvas;
	private static FileBrowser fileBrowser = new FileBrowser();

	/**
	 * Create and get top menu bar
	 * 
	 * @param arena
	 *            robotArena object to access
	 * @param canvas
	 *            arenaCanvas object to access
	 * @return returns the MenuBar for the GUI
	 */
	public static MenuBar get(RobotArena arena, ArenaCanvas canvas) {
		robotArena = arena;
		arenaCanvas = canvas;
		MenuBar menuBar = new MenuBar();
		//Add all menu sections to the menu bar
		menuBar.getMenus().addAll(arenaMenu(), objectMenu(), helpMenu());
		return menuBar;
	}

	/**
	 * @return a menu for changing robots arena
	 */
	private static Menu arenaMenu() {
		Menu mArena = new Menu("Arena");
		
		//Menu item for new arena
		MenuItem miNew = new MenuItem("New Arena");
		miNew.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				NewArenaDialog.open(arenaCanvas);
			}
		});
		
		//Menu item for saving arena
		MenuItem miSave = new MenuItem("Save Arena");
		miSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//Open file browser to save file
				fileBrowser.browse(FileBrowser.BrowserMode.SAVE);
				fileBrowser.saveFile(robotArena.getDetails());
			}
		});
		
		//Menu item for loading arena
		MenuItem miLoad = new MenuItem("Load Arena");
		miLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//Open file browser to load file
				fileBrowser.browse(FileBrowser.BrowserMode.LOAD);
				String loadedData = fileBrowser.loadFile();
				//If it did load some data
				if (loadedData.length() > 0) {
					//Split text to get arena dimensions
					String[] lines = loadedData.split("\\|");
					// Separate x and y 
					String[] values = lines[0].split(":");
					//Create new arena and load objects data to it
					arenaCanvas.newArena(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					robotArena.loadArena(loadedData);
				}
			}
		});
		
		//Menu item for showing or hiding grid
		MenuItem miShowGrid = new MenuItem();
		//Change menu item text according to the current setting
		if (arenaCanvas.getShowGrid()) {
			miShowGrid.setText("Hide Grid");
		} else {
			miShowGrid.setText("Show Grid");
		}
		miShowGrid.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//Change arena setting for showing grid
				arenaCanvas.setShowGrid(!arenaCanvas.getShowGrid());
				//Change menu item's text as well
				if (arenaCanvas.getShowGrid()) {
					miShowGrid.setText("Hide Grid");
				} else {
					miShowGrid.setText("Show Grid");
				}
			}
		});
		
		//Add all menu items to menu
		mArena.getItems().addAll(miNew, miSave, miLoad, miShowGrid);
		return mArena;
	}

	/**
	 * @return a menu for adding objects
	 */
	private static Menu objectMenu() {
		Menu mObject = new Menu("Add Object");
		
		//Menu item for adding a custom robot
		MenuItem miRobot = new MenuItem("Add custom Robot");
		miRobot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AddRobotDialog.open(robotArena); // Open Dialog
			}
		});
		
		//Menu item for adding a custom item
		MenuItem miItem = new MenuItem("Add custom Item");
		miItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AddItemDialog.open(robotArena); // Open Dialog
			}
		});
		
		//Add menu items to menu
		mObject.getItems().addAll(miRobot, miItem);
		return mObject;
	}

	/**
	 * 
	 * @return a menu for help/about
	 */
	private static Menu helpMenu() {
		Menu mHelp = new Menu("Help");
		
		//Menu item for about alert
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				AlertMessage.show("About: Robot Sim",
						"A customizable 2d robot simulator. Design your own robot arenas by adding different robots and items and see how the simulated robots' movement plays out.",
						false);
			}
		});
		
		//Menu item for help alert
		MenuItem miHelp = new MenuItem("Help");
		miHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				AlertMessage.show("Help: Robot Sim",
						"To add random objects please use the toolbar options 'Random Robot' and 'Random Item'.\nTo add new custom objects use 'Add Object' menu item.\nTo play robot simulation, use 'Move Once' or 'Move' toolbar buttons.\nTo change simulation speed use 'Speed Up' and 'Reset Speed' toolbar buttons.\nTo create new, save or load new arenas, use 'Arena' menu item.",
						false);
			}
		});
		
		//Add all items to menu
		mHelp.getItems().addAll(mAbout, miHelp); // add sub-menus to Help
		return mHelp;
	}
}
