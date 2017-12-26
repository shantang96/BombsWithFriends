package Main;


public class GameUpdateLoop extends Thread
{
	GameClient gc;
	
	public GameUpdateLoop(GameClient gc)
	{
		this.gc = gc;
		this.start();
	}
	
	public void run()
	{
		while(true)
		{
			gc.updateLocation();
			gc.sendObject(gc.l);
			try 
			{
				Thread.sleep(1000/20);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
	}

	
	
}
