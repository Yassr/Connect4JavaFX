package application;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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

public class EndScreen {
	
	private static String winner = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
	private static String loser = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
	

	
	public static String getWinner() {
		return winner;
	}

	public static String getLoser() {
		return loser;
	}
	
	static GridPane endPane() {
		
		GridPane gridPane = new GridPane();
		
		Button resetBtn = new Button("Rematch!");
		Button mainBtn = new Button("Main Menu");
		
		String winnerName = (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String winnerColour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		String loserName = (!GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String loserColour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		TextArea leaderArea = new TextArea();
		
		
		Text winnerTxt = new Text("Congratulations: "+winnerName);
		winnerTxt.setFill(Color.web(winnerColour));
		
		winnerTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
        winnerTxt.setEffect(GameDesign.lighting3D());
       
		
		Text loserTxt = new Text("Better Luck Next time " + loserName);
		loserTxt.setFill(Color.web(loserColour));
		
		
		loserTxt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
		loserTxt.setEffect(GameDesign.lighting3D());
		
		resetBtn.setOnAction(event -> {
			try {
				 
//				GameMain.getStage().setScene(GameMain.getMainscene());
				
				Pane restartpane = new Pane();
				
				restartpane.getChildren().add(Disc.getDiscRoot());
				
				Shape gridShape = GameDesign.makeGrid();
				
				restartpane.getChildren().add(gridShape);
				restartpane.getChildren().addAll(GameDesign.selection());
				
				GameMain.getStage().setScene(new Scene(restartpane));

            } catch (Exception e) {
                e.printStackTrace();
            }
		
		});
		
		mainBtn.setOnAction(e -> {
			GameMain.getStage().setScene(GameMain.getScene1());
			// GameMain.getStage().setScene(new Scene(GameMain.getMainroot()));
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
		leaderPane.setMinSize(280, 250);
		
		
		Label winlbl= new Label("Winner");
		Label loselbl= new Label("        Loser");
		HBox wbox = new HBox(70);
		
		winlbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 22));
		winlbl.setTextFill(Color.web(winnerColour));
		winlbl.setEffect(GameDesign.lighting3D());
		loselbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 22));
		loselbl.setTextFill(Color.web(loserColour));
		loselbl.setEffect(GameDesign.lighting3D());
		
		HBox hbox = new HBox(70);
		
		resetBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		mainBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		

		gridPane.setPadding(new Insets(10, 10, 10 ,10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);
		
		
		gridPane.add(winnerTxt, 2, 0);
		gridPane.add(loserTxt, 2, 2);
		wbox.getChildren().addAll(winlbl,loselbl);
		gridPane.add(wbox, 2, 8);
		gridPane.add(leaderPane, 2, 10);
		hbox.getChildren().addAll(mainBtn,resetBtn);
		gridPane.add(hbox, 2, 15);
		
		
		return gridPane;
	}
	
	
	
	protected static void gameOver() {
		Leaderboard.writeLeaderBoard();
//		Leaderboard.getLeaderboard().add(winner+" "+"Winner"+" "+ loser + " " + "Loser");
		Scene endscene = new Scene(endPane(), 450, 500);
		
		GameMain.getStage().setScene(endscene);
        
		// Checks if player 1 Won or Player 2
		System.out.println("Winner: " + (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName()));
        
        
        
	}

}
