package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;


/**
 * 
 * @author Yassr Shaar - 14328571
 * This is the Starting Point of the application
 * This class will create the scene and stage for the game and launch it.
 *
 */
public class GameMain extends Application{
	
	
	private static Stage stage;
	private static Scene mainMenuScene = new Scene(MainMenu.preference(), 450, 500);
	
	
	public static Scene getMainMenuScene() {
		return mainMenuScene;
	}
	

	
	public static Stage getStage() {
		return stage;
	}


	@SuppressWarnings("static-access")
	public void setStage(Stage stage) { 
		this.stage = stage;
	}


	
	
	/**
	 * Set the primaryStage title, set the Scene and show.
	 */
	@Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Connect 4");
		
		stage = primaryStage;
		
		primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
	
	/** 
	 * Launch the game
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }
	

}
