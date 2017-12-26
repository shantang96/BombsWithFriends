package Sprite;
import java.io.Serializable;

import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Tile.*;

public class PlayerBackward extends AnimatedSprite implements Serializable {

	private static final long serialVersionUID = 1;
	public PlayerBackward(String spriteSheetName) {
		super(spriteSheetName ,16,2, 3);
		// TODO Auto-generated constructor stub
	}

}
