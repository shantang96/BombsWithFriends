package ClientToMainServerMessages;

import java.io.Serializable;

public class GenerateGameServerMessage extends MainServerMessage implements Serializable{
	private static final long serialVersionUID = 4362626900268351122L;
	private String mGameServerName = null;
	//private String mGameType = null;
	private int mPlayerCapacity = 1;
	
	public GenerateGameServerMessage(){
		
	}
	public void setGameServerName(String mGameServerName){	
		this.mGameServerName = mGameServerName;
	}
	public void setPlayerCapacity(int mPlayerCapacity){
		this.mPlayerCapacity = mPlayerCapacity;
	}
	public String getGameServerName(){	
		return mGameServerName;
	}
	public int getPlayerCapacity(){
		return mPlayerCapacity;
	}
}
