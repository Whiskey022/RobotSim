package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;

public class TopMenu {
	
	private MenuBar menuBar;
	
	/**
	 * MenuBar constructor, sets up MenuBar
	 */
	TopMenu() {
		menuBar = setMenu();
	}
	
	/**
	 * Function to set up the MenuBar
	 * @return	returns MenuBar	
	 */
	private MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();		// create menu

		Menu mHelp = new Menu("Help");			// have entry for help
				// then add sub menus for About and Help
				// add the item and then the action to perform
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();				// show the about message
            }	
		});
		MenuItem miHelp = new MenuItem("Help");
		miHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showHelp();
            }	
		});
		mHelp.getItems().addAll(mAbout, miHelp); 	// add submenus to Help
		
				// now add File menu, which here only has Exit
		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);						// quit program
		    }
		});
		mFile.getItems().addAll(mExit);
		
		menuBar.getMenus().addAll(mFile, mHelp);	// menu has File and Help
		
		return menuBar;					// return the menu, so can be added
	}
	
	
	/**
	 * "Show About" message
	 */
	private void showAbout() {
		showMessage("About", "RJM's BorderPane Demonstrator");
	}
	
	/**
	 * "Show Help" message
	 */
	private void showHelp() {
		showMessage("Help", "Press Random Earth to draw an Earth at a random angle, or click on canvas to draw Earth there");
	}
	
	/**
	 * Sets up an alert message
	 * @param	TStr	alert message title
	 * @param	CStr	alert message content
	 */
	private void showMessage(String TStr, String CStr) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(TStr);
	    alert.setHeaderText(null);
	    alert.setContentText(CStr);

	    alert.showAndWait();
	}
	
	/**
	 * @return menuBar
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
}
