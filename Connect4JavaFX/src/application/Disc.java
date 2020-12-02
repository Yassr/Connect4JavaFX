package application;

import java.util.Optional;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/*
 * 
 */

class Disc extends Circle{
	
	private final boolean colour1;
	private static Disc[][] grid = new Disc[GameDesign.getColumns()][GameDesign.getRows()];
	private static Pane discRoot = new Pane();
	
	boolean isColour1() {
		return colour1;
	}
	
	
	
	static Pane getDiscRoot() {
		return discRoot;
	}

//	GameDesign.getPlayers().get(0).getColour()

	Disc(boolean colour1) {
		super(GameDesign.getCircle(), colour1 ? Color.web(GameDesign.getPlayers().get(0).getColour()) : Color.web(GameDesign.getPlayers().get(1).getColour()));
		this.colour1 = colour1;
		
		setCenterX(GameDesign.getCircle());
		setCenterY(GameDesign.getCircle());
		
	}
	
	static void dropDisc(Disc disc, int column) {
		int row = GameDesign.getRows() - 1; // Allows us to count from the maximum value so that discs can fall down
		do {
			// Check if there is a disc in the column and row
			// If it is empty we can continue to place the disc
			if(!getDisc(column, row).isPresent()) {
				break; 
			}
				 
			row--;
			
		}while(row >= 0);
			
		if(row < 0) {
//			Alert winAlert = new Alert(AlertType.INFORMATION);
//	        winAlert.setTitle("Game Over!");
//	        winAlert.setHeaderText(null);
//	        winAlert.setContentText("DRAW");
//	        winAlert.show();
			return;
		}
		
		
		
		grid[column][row] = disc;
		
		if(row < 0 || column < 0) {
			Alert winAlert = new Alert(AlertType.INFORMATION);
	        winAlert.setTitle("Game Over!");
	        winAlert.setHeaderText(null);
	        winAlert.setContentText("DRAW");
	        winAlert.show();
			return;
		}
		
		discRoot.getChildren().add(disc);
		disc.setTranslateX(column * (GameDesign.getTileSize() + 5) + GameDesign.getTileSize() / 4);
		
		// Animation of disc dropping
		TranslateTransition drop = new TranslateTransition(Duration.seconds(0.0001), disc);
		drop.setToY(row * (GameDesign.getTileSize() + 5) + GameDesign.getTileSize() / 4);
		
		// Ensures that while the animation of dropping a disc is occurring that the player can't drop multiple discs
		GameMain.getMainroot().setDisable(true);
		
		
		final int cRow = row; // Current row
		drop.setOnFinished(e -> {
			if(GameDesign.gameEnd(column, cRow)) {
				EndScreen.gameOver();
			}
			
			// Switch players once the animation is over and the disc is placed
			GameDesign.setPlayer1Move(!GameDesign.isPlayer1move());
			// Un-disables the pane to let the next player to take their turn. 
			GameMain.getMainroot().setDisable(false);
		});
		drop.play();
	}
	
	
	/* check if we can place disc given the coordinates
	 * Return null if we don't have any discs on that position
	 */
	
	static Optional<Disc> getDisc(int column, int row){
		if(column < 0 || column >= GameDesign.getColumns() || row < 0 || row >= GameDesign.getRows()) {
			return Optional.empty(); // .empty() ensures we don't get a null point exception!
		}
		
		return Optional.ofNullable(grid[column][row]);
		
	}

	
	
	
	
	
}
