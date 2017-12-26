package MainServerResponses;
import java.util.ArrayList;
import Descriptors.GameServerDescriptor;

public class ServerListRequestResponse extends MainServerResponse{
	private static final long serialVersionUID = -6819752003789003082L;
	private ArrayList<GameServerDescriptor> mServerList;
	public ServerListRequestResponse(){
		
	}
	public void setServerList(ArrayList<GameServerDescriptor> mServerList){
		this.mServerList = mServerList;
	}
	public ArrayList<GameServerDescriptor> getServerList(){
		return mServerList;
	}
}
