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
	
	private static Pane gameroot = new Pane();
	private static Stage stage;
	private static Scene mainMenuScene = new Scene(MainMenu.preference(), 450, 500);
	
	
	public static Scene getMainMenuScene() {
		return mainMenuScene;
	}
	

	public static Pane getGameroot() {
		return gameroot;
	}
	
	public static Stage getStage() {
		return stage;
	}


	@SuppressWarnings("static-access")
	public void setStage(Stage stage) { 
		this.stage = stage;
	}


	/**
	 * startGame() adds the Discs layer followed by the grid on top of it 
	 * then the different player names that change based on turns
	 * and finally the selection high lighter is added on top of them all
	 * 
	 * @return the gameroot of the game
	 */
	public static Parent startGame() {

		// Disc Root goes before game grid to give the illusion of a 3D board.
		gameroot.getChildren().add(Disc.getDiscRoot());
		
		Shape gridShape = GameDesign.makeGrid();
		
		gameroot.getChildren().add(gridShape);
		gameroot.getChildren().add(Disc.getnamechng());
		gameroot.getChildren().addAll(GameDesign.selector());
		
		return gameroot;
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
