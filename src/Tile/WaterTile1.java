package Tile;
import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Sprite.*;

public class WaterTile1 extends Tile {

	public WaterTile1(int x, int y) {
		 super(x,y,new WaterSprite1());
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return false;
	}

}
