package Sprite;
import java.io.Serializable;

import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Tile.*;
public class PlayerLeft  extends AnimatedSprite implements Serializable{

	private static final long serialVersionUID = 1;
	public PlayerLeft(String spriteSheetName) {
		super(spriteSheetName ,16,1, 3);
	}

}
