package ServerSide;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import Descriptors.GameServerDescriptor;
import HandlerClasses.MainServerThreadHandler;
import Main.GameServer;
import SQLDriver.SQLDriver;

public class MServer implements Runnable{
	private final int portNumber;
	private volatile boolean moribund;
	private ServerSocket ss = null;
	private MainServerGUI mServerScrean;
	public SQLDriver driver;
	public Vector<MainServerThreadHandler> fileServerThreads;
	private static int baseSessionID = 1234;
	private static int baseGameServerPortNumber = 6789;
	private HashMap<Integer, Integer> sessionIDToUserID = new HashMap<>();
	private ArrayList<GameServer> currentGameServers = new ArrayList<>();

	public MServer(int portNumber, MainServerGUI mServerScrean){
		new Thread(this).start();
		//testingFunction();
		this.mServerScrean = mServerScrean;
		this.portNumber = portNumber;
		mServerScrean.startToStop();
	}
	
	public ArrayList<GameServerDescriptor> returnConvertedGameServerData(){
		ArrayList<GameServerDescriptor> currentGameServerDescriptors = new ArrayList<>();
		if(currentGameServers!=null&&currentGameServers.size()>0){		
			for(GameServer GS :currentGameServers){
				GameServerDescriptor currentServer = new GameServerDescriptor();
				currentServer.setCurrentSize(GS.getCurrentNumberOfPlayers());
				currentServer.setMaxCapacity(GS.getPlayerCapacity());
				currentServer.setPortNumber(GS.getPortNumber());
				currentServer.setGameType("Free for All");
				currentServer.setServerName(GS.getGameServerName());
				currentGameServerDescriptors.add(currentServer);
			}
		}
		return currentGameServerDescriptors;
	}

	private void init() throws IOException {
		ss = new ServerSocket(portNumber);
	}
	
	public void shutDown() {
		mServerScrean.writeToScrean("Shutting Down Server");
		moribund = true;
		mServerScrean.stopToStart();
		try{
			if(!ss.isClosed()){
				ss.close();
			}
		} catch(IOException ioe){
			System.out.println(ioe);
		} 
	}
	
	public void generateNewGameServer(String gameServerName, int playerCapacity){
		try{
			int generatedPort = generateNewPort();
//			System.out.println(generatedPort + " " + playerCapacity);
			GameServer newServer = new GameServer(gameServerName, generatedPort, playerCapacity);
			//GameServer newServer = new GameServer("test", 6789, 4);
			currentGameServers.add(newServer);
			mServerScrean.writeToScrean("GameServer: "+ gameServerName+ " generated using Port: " + generatedPort);
		}catch(Exception e){
			System.out.println("Something is fucked on Shanu's end");
		}
	}
	public boolean processRegistration(String Username, String Password){
		if(!driver.doesExist(Username)){
			driver.registerUser(Username, Password);
			//System.out.println("Registering User");
			return true;
		}
		return false;
	}
	
	public boolean processSignInRequest(String Username, String Password){
		if(driver.confirmLoginAttempt(Username, Password)){
			return true;
		}
		return false;
	}
	
	public int getUserID(String Username){
		return driver.getUserID(Username);
	}
	
	public int getUserToken(int userID){
		int userToken = baseSessionID++;
		sessionIDToUserID.put(userToken, userID);
		return userToken;
	}
	
	public int generateNewPort(){
		return baseGameServerPortNumber++;
	}

	public int lookupUserID(int userToken){
		return sessionIDToUserID.get(userToken);
	}
	public void run(){
		try{
			init();
		}catch(IOException ioe){
			System.out.println(ioe);
			return;
		}
		moribund = false;
		mServerScrean.writeToScrean("Server started on Port: "+ portNumber);
		driver = new SQLDriver(); //make the driver
		driver.connect(); //connect to the DB
		fileServerThreads= new Vector<>();
		while (!moribund){
			Socket clientSocket = null;
			try {
				clientSocket = ss.accept();
				MainServerThreadHandler worker = new MainServerThreadHandler(clientSocket, this, mServerScrean);
				fileServerThreads.add(worker);
				worker.start();			
			}catch(SocketException se){
				System.out.println("SocketException: "+ se);
			} catch (IOException e) {
				System.out.println("Exception during accept: "+ e);
			}
		}
		//This is the end of the world
		if(fileServerThreads.size()>0){
			for(MainServerThreadHandler worker: fileServerThreads){
				worker.shutDown();
			}
		}
		try {
			if(!ss.isClosed()){
				ss.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("FileServer done shutting down...");
	}
}
