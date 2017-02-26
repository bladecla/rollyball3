
package com.rolly.ball.screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rolly.ball.Ball;

import static com.rolly.ball.Constants.PPM;
import static com.rolly.ball.Constants.V_HEIGHT;
import static com.rolly.ball.Constants.V_WIDTH;

import com.rolly.ball.CollisionManager;
import com.rolly.ball.Ground;
import com.rolly.ball.HUD;
import com.rolly.ball.Handler;
import com.rolly.ball.Platform;
import com.rolly.ball.RollyBall;

public class GameScreen implements Screen{

	private RollyBall game;
	
	private OrthographicCamera camera;
	private Viewport port;
	private Texture background;



	private Box2DDebugRenderer b2dr;
	private World world;
	private Ground ground;
	private CollisionManager cm;
	private Handler handler;
	private HUD hud;
	private Preferences save = Gdx.app.getPreferences("saveData");


	private Ball rolly;
	private float distance = 0f;
	private Integer score, hiscore = save.getInteger("hi-score", 0);
	private float deltaP;
	private float platformDelay = 1.0f;
	private float timer, scoreSpeed = 400;
	private float oldP;
	private float groundHeight;
	private float defaultVolume = .5f, currentVolume;
	private boolean muted = false;
	private boolean jumping, drawMsg = true;
	private boolean pressed = false;
	public State gameState;
	private String message;
	
	private BitmapFont font;
	
	private Music music, die, ready, currentMusic;
	private Sound  jump;



	
	private enum State
	{
		Ready(),
		Play(),
		GameOver()
	}
	
	public GameScreen(RollyBall game)
	{
		this.game = game;
		
		gameState = State.Ready;
		camera = new OrthographicCamera();
		port = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, camera);
		camera.position.set((port.getWorldWidth()/2) , (port.getWorldHeight()/2) , 0);

	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	world = new World(new Vector2(0, -V_HEIGHT/30), true);
	world.setContactListener(cm = new CollisionManager());
	b2dr = new Box2DDebugRenderer();
		ground = new Ground(port.getWorldWidth() *2, port.getWorldHeight()/4, world);
//		rolly = new Ball(world);
		handler = new Handler();
		background = new Texture("skyy.jpg");

		currentVolume = defaultVolume;
		music = Gdx.audio.newMusic(Gdx.files.internal("bgm.wav"));
		music.setVolume(currentVolume);
		music.setLooping(true);
		die = Gdx.audio.newMusic(Gdx.files.internal("die.wav"));
		die.setVolume(currentVolume);
		jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));

		ready = Gdx.audio.newMusic(Gdx.files.internal("ready.wav"));
		ready.setVolume(currentVolume);
		ready.setLooping(true);
		
		
		initFonts();
		hud = new HUD(game.batch, font);
	}

	private void initFonts()
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ALBA____.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = (int)(.06*V_HEIGHT);
		params.color = Color.WHITE;
		font = generator.generateFont(params);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		timer += Gdx.graphics.getDeltaTime();
		if(rolly != null)
			
		{
		oldP = rolly.GetBody().getPosition().x;
		}
		
		world.step(1/60f, 6, 2);
		
		for (int i = handler.getList().size() - 1; i >= 0 ; i-- )
		{
			if (handler.getList().get(i).X() < camera.position.x - port.getWorldWidth())
			{
			handler.Remove(handler.getList().get(i));
			}
		}
		
		
		
		
		switch(gameState)
		{
		case Ready:
			if(!ready.isLooping() && !Gdx.input.isTouched()){gameState = State.Play;}
			distance = 0f;
			score = 0;
			die.stop();
			currentMusic = ready;
			currentMusic.play();

		message = "Tap!";

			if (rolly == null)
			{
				rolly = new Ball(world);
				
				
				
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || (Gdx.input.justTouched()))
			{


				ready.setLooping(false);


			}	
			break;
			
		case Play:

			message = "Let's Roll!";
			

			if (!ready.isPlaying())
			{
				currentMusic = music;
			currentMusic.play();
			}
			
			deltaP = rolly.GetBody().getPosition().x - oldP;
			distance += deltaP;
			score = (int)(scoreSpeed*distance/rolly.getXVel());
			if (score > save.getInteger("hi-score"))
			{
				hiscore = score;
			}
			
			
			
	rolly.roll(cm.IsGrounded());
			
	if (timer > platformDelay && rolly.maxSpeedReached())
	{
		drawMsg = false;
		if (handler.getList().size() < 5)
		{
		handler.Add(new Platform(world, camera.position.x + port.getWorldWidth()/2));
		}
		timer = 0;
	}
	
			
		
		//input
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched())
				{
//					System.out.println(camera.position.x);

					pressed = true;
					if(cm.IsGrounded())
					{
						jumping = rolly.jump(jumping);
					
						if ( jumping && !muted)
						{
					
							jump.play(currentVolume);
					
						}	
					}
					
				}
				
				
				if ((pressed && !Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (pressed && !Gdx.input.isTouched()))
				{

					rolly.endjump();
					pressed = false;
				}
				
				if (rolly.GetBody().getPosition().y < -75/PPM)
				{
					gameState = State.GameOver;
				}
				
			break;
			
		case GameOver:
			
			if (rolly != null)
			{
			music.stop();
				currentMusic = die;
			currentMusic.play();
			die.setLooping(false);
				ready.setLooping(true);
//			world.destroyBody(rolly.GetBody());
			rolly.kill(true);
			rolly = null;
				timer = 0;
				message = "GAME OVER";
				drawMsg = true;
				if (hiscore > save.getInteger("hi-score")) {
					save.putInteger("hi-score", hiscore);
					save.flush();
				}
			}



			if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && timer >= 3)
			{
				camera.position.set((port.getWorldWidth()/2) , (port.getWorldHeight()/2) , 0);
				handler.Clear();
				
				deltaP = 0;
				gameState = State.Ready;
			}
			break;
			
		}
		

		if(rolly != null)
		{
		rolly.update();
		}
		handler.Update();
		
		
		
		
		camera.position.x += deltaP;
		
//		+ (rolly.GetBody().getPosition().x + 100/PPM) ;
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(background, camera.position.x - port.getWorldWidth()/2, 0, port.getWorldWidth(), port.getWorldHeight());
		ground.draw(game.batch);

		handler.Draw(game.batch);
		if (rolly != null)
		{
		rolly.draw(game.batch);
		
		}

		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.draw(game.batch, score.toString(), hiscore.toString());
		if (drawMsg) {
			hud.message(game.batch, message);
		}
		game.batch.end();

		
		
//		hud.setScore(distance);
		hud.stage.act();
		hud.stage.draw();
		muted = hud.muted();
		if (muted)
		{
		currentMusic.setVolume(0);
		} else if (!muted)
		{
			currentMusic.setVolume(currentVolume);
		}
		
//		b2dr.render(world, camera.combined);

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) Gdx.app.exit();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		port.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		b2dr.dispose();
		world.dispose();
		music.dispose();
		die.dispose();
		ready.dispose();
		jump.dispose();
		background.dispose();
		font.dispose();
		hud.dispose();
	}
	
	

}
