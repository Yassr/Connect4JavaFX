package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
 * MainMenu class contains everything related to the very first page of the game where the player decisions are made
 * Such as Names, colour, grid size and music on/off
 *
 */
public class MainMenu {
	private static Pane gameroot = new Pane();
	private static boolean music = true;
	private static Music gameplaymusic = new Music("/audio/gameplayMusic.wav", music);
	private static Image on = new Image("/audio/audio_icon_ON.png");
	private static Image off = new Image("/audio/audio_icon_OFF.png");
	private static ImageView vimg1 = new ImageView(on);
	private static ImageView vimg2 = new ImageView(off);
	private static Button toggleMusic = new Button("Toggle Music");
	
	
	public static Pane getGameroot() {
		return gameroot;
	}
	
	
	public MainMenu() {
	
	}
	
	
	public static boolean isMusic() {
		return music;
	}


	public static Music getGameplaymusic() {
		return gameplaymusic;
	}


	/**
	 * Takes in the player preferences Name, colour picker choice & board size
	 * @return the gridPane to be displayed
	 */
	public static GridPane preference() {
		
		GridPane gridPane = new GridPane();
		Button startBtn = new Button();
		Label startLabel = new Label("Start Game");
		Label welcomelbl = new Label("Connect 4 FX");
		Label titlelbl = new Label("Player preferences");
		Label columnlbl = new Label("Columns");
		Label rowlbl = new Label("Rows");
		Label instructlbl = new Label("Optional: Modify the Board size");
		columnlbl.setTooltip(new Tooltip("Default Columns is 7 and therefore the minimum"));
		rowlbl.setTooltip(new Tooltip("Default Rows is 6 and therefore the minimum"));
		
		
		// Set the minimum and maximum input allowed for the column and row spinners
		Spinner<Integer> columnSpinner = new Spinner<>(7, 10, 0, 1);
		Spinner<Integer> rowSpinner = new Spinner<>(6, 9, 0, 1);

		
		// Creating Text input
		Text nameText1 = new Text("Player 1");
		Text nameText2 = new Text("Player 2");
		
		// Creating Text fields for names
		ColorPicker colorPicker1 = new ColorPicker(Color.RED);
		ColorPicker colorPicker2 = new ColorPicker(Color.YELLOW);
		
		//	TODO figure out Opacity 
		if(colorPicker1.getValue().getOpacity() < 1) {
			String cp1 = colorPicker1.getValue().toString();
			
			colorPicker1.setValue(Color.web(cp1, 1));
		}
		
		
		TextField nameInput1 = new TextField();
		nameInput1.setPromptText("Choose a name (max 8 chars)");
		TextField nameInput2 = new TextField();
		nameInput2.setPromptText("Choose a name (max 8 chars)");
		
		startBtn.setOnAction(e -> {
			GameDesign.setCOLUMNS(columnSpinner.getValue());
			GameDesign.setROWS(rowSpinner.getValue());
			GameDesign.createPlayer(nameInput1.getText(), colorPicker1.getValue().toString());
			GameDesign.createPlayer(nameInput2.getText(), colorPicker2.getValue().toString());
			
			GameMain.getStage().setScene(new Scene(startGame()));
			
		});
		
		
		
		MainMenu mm = new MainMenu();
		vimg1.setFitHeight(30);
		vimg1.setPreserveRatio(true);
		vimg2.setFitHeight(30);
		vimg2.setPreserveRatio(true);
		

		toggleMusic.setGraphic(vimg1);
		toggleMusic.setOnAction(e -> mm.musicToggle());
		
		
		welcomelbl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 32));

		// Timeline used to loop between the player colours and set it on the Welcome Label
		Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> welcomelbl.setTextFill(Color.web(colorPicker1.getValue().toString()))),
                new KeyFrame(Duration.seconds(1.0), e -> welcomelbl.setTextFill(Color.web(colorPicker2.getValue().toString())))
        );

		// Repeat the cycle up to 100 times..after which it'll stay on one colour
        timeline.setCycleCount(100);
		
		
		welcomelbl.setEffect(GameDesign.lighting3D());
		titlelbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
		
		// Start button and size
		startBtn.setPrefSize(GameDesign.getTileSize()*3,GameDesign.getTileSize());
	
		startLabel.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 30));
		startLabel.setEffect(GameDesign.lighting3D());
		startBtn.setGraphic(startLabel);
		
		// Change the colour of the start button depending on the player choices
		// Adjusting based on mouse entering and exiting
		startBtn.setOnMouseEntered(e -> startLabel.setTextFill(Color.web(colorPicker1.getValue().toString())));
		startBtn.setOnMouseExited(e -> startLabel.setTextFill(Color.web(colorPicker2.getValue().toString())));
		
		gridPane.setPadding(new Insets(10, 10, 10 ,10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);
		
		gridPane.add(welcomelbl, 2, 0);
		gridPane.add(titlelbl, 2, 2);
		gridPane.add(nameText1, 2, 9);
		gridPane.add(nameInput1, 2, 10);
		gridPane.add(colorPicker1, 3, 10);
		gridPane.add(nameText2, 2, 14);
		gridPane.add(nameInput2, 2, 15);
		gridPane.add(colorPicker2, 3, 15);
		gridPane.add(instructlbl, 2, 22);
		gridPane.add(columnlbl, 2, 23);
		gridPane.add(rowlbl, 3, 23);
		gridPane.add(columnSpinner, 2, 24);
		gridPane.add(rowSpinner, 3, 24);
		gridPane.add(startBtn, 2, 25);
		gridPane.add(toggleMusic,3, 25);
		
		// Play the gameplay music and also 'play' the timeline effect
		timeline.play();
		gameplaymusic.loop();
		return gridPane;
	}
	
	/**
	 * startGame() adds the Discs layer followed by the grid on top of it 
	 * then the different player names that change based on turns
	 * and finally the selection high lighter is added on top of them all
	 * 
	 * @return the gameroot of the game
	 */
	public static Parent startGame() {
		
		
		// Disc Root goes before game grid to give the illusion of a 3D board.
		gameroot.getChildren().clear();
		gameroot.getChildren().add(Disc.getDiscRoot());
		
		Shape gridShape = GameDesign.makeGrid();

		gameroot.getChildren().add(gridShape);
		gameroot.getChildren().add(Disc.getnamechng());
		gameroot.getChildren().addAll(GameDesign.selector());
		
		
		return gameroot;
	}
	
		
	
	/**
	 * Allows the toggling of music on or off when the button is flicked
	 * Also changes the image of the on/off button to match the status of the toggle
	 */
	public void musicToggle() {
        if(music){
        	toggleMusic.setGraphic(vimg2);
            music = false;
            gameplaymusic.stop();
        } else {
        	toggleMusic.setGraphic(vimg1);
            music = true;
            gameplaymusic.loop();
        }
    }
	

}
