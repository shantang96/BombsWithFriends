package Main;

import java.io.Serializable;


public abstract class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected float _x;
	protected float _y;
	protected int _width;
	protected int _height;


	public void setX(int x)
	{
		_x=x;
	}
	public void setY(int y)
	{
		_y=y;
	}
	public void setWidth(int w)
	{
		_width = w;
	}
	public void setHeight(int h)
	{
		_height = h;
	}
	public int getX()
	{
		return (int)_x;	
	}
	public int getY()
	{
		return (int)_y;	
	}
	public int getWidth()
	{
		return _width;	
	}
	public int getHeight()
	{
		return _height;	
	}
	public abstract boolean isRemove();
	}
