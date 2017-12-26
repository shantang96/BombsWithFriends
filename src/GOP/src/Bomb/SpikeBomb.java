package Bomb;

import Sprite.*;
import Item.*;
import Tile.*;
import Player.*;
import Main.*;
public class SpikeBomb extends BombTile {

	public SpikeBomb(int x, int y, int explosionnumber) {
		super(x, y, explosionnumber);
		radius+=2;
		_speed = .75;
		 ns = 1000000000.0*4.5;
		_sprite = new SpikeBombSprite();
	}
	
	public void explosion (HandlerUpdate handler){
		done = true;
		ExplosionTile explosion = new ExplosionTile(_targetx, _targety, null);

		explosioncollision(handler, handler.getAtTile(_targetx, _targety));
		handler.addObject(explosion);
		
		for(int x2 = -explosion.getWidth(); x2 >= -explosion.getWidth()*radius; x2-= explosion.getWidth())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx+x2, _targety));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx+x2, _targety, _holdItem));
			_holdItem = null;
			
		}
		for(int x2 = explosion.getWidth(); x2 <= explosion.getWidth()*radius; x2+= explosion.getWidth())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx+x2, _targety));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx+x2, _targety, _holdItem));
			_holdItem = null;
		}
		for(int y2 = -explosion.getHeight(); y2 >= -explosion.getHeight()*radius; y2-= explosion.getHeight())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx, _targety+y2));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx, _targety+y2, _holdItem));
			_holdItem = null;
			
		}
		for(int y2 = explosion.getHeight(); y2 <=explosion.getHeight()*radius; y2+=explosion.getHeight())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx, _targety+y2));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx, _targety+y2, _holdItem));
			_holdItem = null;
		}
	}
	


}
