package Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Player.Player;

public class GameServerThread extends Thread
{ 
	//Each client gets a GameServerThread.
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public GameServerListener gsl;
	private Lock lock;
	private Player p = null;
	private int threadNo;
	public GameServerThread(Socket s, GameServerListener gsl, int no)
	{
		this.threadNo = no;
		lock = new ReentrantLock();
		try
		{
			this.gsl = gsl;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	

	public void run()
	{

		
		while(true)
		{
			try
			{
			
				Object obj = ois.readObject();
//				System.out.println("Read an object from Client!");
				if(obj != null)
				{	
					if(obj instanceof Player)
					{
//						System.out.println("Recieved a player from client :" + threadNo );
						this.p = (Player)obj;
						p.setPlayerNo(threadNo);
						//Client's gonna send a Player to the ServerThread.
					}
					
					if(obj instanceof Location)
					{
						Location l = (Location)obj;
						//System.out.println("up" + l.up + " down" + l.down + " left" + l.left + " right" + l.right );
						gsl.setPlayerMoves(l, threadNo);
				    	
					}
					
					if(obj instanceof Message) {
						Message msg = (Message) obj;
						gsl.sendToAllOtherThreads(msg, threadNo);
					}
										
				}
				//System.out.println("Update");
				
			}
			catch(IOException ioe)
			{
				//Client has now disconnected.
//				System.out.println("Client disconnected. " + ioe.getMessage());
//				System.out.println("Caught EOF exception! " + ioe.getMessage());
			//	ioe.printStackTrace();
			}
			catch(ClassNotFoundException cnfe)
			{
				cnfe.printStackTrace();
			}
			finally
			{
//				gsl.removeServerThread(this);
			}
			
			
			
//			long now = System.nanoTime();
//	    	delta += (now - lastTime) / ns;
//	    	lastTime = now;
//	    	if(gsl.getHandler() != null)
//	    	{
//		    	while(delta >= 1) 
//		    	{
//		    		Update(now);
//		    		LinkedList<GameObject> link = new LinkedList<GameObject>(gsl.getHandlerObjects());
//		    		gsl.sendToAllThreads(link);
//		    		delta--;
//		    	}
//	    	}
	    	
			
		}
	}
	
	public void sendObject(Object obj)
	{
		lock();
		try
		{
			if(obj != null)
			{
				//Send the object.
				oos.writeObject(obj);
				oos.flush();
				oos.reset();
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		unlock();
	}
	
	public Player getPlayer()
	{
		if(p!= null)
		{
			return p;
		}
		else
		{
			return null;
		}
	}
	
	public void Update(double deltaTime) 
	{
		gsl.updateHandler(deltaTime);
	}
	
	public void sendToAllThreads(Object o)
	{
		gsl.sendToAllThreads(o);
	}
	public void lock()
	{
		lock.lock();
	}
	public void unlock()
	{
		lock.unlock();
	}
	
}
