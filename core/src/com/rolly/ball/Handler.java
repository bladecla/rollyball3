package com.rolly.ball;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


//handles platforms

public class Handler {
	
	private LinkedList<Platform> plats = new LinkedList<Platform>();
	
	public Handler()
	{
		
	}
	
	public LinkedList<Platform> getList()
	{
		return plats;
	}
	
public void Update()
{
	for (int i = plats.size() - 1; i >= 0 ; i-- )
	{
		plats.get(i).update();
		
		if (plats.get(i).Dead() == true)
		{
			plats.remove(plats.get(i));
//			System.out.println(plats.size());
		}
	}
}
	
public void Draw(SpriteBatch batch)
{
	if (plats.size() >= 1)
	{
		for (int i = plats.size() - 1; i >= 0 ; i-- )
		{
		
			plats.get(i).draw(batch);
		}
	}
}

public void Add(Platform plat)
{
	
	plats.add(plat);
	
}

public void Remove(Platform plat)
{
	if (plats.size() >= 1)
	{
		plat.Kill(true);
	this.plats.remove(plat);
	
//		System.out.println(this.plats.size());

	}
}

public void Clear()
{
	
	for (int i = plats.size() - 1; i >= 0 ; i-- )
	{
		plats.get(i).Kill(true);
	}
	plats.clear();
}
}

