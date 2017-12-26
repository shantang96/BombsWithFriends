package Sprite;
import java.io.Serializable;


public class Sprite implements Serializable {
	
	private static final long serialVersionUID = 1;
	private int _x, _y;
	private SpriteSheet _sheet;
	public final int _SIZE;
	public final int[] _pixels;
	public Sprite(int x, int y, SpriteSheet spriteSheet)
	{
		_x = x;
		_y = y;
		_SIZE = spriteSheet.SPRITESIZE;
		_pixels = spriteSheet.load(x, y);
	}
}
