package Main;

import Bomb.*;
import Item.*;
import Player.*;
import Sprite.*;
import Tile.*;
public interface Updateable {
	public void update( HandlerUpdate handler, double deltaTime);
}
