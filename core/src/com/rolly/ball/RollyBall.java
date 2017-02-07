package com.rolly.ball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rolly.ball.screens.GameScreen;

public class RollyBall extends Game {
	
	public SpriteBatch batch;

	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose(){
		this.getScreen().dispose();
		batch.dispose();
	}
}
