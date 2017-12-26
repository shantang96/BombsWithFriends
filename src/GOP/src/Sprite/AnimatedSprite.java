package Sprite;
import java.io.Serializable;

import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Tile.*;

public class AnimatedSprite implements Updateable, Serializable{
	
	private static final long serialVersionUID = 1;
	private Sprite[] _sprites;
	private int _spritesIndex;
	private boolean _running;
	private Double lastTime;
	double ns = 1000000000.0*.50;
	double delta = 0;
	
	private String spriteSheetName;
	
	public AnimatedSprite()
	{
		spriteSheetName = "./resources/spritesheets/BasicPlayerSheet.png";
	}
	
	public void setSpriteSheet(String spriteSheetName){
		this.spriteSheetName = spriteSheetName;
	}
	
	public AnimatedSprite(String path, int sizeOfSprites, int column, int numberOfSprites) {
		_sprites = new Sprite[numberOfSprites];
		
		for(int i = 0; i < _sprites.length; ++i)
		{
			_sprites[i] = new Sprite(column,i,new SpriteSheet(path,16));
		}
		_spritesIndex = 0;
		_running = false;
	}
	
	public void start()
	{
		_running = true;
	}
	
	public void stop()
	{
		lastTime = null;
		_spritesIndex = 0;
		_running = false;
	}

	public void update(HandlerUpdate handler, double deltaTime) {
		if(lastTime == null)
		{
			lastTime = new Double(deltaTime);
		}
	    	double now = deltaTime;
	    	
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	
	    	if(delta >= 1) {
	    		if(_running){
	    			if(_spritesIndex >= _sprites.length-1){
	    				_spritesIndex = 0;
	    			} else {
	    				_spritesIndex++;
	    			}
	    		}
	    		delta--;
	    	}

	}

	
	public Sprite getSprite(){
		return _sprites[_spritesIndex];
	}

	
	

}
