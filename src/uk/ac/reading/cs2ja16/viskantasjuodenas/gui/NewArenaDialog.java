package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


/**
 * Class to open "New Arena" dialog
 */
public class NewArenaDialog {

	private static int width;
	private static int height;
	private static ArenaCanvas arenaCanvas;

	/**
	 * Open "New Arena" dialog
	 * @param canvas provide ArenaCanvas object to which new arena will be drawn
	 */
	public static void open(ArenaCanvas canvas) {
		arenaCanvas = canvas;
		
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Create a new Arena");

		// Set the button types.
		ButtonType addBtnType = new ButtonType("Create", ButtonData.APPLY);
		dialog.getDialogPane().getButtonTypes().addAll(addBtnType, ButtonType.CANCEL);

		// Set parent grid pane
		GridPane parentPane = new GridPane();
		parentPane.setHgap(10);
		parentPane.setVgap(10);
		parentPane.setPadding(new Insets(20, 150, 10, 10));

		// Grid pane for arena size
		GridPane sizePane = new GridPane();
		TextField widthField = new TextField();
		widthField.setText("10");
		widthField.setMaxWidth(100);
		widthField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if (!newValue.matches("\\d*")) {
						widthField.setText(newValue.replaceAll("[^\\d]", ""));
					}
				});
		TextField heightField = new TextField();
		heightField.setText("10");
		heightField.setMaxWidth(100);
		heightField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if (!newValue.matches("\\d*")) {
						widthField.setText(newValue.replaceAll("[^\\d]", ""));
					}
				});
		// Add text fields to the grid pane
		sizePane.add(new Label("Width:  "), 0, 0);
		sizePane.add(widthField, 1, 0);
		sizePane.add(new Label("   Height:  "), 2, 0);
		sizePane.add(heightField, 3, 0);

		// Add all grid panes to the parent pane
		parentPane.add(new Label("Set arena dimensions."), 0, 0);
		parentPane.add(sizePane, 0, 1);

		dialog.getDialogPane().setContent(parentPane);

		// Request focus on the widthField by default.
		Platform.runLater(() -> widthField.requestFocus());

		// Convert the result to a widthField when the create button is clicked
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addBtnType) {
				setResults(widthField.getText(), heightField.getText());
				arenaCanvas.newArena(width, height);
			}
			return null;
		});

		dialog.showAndWait();
	}
	
	/**
	 * Convert dialog results from string to values RobotArena object can accept
	 * 
	 * @param widthVal
	 *            width
	 * @param heightVal
	 *            height
	 */
	private static void setResults(String widthVal, String heightVal) {
		width = Integer.parseInt(widthVal);
		height = Integer.parseInt(heightVal);
	}

}
