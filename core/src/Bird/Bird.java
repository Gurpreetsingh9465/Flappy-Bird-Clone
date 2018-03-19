package Bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import GameInfo.GameInfo;

/**
 * Created by gurpreet on 18/3/18.
 */

public class Bird extends Sprite{
    private World world;
    private Texture[] birds;
    private Body body;
    private Vector2 colissionchecker;
    public Bird(World world,int i,float x,float y){
        this.world = world;
        birds = new Texture[3];

        if(i == 0){
            birds[0] = new Texture("sprites/redbird-downflap.png");
            birds[1] = new Texture("sprites/redbird-midflap.png");
            birds[2] = new Texture("sprites/redbird-upflap.png");
        }else{

            birds[0] = new Texture("sprites/bluebird-downflap.png");
            birds[1] = new Texture("sprites/bluebird-midflap.png");
            birds[2] = new Texture("sprites/bluebird-upflap.png");
        }
        colissionchecker = new Vector2(x,y);
        setPosition(x-getWidth()/2,y-getHeight()/2);
        createBody();
    }
    void createBody(){
        BodyDef bodyDef = new BodyDef();
        // static body didnot effected by gravity or force;
        // kinematic body is not affected by gravity but it is affected by otherforces
        // dynamic body is affect by gravity and other forces
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX()/GameInfo.PPM,getY()/GameInfo.PPM);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()/2)/GameInfo.PPM,(getHeight()/2)/GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }
    public void updatePlayer(){
        this.setPosition(body.getPosition().x* GameInfo.PPM,body.getPosition().y*GameInfo.PPM);
        colissionchecker.set(new Vector2(getX()+getWidth()/2,getY()+getHeight()/2));
    }
    public Body getBody(){
        return body;
    }


    public Texture[] getTextures(){
        return birds;
    }
    public float getWidth(){
        return getTextures()[0].getWidth();
    }
    public float getHeight(){
        return getTextures()[0].getHeight();
    }


    public Vector2 getColissionchecker() {
        return colissionchecker;
    }
}
