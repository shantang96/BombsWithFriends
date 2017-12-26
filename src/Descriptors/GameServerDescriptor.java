package Descriptors;

import java.io.Serializable;

public class GameServerDescriptor implements Serializable{
	private static final long serialVersionUID = 7740151588156402220L;
	private String mServerName, mGameType;
	private int mPortNumber, mCurrentSize, mMaxCapacity;

	public GameServerDescriptor(){
		
	}
	public void setServerName(String mServerName){
		this.mServerName = mServerName;
	}
	public void setPortNumber(int mPortNumber){
		this.mPortNumber = mPortNumber;
	}
	public void setCurrentSize(int mCurrentSize){
		this.mCurrentSize = mCurrentSize;
	}
	public void setMaxCapacity(int mMaxCapacity){
		this.mMaxCapacity = mMaxCapacity;
	}
	public void setGameType(String mGameType){
		this.mGameType = mGameType;
	}
	public String getServerName(){
		return mServerName;
	}
	public int getPortNumber(){
		return mPortNumber;
	}
	public int getCurrentSize(){
		return mCurrentSize;
	}
	public int getMaxCapacity(){
		return mMaxCapacity;
	}
	public String getGameType(){
		return mGameType;
	}
	
}
