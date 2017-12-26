package Bomb;
import Sprite.*;
import Item.*;
import Tile.*;
import Player.*;
import Main.*;
import java.util.ArrayList;

public abstract class BombTile extends Tile implements Updateable { 
	protected boolean done;
	protected double ns = 1000000000.0*3;
	private double delta = 0;
	private Double lastTime; 
	 int radius;
	 Item _holdItem;
	 int _targetx, _targety;
	protected double _speed = 1.25;
	private int _direction;
	 boolean explode;
	private int _itemtype;
	int b = 0;
	BombTile(int x, int y, int explosionnumber) {
		super(x, y, new BombSprite());
		radius = explosionnumber;
		done = false;
		explode = true;
		_holdItem = null;
		_direction = -1;
		_targetx = x;
		_targety = y;
	}

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return done;
	}


	public void update(HandlerUpdate handler, double deltaTime) {
		if(_y == _targety && _x == _targetx)
		{
			if(_direction == 0)
			{
				if(_itemtype == 1 && !kickCollision(handler.getAtTile((int)_x, (int)_y+16)))
				{
					_targety +=16;
				}
				else if(_itemtype == 2 &&  explode == false)
				{
					punchCollision(handler.getAtTile((int)_x, (int)_y+16));
					_targety +=16;
				}
			}
			else if(_direction == 1){
				if(_itemtype == 1 && !kickCollision(handler.getAtTile((int)_x-16, (int)_y)))
				{
					_targetx -=16;
				}
				else if(_itemtype == 2 &&  explode == false)
				{
					punchCollision(handler.getAtTile((int)_x-16, (int)_y));
					_targetx -=16;
				}
			}
			else if(_direction == 2){
				if(_itemtype == 1 && !kickCollision(handler.getAtTile((int)_x, (int)_y-16)))
				{
					_targety -=16;
				}
				else if(_itemtype == 2 &&  explode == false)
				{
					punchCollision(handler.getAtTile((int)_x, (int)_y-16));
					_targety -=16;
				}
			}
			else if(_direction == 3){
				if(_itemtype == 1 && !kickCollision(handler.getAtTile((int)_x+16, (int)_y)))
				{
					_targetx +=16;
				}
				else if(_itemtype == 2 && explode == false)
				{
					punchCollision(handler.getAtTile((int)_x+16, (int)_y));
					_targetx +=16;
				}
			}
		} 
		else
		{
			move();
		}
		
		
		if(lastTime == null)
		{
			lastTime = new Double(deltaTime);
		}
	    	double now = deltaTime;
	    	
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	
	    	if(b >= 50 && explode == true && _y == _targety && _x == _targetx) {
	    		explosion(handler);
	    		b=0;
	    	}
	    	b++;

		}


	public abstract void explosion (HandlerUpdate handler);
	
	protected int explosioncollision(HandlerUpdate handler, ArrayList<GameObject> atTile) {

			for(int i = 0; i < atTile.size(); ++i){
				if(atTile.get(i) instanceof BrickTile)
				{
					return 1;
				}
				else if(atTile.get(i) instanceof BombTile)
				{
					if(((BombTile) atTile.get(i)).isRemove() == false)
					{
						((BombTile) atTile.get(i)).explosion(handler);
					}
				}
				else if(atTile.get(i) instanceof BoxTile )
				{
					_holdItem = ((BoxTile) atTile.get(i)).generateItem();
					return 2;
				}
				else if(atTile.get(i) instanceof Item)
				{
					((Item) atTile.get(i)).destroy();
				}
			}

		return 0;
	}
	
	public int getX()
	{
		return _targetx;
	}
	public int getY()
	{
		return _targety;
	}

	private void move() {
		if(_direction == 0)
		{
			if(_y+_speed > _targety){
				_y = _targety;
			} else {
				_y+=_speed;
			}
		} 
		else if(_direction == 1)
		{
			if(_x-_speed < _targetx){
				_x = _targetx;
			} else {
				_x-=_speed;
			}
			
		}
		else if(_direction == 2)
		{
			if(_y-_speed < _targety){
				_y = _targety;
			} else {
				_y-=_speed;
			}
		}
		else if(_direction == 3)
		{
			if(_x+_speed > _targetx){
				_x = _targetx;
			} else {
				_x+=_speed;
			}	
		}
	}

	private void punchCollision(ArrayList<GameObject> atTile) {
		for(int i = 0; i < atTile.size(); ++i){
			if(atTile.get(i) instanceof BrickTile || atTile.get(i) instanceof BoxTile || atTile.get(i) instanceof BombTile || atTile.get(i) instanceof Player )
			{
				return;
			}
			}
		explode = true;
	}
	private boolean kickCollision(ArrayList<GameObject> atTile) {
		for(int i = 0; i < atTile.size(); ++i){
		if(atTile.get(i) instanceof BrickTile || atTile.get(i) instanceof BoxTile || atTile.get(i) instanceof BombTile || atTile.get(i) instanceof Player )
		{
			
			_direction = -1;
			return true;
		}
		}
		
		return false;
	}

	public void force(int itemtype, int direction) {
		_itemtype = itemtype;
		_direction = direction;
		if(_itemtype == 2){
			explode = false;
		}
		
	}
	


}
