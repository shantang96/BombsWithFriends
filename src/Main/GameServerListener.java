package Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import Player.Player;


public class GameServerListener extends Thread 
{
	//Every GameServer has a GameServerListener
	//A GameServerListener is a Game
	private ServerSocket ss;
	private Vector<GameServerThread> serverThreads;
	@SuppressWarnings("unused")
	//private Core c;
	private ArrayList<Player> playerList;
	private volatile HandlerUpdate h;
	private int nPlayers;
	private int threadNo = 0;
	
	public GameServerListener(int port, int nPlayers)
	{
		this.nPlayers = nPlayers;
		try
		{
			ss = new ServerSocket(port);
			serverThreads = new Vector<GameServerThread>();
			playerList = new ArrayList<Player>();
			
		}
		catch (IOException ioe)
		{
			//When port is already in use.
			//TextEditServerGUI.addMessage("Port " + port + " is already in use." );
		}
		
	}
	
	public void removeServerThread(GameServerThread st)
	{
		serverThreads.remove(st);
	}

	public void stopThread()
	{
		try
		{
			ss.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				
				//Acts as the ServerListener
				//The serverThreads are the ServerClientCommunicators
				//ServerThreads are started in their constructor.
				
				//GameServerGUI.addMessage("Server started on port " + ss.getLocalPort() + ".");
				//GameServerGUI.addMessage("Waiting for connection..");
				System.out.println("Server started on port " + ss.getLocalPort() + ".");
				System.out.println("Waiting for connection..");
				
				Socket s = ss.accept(); //Waiting for someone to connect to us. //Blocking line. Stops right here until someone connects to it.
				//GameServerGUI.addMessage("Connection from: " + s.getInetAddress());
				System.out.println("Connection from: " + s.getInetAddress());
				threadNo++;
				GameServerThread gst = new GameServerThread(s, this, threadNo);
				serverThreads.add(gst);
				System.out.println(serverThreads.size());
				if(serverThreads.size() == nPlayers)
				{
//					System.out.println("We now have " + nPlayers + " threads.");
					
					while(!initializeAll())
					{
						
					}
					if(playerList.size() == nPlayers)
					{
//						System.out.println("We now have " + nPlayers + " players.");
						for(GameServerThread st : serverThreads)
						{
							System.out.println("We sending playerlist ");
							st.sendObject(playerList);													
						}
							System.out.println("Initialized all!");
//							c = new Core(playerList);
//							h = c.getHandler();
//							KeyBoard key = new KeyBoard();
							Player[] playerArray = new Player[playerList.size()];
							for(int i=0; i<playerList.size(); i++)
							{
								//playerList.get(i).setKeyboard(key);
								playerArray[i] = playerList.get(i);
							}
							Level l = new Level(playerArray,"./resources/level/BasicLevel.png");
							h = new HandlerUpdate(playerList, l);
							System.out.println("HEEEEEEEYYYY");
							new ServerLoop(this);
//							break;
					}
					
					
				}
			}
			
		}
		catch (IOException ioe)
		{
			//When port is already in use.
			System.out.println("Port " + ss.getLocalPort() + "is already in use.");
			//GameServerGUI.addMessage("Port " + ss.getLocalPort() + "is already in use." );
		}
		finally
		{
			if(ss !=null)
			{
				try
				{
					ss.close();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
					//GameServerGUI.addMessage(ioe.getMessage());
				}
			}
		}
	}
	
	public Boolean initializeAll()
	{
//		System.out.println(playerList.size());
		playerList.clear();
		for(GameServerThread gst : serverThreads)
		{
			if(gst.getPlayer() != null && playerList.size() <= nPlayers)
			{
				System.out.println("Added player to playerList : " + gst.getPlayer().getPlayerNo());
				playerList.add(gst.getPlayer());
				System.out.println("Player " + gst.getPlayer().getPlayerNo());
			}
		}
		if(playerList.size() == nPlayers)
		{
			return true;
		}
		else
		{
			return false;
		}
//		System.out.println("PLAYERLIST.SIZE = " + playerList.size());
//		if(playerList.size() == nPlayers)
//		{
//			System.out.println("We now have " + nPlayers + " players.");
//			for(GameServerThread gst : serverThreads)
//			{
//				gst.sendObject(playerList);
//
//				//The client can get the handler from the Core.
//				//gst.sendObject(h); //Handler has the playerList.
//				//gst.sendObject(playerList);
//			}
//			return true;
//		}
//		return false;
	}
	
	public int getThreadCount()
	{
		return serverThreads.size();
	}
	
	public void setObjects(LinkedList<GameObject> objects)
	{
		h.setObjects(objects);
	}
	
	public HandlerUpdate getHandler()
	{
		return h;
	}
	
	public void updateHandler(double deltaTime)
	{
		h.Update(deltaTime);
	}
	
	public void setHandlerObjects(LinkedList<GameObject> objs)
	{
		h.setObjects(objs);
	}
	
	public void setPlayerMoves(Location l, int pNo)
	{
		if(h != null)
		{
			for(GameObject go : h.objects)
				{
					if(go instanceof Player)
					{
						Player P = (Player)go;
						if(P.getPlayerNo() == pNo)
						{
							P.setMoves(l);
						}
						go = (GameObject)P;
					}
		//			if(p.getPlayerNo() == pNo)
		//			{
		//				System.out.println("GOT IN HERE");
		//				p.setMoves(l);
		//			}
				}
		}
	}
	
	public void sendToAllThreads(Object o)
	{
	//	System.out.println("sending to all threads");
		for(GameServerThread gst : serverThreads)
		{
			gst.sendObject(o);
		}
	}
	
	public LinkedList<GameObject> getHandlerObjects()
	{
		return h.getObjects();
	}

	public void sendToAllOtherThreads(Object o, int threadNo) {
		for(int i = 0; i < serverThreads.size(); ++i)
		{
			System.out.println("threadNo-1: " + (threadNo-1));
			if(i != threadNo-1)
			{
				serverThreads.get(i).sendObject(o);
			}		
		}
	}
	
	//Clients will take the Handler and use it.
}
