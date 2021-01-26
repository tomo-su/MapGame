import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import java.io.File;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    /** BGM */
    public AudioClip ac;
    /** SE */
    public AudioClip se; 

    /** hp (label)*/
    public Label hp;
    /** score (label) */
    public Label score;
    /** アイテムのcount (label) */
    public Label count;
    /** hp */
    int HP = 500;
    /** score */
    int SCORE;
    /** count */
    int COUNT;

    /** enemy  */
    public Enemy enemy;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameInit();
    }
    // Draw the map
    public void drawMap(MoveChara c, Enemy e, MapData m){
        int cx = c.getPosX();
        int cy = c.getPosY();
        int ex = e.getPosX();
        int ey = e.getPosY();
        mapGrid.getChildren().clear();
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if(x==cx && y==cy && x==ex && y==ey) {
                    mapGrid.add(e.getCatVsEmemyImageView(), x, y);
                } else if (x==cx && y==cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else if (x==ex && y==ey) {
                    mapGrid.add(e.getEnemyImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    // Get users key actions
    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode(); System.out.println("keycode:"+key);
        if (key == KeyCode.H){
        	leftButtonAction();
        }else if (key == KeyCode.J){
            downButtonAction(); 
        }else if (key == KeyCode.K){
            upButtonAction();
        }else if (key == KeyCode.L){
            rightButtonAction();
        }
    }

    // Operations for going the cat down
    public void upButtonAction(){
        printAction("UP");
        chara.setCharaDirection(MoveChara.TYPE_UP);
        chara.move(0, -1);
        processMove();
    }

    // Operations for going the cat down
    public void downButtonAction(){
        printAction("DOWN");
        chara.setCharaDirection(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        processMove();
    }

    // Operations for going the cat right
    public void leftButtonAction(){
        printAction("LEFT");
        chara.setCharaDirection(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        processMove();
    }

    // Operations for going the cat right
    public void rightButtonAction(){
        printAction("RIGHT");
        chara.setCharaDirection(MoveChara.TYPE_RIGHT);
        chara.move(1, 0);
        processMove();
    }

    public void func1ButtonAction(ActionEvent event) {
        System.out.println("func1: Nothing to do");
    }

    // Print actions of user inputs
    public void printAction(String actionString) {
        System.out.println("Action: " + actionString);
    }

    /**
     * ゲーム画面を生成
     */
    public void gameInit() {
        mapData = new MapData(21, 15);
        enemy = new Enemy(1, 1, mapData);
        chara = new MoveChara(1, 1, mapData, enemy);
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        drawMap(chara, enemy, mapData);
        playMusic();
        COUNT = 0;
        hp.setText("HP:" + HP);
        score.setText("  SCORE:" + SCORE);
        count.setText("  COUNT:" + COUNT);
    }

    /**
     * ゲーム画面の再生成のテスト
     * @param event
     */
    public void debugButtonAction(ActionEvent event) {
        gameInit();
    }

    /**
     * 指定したmp3ファイルを再生
     */
    public void playMusic() {
        ac = new AudioClip(new File("./sound/tammb14.mp3").toURI().toString());
        ac.stop();
        ac.setVolume(1);
        ac.setCycleCount(AudioClip.INDEFINITE);
        ac.play();
    }

    /**
     * アイテムの取得
     * @param x　キャラのx座標
     * @param y  キャラのy座標
     */
    public void gettingKey(int x, int y) {
        if(mapData.getMap(x, y) == 2) {
            mapData.setMap(x, y, 0);
            mapData.setImageViews();
            int index = y*mapData.getWidth() + x;
            mapImageViews[index] = mapData.getImageView(x, y);
            drawMap(chara, enemy, mapData);
            COUNT++;
            count.setText("  COUNT: " + COUNT);
            se = new AudioClip(new File("./sound/pickupkey.mp3").toURI().toString());
            se.setVolume(10);
            se.play();
        }
    }

    /**
     * 次のステージにいけるかの判定
     * @param x　キャラのx座標
     * @param y　キャラのy座標
     */
    public void judgeGoal(MoveChara c, Enemy e) {
        if(c.getPosX() == 19 && c.getPosY() == 13  && COUNT == 4) {
            gameInit();
            return;        
        }
        if(e.getPosX() == 19 && e.getPosY() == 13) {
            gameInit();
        }
    }

    /**
     * labelに数値を反映
     */
    public void setStatus() {
        HP -=  1;
        System.out.println("HP:"+ hp);
        SCORE = 500 - (500-HP);
        System.out.println("SCORE:" + SCORE);
        hp.setText("HP:" + HP);
        score.setText("  SCORE:" + SCORE);
        if(HP == 0){
            chara = new MoveChara(1,1,mapData, enemy);
            HP = 500;
            SCORE -= 500;
            System.out.println("GameOver!!");
        }
    }

    public void processMove() {
        enemy.moveDicider();
        drawMap(chara, enemy, mapData);
        setStatus();
        gettingKey(chara.getPosX(), chara.getPosY());
        judgeGoal(chara, enemy);
    }

}
