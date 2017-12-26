package Tile;
import java.util.Random;
import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Sprite.*;

public class BoxTile extends Tile {

	boolean done;
	public BoxTile(int x, int y) {
		super(x,y,new BoxSprite());
		done = false;
	}

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return done;
	}

	public Item generateItem() {
		done = true;
		Random rn = new Random();
		rn.setSeed(System.nanoTime());
		switch(rn.nextInt(8))
		{
		case 0:		return new BombUpItem((int)_x, (int)_y);
				
		case 1:		return new ExplosionUpItem((int)_x, (int)_y);
				
		case 2:		return new SpeedUpItem((int)_x, (int)_y);
		
		case 3:		return new KickBombItem((int)_x, (int)_y);
		
		case 4:		return new PunchBombItem((int)_x, (int)_y);
		
		case 5:		return new SpikeBombItem((int)_x, (int)_y);
		
		case 6:		return new SpeedBombItem((int)_x, (int)_y);
		}
		return null;
		
	}


}
