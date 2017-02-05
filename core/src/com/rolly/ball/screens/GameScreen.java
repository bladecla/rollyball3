
package com.rolly.ball.screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rolly.ball.Ball;

import static com.rolly.ball.Constants.PPM;

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
	
	private Ball rolly;
	private float distance = 0f;
	private Integer score, hiscore = 0;
	private float deltaP;
	private float platformDelay = 1.0f;
	private float timer;
	private float oldP;
	private float groundHeight;
	private boolean jumping;
	private boolean pressed = false;
	public State gameState;
	
	private BitmapFont font;
	
	private Music music, die, ready;
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
		port = new FitViewport(RollyBall.V_WIDTH / PPM, RollyBall.V_HEIGHT / PPM, camera);
		camera.position.set((port.getWorldWidth()/2) , (port.getWorldHeight()/2) , 0);
		groundHeight = (port.getWorldHeight()/4);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	world = new World(new Vector2(0, -12.81f), true);
	world.setContactListener(cm = new CollisionManager());
	b2dr = new Box2DDebugRenderer();
		ground = new Ground(port.getWorldWidth() *2, groundHeight, world);
//		rolly = new Ball(world);
		handler = new Handler();
		background = new Texture("skyy.jpg");
		music = Gdx.audio.newMusic(Gdx.files.internal("bgm.wav"));
		die = Gdx.audio.newMusic(Gdx.files.internal("die.wav"));
		jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
		ready = Gdx.audio.newMusic(Gdx.files.internal("ready.wav"));
		
		
		initFonts();
		hud = new HUD(game.batch, font);
	}

	private void initFonts()
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ALBA____.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = 36;
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
			
			distance = 0f;
			score = 0;
			die.stop();
			ready.setLooping(true);
			ready.play();
			
			if (rolly == null)
			{
				rolly = new Ball(world);
				
				
				
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || (Gdx.input.justTouched()))
			{
				ready.setLooping(false);
			gameState = State.Play;
			}	
			break;
			
		case Play:
			
			
			
			music.setLooping(true);
			if (!ready.isPlaying())
			{
			music.play();
			}
			
			deltaP = rolly.GetBody().getPosition().x - oldP;
			distance += deltaP;
			score = (int)distance;
			if (score > hiscore)
			{
				hiscore = score;
			}
			
			
			
	rolly.roll(cm.IsGrounded());
			
	if (timer > platformDelay && rolly.maxSpeedReached())
	{
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
					
						if ( jumping)
						{
					
							jump.play();
					
						}	
					}
					
				}
				
				
				if (pressed && !Gdx.input.isKeyPressed(Input.Keys.SPACE))
				{
					pressed = false;
					rolly.endjump();
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
			die.play();
			die.setLooping(false);
//			world.destroyBody(rolly.GetBody());
			rolly.kill(true);
			rolly = null;
			}
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched())
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
		if (rolly != null)
		{
		rolly.draw(game.batch);
		
		}
		handler.Draw(game.batch);
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.draw(game.batch, score.toString(), hiscore.toString());
		game.batch.end();
		
		
//		hud.setScore(distance);
		hud.stage.act();
		hud.stage.draw();
		
		
//		b2dr.render(world, camera.combined);
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
	}
	
	

}
