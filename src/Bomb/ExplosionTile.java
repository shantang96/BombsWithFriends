package Bomb;
import Sprite.*;
import Item.*;
import Tile.*;
import Player.*;
import Main.*;
public class ExplosionTile extends Tile implements Updateable{
	Item _item;
	double ns = 1000000000.0*.75;
	double delta = 0;
	int b =0;
	Double lastTime;
	private boolean done;
	ExplosionTile(int x, int y, Item _holdItem) {
		super(x, y,  new ExplosionSprite());
		done = false;
		_item = _holdItem;
		//super(x, y, explosion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return done;
	}
	public void setItem(Item item)
	{
		_item = item;
	}
	@Override
	public void update(HandlerUpdate handler, double deltaTime) {
		if(lastTime == null)
		{
			lastTime = new Double(deltaTime);
		}
	    	double now = deltaTime;
	    	
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	
	    	if(b >= 50) {
	    		done = true;
	    		if(_item != null)
	    		{
	    			handler.addObject(_item);
	    			b=0;
	    		}
	    	}
	    	b++;

		}
		
	}

