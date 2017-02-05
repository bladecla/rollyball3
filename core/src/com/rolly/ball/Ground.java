package com.rolly.ball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.rolly.ball.Constants.PPM;

public class Ground extends Sprite {

	//fields
	private static Texture sprite = new Texture("grass.png");
	private Body body;
	private BodyDef bdef;
	private FixtureDef fdef;
	
	//constructors
	public Ground(float w, float h, World world)
	{
		super(sprite);
		setBounds(0,0,w,h/2 + 10/PPM);
		
		bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(w/2, 0/ PPM);
		
		
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w/2, (h/2));
		
		fdef = new FixtureDef();
		fdef.density = 25;
		fdef.shape = shape;
		
//		fdef.friction = 0.5f;
		
		
		body = world.createBody(bdef);
		body.createFixture(fdef).setUserData("ground");
		
		setPosition(body.getWorldCenter().x - w/2, body.getWorldCenter().y);
		
		shape.dispose();
	}
	
	//properties
	public Body getBody()
	{
		return body;
	}
}
