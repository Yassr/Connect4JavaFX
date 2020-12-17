package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * 
 * @author Yassr Shaar - 14328571
 * The Class Disc deals with different aspects of the discs that are used to play the game
 * There are a few methods specifically designed to be called during the drop of the disc.
 */
public class Disc extends Circle{
	
	private boolean firstColour;
	private static Disc[][] grid = new Disc[GameDesign.getColumns()][GameDesign.getRows()];
	private static Pane discRoot = new Pane();
	private static Music discmusic = new Music("/audio/discDrop.wav", true);
	private static Music victory = new Music("/audio/victory.wav", true);
	private static Pane namechng = new Pane();
	private boolean turn = true;
	private static ArrayList<String> movescounter = new ArrayList<>();
	private static boolean isDraw = false;
	static MainMenu mm = new MainMenu();
	
	
	public static boolean isDraw() {
		return isDraw;
	}

	public static void setDraw(boolean isDraw) {
		Disc.isDraw = isDraw;
	}

	public static ArrayList<String> getMovescounter() {
		return movescounter;
	}

	boolean isFirstColour() {
		return firstColour;
	}
	
	static Pane getDiscRoot() {
		return discRoot;
	}
	
	static Pane getnamechng() {
		return namechng;
	}

	public Disc(){
		// An empty constructor for Disc() 
	}
	
	/** 
	 * firstColour is checked to determine which player's turn it is, once that is know, the colour is assigned to the disc to be dropped.
	 * @param firstColour this boolean checks where the disc being dropped belongs to the first player i.e. Player1's colour.
	 * The centerX and centerY are set so that the discs are dropped in the correct location which would be the Circles previously cut-out.
	 */
	public Disc(boolean colour1) {
		super(GameDesign.getCircle(), colour1 ? Color.web(GameDesign.getPlayers().get(0).getColour()) : Color.web(GameDesign.getPlayers().get(1).getColour()));
		this.firstColour = colour1;
		
		setCenterX(GameDesign.getCircle());
		setCenterY(GameDesign.getCircle());
		
	}
	
	/**
	 * Drop disc manages everything that occurs that allows a disc to drop and what happens WHILE the disc is dropping
	 * @param disc this is an instance of Disc which knows the colour associated with the player who's turn it is.
	 * @param column the column selected for dropping the disc
	 */
	static void dropDisc(Disc disc, int column) {
		Disc ds = new Disc();
		GameDesign gd = new GameDesign();
		
		int row = GameDesign.getRows() - 1; // Allows us to count from the maximum value so that discs can fall down
		
		while(row >= 0) {
			// Check if there is a disc in the column and row
			// If it is empty we can continue to place the disc
			if(!getDisc(column, row).isPresent()) {
				break; 
			}
			row--;
		}
			
		if(row < 0) {
			// If the player tries to place a disc while there is no space left they will receive a warning
			Alert winAlert = new Alert(AlertType.INFORMATION);
	        winAlert.setTitle("Not allowed");
	        winAlert.setHeaderText(null);
	        winAlert.setContentText("Can't place another disc here");
	        winAlert.show();
			return;
		}
		
		grid[column][row] = disc;

		discRoot.getChildren().add(disc);
		
		disc.setTranslateX(column * (GameDesign.getTileSize() + 5) + GameDesign.getTileSize() / 4);
		
		// Animation of disc dropping
		TranslateTransition dropTransition = new TranslateTransition(Duration.seconds(0.001), disc);
		dropTransition.setToY(row * (GameDesign.getTileSize() + 5) + GameDesign.getTileSize() / 4);
		
		// Play the disc drop music sound
		discmusic.play();
		
		// Ensures that while the animation of dropping a disc is occurring that the player can't drop multiple discs
		GameMain.getGameroot().setDisable(true);
		
	
		final int cRow = row; // Current row
		
		dropTransition.setOnFinished(e -> {
			
			// Check if the game is won
			if(GameDesign.gameEnd(column, cRow)) {
				
				// Stop the mainmenu music & play the victory sound clip
				mm.getGameplaymusic().stop();
				victory.play();

				EndScreen.gameOver();
			}
			
			// Switch players once the animation is over and the disc is placed
			GameDesign.setPlayer1Move(!GameDesign.isPlayer1move());
			
			ds.turn = !(ds.turn);
			
			if(!GameDesign.gameEnd(column, cRow) && GameMain.getGameroot().isDisabled()) {
				// Un-disables the pane to let the next player to take their turn. 
				GameMain.getGameroot().setDisable(false);
			}

			movescounter.add("COL "+column+"  ROW  " + cRow);
			System.out.println(movescounter.size());
			System.out.println(ds.isDraw());
			
			if(movescounter.size() == (gd.getRows()*gd.getColumns())) {
				ds.drawCheck();
				return;
			}
			
			
			
			// Player name change
			ds.playerTurn();
			
			
			
		});
		dropTransition.play();
	}
	
	
	/* check if we can place disc given the coordinates
	 * Return null if we don't have any discs on that position
	 */
	
	public static Optional<Disc> getDisc(int column, int row){
		if(column < 0 || column >= GameDesign.getColumns() || row < 0 || row >= GameDesign.getRows()) {
			return Optional.empty(); // .empty() ensures we don't get a null point exception!
		}
		
		return Optional.ofNullable(grid[column][row]);
		
	}	
	
	
	public void drawCheck() {
		Disc ds = new Disc();
		Alert winAlert = new Alert(AlertType.INFORMATION);
        winAlert.setTitle("Game Over!");
        winAlert.setHeaderText(null);
        winAlert.setContentText("DRAW");
        winAlert.show();
        ds.setDraw(true);
        System.out.println(ds.isDraw());
        EndScreen.gameOver();
	}
	
	public void playerTurn() {
		Disc ds = new Disc();
		GameDesign gd = new GameDesign();
	// --------------------Player Name change------------------------------------
	
		String player1Name = (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String player1Colour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		String player2Name = (!GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String player2Colour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		Label plbl = new Label("");
	
		
		if(!ds.turn) {
			plbl.setText("Turn :\t  "+ player2Name);
			plbl.setTextFill(Color.web(player2Colour));
			
			ds.turn = false;
			
		}else if(ds.turn){
	
			plbl.setText("Turn :\t  "+ player1Name);
			plbl.setTextFill(Color.web(player1Colour));
			
			ds.turn = true;
			
		}
	
		HBox hbox = new HBox();
		hbox.setMinWidth((gd.getColumns()-2) * gd.getTileSize());
		hbox.setPadding(new Insets(5, 5, 5 ,5));
		
		plbl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
			
		// lighting
		Light.Distant light = new Light.Distant();
	    light.setAzimuth(60.0);
	    light.setElevation(80.0);
	
	    Lighting colourDepth = new Lighting();
	    colourDepth.setLight(light);
	    colourDepth.setSurfaceScale(5.0);
		
	    plbl.setEffect(colourDepth);
		
		hbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 150, 150),CornerRadii.EMPTY,Insets.EMPTY)));
		hbox.setTranslateY((gd.getRows()+0.9) * gd.getTileSize());
		hbox.setTranslateX(2.5*gd.getTileSize());
				
		
		hbox.getChildren().add(plbl);
		
		namechng.getChildren().add(hbox);
		
		
		// --------------------------------------------------------
	}
	
}
