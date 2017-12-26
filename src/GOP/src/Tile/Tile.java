package Tile;
import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Sprite.*;


public abstract class Tile extends GameObject implements Renderable{

		protected Sprite _sprite;

		public Tile(int x,int y,Sprite sprite)
		{
			_x = x;
			_y = y;
			_width = sprite._SIZE;
			_height = sprite._SIZE;
			_sprite = sprite;
		}
		
		public void render(Handler h) {
			for(int y = 0; y < _height; y++) {
				int ya = y + (int)_y;
				for(int x = 0; x < _width; x++) {
					int xa = x + (int)_x;
					if(xa < 0 || xa > h._width || ya < 0 || ya > h._height || xa + ya * h._width >= h._width*h._height) break;
					int col = _sprite._pixels[x + y * _width];
					if(col != 0xffff00ff) h._pixels[xa + ya * h._width] = col;
				}
			}
		}

		public abstract boolean isRemove();

		
}

