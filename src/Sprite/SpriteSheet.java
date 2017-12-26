package Sprite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Bomb.*;
import Item.*;
import Main.*;
import Player.*;
import Tile.*;

public class SpriteSheet {
	private String _path;
	public int HEIGHT;
	public int WIDTH;
	public final int SPRITESIZE;
	public int[] _pixels;
	
	SpriteSheet(String path,int spriteSize)
	{
		_path = path;
		SPRITESIZE = spriteSize;
		try {
			BufferedImage image = ImageIO.read(new File(_path));
			HEIGHT = image.getHeight();
			WIDTH = image.getWidth();
			_pixels = new int[HEIGHT*WIDTH];
			image.getRGB(0, 0, WIDTH, HEIGHT, _pixels, 0, WIDTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[] load(int xoffset, int yoffset)
	{
		
		int[] loader = new int[SPRITESIZE*SPRITESIZE];
		
		for(int y = 0; y < SPRITESIZE; ++y)
		{
			for(int x = 0; x < SPRITESIZE; ++x)
			{
				loader[x+y*SPRITESIZE] = _pixels[(x + xoffset*SPRITESIZE) + (y + yoffset*SPRITESIZE)*WIDTH];
			}
		}
		return loader;
	}
}
