package Main;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Player.*;
import Tile.*;
public class Handler {

	private volatile LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public int _pixels[]; //= new int[Core._coreWidth*Core._coreHeight];
	public int _width;
	public Level _level;
	public int _height;
	public KeyBoard _key;
	public ArrayList<Player> playerList;
	public int PlayerNo = 0;

	public Handler (int w, int h)

	{
		_width = w;
		_height = h;
		_pixels = new int[_width*_height];
//		_level = level;

	}
	
	public void Render() 
	{
		for(int i = 0; i < objects.size(); ++i)
		{
			if( objects.get(i) instanceof Renderable){
				if(objects.get(i).isRemove() == false)
				{
					((Renderable) objects.get(i)).render(this);
					/*if(objects.get(i) instanceof Player)
					{
						System.out.println("_targetx"+objects.get(i).getX() + " _targety"+objects.get(i).getY() );
					}*/
				} 
				else 
				{
					
					if(objects.get(i) instanceof Player)
						{
//							Player px = (Player)objects.get(i);
//							if(px.getPlayerNo() == PlayerNo)
//							{
								JOptionPane.showMessageDialog(null, "	Rest In Peace. U_U", "Game Over!", JOptionPane.WARNING_MESSAGE);
//							}
						}
					objects.remove(i);
				}
			}
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
	
//	public void createLevel()
//	{
//		
//		
//		objects.addAll(_level.loadLevel());
//	}
	
	
	
}
