package application;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;



/*
 *	@TODO Add Music
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
	private boolean music = true;
	
	
	
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
		
		//		players.get(0);
		
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
		// TODO Auto-generated constructor stub
	}

	
	static void handleButtonAction(ActionEvent event) {
		
		GameMain.getStage().setScene(new Scene(GameMain.startGame()));
	}
	
	


	static Shape makeGrid() {
		Shape board = new Rectangle((COLUMNS+1) * TILE_SIZE, (ROWS+1) * TILE_SIZE+30);
		
	
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

		board.setFill(Color.AQUA);
		board.setEffect(lighting3D());
		
		return board;
	}
	
	public static Label whichPlayer(boolean move) {
		
		String player1Name = (GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String player1Colour = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		String player2Name = (!GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName());
		String player2Colour = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getColour() : GameDesign.getPlayers().get(1).getColour();
		
		
		Label playerlbl = new Label();
		
		
		if(move) {
			playerlbl.setText(player1Name);
			playerlbl.setTextFill(Color.web(player1Colour));
			
		}else{
			playerlbl.setText(player2Name);
			playerlbl.setTextFill(Color.web(player2Colour));
			
		}
		
		playerlbl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		playerlbl.setEffect(GameDesign.lighting3D());
		
		HBox hbox = new HBox(70);
		hbox.setPadding(new Insets(10, 10, 10 ,10));
		playerlbl.setTranslateY((ROWS+0.75) * TILE_SIZE);
		playerlbl.setTranslateX(3.25*TILE_SIZE);
		hbox.getChildren().add(playerlbl);
	
		
		return playerlbl;
	}
	
	
	public static Effect lighting3D() {
		// Example lighting from found in Light superclass
		Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting colourDepth = new Lighting();
        colourDepth.setLight(light);
        colourDepth.setSurfaceScale(5.0);
		
        return colourDepth;
	}
	
	
	static List<Rectangle> selection(){
		// Create a list of type Rectangle
		List<Rectangle> list = new ArrayList<>();
		
		/* Choose all of the rows within the column
		 * that the mouse is hovering over 
		 * by giving this rectangle a colour (that is slightly transparent
		 * we can create a selector so players know where they are on the board.
		 */
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
		
		System.out.println("COL "+column+"  ROW  " + row);
		return checkWin(vertical) || checkWin(horizontal) || checkWin(diagonalT) || checkWin(diagonalB);
		
	}
	
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
