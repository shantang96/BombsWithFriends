package Main;

//Each gameServer is a game
public class GameServer {

	private GameServerListener sl;
	private String mGameServerName;
	private int mPortNumber;
	private int mPlayerCapacity;
	private int mCurrentNumberOfPlayers;
	
	public GameServer(String mGameServerName, int port, int mPlayerCapacity)
	{
		this.mGameServerName = mGameServerName;
		this.mPlayerCapacity = mPlayerCapacity;
		this.mPortNumber = port;
		sl = new GameServerListener(port, mPlayerCapacity);
		sl.start();
	}
	//Setters
	public void setGameServerName(String mGameServerName){
		this.mGameServerName = mGameServerName;
	}
	public void setPortNumber(int mPortNumber){
		this.mPortNumber = mPortNumber;
	}
	public void setPlayerCapacity(int mPlayerCapacity){
		this.mPlayerCapacity = mPlayerCapacity;
	}
	public void setCurrentNumberOfPlayers(int mCurrentNumberOfPlayers){
		this.mCurrentNumberOfPlayers = mCurrentNumberOfPlayers;
	}
	//Getters
	public String getGameServerName(){
		return mGameServerName;
	}
	public int getPortNumber(){
		return mPortNumber;
	}
	public int getPlayerCapacity(){
		return mPlayerCapacity;
	}
	public int getCurrentNumberOfPlayers(){
		return mCurrentNumberOfPlayers;
	}
	
	public void stop()
	{
		sl.stopThread();
//		TextEditServerGUI.addMessage("Server stopped.");
	}
	
	public int playerCount()
	{
		return sl.getThreadCount();
	}
	
	/*public static void main(String[] args)
	{
		new GameServer("test", 6789, 4);
	}*/
	
}
