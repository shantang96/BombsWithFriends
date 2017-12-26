package Sprite;
import java.io.Serializable;

import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Tile.*;

public class PlayerForward extends AnimatedSprite implements Serializable{

	private static final long serialVersionUID = 1;
	public PlayerForward(String spriteSheetName) {
		super(spriteSheetName ,16,0, 3);
	}

}
