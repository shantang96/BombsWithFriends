package Main;

import java.util.LinkedList;

public class ServerLoop extends Thread
{
	public GameServerListener gsl;

	public ServerLoop(GameServerListener gsl)
	{
		this.gsl = gsl;
		this.start();
	}
	
	public void run()
	{
		
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		
//		int frames = 0;
//		int updates = 0;
		
		long lastTime = System.nanoTime();
//		long performace = System.currentTimeMillis();
		
		while(true)
		{	
			long now = System.nanoTime();
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	if(gsl.getHandler() != null)
	    	{
		    	while(delta >= 1) 
		    	{
		    		gsl.updateHandler(now);
		    		LinkedList<GameObject> link = new LinkedList<GameObject>(gsl.getHandlerObjects());
		    		//System.out.println(link.size());
		    		gsl.sendToAllThreads(link);
		    		delta--;
		    	}
	    	}
		}
	}
	
}
