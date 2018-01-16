package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessage{
	
	public static void show(String title, String content, boolean errorMessage) {
		Alert alert;
		if (errorMessage) {
			alert = new Alert(AlertType.ERROR);
		} else {
			alert = new Alert(AlertType.INFORMATION);
		}
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);

	    alert.showAndWait();
	}
}
