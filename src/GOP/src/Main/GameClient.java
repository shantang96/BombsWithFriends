package Main;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Player.Player;

//Client sends a Location object that has a Map of Bombs and a PlayerLocation which the server adds to the handler
//So the handler would need a setPlayerLocation(int x, int y) and a setBombDropped(BombType b, int x, int y);

//The server sends back the updated Handler and the clients update their handlers to look like the server's handler with updates from all
//the clients.

//Changed GameServer and GameServerListener and GameClient


public class GameClient extends Thread
{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Player p;
	private Handler h;
	private int playerNo = 0;
	public Location l;
	private GameBoardGUI gbgui = null;
	public volatile Boolean playerListRecieved = false;
	private String username, servername;
	private String spriteSheetName;
	
	@SuppressWarnings("unused")
	private Core c;
	private ArrayList<Player> playerList = null;
	private LinkedList<GameObject> hObjects = null;
	public GameClient(String hostname, int port, String username, String servername, String spriteSheetName)
	{
		this.username = username;
		this.spriteSheetName = spriteSheetName;
		Socket s = null;
		try
		{
			s = new Socket(hostname, port);
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			recieveNo();
			createPlayer();
			sendPlayer();
//			System.out.println("SENT PLAYER TO SERVER!");
			this.start();
	
//			System.out.println("GOT HERE BEFORE WHILE WAITING FOR PLAYERLIST");
			while(playerListRecieved == false)
			{
				//Wait till it's not null.
			}
			//c = new Core(playerList);
			System.out.println("playerList size: " + playerList.size());
			gbgui = new GameBoardGUI(playerList, username, servername, this);
			c = gbgui.core;
			h = gbgui.core.getHandler();
			gbgui.core.getHandler().PlayerNo = playerNo;
			gbgui.revalidate();
			gbgui.repaint();
			//System.out.println("This is odd");
			//Send in the playerList to the core so it can have all the players on your 
			//core.
			
			new GameUpdateLoop(this);
			
			while(true)
			{
				try
				{
					Thread.sleep(10);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
//			while(true)
//			{
//				updateLocation();
//				//SEND OBJECT AND UPDATES.
//				oos.writeObject(l);
//				oos.flush();
//				Thread.sleep(1000/60);
////				System.out.println("Sent a location object!");
////				oos.writeObject(p);
////				oos.flush();
//			}
			
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		finally
		{
			try
			{
				if(s!=null)
				{
					s.close();
				}
			}
			catch(IOException ioe)
			{
				System.out.println("ioe : " + ioe.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void run()
	{
		try
		{
			while(true)
			{
				Object obj = ois.readObject();
				
				if (obj instanceof Message) {
					//if(!username.equals(((Message) obj).getUsername())) 
					//{
						try
						{
							gbgui.appendString('\n' + ((Message) obj).getUsername() + ": " + ((Message) obj).getMessage());
						}
						catch(BadLocationException ble)
						{
							ble.printStackTrace();
						}
//							JTextPane jtp = gbgui.getChatLog();
//						try {
//							System.out.println("client message is being appended");
//							appendString('\n' + ((Message) obj).getUsername() + ": " + ((Message) obj).getMessage(), jtp);
//						} catch (BadLocationException e) {
//							e.printStackTrace();
//						}
					//}
					
				}
				
				if(obj instanceof ArrayList<?>)
				{
					System.out.println("GOT Player List");
					this.playerList = (ArrayList<Player>)obj;
					//update array list if it's not null.
					for(Player p : playerList)
					{
						if(p.getPlayerNo() == playerNo)
						{
							this.p = p;
						}
					}
					playerListRecieved = true;
					System.out.println("RECIEVED PLAYER LIST FROM SERVER");
					
				}
				
				if(obj instanceof LinkedList<?> && gbgui !=null)
				{
					hObjects = (LinkedList<GameObject>)obj;
					if(hObjects != null)
					{
//						System.out.println("WASNT NULL!!");
//						c.setHandlerObjects(hObjects); //Renders too.
						gbgui.setCoreHandlerObjects(hObjects); //Renders too.
						gbgui.core.Render();
//						c.Render();
					}
//					h.setObjects(hObjects);
//					h.Render();
				}
				//Update the Core with the handler.
//				if (obj instanceof Tile) {
//					//System.out.println("here");
//				}
				else {
					
				}

			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
	}
	
	public void createPlayer()
	{
		//CustomizerGUI, create your player and send it in.
		p = new Player(16,16, playerNo, spriteSheetName);
	}
	
	public void sendPlayer()
	{
		try
		{
			oos.writeObject(p);
			oos.flush();
			oos.reset();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public void sendMessage(Message m) 
	{
		try {
			oos.writeObject(m);
			oos.flush();
			oos.reset();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void updateLocation()
	{	
		l = new Location(c.key);
	}
	
	public void recieveNo()
	{
//		while(playerNo == 0)
//		{
//			try
//			{
//				playerNo = (int)ois.readObject();
//			}
//			catch(IOException ioe)
//			{
//				ioe.printStackTrace();
//			}
//			catch(ClassNotFoundException cnfe)
//			{
//				cnfe.printStackTrace();
//			}
//		}
	}
	
	/*public static void main(String[] args)
	{
//		new GameClient("localhost", 7011);
		new GameClient("localhost", 6789);
	}*/

	public void sendObject(Object obj)
	{
		try
		{
			oos.writeObject(obj);
			oos.flush();
			oos.reset();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
	
	public void appendString(String str, JTextPane jtp) throws BadLocationException {
	     StyledDocument document = (StyledDocument) jtp.getDocument();
	     Style style = jtp.addStyle("I'm a Style", null);
	     StyleConstants.setForeground(style, Color.black);
	     document.insertString(document.getLength(), str, style);
	 }
	
	
}
