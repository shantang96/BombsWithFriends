package ClientSide;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import ClientToMainServerMessages.GenerateGameServerMessage;
import ClientToMainServerMessages.ServerListRequestMessage;
import Descriptors.GameServerDescriptor;
import Main.GameClient;

public class GameFinderGUI extends JPanel{
	private static final long serialVersionUID = -6005389666322029366L;
	private JList<String> list;	
	private Box mMainBox;
	private JPanel southPanel, mListPanel;
	private JButton mJoinButton, mRefreshButton, mCreateButton, mCustomizerButton;
	private String[] JListValues;
	private String username, serverName;
	private ArrayList<GameServerDescriptor> mGameServerDescriptors = null;
	private MainClientGUI mMainClientGUI = null;
	private Vector<String> mServerListVector = null;
	private Vector<Integer> mServerPortVector =null;
	private String spriteSheetName = "./resources/spritesheets/BasicPlayerSheet.png";
	private int spriteNumber = 0;
	public String ip = "localhost";
	
	public GameFinderGUI(MainClientGUI mMainClientGUI, String username) {
		this.mMainClientGUI = mMainClientGUI;
		this.username = username;
		UpdateServerList();
		initialize();
		create();
		addActions();
	}
	
	public void changeSpriteSheet(String spriteSheetName){
		this.spriteSheetName = spriteSheetName;
	}
	
	public void changeCurrentSprite(int spriteNumber){
		this.spriteNumber = spriteNumber;
	}
	
	private void UpdateServerList(){
		ServerListRequestMessage message = new ServerListRequestMessage();
		
		mMainClientGUI.getConnector().transmitToServer(message);
		mGameServerDescriptors = mMainClientGUI.getConnector().getCurrentServerList();
		UpdateServerListHelper();
	}
	private void UpdateServerListHelper(){
		mServerListVector = new Vector<>();
		mServerPortVector = new Vector<>();
		if(mGameServerDescriptors.size()>0){
			for(GameServerDescriptor curr: mGameServerDescriptors){
				String currentListElement = null;
				int currentSize = curr.getCurrentSize();
				int maxCapacity = curr.getMaxCapacity();
				int portNumber = curr.getPortNumber();
				String serverName = curr.getServerName();
				String gameType = curr.getGameType();
				currentListElement = serverName + " GameType: "+gameType+": Port #: " + portNumber + " " + currentSize + "/" + maxCapacity;
				this.serverName = serverName;
				System.out.println(currentListElement);
				mServerListVector.add(currentListElement);
				mServerPortVector.add(portNumber);
			}
		}	
		if(list==null) list = new JList<String>(mServerListVector);
		
		ListModel<String> model = list.getModel();
		DefaultListModel<String> listModel = new DefaultListModel();	
		for(String currServer: mServerListVector){
			listModel.addElement(currServer);
		}
		
		list.setModel(listModel);
		revalidate();
		repaint();
	}
	
	private void initialize(){
		southPanel = new JPanel();
		mJoinButton = new JButton("Join");
		mRefreshButton = new JButton("Refresh");
		mCreateButton = new JButton("Create");
		mCustomizerButton = new JButton("Customizer");
		mListPanel = new JPanel();
		
		mMainBox = Box.createVerticalBox();
	}
	
	private void create(){
		southPanel.setLayout(new GridLayout(2,2));
		southPanel.add(mJoinButton);
		southPanel.add(mRefreshButton);
		southPanel.add(mCustomizerButton);
		southPanel.add(mCreateButton);
		list.setFixedCellWidth(375);
		mListPanel.add(new JScrollPane(list));
		mMainBox.add(mListPanel);
		mMainBox.add(southPanel);
		add(mMainBox);
	}
	private void createHelper(){
		  JTextField serverNameField = new JTextField(15);
	      //JTextField gameTypeField = new JTextField(15);
	      JTextField playerCapacityField = new JTextField(15);
	      JPanel mainPanel = new JPanel();
	      mainPanel.setLayout(new GridLayout(2,2));   
	      mainPanel.add(new JLabel("GameServer Name"));
	      mainPanel.add(serverNameField);
	     // mainPanel.add(new JLabel("GameType:"));
	      //mainPanel.add(gameTypeField);
	      mainPanel.add(new JLabel("Max Players: "));
	      mainPanel.add(playerCapacityField);
	      int result = JOptionPane.showConfirmDialog(null, mainPanel, 
	               "Create Game", JOptionPane.OK_CANCEL_OPTION);
	      if(result==JOptionPane.OK_OPTION){
	    	  serverName = serverNameField.getText();
	    	 // String gameType = gameTypeField.getText();
	    	  int playerCapacity = 4;
	    	  playerCapacity = Integer.parseInt(playerCapacityField.getText());
	    	  if(!serverName.isEmpty()&&playerCapacity>0){
	    			GenerateGameServerMessage message = new GenerateGameServerMessage();
	    			message.setGameServerName(serverName);
	    			message.setPlayerCapacity(playerCapacity);
	    			mMainClientGUI.getConnector().transmitToServer(message);
	    			mGameServerDescriptors = mMainClientGUI.getConnector().getCurrentServerList();
	    			UpdateServerListHelper();
	    			
	    	  }
	      }
	}
	
	private void addActions(){
		mJoinButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex()!=-1){
				int index = list.getSelectedIndex();
				System.out.println("server port " + mServerPortVector.elementAt(index)); 
				//new GameClient("localhost",mServerPortVector.elementAt(index));
				//new GameClient("localhost",mServerPortVector.elementAt(index));
				new Thread( new Runnable() {
				    @Override
				    public void run() {
				    	new GameClient(ip, mServerPortVector.elementAt(index), username, serverName, spriteSheetName);
				    }
				}).start();
				//new GameClient("localhost", 6789);
				//System.out.print(mServerPortVector.elementAt(index));
				}

			}		
		});
		mRefreshButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateServerList();
				revalidate();
				repaint();
				
			}
		});
		mCreateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				createHelper();
			}
		});
		mCustomizerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(mMainBox);
				add(new CustomizerGUI(mMainClientGUI,username, spriteNumber),BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
	}
}	
	
	
