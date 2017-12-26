//package Main;
//
//import java.util.ArrayList;
//import Bomb.*;
//import Main.*;
//import Player.*;
//import Sprite.*;
//import Tile.*;
//public class Bullet extends Tile implements Updateable//extends GameObject implements Renderable, Updateable
//{
//	private double _speed;
//	private int _direction;
//	private boolean done;
//	
//	public Bullet(int x, int y, int dir)
//	{
//		super( x, y, new BombSprite());
//		done = false;
//		_direction = dir;
//		_speed = 2;
//	}
//
//	@Override
//	public void update(Handler handler, double deltaTime) 
//	{	
//		
//			if(_direction == 0 )
//			{
//				collision(handler.getAtTile((int)_x, -(int)_y%16+(int)_y));
//				_y -= _speed;
//			}
//			else if(_direction == 1){
//				collision(handler.getAtTile((int)_x, (int)_y%16+(int)_y));
//				_y += _speed;
//			}
//			else if(_direction == 2){
//				collision(handler.getAtTile(-(int)_x%16+(int)_x, +(int)_y));
//				_x -= _speed;
//			}
//			else if(_direction == 3 ){
//				collision(handler.getAtTile((int)_x%16+(int)_x, +(int)_y));
//				_x += _speed;
//
//		}
//		
//	}
//
//	private void collision(ArrayList<GameObject> atTile) {
//			for(int i = 0; i < atTile.size(); ++i){
//				if(atTile.get(i) instanceof BrickTile || atTile.get(i) instanceof BoxTile || atTile.get(i) instanceof BombTile )
//				{
//					done = true;
//				}
//				if(atTile.get(i) instanceof Player)
//				{
//					Player p  = (Player)atTile.get(i);
//					//p.hit();
//				}
//			}
//		}
//
//	@Override
//	public boolean isRemove() {
//		// TODO Auto-generated method stub
//		return done;
//	}
//
//	@Override
//	public void update(HandlerUpdate handler, double deltaTime) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//}
