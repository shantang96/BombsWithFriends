package Main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

import Bomb.*;
import Item.*;
import Player.*;
import Sprite.*;
import Tile.*;

public class Core extends Canvas {
	private static final long serialVersionUID = 1L;
	private final int _coreWidth = 400;
	private final int _coreHeight = 192;//_coreWidth / 16 * 9;
	private final int _scale = 3;
	
	private final String _title = "Title.";
	
//	private JFrame _coreFrame;
//	private Thread _coreThread;
	private boolean _running;
	
	private volatile Handler test;
	private ArrayList<Player> playerList;

	
	public KeyBoard key;
	Core(ArrayList<Player> playerList)
	{
		super();
		this.playerList = playerList;
		init();
		setup();
//		start();
	}

	private void init() 
	{
//		_coreFrame = new JFrame();
//		_coreThread = new Thread(this);
		_running = false;
		key = new KeyBoard();
		addKeyListener(key);
//		KeyBoard[] keys = new KeyBoard[1];
//		keys[0] = key;
		
//		Player[] playerArray = new Player[playerList.size()];
//		for(int i=0; i<playerList.size(); i++)
//		{
////			playerList.get(i).setKeyboard(key);
//			playerArray[i] = playerList.get(i);
//		}
//		Level l = new Level(playerArray,"./resources/level/BasicLevel.png");
		
		test = new Handler(_coreWidth,_coreHeight);
	}
	private void setup()
	{
		setPreferredSize(new Dimension(_coreWidth*_scale, _coreHeight*_scale));
//		_coreFrame.setResizable(false);
//		_coreFrame.setTitle(_title);
//		_coreFrame.setSize(_coreWidth, _coreHeight);
//		_coreFrame.add(this);
//		_coreFrame.pack();
//		_coreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		_coreFrame.setLocationRelativeTo(null);
//		_coreFrame.setVisible(true);
		
//		test.createLevel();
	}
	public void start()
	{
		_running = true;
//		_coreThread.start();
	}

	/*public void run() 
	{
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		
		int frames = 0;
		int updates = 0;
		
		long lastTime = System.nanoTime();
		long performace = System.currentTimeMillis();
		while(_running)
		{
	    	long now = System.nanoTime();
	    	
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	
	    	while(delta >= 1) {
//	    		Update(now);
	    		updates++;
	    		delta--;
	    	}
			Render();
			frames++;
			
	        if(System.currentTimeMillis() - performace >= 1000) {
	        	performace += 1000;
//	        	_coreFrame.setTitle(_title + "  |  " + updates + " ups, " + frames + " fps");
	        	frames = 0;
	        	updates = 0;
	        }
		}
		
	}*/
	
	public void Render() 
	{
		//System.out.println("it reach this");
		BufferedImage img = new BufferedImage(_coreWidth, _coreHeight,BufferedImage.TYPE_INT_RGB);
	    int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	        BufferStrategy bs=this.getBufferStrategy();
	        if(bs==null){
	            createBufferStrategy(2);
	            return;
	        }
	        
	        for (int i = 0; i < pixels.length; ++i){
	            pixels[i] = 0;
	        }
	        
	        test.Render();
	        for (int i = 0; i < pixels.length; ++i){
	            pixels[i] = test._pixels[i];
	        }

	      
	        Graphics g= bs.getDrawGraphics();
	        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	        g.dispose();
	        bs.show();
	}
	
//	public void Update(double deltaTime) 
//	{
//		test.Update(deltaTime);
//	}
	public static void main(String[] args)
	{
		ArrayList<Player> pList = new ArrayList<Player>();
		for(int i=0; i<4; i++)
		{
			Player p = new Player(0,0, i+1);
			pList.add(p);
		}
		new Core(pList);
	}
	
	
	public Handler getHandler()
	{
		return test;
	}
	public ArrayList<Player> getPlayers()
	{
		return playerList;
	}

	public void setHandlerObjects(LinkedList<GameObject> objects)
	{
		test.setObjects(objects);
		Render();
	}
	
}
