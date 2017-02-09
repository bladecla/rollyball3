package com.rolly.ball;

import static com.rolly.ball.Constants.PPM;
import static com.rolly.ball.Constants.V_HEIGHT;
import static com.rolly.ball.Constants.V_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
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
	private Skin buttonSkin;
	private OrthographicCamera camera;
	private ImageButton muteButton;
	private TextureAtlas atlas;
	private boolean muted = false;

	public HUD (SpriteBatch batch, BitmapFont font)
	{
		this.font = font;
		white = new Label.LabelStyle(font, Color.WHITE);
		camera = new OrthographicCamera();
		port = new FitViewport(V_WIDTH, V_HEIGHT, camera);
		stage = new Stage(port, batch);

		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("sound.atlas");
		buttonSkin = new Skin(atlas);


		ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("sound_on");
		buttonStyle.checked = buttonSkin.getDrawable("sound_mute");

		muteButton = new ImageButton(buttonStyle);





		table = new Table();
		table.bottom();
		table.setFillParent(true);

		distLabel = new Label("Distance: ", white);
		scoreLabel = new Label("Hi-Score: ", white);
		
		
		table.add(distLabel).padRight(port.getScreenWidth()/3);
		table.add(scoreLabel).padRight(port.getScreenWidth()/6);



		muteButton.setHeight(30*V_HEIGHT/muteButton.getHeight());
		muteButton.setWidth(muteButton.getWidth()*(muteButton.getHeight()/muteButton.getWidth()));
		muteButton.setPosition((V_WIDTH - muteButton.getWidth()) - V_WIDTH/25, (V_HEIGHT - muteButton.getHeight())-V_HEIGHT/25);
		muteButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if(!muted)
				muted = true;
				else if (muted) muted = false;

			}
		});
//		table.add(scoreLabel).pad(10);
//		table.add(score.toString());
	
		stage.addActor(table);
		stage.addActor(muteButton);
		
	}
	
	public void draw(Batch batch, String score, String hiscore)
	{
		
		font.draw(batch, score, distLabel.getRight() + port.getScreenWidth()/20, distLabel.getHeight()/1.5f);
		font.draw(batch, hiscore, scoreLabel.getRight() + port.getScreenWidth()/20, scoreLabel.getHeight()/1.5f);

	}

	public void message(Batch batch, String message){


		font.draw(batch, message, port.getScreenWidth()/2, port.getScreenHeight()/2 + font.getCapHeight(), 0, 1, false );

	}

	public void dispose ()
	{
		stage.dispose();
		atlas.dispose();

		buttonSkin.dispose();

	}

	public boolean muted()
	{
		return muted;
	}
}
