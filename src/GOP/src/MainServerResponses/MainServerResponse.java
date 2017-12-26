package MainServerResponses;

import ClientToMainServerMessages.MainServerMessage;

public class MainServerResponse extends MainServerMessage {

	private static final long serialVersionUID = -1900492265884487632L;
	private boolean success; 
	private String text;
	private int userToken;
	
	public MainServerResponse(){
		success = false;
		text = null;
		userToken = -1;
		
	}
	
	public void setUserToken(int userToken){
		this.userToken = userToken;
	}
	public int getUserToken(){
		return userToken;
	}
	public void setSuccess(boolean success){
		this.success = success;
	}
	public boolean getSuccess(){
		return success;
	}
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return text;
	}
}