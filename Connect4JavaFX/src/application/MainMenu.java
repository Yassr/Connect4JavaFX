package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenu {
	private static Music gameplay = new Music("/audio/gameplayMusic.wav");
	private static Image on = new Image("/audio/audio_icon_ON.png");
	private static Image off = new Image("/audio/audio_icon_OFF.png");
	private static ImageView vimg1 = new ImageView(on);
	private static ImageView vimg2 = new ImageView(off);
	
	private static boolean music = true;
	private static Button toggleMusic = new Button("Toggle Music");
	
	
	MainMenu(){}
	
	static GridPane preference() {
		
		GridPane gridPane = new GridPane();
		
		Button startBtn = new Button();
		Label starLabel = new Label("Start Game");
		Label welcomelbl = new Label("Connect 4 FXgame");
		Label titlelbl = new Label("Player preferences");
		Label columnlbl = new Label("Columns");
		Label rowlbl = new Label("Rows");
		Label instructlbl = new Label("Modify the Board size (if you want)");
		columnlbl.setTooltip(new Tooltip("Default Columns is 7 and therefore the minimum"));
		rowlbl.setTooltip(new Tooltip("Default Rows is 6 and therefore the minimum"));
		
		
		Spinner<Integer> columnSpinner = new Spinner<>(7, 10, 0, 1);

		Spinner<Integer> rowSpinner = new Spinner<>(6, 9, 0, 1);

		
		// Creating Text input
		Text nameText1 = new Text("Player 1");
		Text nameText2 = new Text("Player 2");
		
		// Creating Text fields for names
		ColorPicker colorPicker1 = new ColorPicker(Color.RED);
		ColorPicker colorPicker2 = new ColorPicker(Color.YELLOW);
		
		
		TextField nameInput1 = new TextField();
		nameInput1.setPromptText("Choose a name (max 6 chars)");
		TextField nameInput2 = new TextField();
		nameInput2.setPromptText("Choose a name (max 6 chars)");
		
		startBtn.setOnAction(e -> {
			GameDesign.setCOLUMNS(columnSpinner.getValue());
			GameDesign.setROWS(rowSpinner.getValue());
			GameDesign.createPlayer(nameInput1.getText(), colorPicker1.getValue().toString());
			GameDesign.createPlayer(nameInput2.getText(), colorPicker2.getValue().toString());
			
			GameDesign.handleButtonAction(e);});
		
		
		
		MainMenu mm = new MainMenu();
		vimg1.setFitHeight(30);
		vimg1.setPreserveRatio(true);
		vimg2.setFitHeight(30);
		vimg2.setPreserveRatio(true);
		

		toggleMusic.setGraphic(vimg1);
		toggleMusic.setOnAction(e -> mm.musicToggle());
		
		
		welcomelbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		welcomelbl.setTextFill(Color.AQUA);
		
		welcomelbl.setEffect(GameDesign.lighting3D());
		titlelbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
		
		startBtn.setPrefSize(GameDesign.getTileSize()*3,GameDesign.getTileSize());
		
		starLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		starLabel.setEffect(GameDesign.lighting3D());
		startBtn.setGraphic(starLabel);
		
		startBtn.setOnMouseEntered(e -> starLabel.setTextFill(Color.web(colorPicker1.getValue().toString())));
		startBtn.setOnMouseExited(e -> starLabel.setTextFill(Color.web(colorPicker2.getValue().toString())));
		
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
		
		gameplay.loop();
		return gridPane;
	}
		
	public void musicToggle() {
        if(music){
        	toggleMusic.setGraphic(vimg2);
            music = false;
            gameplay.stop();
        } else {
        	toggleMusic.setGraphic(vimg1);
            music = true;
            gameplay.loop();
        }
    }
	
	// Was meant to be used for the colour borders, didn't use so far...
	private static String toHexString(Color color) {
		  int r = ((int) Math.round(color.getRed()     * 255)) << 24;
		  int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
		  int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
		  int a = ((int) Math.round(color.getOpacity() * 255));
		  return String.format("#%08X", (r + g + b + a));
		}

}
