package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessageInfo extends AlertMessage{
	
	private String content;
	private String title = "Information";
	
	AlertMessageInfo(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	AlertMessageInfo(String content) {
		this.content = content;
	}
	
	@Override
	public void show() {
		Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);

	    alert.showAndWait();
	}
}
