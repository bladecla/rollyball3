package com.rolly.ball;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionManager implements ContactListener {

	private boolean grounded = false;
	@Override
	public void beginContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa.getUserData() != null && fb.getUserData() != null && 
				fa.getUserData().equals("rolly") && fb.getUserData().equals("ground"))
		{
			grounded = true;
			
		}
		else if (fa.getUserData() != null && fb.getUserData() != null && 
				fb.getUserData().equals("rolly") && fa.getUserData().equals("ground"))
		{
			grounded = true;
			
		}
		
	}

	@Override
	public void endContact(Contact c) {
		// TODO Auto-generated method stub
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa.getUserData() != null && fb.getUserData() != null && 
				fa.getUserData().equals("rolly") && fb.getUserData().equals("ground"))
		{
			grounded = false;
			
		}
		else if (fa.getUserData() != null && fb.getUserData() != null && 
				fb.getUserData().equals("rolly") && fa.getUserData().equals("ground"))
		{
			grounded = false;
			
		}
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean IsGrounded()
	{
		return grounded;
	}
}
