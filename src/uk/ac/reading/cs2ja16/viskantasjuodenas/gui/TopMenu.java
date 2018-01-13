package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

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
		
		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);						// quit program
		    }
		});
		mFile.getItems().addAll(mExit);

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
		mHelp.getItems().addAll(mAbout, miHelp); 	// add sub-menus to Help	
		
		menuBar.getMenus().addAll(mFile, mHelp);
		
		return menuBar;
	}
	
	/**
	 * @return menuBar
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
}
