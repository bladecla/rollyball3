package com.rolly.ball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rolly.ball.screens.GameScreen;

public class RollyBall extends Game {
	
	public SpriteBatch batch;
	public static final float V_WIDTH = 800, V_HEIGHT = 600;
	
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
