package Tile;
import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Sprite.*;

public class GrassTile extends Tile {

	public GrassTile(int x, int y) {
		super(x,y,new GrassSprite());
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return false;
	}

}
