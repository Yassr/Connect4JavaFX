package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenu {
	private static Music gameplay = new Music("/audio/gameplayMusic.wav");
	static GridPane preference() {
		
		GridPane gridPane = new GridPane();
		
		Button startBtn = new Button("Start Game");
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
		
		
		welcomelbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		welcomelbl.setTextFill(Color.AQUA);
		
		welcomelbl.setEffect(GameDesign.lighting3D());
		titlelbl.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
		
		startBtn.setPrefSize(GameDesign.getTileSize()*2,GameDesign.getTileSize());
		

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
		
		gameplay.loop();
		return gridPane;
	}
		

}
