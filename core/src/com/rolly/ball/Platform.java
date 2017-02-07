package com.rolly.ball;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.rolly.ball.Constants.PPM;
import static com.rolly.ball.Constants.V_HEIGHT;

public class Platform extends Sprite{
	
	//fields
	private int w = (int)V_HEIGHT/3, h = (int)V_HEIGHT/18;
	private Random rand;
	private int spawnRange;
	private float x;
	private boolean setDead = false;
	private boolean dead = false;
	private Body body;
	private BodyDef bdef;
	private FixtureDef fdef;
	private Fixture sensor;
	private static Texture sprite = new Texture("plat2.png");
	private World world;
	
	//constructors
	public Platform (World world, float x)
	{
		super(sprite);
		this.x = x;
		this.world = world;
		rand = new Random();
		spawnRange =   (int)(.3*V_HEIGHT + rand.nextInt((int)(.5*V_HEIGHT)));
		
		setBounds(0, 0, w/PPM, h/PPM);
		
		
		bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.KinematicBody;
		bdef.position.set(x + w/PPM/2, spawnRange/PPM );
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w/2/PPM, h/2/PPM);
		
		fdef = new FixtureDef();
		fdef.density = 2;
		fdef.shape = shape;
		
		body = world.createBody(bdef);
		body.createFixture(fdef);
		
		shape = new PolygonShape();
		shape.setAsBox(((w/2) - w/25)/PPM, h/6/PPM, new Vector2(4/PPM, h/4/PPM), 0);
		
		
		fdef.shape = shape;
		
		
		fdef.isSensor = true;
		sensor = body.createFixture(fdef);
		sensor.setUserData("ground");
		
		shape.dispose();
		
//		System.out.println(x);
	}
 
	//properties
	public boolean Dead()
	{
		return dead;
	}
	
	public void Kill(boolean isDead)
	{
		setDead = isDead;
		if (setDead && !dead)
		{
			world.destroyBody(body);
			dead = true;
		}
		
	}
	
	public float X()
	{
		return x;
	}
	

	public Body getBody() {
		// TODO Auto-generated method stub
		return body;
	}
	
	//methods
	public void update()
	{
		if (!dead)
		{
		setPosition(body.getWorldCenter().x - w/2/PPM, body.getWorldCenter().y - h/2/PPM);
		}
	}
	
	
	
	public void draw(Batch batch)
	{
		if (!dead)
			super.draw(batch);
	}
	
	
}
