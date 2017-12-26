package Main;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mUsername, mMessage;
	
	public Message() {

	}
	public void setUsername(String mUsername){
		this.mUsername = mUsername;
	}
	public void setMessage(String mMessage){
		this.mMessage = mMessage;
	}
	public String getUsername() {
		return mUsername;
	}
	
	public String getMessage() {
		return mMessage;
	}

}
