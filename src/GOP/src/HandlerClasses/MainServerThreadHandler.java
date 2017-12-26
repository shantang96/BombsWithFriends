package HandlerClasses;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ClientToMainServerMessages.GenerateGameServerMessage;
import ClientToMainServerMessages.LoginMessage;
import ClientToMainServerMessages.MainServerMessage;
import ClientToMainServerMessages.ServerListRequestMessage;
import ClientToMainServerMessages.SignUpMessage;
import Descriptors.GameServerDescriptor;
import MainServerResponses.MainServerResponse;
import MainServerResponses.ServerListRequestResponse;
import ServerSide.MServer;
import ServerSide.MainServerGUI;

public class MainServerThreadHandler extends Thread {
	
	private volatile boolean moribund;
	private Socket clientSocket;
	private MServer mainServer;
	private String username;
	MainServerGUI serverScrean;
	public MainServerThreadHandler(Socket clientSocket,MServer fileServer,MainServerGUI serverScrean){
		this.clientSocket = clientSocket;
		this.mainServer = fileServer;
		this.serverScrean = serverScrean;
	}
	public void shutDown(){
		moribund = true;
		this.interrupt();
	}
	private void generateSignUpResponse(MainServerMessage newMessage, ObjectOutputStream objectOut){
		SignUpMessage signUpMessage = (SignUpMessage) newMessage;
		username = signUpMessage.getUserame();
		byte[] encryptedPassword = signUpMessage.getEncryptedPassword();
		String encrypt = new String(encryptedPassword);
		System.out.println("EncryptedPassword is "+ new String(encryptedPassword));
		MainServerResponse response = new MainServerResponse();
		response.setSuccess(true);
		response.setText("Sign-up attempt User: "+ username + " Password: " + encrypt);
		serverScrean.writeToScrean("Sign-up attempt User: "+ username + " Password: " + encrypt);;
		if(mainServer.processRegistration(username, encrypt)){
			serverScrean.writeToScrean("Sign-up Success User: "+ username);
			int UserID = mainServer.getUserID(username);
			int UserToken = mainServer.getUserToken(UserID);
			response.setUserToken(UserToken);
			response.setSuccess(true);
		}else{
			serverScrean.writeToScrean("Sign-up Failure User: "+ username);
			response.setSuccess(false);
		}
		try {
			objectOut.writeObject(response);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void generateLoginResponse(MainServerMessage newMessage,ObjectOutputStream objectOut){
		LoginMessage signUpMessage = (LoginMessage) newMessage;
		username = signUpMessage.getUserame();
		byte[] encryptedPassword = signUpMessage.getEncryptedPassword();
		String encrypt = new String(encryptedPassword);
		System.out.println("EncryptedPassword is "+ new String(encryptedPassword));
		MainServerResponse response = new MainServerResponse();
		response.setSuccess(true);
		response.setText("Sign-up attempt User: "+ username + " Password: " + encrypt);
		serverScrean.writeToScrean("Sign-up attempt User: "+ username + " Password: " + encrypt);;
		if(mainServer.processSignInRequest(username, encrypt)){
			serverScrean.writeToScrean("Sign-up Success User: "+ username);
			int UserID = mainServer.getUserID(username);
			int UserToken = mainServer.getUserToken(UserID);
			response.setUserToken(UserToken);
			response.setSuccess(true);
		}else{
			serverScrean.writeToScrean("Sign-up Failure User: "+ username);
			response.setSuccess(false);
		}
		try {
			objectOut.writeObject(response);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void generateServerListResponse(ObjectOutputStream objectOut){
		ArrayList<GameServerDescriptor> currentGameServerDescriptors = mainServer.returnConvertedGameServerData();
		ServerListRequestResponse response = new ServerListRequestResponse();
		response.setServerList(currentGameServerDescriptors);
		response.setSuccess(true);
		int UserID = mainServer.getUserID(username);
		int UserToken = mainServer.getUserToken(UserID);
		response.setUserToken(UserToken);
		try {
			objectOut.writeObject(response);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void generateGameServerGenerationResponse(MainServerMessage newMessage, ObjectOutputStream objectOut){
		GenerateGameServerMessage message = (GenerateGameServerMessage) newMessage;
		System.out.println("DEBUG MSTH: SERVERNAME: " + message.getGameServerName() + " PLAYERCAPACITY: "+ message.getPlayerCapacity() );
		mainServer.generateNewGameServer(message.getGameServerName(), message.getPlayerCapacity());
		ArrayList<GameServerDescriptor> currentGameServerDescriptors = mainServer.returnConvertedGameServerData();
		ServerListRequestResponse response = new ServerListRequestResponse();
		response.setServerList(currentGameServerDescriptors);
		
		response.setSuccess(true);
		int UserID = mainServer.getUserID(username);
		int UserToken = mainServer.getUserToken(UserID);
		response.setUserToken(UserToken);
		try {
			objectOut.writeObject(response);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unused")
	public void run(){
		ObjectInputStream objectIn = null;
		ObjectOutputStream objectOut = null;
		try {
			objectIn = new ObjectInputStream(clientSocket.getInputStream());
			objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
			while(!moribund){
				//get the message
				MainServerMessage newMessage = null;
				try {
					newMessage = (MainServerMessage) objectIn.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) { }
				if(newMessage!=null){
					System.out.println("You have received a message!!" + newMessage);
					if(newMessage instanceof SignUpMessage){
						generateSignUpResponse(newMessage,objectOut);
					}
					else if(newMessage instanceof LoginMessage){
						generateLoginResponse(newMessage,objectOut);
					}
					else if(newMessage instanceof ServerListRequestMessage){
						generateServerListResponse(objectOut);
					}
					else if(newMessage instanceof GenerateGameServerMessage){
						generateGameServerGenerationResponse(newMessage,objectOut);
					}
				}
				/*
				try {
					sleep(1000000); //5000
				} catch (InterruptedException e) {}	*/
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Can't go on. Shutting... Down... Aggggh");
			try {
				if(objectIn!=null) objectIn.close();
				if(objectOut!=null) objectOut.close();
				clientSocket.close();
				
			} catch (IOException e) {
			}
			return;
		}
		
		System.out.println("Worker thread shutting down...");
	}
}
