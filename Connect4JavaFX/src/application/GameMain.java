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
	
	private static Pane mainroot = new Pane();
	private static Stage stage;
	private static Scene scene1 = new Scene(MainMenu.preference(), 450, 500);
	
	
	public static Scene getScene1() {
		return scene1;
	}
	

	public static Pane getMainroot() {
		return mainroot;
	}
	
	public static Stage getStage() {
		return stage;
	}


	@SuppressWarnings("static-access")
	public void setStage(Stage stage) { 
		this.stage = stage;
	}


	/**
	 * startGame() calls 
	 * @return
	 */
	public static Parent startGame() {

		// Disc Root goes before game grid to give the illusion of a 3D board.
		mainroot.getChildren().add(Disc.getDiscRoot());
		
		Shape gridShape = GameDesign.makeGrid();
		
		mainroot.getChildren().add(gridShape);
		mainroot.getChildren().add(Disc.getnamechng());
		
		mainroot.getChildren().addAll(GameDesign.selection());
		
		
		return mainroot;
	}
	
	

	@Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Connect 4");
		
		stage = primaryStage;

		
		primaryStage.setScene(scene1);
        primaryStage.show();
    }
	
	
	public static void main(String[] args) {
        launch(args);
    }
	

}
