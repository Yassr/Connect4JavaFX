package application;

import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;



public class GameMain extends Application{
	
	private static Pane mainroot = new Pane();
	private static Stage stage;
	private static Scene scene1 = new Scene(MainMenu.preference(), 450, 500);
	
//	private static Scene mainscene= new Scene(startGame());
	
	public static Scene getScene1() {
		return scene1;
	}
	

//	public static Scene getMainscene() {
//		return mainscene;
//	}



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



	static Parent startGame() {

		// Disc Root goes before game grid to give the illusion of 3D gameplay.
		mainroot.getChildren().add(Disc.getDiscRoot());
		
		Shape gridShape = GameDesign.makeGrid();
		
//		Label player1 = new Label("Player1");
////		Label player2 = new Label("Player2");
//		
//		HBox hbox = new HBox(70);
//		
//		hbox.getChildren().add(player1);
//		
		
		
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
