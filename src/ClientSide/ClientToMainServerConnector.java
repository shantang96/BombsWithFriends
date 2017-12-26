package ClientSide;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ClientToMainServerMessages.MainServerMessage;
import Descriptors.GameServerDescriptor;
import MainServerResponses.MainServerResponse;
import MainServerResponses.ServerListRequestResponse;

public class ClientToMainServerConnector {
	private int userToken = -1;
	private ArrayList<GameServerDescriptor> currentServerList = null;
	public String ip = "localhost";
	public ClientToMainServerConnector(){
		
	}
	public int getUserToken(){
		return userToken;
	}
	public ArrayList<GameServerDescriptor> getCurrentServerList(){
		return currentServerList;
	}
	public int transmitToServer(MainServerMessage message){
		Socket s = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		boolean success = false;
		int result = -1;
		try{
			s = new Socket(ip, 7000);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			oos.writeObject(message);
			oos.flush();
			MainServerMessage mainServerMessage = (MainServerMessage) ois.readObject();
			System.out.println("Received Message " + mainServerMessage);
			if(mainServerMessage instanceof MainServerResponse){
				@SuppressWarnings("unused")
				String text = ((MainServerResponse)mainServerMessage).getText();
				success = ((MainServerResponse)mainServerMessage).getSuccess();
				//System.out.println("Text: "+ text + " Success: "+ success);	
				if(success) {
					result = 1;
					userToken = ((MainServerResponse)mainServerMessage).getUserToken();
				}
				else result = 2;
			}
			if(mainServerMessage instanceof ServerListRequestResponse){
				try{
					result = 1;
					currentServerList = ((ServerListRequestResponse) mainServerMessage).getServerList();
				}catch(Exception e){ }
			}
		} catch(Exception e){
			e.printStackTrace();
			result = 0;
		}
		return result;
		//KEY
		//0 : no server online
		//1 : successful login/register
		//2 : Register up password in use || Login Incorrect data
	}
}
