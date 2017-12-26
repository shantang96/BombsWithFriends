package Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Bomb.*;
import Player.KeyBoard;
import Tile.*;
public class Location implements Serializable  
{
	private static final long serialVersionUID = 7055937624430613569L;
//	public int x;
//	public int y;
//	public ArrayList<BombTile> bombMap;
//	public KeyBoard key;
	public Boolean up;
	public Boolean down;
	public Boolean left;
	public Boolean right;
	public Boolean space;
	public Boolean z;
	public Boolean x;
	public int playerNo;
	
	public Location(KeyBoard key)
	{
//		this.x = x;
//		this.y = y;
//		this.bombMap = bombMap;
//		this.key = key;
		this.up = key.up;
		this.down = key.down;
		this.left = key.left;
		this.right = key.right;
		this.space = key.space;
		this.z = key.z;
		this.x = key.x;
		
//		Random rand = new Random();
//		int n = rand.nextInt(4);
// 		if(n==0)
//		{
//			this.right = true;
//		}
//		if(n ==1)
//		{
//			this.down = true;
//		}
//		if(n == 2)
//		{
//			this.up = true;
//		}
//		if(n==3)
//		{
//			this.left = true;
//		}
		
//		this.down = true;
		
	}
//	public void setX(int x){
//		this.x = x;
//	}
//	public void setY(int y){
//		this.y = y;
//	}
//	public void setBombMap(ArrayList<BombTile> bombMap){
//		this.bombMap = bombMap;
//	}
//	public int getX(){
//		return x;
//	}
//	public int getY(){
//		return y;
//	}
//	public ArrayList<BombTile> getBombMap(){
//		return bombMap;
//	}
	
	
}
