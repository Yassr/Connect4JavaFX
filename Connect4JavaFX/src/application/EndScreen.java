package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EndScreen {
	
	
	static GridPane endPane() {
		
		GridPane gridPane = new GridPane();
		
		Button resetBtn = new Button("Rematch!");
		Button mainBtn = new Button("Main Menu");
		
		String winnerName = (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String winnerColour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		String loserName = (!GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String loserColour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		Label titlelbl = new Label("Congratulations "+winnerName+"!!!");
		titlelbl.setTextFill(Color.web(winnerColour));
		
		// Example lighting from found in Light superclass
		Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting colourDepth = new Lighting();
        colourDepth.setLight(light);
        colourDepth.setSurfaceScale(5.0);
		
        titlelbl.setEffect(colourDepth);
       
		
		// Creating Text input
		Text loserTxt = new Text("Loser: " + loserName);
		loserTxt.setFill(Color.web(loserColour));
		
		
		loserTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		loserTxt.setEffect(colourDepth);
		
		resetBtn.setOnAction(event -> {
			try {
				 
				GameMain.getStage().setScene(GameMain.getMainscene());

            } catch (Exception e) {
                e.printStackTrace();
            }
		
		});
		
		mainBtn.setOnAction(e -> {
			GameMain.getStage().setScene(GameMain.getScene1());
			// GameMain.getStage().setScene(new Scene(GameMain.getMainroot()));
		});
		
		
		titlelbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
		
		HBox hbox = new HBox(70);
		
		resetBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		mainBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		

		gridPane.setPadding(new Insets(10, 10, 10 ,10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);
		
		
		gridPane.add(titlelbl, 2, 0);
		gridPane.add(loserTxt, 2, 2);
		hbox.getChildren().addAll(mainBtn,resetBtn);
		gridPane.add(hbox, 2, 40);
		
		
		return gridPane;
	}
	
	
	
	
	
	
	protected static void gameOver() {
		
		Scene endscene = new Scene(endPane(), 450, 500);
		
		GameMain.getStage().setScene(endscene);
        
		// Checks if player 1 Won or Player 2
		System.out.println("Winner: " + (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName()));
        
//		Alert winAlert = new Alert(AlertType.INFORMATION);
//        winAlert.setTitle("Game Over!");
//        winAlert.setHeaderText(null);
//        winAlert.setContentText("Winner: " + (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName()));
//        winAlert.show();
        
        
	}

}
