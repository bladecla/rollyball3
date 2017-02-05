package com.rolly.ball;

import static com.rolly.ball.Constants.PPM;

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

public class HUD {
	
	private BitmapFont font;
	private Float score = 0f;
	public Stage stage;
	private Table table;
	private Label scoreLabel, distLabel;
	private Label.LabelStyle white;
	private Viewport port;
	private Skin skin;
	private OrthographicCamera camera;
	
	public HUD (SpriteBatch batch, BitmapFont font)
	{
		this.font = font;
		white = new Label.LabelStyle(font, Color.WHITE);
		camera = new OrthographicCamera();
		port = new FitViewport(RollyBall.V_WIDTH, RollyBall.V_HEIGHT, camera);
		stage = new Stage(port, batch);
		skin = new Skin();
		
		table = new Table();
		table.bottom();
		table.setFillParent(true);
		table.setSkin(skin);
		distLabel = new Label("Distance: ", white);
		scoreLabel = new Label("Hi-Score: ", white);
		
		
		table.add(distLabel).padBottom(10).padRight(300);
		table.add(scoreLabel).padBottom(10).padRight(100);
//		table.add(scoreLabel).pad(10);
//		table.add(score.toString());
	
		stage.addActor(table);
		
	}
	
	public void draw(Batch batch, String score, String hiscore)
	{
		
		font.draw(batch, score, camera.position.x/2 + 10, font.getCapHeight() + 30);
		font.draw(batch, hiscore, port.getScreenWidth(), font.getCapHeight() + 30);
	}
}
