package Bomb;
import Sprite.*;
import Item.*;
import Tile.*;
import Player.*;
import Main.*;
public class Bomb extends BombTile {

	public Bomb(int x, int y, int explosionnumber) {
		super(x, y, explosionnumber);
		// TODO Auto-generated constructor stub
	}
	public void explosion (HandlerUpdate handler){
		done = true;
		ExplosionTile explosion = new ExplosionTile(_targetx, (int)_y, null);
		explosioncollision(handler, handler.getAtTile(_targetx, _targety));
		handler.addObject(explosion);
		
		for(int x2 = -explosion.getWidth(); x2 >= -explosion.getWidth()*radius; x2-= explosion.getWidth())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx+x2, (int)_y));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx+x2, (int)_y, _holdItem));
			_holdItem = null;
			if(type == 2)
			{
				break;
			}
			
		}
		for(int x2 = explosion.getWidth(); x2 <= explosion.getWidth()*radius; x2+= explosion.getWidth())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx+x2, (int)_y));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx+x2, (int)_y, _holdItem));
			_holdItem = null;
			if(type == 2)
			{
				break;
			}
		}
		for(int y2 = -explosion.getHeight(); y2 >= -explosion.getHeight()*radius; y2-= explosion.getHeight())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx, (int)_y+y2));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx, (int)_y+y2, _holdItem));
			_holdItem = null;
			if(type == 2)
			{
				break;
			}
			
		}
		for(int y2 = explosion.getHeight(); y2 <=explosion.getHeight()*radius; y2+=explosion.getHeight())
		{
			int type = explosioncollision(handler, handler.getAtTile(_targetx, (int)_y+y2));
			if(type == 1)
			{
				break;
			}
			handler.addObject(new ExplosionTile(_targetx, (int)_y+y2, _holdItem));
			_holdItem = null;
			if(type == 2)
			{
				break;
			}
		}
	}

}
