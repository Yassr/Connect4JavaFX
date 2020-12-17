package application;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * 
 * @author Yassr Shaar - 14328571
 * the EndScreen class displays the winner and loser of each game
 * Followed by the leaderboard of the winner, loser and if the game ended with a draw
 *
 */
public class EndScreen {

	private static Music endMusic = new Music("/media/endMusic.wav", true);
	private static Image backimage = new Image("/media/backbutton.png");
	private static ImageView vimg1 = new ImageView(backimage);

	
	
	
	public static Music getEndMusic() {
		return endMusic;
	}



	/**
	 * Displays the outcome of the game along with the leaderboard
	 * A button is available to review the state of the board when the game finished
	 * @return the gridPane to be displayed
	 */
	public static GridPane endPane() {
		
		GridPane gridPane = new GridPane();
		
		Button finalgridBtn = new Button("Review Final Board!");
		Button mainBtn = new Button("Main Menu");
		
		String winner = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
		String loser = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
		
		String winnerColour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		String loserColour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		TextArea leaderArea = new TextArea();
		
		
		Text winnerTxt = new Text("Congratulations: "+winner);
		winnerTxt.setFill(Color.web(winnerColour));
		
		winnerTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 28));
        winnerTxt.setEffect(GameDesign.lighting3D());
       
		
		Text loserTxt = new Text("Better Luck Next time " + loser);
		loserTxt.setFill(Color.web(loserColour));
		
		
		loserTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
		loserTxt.setEffect(GameDesign.lighting3D());
		
		
		
		mainBtn.setOnAction(e -> {
			endMusic.stop();
			GameMain.getStage().setScene(GameMain.getMainMenuScene());
		});
		
		
		
		
		
		/*
		 * Leaderboard implementation
		 */
		Future<List<String>> future;
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Leaderboard reader = new Leaderboard();
		
		future = executorService.submit(new Callable<List<String>>() {
			@SuppressWarnings("static-access")
			public List<String> call() throws Exception {
				return reader.read(new File("leaderboard.txt"));
			}
		});
		
		List<String> lines;
		try {
			lines = future.get();
			executorService.shutdownNow();
			leaderArea.clear();
			for (String line : lines ) {
				leaderArea.appendText(line + "\n");
			}
			
		} catch (InterruptedException | ExecutionException e1) {
			
			e1.printStackTrace();
		} 
		
		leaderArea.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
		leaderArea.setEditable(false);
		
		StackPane leaderPane = new StackPane(leaderArea);
		leaderPane.setPrefSize(280, 250);
		
		Label winlbl= new Label("Winner");
		Label loselbl= new Label("        Loser");
		HBox wbox = new HBox(70);
		
		winlbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 22));
		winlbl.setTextFill(Color.web(winnerColour));
		winlbl.setEffect(GameDesign.lighting3D());
		loselbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 22));
		loselbl.setTextFill(Color.web(loserColour));
		loselbl.setEffect(GameDesign.lighting3D());
		
		
		
		finalgridBtn.setOnAction(event -> {
			try {
				 
//				GameMain.getStage().setScene(GameMain.getMainscene());
				
				Pane finalpaneview = new Pane();
				
				finalpaneview.getChildren().add(Disc.getDiscRoot());
				
				Shape gridShape = GameDesign.makeGrid();
				
				
				HBox hbox = new HBox();
				hbox.setMinWidth((GameDesign.getColumns()-2) * GameDesign.getTileSize());
				hbox.setPadding(new Insets(5, 5, 5 ,5));
				
				Button back = new Button("Click to Back");
				vimg1.setFitHeight(30);
				vimg1.setPreserveRatio(true);
				back.setGraphic(vimg1);
				back.setBackground(new Background(new BackgroundFill(Color.rgb(0, 150, 150),CornerRadii.EMPTY,Insets.EMPTY)));
				
				// lighting
				Light.Distant light = new Light.Distant();
			    light.setAzimuth(60.0);
			    light.setElevation(80.0);
			
			    Lighting colourDepth = new Lighting();
			    colourDepth.setLight(light);
			    colourDepth.setSurfaceScale(5.0);
				
				
				hbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 150, 150),CornerRadii.EMPTY,Insets.EMPTY)));
				hbox.setTranslateY((GameDesign.getRows()+0.8) * GameDesign.getTileSize());
				hbox.setTranslateX(50);
						
				Text drawTxt = new Text("DRAW");
				
				drawTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 28));
				drawTxt.setEffect(GameDesign.lighting3D());
				
				//---------
				Scene endscene = new Scene(endPane(), 410, 500);
				back.setOnMouseClicked(e -> GameMain.getStage().setScene(endscene));
				
				//---------
				
				
				if(Disc.isDraw()) {
					hbox.getChildren().add(back);
					hbox.getChildren().add(drawTxt);
				}else {
					hbox.getChildren().add(back);
					hbox.getChildren().add(winnerTxt);
				}
				
				finalpaneview.getChildren().addAll(gridShape, hbox);
				gridShape.setDisable(true);
				
				GameMain.getStage().setScene(new Scene(finalpaneview));

            } catch (Exception e) {
                e.printStackTrace();
            }
		
		});
		
		
		
		HBox hbox = new HBox(30);
		
		finalgridBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		mainBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		

		gridPane.setPadding(new Insets(10, 10, 10 ,10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);
		
		
		

		Text drawtext = new Text("DRAW");
		drawtext.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		drawtext.setEffect(GameDesign.lighting3D());
		
		Text blnt = new Text ("Better Luck Next time");
		blnt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		blnt.setEffect(GameDesign.lighting3D());
		
		if(Disc.isDraw()) {
			gridPane.add(drawtext, 2, 0);
			gridPane.add(blnt, 2, 2);
			wbox.getChildren().addAll(winlbl,loselbl);
			gridPane.add(wbox, 2, 8);
			gridPane.add(leaderPane, 2, 10);
			hbox.getChildren().addAll(mainBtn,finalgridBtn);
			gridPane.add(hbox, 2, 15);
			
			return gridPane;
		}else {
			gridPane.add(winnerTxt, 2, 0);
			gridPane.add(loserTxt, 2, 2);
			wbox.getChildren().addAll(winlbl,loselbl);
			gridPane.add(wbox, 2, 8);
			gridPane.add(leaderPane, 2, 10);
			hbox.getChildren().addAll(mainBtn,finalgridBtn);
			gridPane.add(hbox, 2, 11);
			
			return gridPane;
		}
		
		
		
	}
	
	
	
	protected static void gameOver() {
		
		// Create a Timeline to slow down the transition onto the next music clip
		Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3.3))
        );
		
        timeline.setOnFinished(eg -> endMusic.loop());
        timeline.play();
		
		Leaderboard.writeLeaderBoard();
		
		Scene endscene = new Scene(endPane(), 410, 500);
		GameMain.getStage().setScene(endscene);
        
		// Checks if player 1 Won or Player 2
		System.out.println("Winner: " + (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName()));
        
        
        
	}

}
