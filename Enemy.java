import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Enemy {
  private final String pngPathBeforeEnemy = "png/enemy";
  private final String pngPathBeforeCatVsEnemy = "png/cat_vs_enemy";
  private final String pngPathAfter = ".png";

  private int posX;
  private int posY;
  private int hp;
  private int beforePosX;
  private int beforePosY;

  private MapData mapData;

  Enemy(int startX, int startY, MapData mapData) {
    posX = startX;
    posY = startY;
    hp = 500;
    this.mapData = mapData;
    beforePosX = 1;
    beforePosY = 1;
  }

  public int getPosX() {
    return posX;
  }
  public int getPosY() {
    return posY;
  }

  public int getHp() {
    return hp;
  }

  public void setPosX(int posX) {
    this.posX = posX;
  }

  public void setPosY(int posY) {
    this.posY = posY;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public ImageView getEnemyImageView() {
    return new ImageView(new Image(pngPathBeforeEnemy + pngPathAfter));
  }

  public ImageView getCatVsEmemyImageView() {
    int r = (int)(Math.random()*4);
    if (r == 0) {
        MapGame.setMessage(2,"間抜けな面してんな！");;
    } else if (r == 1) {
        MapGame.setMessage(2, "勝てるわけないよ、あきらめな");
    } else if (r == 2) {
        MapGame.setMessage(2, "先に行ってるぜ");
    } else if (r == 3) {
        MapGame.setMessage(2, "負けるわけにはいかないからな");
    }
    return new ImageView(new Image(pngPathBeforeCatVsEnemy + pngPathAfter));
  }

  public boolean isMovable(int dx, int dy){
      if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_WALL){
          beforePosX = posX;
          beforePosY = posY;
          return false;
      } else if ((mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_SPACE || mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_KEY) && (beforePosX != posX+dx || beforePosY != posY+dy)){
          beforePosX = posX;
          beforePosY = posY;
          return true;
      }
      return false;
  }

  public boolean move(int dx, int dy){
    if(0 > hp) {
      return true;
    }
    if (isMovable(dx,dy)){
          posX += dx;
          posY += dy;
          hp -= 1;
          return true;
    }
    return false;
  }

  public void moveDicider() {
    int[][] randoms = {{0,1},{1,0},{0,-1},{-1,0}};
    int moves[] = randoms[(int) (Math.random() * 4)];
    if(!move(moves[0], moves[1])) {
      moveDicider();
    }
  }
}
