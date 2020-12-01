package application;

import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets; 



public class GameMain extends Application{
	
	private static Pane mainroot = new Pane();
	private static Stage stage;
	


	public static Pane getMainroot() {
		return mainroot;
	}
	
	public static Stage getStage() {
		return stage;
	}



	public void setStage(Stage stage) {
		this.stage = stage;
	}



	static Parent startGame() {

		// Disc Root goes before game grid to give the illusion of 3D gameplay.
		mainroot.getChildren().add(Disc.getDiscRoot());
		
		Shape gridShape = GameDesign.makeGrid();
		
		mainroot.getChildren().add(gridShape);
		mainroot.getChildren().addAll(GameDesign.selection());
		
		
		return mainroot;
	}
	
	
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Connect 4");
		
		stage = primaryStage;


		Scene scene1 = new Scene(MainMenu.preference(), 450, 500);
		
		primaryStage.setScene(scene1);
        primaryStage.show();
    }
	
	
	public static void main(String[] args) {
        launch(args);
    }
	

}
