package GameInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gurpreet on 18/3/18.
 */


public class GameInfo {
    public static final int  BirdWIDTH = 110;
    public static final int BirdHeight = 90;
    public static final int PPM = 300;
    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final int tubeHeight = HEIGHT;
    public static final int tubeWidth = WIDTH/4-50;
    public static final int GAP = HEIGHT/5;
    public static final float tubeGap = WIDTH/2+50;
    public static final int speed = 8;
    public static final int scorewidth = tubeWidth - tubeWidth/2;
    public static final int scoreheight = HEIGHT/6;
    public static final Vector2 scoreposition = new Vector2(WIDTH/2-scorewidth/2,HEIGHT/2-scoreheight/2+HEIGHT/3);

}


