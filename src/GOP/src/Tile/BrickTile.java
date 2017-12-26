package Tile;
import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Sprite.*;

public class BrickTile extends Tile {

	public BrickTile(int x, int y) {
		 super(x,y,new BrickSprite());
	}

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return false;
	}

}
