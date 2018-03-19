package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import MainGame.Flappy;


public class MyGdxGame extends Game {
	SpriteBatch Batch;
	@Override
	public void create () {
		Batch = new SpriteBatch();
		setScreen(new Flappy(this));
	}

	@Override
	public void render () {
		super.render();

	}
	public SpriteBatch getBatch(){
		return Batch;
	}

	@Override
	public void dispose () {

	}
}
