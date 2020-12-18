package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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

	private static final Music endMusic = new Music("/media/endMusic.wav", true);
	private static final Image backimage = new Image("/media/backbutton.png");
	private static final ImageView vimg1 = new ImageView(backimage);

	private static ArrayList<String> textread = new ArrayList<String>();
	
	
	
	/**
	 * This method is called when the game is determined to be over.
	 * It starts off by waiting a few seconds then playing the end music
	 * Then calls the method writeLeaderBoard() from Class Leaderboard
	 * Finally the scene is set
	 */
	protected static void gameOver() {
		
		// Create a Timeline to slow down the transition onto the next music clip
		Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3.5))
        );
		// After waiting 3.5 seconds the end music is played in a loop
        timeline.setOnFinished(eg -> endMusic.loop());
        timeline.play();
		
        // The leaderboard is written onto
		Leaderboard.writeLeaderBoard();
		
		Scene endscene = new Scene(endPane(), 410, 500);
		GameMain.getStage().setScene(endscene);
        
	}
	

	/**
	 * Displays the outcome of the game along with the leaderboard
	 * Two buttons are available, one to return to main menu and play again, 
	 * another to review the state of the board when the game finished
	 * @return the gridPane to be displayed
	 */
	public static GridPane endPane() {
		
		GridPane gridPane = new GridPane();
		
		// Create the two buttons for the screen
		Button finalgridBtn = new Button("Review Final Board!");
		Button mainBtn = new Button("Main Menu");

		// Get winner and loser names
		String winner = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
		String loser = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
		// Get winner and loser colours
		String winnerColour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		String loserColour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		
		// Winner and loser messages
		Text winnerTxt = new Text("Congratulations: "+winner);
		winnerTxt.setFill(Color.web(winnerColour));
		winnerTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 28));
        winnerTxt.setEffect(GameDesign.lighting3D());
       
		
		Text loserTxt = new Text("Better Luck Next time " + loser);
		loserTxt.setFill(Color.web(loserColour));
		loserTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
		loserTxt.setEffect(GameDesign.lighting3D());
		
		// Go back to Main menu button, first stops end music then changes scenes
		mainBtn.setOnAction(e -> {
			endMusic.stop();
			GameMain.getStage().setScene(GameMain.getMainMenuScene());
		});
		
		// Calls function to print the leaderboard onto the text area
		TextArea leaderArea = printLeaderTextArea(); 
		
	
		
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
		
		Text drawtext = new Text("DRAW");
		drawtext.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		drawtext.setEffect(GameDesign.lighting3D());
		
		Text blnt = new Text ("Better Luck Next time");
		blnt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		blnt.setEffect(GameDesign.lighting3D());
		
		
		// Button that displays the final state of game's pane
		finalgridBtn.setOnAction(event -> {
			try {
				textread.clear();
				// Create new pane and add the game root
				Pane finalpaneview = new Pane();
				finalpaneview.getChildren().add(Disc.getDiscRoot());
				
				// Create a new shape that takes on the previous grid details
				Shape gridShape = GameDesign.makeGrid();
				
				HBox detailsBox = new HBox();
				detailsBox.setMinWidth((GameDesign.getColumns()-2) * GameDesign.getTileSize());
				detailsBox.setPadding(new Insets(5, 5, 5 ,5));
				
				// Back button for going back to the End Screen
				Button backBtn = new Button("Click to Back");
				vimg1.setFitHeight(30);
				vimg1.setPreserveRatio(true);
				backBtn.setGraphic(vimg1);
				backBtn.setBackground(new Background(new BackgroundFill(Color.rgb(0, 150, 150),CornerRadii.EMPTY,Insets.EMPTY)));
				
				// lighting
				Light.Distant light = new Light.Distant();
			    light.setAzimuth(60.0);
			    light.setElevation(80.0);
			
			    Lighting colourDepth = new Lighting();
			    colourDepth.setLight(light);
			    colourDepth.setSurfaceScale(5.0);
				
				detailsBox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 150, 150),CornerRadii.EMPTY,Insets.EMPTY)));
				detailsBox.setTranslateY((GameDesign.getRows()+0.8) * GameDesign.getTileSize());
				detailsBox.setTranslateX(50);
				
				
				Text drawTxt = new Text("DRAW");
				drawTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 28));
				drawTxt.setEffect(GameDesign.lighting3D());
				
				// backButton action event, go back to end screen
				Scene endscene = new Scene(endPane(), 410, 500);
				backBtn.setOnMouseClicked(e -> GameMain.getStage().setScene(endscene));

				
				// Check's if the game resulted in a Draw or a win.
				if(Disc.isDraw()) {
					// if the game is a draw then Draw is displayed
					detailsBox.getChildren().add(backBtn);
					detailsBox.getChildren().add(drawTxt);
				}else {
					// Else the winner is displayed
					detailsBox.getChildren().add(backBtn);
					detailsBox.getChildren().add(winnerTxt);
				}

				finalpaneview.getChildren().addAll(gridShape, detailsBox);
				// Disable the grid Pane so no further input can be taken in
				gridShape.setDisable(true);
				
				GameMain.getStage().setScene(new Scene(finalpaneview));

            } catch (Exception e) {
                e.printStackTrace();
            }
		
		});
		
		
		// Horizontal box made for the buttons with a distance of 30 between them
		HBox buttonsBox = new HBox(30);
		
		finalgridBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		mainBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		
		// Adds padding and gaps between elements in the gridPane
		gridPane.setPadding(new Insets(10, 10, 10 ,10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);
			
		//Depending on if the game resulted in a Draw or a win different elements are displayed on the screen
		if(Disc.isDraw()) {
			gridPane.add(drawtext, 2, 0);
			gridPane.add(blnt, 2, 2);
			
		}else {
			gridPane.add(winnerTxt, 2, 0);
			gridPane.add(loserTxt, 2, 2);
			
		}
		
		// Add all of the remainder items to be viewed on the gridPane
		wbox.getChildren().addAll(winlbl,loselbl);
		gridPane.add(wbox, 2, 8);
		gridPane.add(leaderPane, 2, 10);
		buttonsBox.getChildren().addAll(mainBtn,finalgridBtn);
		gridPane.add(buttonsBox, 2, 15);
		
		return gridPane;
		
	}


	public static TextArea printLeaderTextArea() {
		// Leader board text area
		TextArea leaderArea = new TextArea();
		Leaderboard reader = new Leaderboard();
		
		/*
		 * Leaderboard implementation
		 * Future is used to make a promise that there will be a list of strings returned
		 */
		
		Iterator<String> i = textread.iterator();
		
		while (i.hasNext()) {
			   String str = i.next(); // must be called before you can call i.remove()
			   // remove any previous entries in the leaderboard
			   i.remove();
			}
		
		textread = reader.readFile(new File("leaderboard.txt"));
		
		
		Future<List<String>> future;
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		final List<String> readable = textread;
		future = executorService.submit(new Callable<List<String>>() {
			@SuppressWarnings("static-access")
			public List<String> call() throws Exception {
				return readable;
			}
		});
		
		List<String> lines;
		try {
			lines = future.get();
			// Allows all the lines to be retrieved before terminating 
			executorService.shutdownNow();
			leaderArea.clear();
			for (String line : lines ) {
				// Prints the results of each line from the leaderboard.txt
				leaderArea.appendText(line + "\n");
			}	
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
		return leaderArea;
	}
	
	
	

}