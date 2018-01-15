package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class Main extends Application {

	private int arenaWidth = 5;
	private int arenaHeight = 5;
	private int objectSize = (int) (140/Math.sqrt((Math.sqrt(arenaHeight * arenaWidth))));
	private int canvasWitdh = arenaWidth*objectSize;
	private int canvasHeight = arenaHeight*objectSize;
	private RobotArena robotArena;
	private ArenaCanvas robotCanvas;
	private static Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		primaryStage = stage;
		
		calculateDimensions();
		
		robotArena = new RobotArena(arenaWidth, arenaHeight);
		robotCanvas = new ArenaCanvas(canvasWitdh, canvasHeight, objectSize, robotArena);

		primaryStage.setTitle("Robot Simulator");

		BorderPane bp = new BorderPane();

		bp.setTop(new TopMenu(robotArena, robotCanvas).getMenuBar());
		bp.setCenter(robotCanvas.getGroup()); // put group in centre pane
		bp.setBottom(new BottomToolbar(robotArena, robotCanvas).getMenuBar()); /// add button to bottom

		Scene scene = new Scene(bp, canvasWitdh*1.2, canvasHeight*1.4);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	public void newArena(int arenaWidth, int arenaHeight) {
		this.arenaWidth = arenaWidth;
		this.arenaHeight = arenaHeight;
		robotArena = new RobotArena(arenaWidth, arenaHeight);
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	private void calculateDimensions() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenHeight = screenSize.getHeight();
		double screenWidth = screenSize.getWidth();
	}

}
