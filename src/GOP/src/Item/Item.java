package Item;
import Bomb.*;
import Main.*;
import Player.*;
import Sprite.*;
import Tile.*;

public abstract class Item extends Tile{

	private boolean done;
	
	Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void destroy() 
	{
		done = true;
	}
	
	public boolean isRemove() {
		return done;
	}


}
