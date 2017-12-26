package ClientToMainServerMessages;
import java.io.Serializable;

public class SignUpMessage extends MainServerMessage implements Serializable{
	private static final long serialVersionUID = 9171750186401056985L;
	public String mUsername;
	public byte[] mEncryptedPassword;
	public SignUpMessage(){ }
	public void setUsername(String mUsername){
		this.mUsername = mUsername;
	}
	public String getUserame() {
		return mUsername;
	}
	public void setEncryptedPassword(byte[] mEncryptedPassword){
		this.mEncryptedPassword = mEncryptedPassword;
	}
	public byte[] getEncryptedPassword() {
		return mEncryptedPassword;
	}
}
