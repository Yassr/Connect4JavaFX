package application;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;





/*
 *	@TODO Create player class and take player1Move along with Disc.colour1
 *		  Take in user names.
 *	  	  Take in colour choice.
 * 		  Save score board.
 * 
 */


public class GameDesign {
	
	// Classic Connect Four is played on a Grid that is 6 high and 7 wide
	private static final int TILE_SIZE = 80;
	private static int COLUMNS = 7;
	private static int ROWS = 6;
	private static final int CIRCLE = TILE_SIZE / 2;
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static boolean player1Move= true;
	
	
	public static void createPlayer(String name, String colour) {
		
		players.add(new Player(name, colour));
		
		int counter = 0;
		
		for(Player player : players) {
			counter++;
			String fixName = player.getName();
			fixName = player.getName().replaceAll("\\s+","");
			
			if(fixName.isEmpty()) {
				player.setName("Player"+counter);
			}else {
				player.setName(fixName);
			}
		}
		
		//		players.get(0);
		
	}
	
	
	
	
	public static void setCOLUMNS(int cOLUMNS) {
		COLUMNS = cOLUMNS;
	}




	public static void setROWS(int rOWS) {
		ROWS = rOWS;
	}




	public static ArrayList<Player> getPlayers() {
		return players;
	}




	public static boolean isPlayer1move() {
		return player1Move;
	}
	


	public static void setPlayer1Move(boolean player1Move) {
		GameDesign.player1Move = player1Move;
	}




	public static int getTileSize() {
		return TILE_SIZE;
	}


	public static int getColumns() {
		return COLUMNS;
	}


	public static int getRows() {
		return ROWS;
	}


	public static int getCircle() {
		return CIRCLE;
	}
	
	
	
	public GameDesign(int circle, Object object) {
		// TODO Auto-generated constructor stub
	}

	
	static void handleButtonAction(ActionEvent event) {
		
		GameMain.getStage().setScene(GameMain.getMainscene());
	}
	
	


	static Shape makeGrid() {
		Shape board = new Rectangle((COLUMNS+1) * TILE_SIZE, (ROWS+1) * TILE_SIZE);
		
	
		for(int y = 0; y < ROWS; y++) {
			for(int x = 0; x < COLUMNS; x++) {
				Circle circle = new Circle(CIRCLE);
				
				circle.setCenterX(CIRCLE);
				circle.setCenterY(CIRCLE);
				circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
				circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);
				
				// punching holes from the rectangle using the circles created to create a Connect 4 Grid
				board = Shape.subtract(board, circle);
			}
		}
		
		// Example lighting from found in Light superclass
		Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting colourDepth = new Lighting();
        colourDepth.setLight(light);
        colourDepth.setSurfaceScale(5.0);
		
		board.setFill(Color.AQUA);
		board.setEffect(colourDepth);
		
		return board;
	}
	
	
	static List<Rectangle> selection(){
		List<Rectangle> list = new ArrayList<>();
		
		for(int x = 0; x < COLUMNS; x++) {
			Rectangle selector = new Rectangle(TILE_SIZE, (ROWS+1) * TILE_SIZE);
			selector.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
			
			selector.setFill(Color.TRANSPARENT);
			selector.setOnMouseEntered(e -> selector.setFill(Color.rgb(209, 209, 209, 0.3)));
			selector.setOnMouseExited(e -> selector.setFill(Color.TRANSPARENT));
			
			final int column = x;
			selector.setOnMouseClicked(e -> Disc.dropDisc(new Disc(player1Move), column));
			
			
			list.add(selector);
		}
		
		
		return list;
	}
	
	protected static boolean gameEnd(int column, int row) {
		// directions that result in a win
		
		/* Since we can win with a combination of 4, we need to check 3 off our point in each direction of our disc.
		* map to an object which is a point, to give a stream of rows 
		* we pass r rather than row as this is where the row is getting incremented, allowing us to create new points.
		* Collect to a list */
		List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3).mapToObj(r -> new Point2D(column, r)).collect(Collectors.toList());
		List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3).mapToObj(c -> new Point2D(c, row)).collect(Collectors.toList());
		
		// Diagonal is more tricky, requires checking from top left to bottom right & Bottom left to top right.
		Point2D topLeft = new Point2D(column - 3, row - 3);
		List<Point2D> diagonalT = IntStream.rangeClosed(0, 6).mapToObj(i -> topLeft.add(i, i)).collect(Collectors.toList());
		
		Point2D bottomLeft = new Point2D(column - 3, row + 3);
		List<Point2D> diagonalB = IntStream.rangeClosed(0, 6).mapToObj(i -> bottomLeft.add(i, -i)).collect(Collectors.toList());
		
		
		return checkWin(vertical) || checkWin(horizontal) || checkWin(diagonalT) || checkWin(diagonalB);
		
	}
	
	private static boolean checkWin(List<Point2D> points) {
		int combo = 0;
		
		for(Point2D p : points) {
			int column = (int) p.getX();
			int row = (int) p.getY();
			
			/* disc in this position, if there is no disc then we create a new disc 
			 * which is a different colour so that the check fails and the chain stops*/
			Disc disc = Disc.getDisc(column, row).orElse(new Disc(!player1Move));
			
			// Since there are only 2 colours, if it is the move of Yellow(Player 2) it will not be Red(Player 1)'s move
			// If the colour is yellow(colour2) then disc.isColour1() will be false
			if(disc.isColour1() == player1Move) {
				combo++;
				if(combo == 4) {
					return true;
				}
			}else {
				combo = 0;
			}
		}
		
		return false;
		
	}
	
	
	
	
}
