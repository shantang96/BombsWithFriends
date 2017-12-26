package Player;

import java.io.Serializable;
import java.util.ArrayList;

import Bomb.*;
import Item.*;
import Main.*;
import Sprite.*;
import Tile.*;
public class Player extends GameObject implements Renderable, Updateable, Serializable {

	private static final long serialVersionUID = 1L;
	private Sprite _sprite;
	private AnimatedSprite[] _playerAnimation;
//	private KeyBoard _keyboard;
	private ArrayList<BombTile> _bombs = new ArrayList<BombTile>();
	private int _itemtype;
	private int _bombtype;
	private int _targetx, _targety;
	private double _speed;
	private int _bombnumber;
	private int _explosionnumber;
	private int _direction2;
	private boolean killed = false;
	public boolean flagger = false;
	private String spriteSheetName;
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean space;
	public boolean z_;
	public boolean x_;
	
	private int hit = 0;
	private int z = 0;
	private Boolean keyZ = false;
	private int playerNo;

	
	public Player(int x,int y, int playerNo) {
		_x = x;
		_y = y;
		_targetx = x;
		_targety = y;
		this.playerNo = playerNo;

		_direction2 = 0;
		_playerAnimation = new AnimatedSprite[4];
		_playerAnimation[0] = new PlayerForward(spriteSheetName);
		_playerAnimation[1] = new PlayerLeft(spriteSheetName);
		_playerAnimation[2] = new PlayerBackward(spriteSheetName);
		_playerAnimation[3] = new PlayerRight(spriteSheetName);
		_sprite = _playerAnimation[_direction2].getSprite();
		
		_width = _sprite._SIZE;
		_height = _sprite._SIZE;
//		_keyboard = keyboard;
		
		_bombtype = 0;
		_explosionnumber = 2;
		_bombnumber = 1;
		_speed = 1;
		
		_itemtype = 0;
	}
	
	public Player(int x,int y, int playerNo, String spriteSheetName) {
		
		_x = x;
		_y = y;
		_targetx = x;
		_targety = y;
		this.playerNo = playerNo;

		_direction2 = 0;
		_playerAnimation = new AnimatedSprite[4];

//		System.out.println("player: spriteSHeetName: " + spriteSheetName);
		_playerAnimation[0] = new PlayerForward(spriteSheetName);
		_playerAnimation[1] = new PlayerLeft(spriteSheetName);
		_playerAnimation[2] = new PlayerBackward(spriteSheetName);
		_playerAnimation[3] = new PlayerRight(spriteSheetName);
//		for(int i = 0; i < 4; i++){
//			_playerAnimation[i].setSpriteSheet(spriteSheetName);
//		}
		_sprite = _playerAnimation[_direction2].getSprite();
		
		_width = _sprite._SIZE;
		_height = _sprite._SIZE;
//		_keyboard = keyboard;
		
		_bombtype = 0;
		_explosionnumber = 2;
		_bombnumber = 1;
		_speed = 6;
		
		_itemtype = 0;
	}
	
	@Override
	public void update(HandlerUpdate handler, double deltaTime) {
		
		//System.out.println("x" + _x + " y" + _y + " _targetx"+_targetx + " _targety"+_targety );
		for(int i = 0; i < _bombs.size(); ++i){
			if( _bombs.get(i).isRemove() == true){
				_bombs.remove(i);
			}
		} 
		
		collision(handler.getAtTile(_targetx,_targety));
		if(_y == _targety && _x == _targetx)
		{
			_playerAnimation[_direction2].stop();
			if(space && _bombs.size() < _bombnumber &&!collision(handler.getAtTile((int)_x, (int)_y))){
				BombTile newBomb = null;
				if(_bombtype == 0){
				 newBomb = new Bomb((int)_x,(int)_y, _explosionnumber);
				} else if(_bombtype == 1){
					 newBomb = new SpikeBomb((int)_x,(int)_y, _explosionnumber);
				} else if(_bombtype == 2){
					 newBomb = new SpeedBomb((int)_x,(int)_y, _explosionnumber);
				}
				
				handler.addObject(newBomb);
				_bombs.add(newBomb);
			}
			else if(x_)
			{
				if(_itemtype != 0){
					if(_direction2 == 0){
						bombCollision(handler.getAtTile((int)_x,(int)_y+16));
					} else if(_direction2 == 1){
						bombCollision(handler.getAtTile((int)_x-16,(int)_y));
					} else if(_direction2 == 2){
						bombCollision(handler.getAtTile((int)_x,(int)_y-16));
					}else if(_direction2 == 3){
						bombCollision(handler.getAtTile((int)_x+16,(int)_y));
					}
				}
			}
			/*else if(_keyboard.x && _keyboard.up &&  !collision(handler.getAtTile((int)_x, (int)_y-16)))
			{
				_targety = (int)_y-16;
				_direction = -1;
				_direction2 = 0;
				handler.addObject(new Bullet((int)_x, (int)_y, _direction2));
			}
			else if(_keyboard.x && _keyboard.down &&  !collision(handler.getAtTile((int)_x, (int)_y+16))){
				_targety = (int)_y+16;
				_direction = 1;
				_direction2 = 1; 
				handler.addObject(new Bullet((int)_x, (int)_y, _direction2));

			} else if(_keyboard.x && _keyboard.left &&  !collision(handler.getAtTile((int)_x-16, (int)_y))){
				_targetx = (int)_x-16;
				_direction = -1;
				_direction2 = 2;
				handler.addObject(new Bullet((int)_x, (int)_y, _direction2));

			} else if(_keyboard.x && _keyboard.right &&  !collision(handler.getAtTile((int)_x+16, (int)_y))){
				_targetx = (int)_x+16;
				_direction = 1;
				_direction2 = 3; 
				handler.addObject(new Bullet((int)_x, (int)_y, _direction2));*/

			else if(up ){//&&  !collision(handler.getAtTile((int)_x, (int)_y-16))){
				_direction2 = 2; 
				if(!collision(handler.getAtTile((int)_x, (int)_y-16))){
					_targety = (int)_y-16;
					_playerAnimation[_direction2].start();
				}
			} else if(down ){//&&  !collision(handler.getAtTile((int)_x, (int)_y+16))){
				_direction2 = 0; 
				if(!collision(handler.getAtTile((int)_x, (int)_y+16))){
				_targety = (int)_y+16;
				_playerAnimation[_direction2].start();
				}
			} else if(left ){//&&  !collision(handler.getAtTile((int)_x-16, (int)_y))){
				_direction2 = 1; 
				if( !collision(handler.getAtTile((int)_x-16, (int)_y))){
				_targetx = (int)_x-16;
				_playerAnimation[_direction2].start();
				}
			} else if(right ){//&&  !collision(handler.getAtTile((int)_x+16, (int)_y))){
				_direction2 = 3; 
				if(!collision(handler.getAtTile((int)_x+16, (int)_y))){
				_targetx = (int)_x+16;
				_playerAnimation[_direction2].start();
				}
			}
			/*else if(_keyboard.x)
			{
				handler.addObject(new Bullet((int)_x, (int)_y, _direction2));
			}*/
			else if(z_)
			{
				keyZ=true;
				collision(handler.getAtTile(_targetx,_targety));
			}
		}
		else
		{
			move();
			_playerAnimation[_direction2].update(handler, deltaTime);
		}
		
		_sprite = _playerAnimation[_direction2].getSprite();
	}

	public void setSpriteSheet(String spriteSheetName) {
		this.spriteSheetName = spriteSheetName;
	}
	
	private void bombCollision(ArrayList<GameObject> atTile) {
		for(int i = 0; i < atTile.size(); ++i)
		{
			if( atTile.get(i) instanceof BombTile)
			{ 
				 ((BombTile) atTile.get(i)).force(_itemtype,_direction2);
			}
		}
	}

	private boolean collision(ArrayList<GameObject> atTile) {
		for(int i = 0; i < atTile.size(); ++i){
			if(atTile.get(i) instanceof BrickTile || atTile.get(i) instanceof BoxTile || atTile.get(i) instanceof BombTile )
			{
				return true;
			}
			else if(atTile.get(i) instanceof ExplosionTile)
			{
				bombed();
			}
			else if(atTile.get(i) instanceof Player && keyZ)
			{
				Player P = (Player)atTile.get(i);
				P.meleeDamage();
				keyZ = false;
			}
			else if(atTile.get(i) instanceof Item)
				{
					if(atTile.get(i) instanceof BombUpItem && _bombnumber < 5)
					{
						_bombnumber++;
					}
					else if(atTile.get(i) instanceof ExplosionUpItem && _explosionnumber < 4)
					{
						_explosionnumber++;
					}
					else if(atTile.get(i) instanceof SpeedUpItem && _speed < 2.5)
					{
						_speed += 0.5;
					}
					else if(atTile.get(i) instanceof KickBombItem)
					{
						_itemtype = 1;
					}
					else if(atTile.get(i) instanceof PunchBombItem)
					{
						_itemtype = 2;
					}
					else if(atTile.get(i) instanceof SpikeBombItem)
					{
						_bombtype = 1;
					}
					else if(atTile.get(i) instanceof SpeedBombItem)
					{
						_bombtype = 2;
					}
					((Item) atTile.get(i)).destroy();
			}
		}
		return false;
	}

	private void move() {
		/*if(_y != _targety){
			_y+=_speed*(double)_direction;
		} else if(_x != _targetx){
			_x+=_speed*(double)_direction;
		}	
		if(_direction == -1 && _y+_speed*_direction < _targety){
			_y = _targety;
		}
		if(_direction == 1 && _y+_speed*_direction > _targety){
			_y = _targety;
		}
		if(_direction == -1 && _x+_speed*_direction < _targetx){
			_x = _targetx;
		}
		if(_direction == 1 && _x+_speed*_direction > _targetx){
			_x = _targetx;
		}*/
		if(_direction2 == 0)
		{
			if(_y+_speed > _targety){
				_y = _targety;
			} else {
				_y+=_speed;
			}
		} 
		else if(_direction2 == 1)
		{
			if(_x-_speed < _targetx){
				_x = _targetx;
			} else {
				_x-=_speed;
			}
			
		}
		else if(_direction2 == 2)
		{
			if(_y-_speed < _targety){
				_y = _targety;
			} else {
				_y-=_speed;
			}
		}
		else if(_direction2 == 3)
		{
			if(_x+_speed > _targetx){
				_x = _targetx;
			} else {
				_x+=_speed;
			}	
		}
	}

	@Override
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

	@Override
	public boolean isRemove() {
		// TODO Auto-generated method stub
		return killed;
	}

	
	public void bombed()
	{
		killed = true;
	}
	public void hit()
	{
		hit++;
		if(hit > 5)
		{
			killed  = true;
		}
	}

	public void meleeDamage()
	{
		if(z>10)
		{
			killed = true;
		}
		else
		{
			z++;
		}
	}
	
	public int getXCo()
	{
		return _targetx;
	}
	
	public int getYCo()
	{
		return _targety;
	}
	
	public int getPlayerNo()
	{
		return playerNo;
	}
	
	public ArrayList<BombTile> getBombsArray()
	{
		return _bombs;
	}
	
	public void setBombsArray(ArrayList<BombTile> bombMap)
	{
		_bombs = bombMap;	
	}
	
	public void setPlayerNo(int n)
	{
		this.playerNo = n;
	}
	
//	public void setKeyboard(KeyBoard key)
//	{
//		_keyboard = key;
//	}

	public void setX(int x)
	{
		super.setX(x);
		_targetx = x;
	}
	public void setY(int y)
	{
		super.setY(y);
		_targety = y;
		//System.out.println("this is called and _targety: " + _targety);
	}
	public int getX()
	{
		return _targetx;
	}
	public int getY()
	{
		return _targety;
	}
	
	public int getPowerUp() {
		return _itemtype;
	}
	
	public int getBombType() {
		return _bombtype;
	}
	
//	public KeyBoard getKeyboard()
//	{
//		return _keyboard;
//	}
	
	public void setMoves(Location l)
	{
		this.up = l.up;
		this.down = l.down;
		this.left = l.left;
		this.right = l.right;
		this.space = l.space;
		this.x_ = l.x;
		this.z_ = l.z;
		
	}
	
	
	
}
