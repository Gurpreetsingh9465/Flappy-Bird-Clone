package MainGame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


import com.mygdx.game.MyGdxGame;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

import Bird.Bird;
import GameInfo.GameInfo;
import Tubes.Tubes;


/**
 * Created by gurpreet on 18/3/18.
 */

public class Flappy implements Screen {
    int loop = 0;
    MyGdxGame game;
    boolean gameover;
    World world;
    int roll;
    private Texture bg;
    private int score = 0;
    private ArrayList<Texture> numbers;
    private Sound hit;


    private Bird bird;
    private OrthographicCamera box2Dcamera;
    private Box2DDebugRenderer debugRenderer;
    Sound wing;
    Sound point;
    private Vector2 groundpos1;
    private Vector2 groundpos2;

    private Texture Message;


    boolean isPlaying = false;
    private Texture base;
    private Tubes tube1;
    private Tubes tube2;
    private Tubes tube3;
    private Tubes tube4;
    private Texture Gameovertexture;

    public Flappy(MyGdxGame game){
        this.game = game;
        box2Dcamera = new OrthographicCamera();
        box2Dcamera.setToOrtho(false,GameInfo.WIDTH*3, GameInfo.HEIGHT);
        box2Dcamera.position.set(GameInfo.WIDTH, (GameInfo.HEIGHT),0);
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0,-9.8f),true);
        wing = Gdx.audio.newSound(Gdx.files.internal("audio/wing.ogg"));
        point = Gdx.audio.newSound(Gdx.files.internal("audio/point.ogg"));
        hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.ogg"));
        int rand = new Random().nextInt(2);
        if(rand == 0){
            bg = new Texture("sprites/background-day.png");
        }else{
            bg = new Texture("sprites/background-night.png");
        }
        int i = new Random().nextInt(2);
        bird = new Bird(world,i, GameInfo.WIDTH/2-GameInfo.WIDTH/4, GameInfo.HEIGHT/2);
        roll=0;
        int choice = new Random().nextInt(2);
        Message = new Texture("sprites/message.png");
        Gameovertexture = new Texture("sprites/gameover.png");

        tube1 = new Tubes(choice,(box2Dcamera.viewportWidth));
        tube2 = new Tubes(choice,(box2Dcamera.viewportWidth)+GameInfo.tubeGap);
        tube3 = new Tubes(choice,(box2Dcamera.viewportWidth)+GameInfo.tubeGap*2);
        tube4 = new Tubes(choice,(box2Dcamera.viewportWidth)+GameInfo.tubeGap*3);
        gameover = false;
        base = new Texture("sprites/base.png");
        groundpos1 = new Vector2(0,0);
        groundpos2 = new Vector2(GameInfo.WIDTH,0);
        numbers = new ArrayList<Texture>();
        for(int looping = 0;looping<=9;looping++){
            numbers.add(new Texture("sprites/"+Integer.toString(looping)+".png"));
        }

        Gdx.app.log("box2d","box "+box2Dcamera.position.toString()+" box2 "+box2Dcamera.viewportWidth);
    }
    @Override
    public void show() {

    }

    public void update(float dt){
        if (GameInfo.HEIGHT <= bird.getY()){
            bird.getBody().setLinearVelocity(0,-1f);
        }
        //Gdx.app.log("info","body x "+bird.getBody().getPosition().x+" body y "+bird.getBody().getPosition().y);
        if (Gdx.input.justTouched() && !gameover){
            bird.getBody().setType(BodyDef.BodyType.DynamicBody);
            isPlaying = true;
            wing.play(1f);
            bird.getBody().setLinearVelocity(0,4f);
        }

        tube1.updatePosition(tube1.getPositiontop().x - GameInfo.speed);
        tube2.updatePosition(tube2.getPositiontop().x - GameInfo.speed);
        tube3.updatePosition(tube3.getPositiontop().x - GameInfo.speed);
        tube4.updatePosition(tube4.getPositiontop().x - GameInfo.speed);
        if(groundpos2.x+GameInfo.WIDTH==0){
            groundpos2.x = GameInfo.WIDTH;
        }else if(groundpos1.x+GameInfo.WIDTH == 0){
            groundpos1.x = GameInfo.WIDTH;
        }

        groundpos1.x -= GameInfo.speed;
        groundpos2.x -= GameInfo.speed;
        checkTube(tube1);
        checkTube(tube2);
        checkTube(tube3);
        checkTube(tube4);
        if(tube4.collide(bird)||tube1.collide(bird)||tube2.collide(bird)||tube3.collide(bird)){
            Gdx.app.log("statement","are ha kar rhe he");
            Gameover();
        }

        if(tube4.getScore(bird)||tube1.getScore(bird)||tube2.getScore(bird)||tube3.getScore(bird)){
            score+=1;
            point.play(1f);
            Gdx.app.log("score","score is "+ score);
        }
        if (base.getHeight()+GameInfo.HEIGHT/8 >= bird.getColissionchecker().y){
            Gameover();
        }
    }


    public void Gameover(){
        hit.play();
        isPlaying = false;
        bird.rotate(90f);
        gameover = true;


    }


    public void checkTube(Tubes tube){
        if(-GameInfo.tubeWidth*2>(tube.getPositiontop().x+tube.getWidth())){

            tube.updateSize((GameInfo.tubeGap+GameInfo.tubeWidth)*3+tube.getPositionbottom().x);

        }

    }




    public void drawTube(Tubes drawtube, SpriteBatch batch){
        batch.draw(drawtube.getTubeDown(),drawtube.getPositionbottom().x,drawtube.getPositionbottom().y,drawtube.getWidth(),drawtube.getHeight());
        batch.draw(drawtube.getTubeup(),drawtube.getPositiontop().x,drawtube.getPositiontop().y,drawtube.getWidth(),drawtube.getHeight());
    }
    @Override
    public void render(float delta) {
        if(!gameover){
            update(delta);
        }
        debugRenderer.render(world,box2Dcamera.combined);
        world.step(Gdx.graphics.getDeltaTime(),6,2);
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        game.getBatch().draw(bg,0,0, GameInfo.WIDTH, GameInfo.HEIGHT);
        if(!gameover){
            bird.updatePlayer();
            int i = 0;

            if(roll<=10 && roll >=0){
                i = 0;
            }else if(roll<=20 && roll >10){
                i = 1;
            }else if(roll<30 && roll >20){
                i = 2;
            }else{
                roll = 0;
            }



            if(!isPlaying){
                game.getBatch().draw(Message,((GameInfo.WIDTH/2)-(Message.getWidth()/2+210)),((GameInfo.HEIGHT/2)-(Message.getHeight()/2+120)),Message.getWidth()+400,Message.getHeight()+600);
            }
            if(isPlaying && !gameover){
                drawTube(tube1,game.getBatch());
                drawTube(tube2,game.getBatch());
                drawTube(tube3,game.getBatch());
                drawTube(tube4,game.getBatch());
                game.getBatch().draw(base,groundpos1.x,groundpos1.y,GameInfo.WIDTH,base.getHeight()+GameInfo.HEIGHT/8);
                game.getBatch().draw(base,groundpos2.x,groundpos2.y,GameInfo.WIDTH,base.getHeight()+GameInfo.HEIGHT/8);
                displayScore(game.getBatch());
            }
            game.getBatch().draw(bird.getTextures()[i],bird.getX(),bird.getY(),GameInfo.BirdWIDTH, GameInfo.BirdHeight);
        }
        if (gameover){
            displayScore(game.getBatch());
            game.getBatch().draw(Gameovertexture,GameInfo.WIDTH/2-Gameovertexture.getWidth()/2-200,GameInfo.HEIGHT/2-Gameovertexture.getHeight()/2-GameInfo.scoreheight-50,Gameovertexture.getWidth()+400,Gameovertexture.getHeight()+200);
            changestate(delta);
        }
        game.getBatch().end();
        roll+=1;
    }

    public void changestate(float dt){
        if(Gdx.input.justTouched()){
            int rand = new Random().nextInt(2);
            if(rand == 0){
                bg = new Texture("sprites/background-day.png");
            }else{
                bg = new Texture("sprites/background-night.png");
            }
            int i = new Random().nextInt(2);
            bird = new Bird(world,i, GameInfo.WIDTH/2-GameInfo.WIDTH/4, GameInfo.HEIGHT/2);
            roll=0;
            int choice = new Random().nextInt(2);
            Message = new Texture("sprites/message.png");
            score = 0;
            tube1 = new Tubes(choice,(box2Dcamera.viewportWidth));
            tube2 = new Tubes(choice,(box2Dcamera.viewportWidth)+GameInfo.tubeGap);
            tube3 = new Tubes(choice,(box2Dcamera.viewportWidth)+GameInfo.tubeGap*2);
            tube4 = new Tubes(choice,(box2Dcamera.viewportWidth)+GameInfo.tubeGap*3);
            gameover = false;
            base = new Texture("sprites/base.png");
            groundpos1 = new Vector2(0,0);
            groundpos2 = new Vector2(GameInfo.WIDTH,0);
        }
    }

    public void displayScore(SpriteBatch batch){
        if(score == 0){
            batch.draw(numbers.get(0),GameInfo.scoreposition.x,GameInfo.scoreposition.y,GameInfo.scorewidth,GameInfo.scoreheight);
        }
        else{
            int length = (int)(Math.log10(score)+1);
            if(length == 1){
                batch.draw(numbers.get(score),GameInfo.scoreposition.x,GameInfo.scoreposition.y,GameInfo.scorewidth,GameInfo.scoreheight);
            }else if (length == 2){
                batch.draw(numbers.get(score/10),GameInfo.scoreposition.x-GameInfo.scorewidth/2,GameInfo.scoreposition.y,GameInfo.scorewidth,GameInfo.scoreheight);
                batch.draw(numbers.get(score%10),GameInfo.scoreposition.x+GameInfo.scorewidth/2,GameInfo.scoreposition.y,GameInfo.scorewidth,GameInfo.scoreheight);

            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        base.dispose();


        bird.getTextures()[0].dispose();
        bird.getTextures()[1].dispose();
        bird.getTextures()[2].dispose();
    }
}
