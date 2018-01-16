package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class for showing alert messages (error or info)
 */
public class AlertMessage {

	/**
	 * Show error or information alert message
	 * 
	 * @param title
	 *            title for alert box
	 * @param content
	 *            text to show on the alert box
	 * @param errorMessage
	 *            is it error message (information if false)
	 */
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
