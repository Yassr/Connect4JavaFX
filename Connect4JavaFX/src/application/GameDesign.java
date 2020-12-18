package application;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.geometry.Point2D;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/** 
 * 
 * @author Yassr Shaar - 14328571
 * This class focuses on the main game and everything involved in its gameplay
 * Visuals: creating the board, Selector and lighting effects
 * Gameplay: Creating players, Check for winner and end of game.
 */
public class GameDesign {
	
	// Classic Connect Four is played on a Grid that is 6 high and 7 wide
	private static final int TILE_SIZE = 80;
	private static int COLUMNS = 7;
	private static int ROWS = 6;
	private static final int CIRCLE = TILE_SIZE / 2;
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static boolean player1Move= true;
	private Rectangle selector = new Rectangle(TILE_SIZE, (ROWS+1) * TILE_SIZE);
	

	public Rectangle getSelector() {
		return selector;
	}

	public void setSelector(Rectangle selector) {
		this.selector = selector;
	}
	
	public static void setCOLUMNS(int columns) {
		COLUMNS = columns;
	}


	public static void setROWS(int rows) {
		ROWS = rows;
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
	
	
	public GameDesign() {
		// Empty constructor
	}

	
	/**
	 * makeGrid starts off by creating a shape for the board
	 * board is a rectangle that takes columns by tile size and rows by tile size
	 * There are some added pixels to allow for areas of the board that can be manipulated
	 * @return the Shape of the board
	 */
	public static Shape makeGrid() {
		Shape board = new Rectangle((COLUMNS+1) * TILE_SIZE-10, (ROWS+1) * TILE_SIZE+50);

		for(int y = 0; y < ROWS; y++) {
			for(int x = 0; x < COLUMNS; x++) {
				Circle circle = new Circle(CIRCLE);
				// looping through and setting a center for each x and y value
				// to allow for a uniform cropping of the board
				
				circle.setCenterX(CIRCLE);
				circle.setCenterY(CIRCLE);
				circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
				circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);
				
				// punching holes from the rectangle board using the circles created to create a Connect 4 Grid pattern
				board = Shape.subtract(board, circle);
			}
		}

		// Set the colour for the board and add some lighting effects to make it appear 3D
		board.setFill(Color.AQUA);
		board.setEffect(lighting3D());
		
		return board;
	}
	
	/**
	 * The selector highlights the column that the player is hovering over to better visualise where the disc will drop.
	 * @return Returns a list containing the x values being highlighted by the selector.
	 */
	public static List<Rectangle> selector(){
		// Create a list of type Rectangle
		List<Rectangle> list = new ArrayList<>();
		
		/* Choose all of the rows within the column
		 * that the mouse is hovering over 
		 * by giving this rectangle a colour (that is slightly transparent
		 * we can create a selector so players know where they are on the board.
		 */
		for(int x = 0; x < COLUMNS; x++) {
			int column = x;
			GameDesign gd = new GameDesign();
			gd.getSelector().setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
			
			
			String player1Colour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
			String player2Colour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
			
			gd.getSelector().setFill(Color.TRANSPARENT);
			gd.getSelector().setOnMouseEntered(e -> {
				if(GameDesign.isPlayer1move()) {
					gd.getSelector().setFill(Color.web(player1Colour, 0.2));
				}else {
					gd.getSelector().setFill(Color.web(player2Colour, 0.2));
				}
			});
			
			gd.getSelector().setOnMouseExited(e -> gd.getSelector().setFill(Color.TRANSPARENT));
			gd.getSelector().setOnMouseClicked(e -> Disc.dropDisc(new Disc(player1Move), column));
			
			list.add(gd.getSelector());
		}
				
		return list;
	}
	
	/**
	 * createPlayer takes in the player's name and colour when they are assigned in the MainMenu class.
	 * @param name this is checked and manipulated to best suit the structure of the game
	 * Remove any spaces in the name (helps with saving the leader board)
	 * Checks if the name is empty, if it is a default name is given such as Player1
	 * Ensures that the player name is no longer than 8 characters (for visual purposes)
	 * 
	 * @param colour if no colour is selected or returned, by default the colour RED will be assigned.
	 * With the use of the Colour picker, this should never be an issue, but the precautions are being taken here.
	 */
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
			
			if(fixName.length() > 8) {
				player.setName(fixName.substring(0,8));
			}
			
			// Checks if the colour is empty somehow, and sets it to default colour of Red
			if(!player.getColour().matches(".*\\w.*")) {
				player.setColour("#ff0000");
			}
		}
	}
	
	/**
	 * gameEnd is called within Disc to check if the game has ended.
	 * The points on the board are calculated to determine if 3 points to the left/right/diagonally
	 * of the current point in Column and row is of the required combination to win.
	 * 
	 * @param column of where the discs are
	 * @param row of where the discs are
	 * @return Calls checkWin on each of the Point lists to return a true or false on whether a win has occurred.
	 */
	public static boolean gameEnd(int column, int row) {
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
	
	/**
	 * checkWin takes in a list of points
	 * if any list of points are of the same colour as the player and it equals to 4, then a win has occurred.
	 * @param points The points passed in to this method, they are used to map the X and Y of where they are.
	 * @return true if a win has occurred, otherwise false.
	 */
	public static boolean checkWin(List<Point2D> points) {
		int combo = 0;
		
		for(Point2D p : points) {
			int column = (int) p.getX();
			int row = (int) p.getY();
			
			/* disc in this position, if there is no disc then we create a new disc 
			 * which is a different colour so that the check fails and the chain stops*/
			Disc disc = Disc.getDisc(column, row).orElse(new Disc(!player1Move));
			
			// Since there are only 2 colours, if it is the move of Yellow(Player 2) it will not be Red(Player 1)'s move
			// If the colour is yellow(colour2) then disc.isColour1() will be false
			if(disc.isFirstColour() == player1Move) {
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
	
	/** 
	 * lighting3D is a simple effect that is used to create a 3D effect for most Labels used in the game as well as the Grid.
	 * @return colourDepth, a lighting Effect.
	 */
	public static Effect lighting3D() {
		// Example lighting from found in Light superclass
		Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(35.0);

        Lighting colourDepth = new Lighting();
        colourDepth.setLight(light);
        colourDepth.setSurfaceScale(5.0);
		
        return colourDepth;
	}
	
}
