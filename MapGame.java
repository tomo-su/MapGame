import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class MapGame extends Application {
    public static Label l = new Label();
    public static Image c = new Image("png/neko.png");
    public static Image g = new Image("png/goblin.png");
    public static ImageView iv = new ImageView();
    public static GridPane grid = new GridPane();

    Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
	stage = primaryStage;
	primaryStage.setTitle("MAP GAME");
	Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml"));
    Scene myScene = new Scene(myPane_top);

    grid.setPrefHeight(100);
    l.setMaxWidth(600);
    l.setMaxHeight(100);
    l.setWrapText(true);
    l.setFont(new Font("Arial", 30));
    grid.add(iv, 0, 0);
    grid.add(l, 1, 0);
    myPane_top.getChildren().add(grid);
	primaryStage.setScene(myScene);
    primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public static void setMessage(int kind, String message) {
        if(kind == 1) {
            iv.setImage(c);
            message = "「" + message + "」";
        } else {
            iv.setImage(g);
            message = "「" + message + "」";
        }
        l.setText(message);
    }
}
