package Tubes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import java.util.Random;
import java.util.Vector;

import Bird.Bird;
import GameInfo.GameInfo;

/**
 * Created by gurpreet on 18/3/18.
 */

public class Tubes extends Sprite{
    private Rectangle topRectangle;
    private Rectangle bottomRectangle;

    private Texture tubeup;
    private Texture tubeDown;
    private float height;
    private float width;
    private Vector2 positionbottom;
    private Vector2 positiontop;

    public Tubes(int choice,float x){

        this.width = GameInfo.tubeWidth;
        this.height = GameInfo.tubeHeight;
        if(choice == 0){
                tubeup = new Texture("sprites/pipe-green-up.png");
                tubeDown = new Texture("sprites/pipe-green.png");
        }else{
            tubeup = new Texture("sprites/pipe-red-up.png");
            tubeDown = new Texture("sprites/pipe-red.png");
        }
        float totalHeightOfTubes = GameInfo.HEIGHT - GameInfo.GAP;
        float ran = new Random().nextFloat();
        if (ran >0.6){
            ran -= 0.4;
        }else if(ran<0.2){
            ran += 0.2;
        }
        float upPosition = totalHeightOfTubes*ran;
        float DownPosition = totalHeightOfTubes - upPosition;
        upPosition = GameInfo.tubeHeight - upPosition;
        DownPosition = GameInfo.tubeHeight - DownPosition;
        positionbottom = new Vector2(x,-DownPosition);
        positiontop = new Vector2(x,upPosition);


        topRectangle = new Rectangle(positiontop.x,positiontop.y,width,height);
        bottomRectangle = new Rectangle(positionbottom.x,positionbottom.y,width,height);



    }


    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }


    public Texture getTubeup() {
        return tubeup;
    }

    public Texture getTubeDown() {
        return tubeDown;
    }

    public void updateSize(float x){
        float totalHeightOfTubes = GameInfo.HEIGHT - GameInfo.GAP;
        float ran = new Random().nextFloat();
        if (ran >0.6){
            ran -= 0.4;
        }else if(ran<0.2){
            ran += 0.2;
        }
        float upPosition = totalHeightOfTubes*ran;
        float DownPosition = totalHeightOfTubes - upPosition;
        upPosition = GameInfo.tubeHeight - upPosition;
        DownPosition = GameInfo.tubeHeight - DownPosition;
        positionbottom.set(new Vector2(x,-DownPosition));
        positiontop.set(new Vector2(x,upPosition));
        topRectangle.set(new Rectangle(positiontop.x,positiontop.y,width,height));
        bottomRectangle.set(new Rectangle(positionbottom.x,positionbottom.y,width,height));

    }

    public Vector2 getPositionbottom() {
        return positionbottom;
    }

    public Vector2 getPositiontop() {
        return positiontop;
    }

    public void updatePosition(float x){
        positionbottom.x = x;
        positiontop.x = x;
        topRectangle.set(new Rectangle(positiontop.x,positiontop.y,width,height));
        bottomRectangle.set(new Rectangle(positionbottom.x,positionbottom.y,width,height));

    }

    public boolean collide(Bird bird){
        return topRectangle.overlaps(new Rectangle(bird.getColissionchecker().x,bird.getColissionchecker().y,bird.getWidth(),bird.getHeight())) || bottomRectangle.overlaps(new Rectangle(bird.getColissionchecker().x,bird.getColissionchecker().y,bird.getWidth(),bird.getHeight()));
    }
    public boolean getScore(Bird bird){
        Gdx.app.log("info","bird "+bird.getX()+" positioon is "+positionbottom.x);
        return bird.getX() >= (positionbottom.x+2) && bird.getX()<=(positionbottom.x+9);
    }

}






