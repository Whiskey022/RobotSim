package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessageError extends AlertMessage{
	
	private String content;
	
	AlertMessageError(String content){
		this.content = content;
	}
	
	@Override
	public void show() {
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(null);
	    alert.setContentText(content);

	    alert.showAndWait();
	}
}
