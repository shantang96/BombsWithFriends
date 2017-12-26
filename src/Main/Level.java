package Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Bomb.*;
import Item.*;
import Player.*;
import Sprite.*;
import Tile.*;

public class Level {
	Player[] _players;
	int _tilespixels[];
	int _spawnlocationsX[] = {16};
	int _spawnlocationsY[] = {16};
	int _width;
	int _height;
	String _path;
	
	public Level(Player[] players, String path) {
		_path = path;
		_players = players;
		for(int i = 0; i < _players.length; ++i)
		{
			_players[i].setX(16);
			_players[i].setY(16);
		}
		try {
			BufferedImage image = ImageIO.read(new File(_path));
			_width = image.getWidth();
			_height = image.getHeight();
			_tilespixels = new int[_width * _height];
			image.getRGB(0, 0, _width, _height, _tilespixels, 0, _width);
		} catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
	}
	
	 public ArrayList<GameObject> loadLevel() {
		 ArrayList<GameObject> levelTiles = new ArrayList<GameObject>();
		 
		/*for(int y = 0; y < _height; ++y){
				for(int x = 0; x < _width; ++x){
					levelTiles.add(new GrassTile(x*16, y*16));
				}
			}*/
		 for(int y = 0; y < _height; ++y)
		 {
			for(int x = 0; x < _width; ++x)
			{ 
				switch(_tilespixels[x+y*_width])
				{
				case 0xFF00FF00:	levelTiles.add(new GrassTile(x*16, y*16));
						break;
				case 0xFFFF0000:	levelTiles.add(new BrickTile(x*16, y*16));
						break;
				case 0xFF804000:	levelTiles.add(new GrassTile(x*16, y*16));
									levelTiles.add(new BoxTile(x*16, y*16));
						break;
				}	
			}
		 }
		 for(int i = 0; i < _players.length; ++i)
		 {
			 levelTiles.add(_players[i]);
		 }
		return levelTiles;
	} 

}
