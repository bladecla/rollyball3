package com.rolly.ball;

import static com.rolly.ball.Constants.PPM;
import static com.rolly.ball.Constants.V_HEIGHT;
import static com.rolly.ball.Constants.V_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rolly.ball.screens.GameScreen;

public class HUD {
	
	private BitmapFont font;
	private Float score = 0f;
	public Stage stage;
	private Table table;
	private Label scoreLabel, distLabel, messageLabel;
	private Label.LabelStyle white;
	private Viewport port;
	private Skin skin;
	private OrthographicCamera camera;
	
	public HUD (SpriteBatch batch, BitmapFont font)
	{
		this.font = font;
		white = new Label.LabelStyle(font, Color.WHITE);
		camera = new OrthographicCamera();
		port = new FitViewport(V_WIDTH, V_HEIGHT, camera);
		stage = new Stage(port, batch);
		skin = new Skin();
		
		table = new Table();
		table.bottom();
		table.setFillParent(true);
		table.setSkin(skin);
		distLabel = new Label("Distance: ", white);
		scoreLabel = new Label("Hi-Score: ", white);
		
		
		table.add(distLabel).padRight(port.getScreenWidth()/3);
		table.add(scoreLabel).padRight(port.getScreenWidth()/6);
//		table.add(scoreLabel).pad(10);
//		table.add(score.toString());
	
		stage.addActor(table);
		
	}
	
	public void draw(Batch batch, String score, String hiscore)
	{
		
		font.draw(batch, score, distLabel.getRight() + port.getScreenWidth()/20, distLabel.getHeight()/1.5f);
		font.draw(batch, hiscore, scoreLabel.getRight() + port.getScreenWidth()/20, scoreLabel.getHeight()/1.5f);
	}

	public void message(Batch batch, String message){


		font.draw(batch, message, port.getScreenWidth()/2, port.getScreenHeight()/2 + font.getCapHeight(), 0, 1, false );

	}
}
