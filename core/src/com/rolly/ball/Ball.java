package com.rolly.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.rolly.ball.Constants.PPM;

public class Ball extends Sprite{

	//fields
	private int diameter = 75;
	private float jumpVel = 8;
	private float xVel = 2.3f ;
	private BodyDef bdef = new BodyDef();
	private FixtureDef fdef = new FixtureDef();
	private Body body;
	private CircleShape shape = new CircleShape();
	private Fixture sensor;
	private World world;
	private boolean setDead = false, dead = false;
	private static Texture sprite  = new Texture("Rolly3.png");
	
	//constructors
	public Ball(World world)
	{
		super(sprite);
		setBounds(0, 0, diameter/PPM, diameter/PPM);
		setOriginCenter();
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(new Vector2(100/PPM,500/ PPM));
		
		this.world = world;
//		bdef.linearVelocity.set(xVel,0);
		
		
		shape.setRadius((diameter / 2)/ PPM);
		shape.setPosition(new Vector2(Vector2.Zero));
		fdef.shape = shape;

		
//		fdef.friction = .03f;
//		fdef.restitution = .1f;
		
		fdef.density = .2f;
		body = world.createBody(bdef);
		
		body.createFixture(fdef);
		
		shape = new CircleShape();
		fdef.shape.setRadius((diameter + 5)/2/PPM);
		
		fdef.isSensor = true;
		
		sensor = body.createFixture(fdef);
		sensor.setUserData("rolly");
		
		
		body.setUserData(sprite);
		shape.dispose();
		
		
	}
	
	//properties
	public Body GetBody()
	{
		return body;
	}
	 
	public float getXVel()
	{
		return xVel;
	}
	
	public boolean maxSpeedReached()
	{
		if(body.getLinearVelocity().x >= 3*xVel/4)
		{
			return true;
		}
		else { return false;}
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void kill(boolean isDead)
	{
		setDead = isDead;
		if (setDead && !dead)
		{
			world.destroyBody(body);
			dead = true;
		}
	}
	//methods
	
	public boolean jump(boolean jumping)
	{
		if (!jumping)
		{
//			if(body.getPosition().y < diameter*(6/PPM))
//			{
//			body.applyLinearImpulse(new Vector2(0,jumpVel), body.getWorldCenter(),  true);
				  body.setLinearVelocity(body.getLinearVelocity().x, jumpVel);
				  
//				  System.out.println("jumping");  
				return true;
			}
		else return false;
//		}
		
	}
	
	public void endjump() 
	{
//		System.out.println(body.getLinearVelocity().y);
	if (body.getLinearVelocity().y > jumpVel/3)
	{
		body.setLinearVelocity(body.getLinearVelocity().x, jumpVel/2);
	}
	System.out.println(xVel);
	}
	
	public void update()
	{ 
		setPosition(body.getWorldCenter().x - (diameter/2)/PPM, body.getWorldCenter().y - diameter/2/PPM);
		
		setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		if (body.getPosition().y < -75/PPM)
		{
			body.setLinearVelocity(0, 0);
		}
		
		
	}
	
	public void roll(boolean grounded)
	{
		
		if(grounded && body.getLinearVelocity().x < xVel)
		{
		body.applyForceToCenter(.5f, 0, true);
		}
		xVel += Gdx.graphics.getDeltaTime()/100;
	}

	
}
