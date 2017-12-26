package Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import Bomb.Bomb;
import Bomb.BombTile;
import Bomb.SpeedBomb;
import Bomb.SpikeBomb;
import Player.KeyBoard;
import Player.Player;

public class HandlerUpdate 
{

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public int _pixels[]; //= new int[Core._coreWidth*Core._coreHeight];
	public Level _level;
	public KeyBoard _key;
	public ArrayList<Player> playerList;
	public Boolean dodgeball = false;
	public Boolean capturetheflag = false;
	public int drop = 0;
	public int X,Y;

	public HandlerUpdate (ArrayList<Player> playerList, Level level)
	{
		
		_level = level;
		this.playerList = playerList;
		Random r = new Random();
		int n = r.nextInt(playerList.size());
		playerList.get(n).flagger = true;
		 createLevel();

	}
	
//	public void Render() 
//	{
//		for(int i = 0; i < objects.size(); ++i)
//		{
//			if( objects.get(i) instanceof Renderable){
//				if(objects.get(i).isRemove() == false)
//				{
//					((Renderable) objects.get(i)).render(this);
//				} 
//				else 
//				{
//					objects.remove(i);
//				}
//			}
//		}
//		
//		for(Player  p : playerList)
//		{
//			if(p.isRemove() == false)
//			{
//				p.render(this);
//			}
//		}
//
//	}
	
	public void Update(double deltaTime)
	{
		for(int i = 0; i < objects.size(); ++i)
		{
			if(objects.get(i).isRemove() == true)
			{
				objects.remove(i);
			}
			else if( objects.get(i) instanceof Updateable)
			{
				 ((Updateable) objects.get(i)).update(this, deltaTime);
			}
			
		}
		
		for(Player p : playerList)
		{
			if(p.isRemove() == false)
			{
				p.update(this, deltaTime);
				X = p.getX();
				Y = p.getY();
			} 
		}
		
		drop++;
		if(dodgeball == true && drop >= 50)
		{
			for(int i=0; i<2; i++)
			{
				Random r = new Random();
				int x = r.nextInt(5)*16;
				x+= X;
				int y = r.nextInt(5)*16;
				y+= Y;
				BombTile newBomb = null;
				int _bombtype = r.nextInt(3);
				if(_bombtype == 0)
				{
				 newBomb = new Bomb(x, y, 2);
				} else if(_bombtype == 1)
				{
					 newBomb = new SpikeBomb(x, y, 2);
				} else if(_bombtype == 2)
				{
					 newBomb = new SpeedBomb(x, y, 2);
				}
				addObject(newBomb);
			}
			drop = 0;
		}
		
	}
	
	public void addObject(GameObject obj)
	{
		if(obj == null){System.out.println("fuck");}
		objects.add(obj);
	}
	
	public void removeObject(GameObject obj)
	{
		objects.remove(obj);
	}
	
	public void setObjects(LinkedList<GameObject> objects)
	{
		this.objects = objects;
	}
	
	public LinkedList<GameObject> getObjects()
	{
		return objects;
	}
	
	
	public ArrayList<GameObject> getAtTile(int x, int y)
	{	ArrayList<GameObject> atTile = new ArrayList<GameObject>();
		for(int i = 0; i < objects.size(); ++i){
			if(objects.get(i).getX() == x && objects.get(i).getY() == y)
			{
				atTile.add(objects.get(i));
			}
		}
		return atTile;
		
	}
	
	public void createLevel()
	{	
		objects.addAll(_level.loadLevel());
	}
	
	
}
