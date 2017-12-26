package ClientSide;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ClientToMainServerMessages.LoginMessage;


public class LoginGUI extends JPanel{
	private static final long serialVersionUID = 2336926634430689144L;
	private JLabel mUsernameLabel,mPasswordLabel;
	private JTextField mUsernameField;
	private JPasswordField mPasswordField;
	private JButton mLoginButton,mBackButton;
	private JPanel mTopGrid, mBottomGrid;
	private MainClientGUI mMainClientWindow;
	
	public LoginGUI(MainClientGUI mMainClientWindow){
		this.mMainClientWindow = mMainClientWindow;
		instantiateComponents();
		createGUI();
		addActions();
	}
	
	private void instantiateComponents(){
		mUsernameLabel = new JLabel(" Username: ");
		mUsernameLabel.setHorizontalAlignment(WIDTH/2);
		mPasswordLabel = new JLabel(" Password: ");
		mPasswordLabel.setHorizontalAlignment(WIDTH/2);
		mUsernameField = new JTextField();
		mPasswordField = new JPasswordField();
		mTopGrid = new JPanel();
		mBottomGrid = new JPanel();
		mLoginButton = new JButton("Login");
		mBackButton = new JButton("Back");
		setLayouts();
		gridSetup();
	}
	private void gridSetup(){
		Box UsernameBox = Box.createHorizontalBox();
		Box PasswordBox = Box.createHorizontalBox();

		UsernameBox.add(mUsernameLabel);
		UsernameBox.add(mUsernameField);
		PasswordBox.add(mPasswordLabel);
		PasswordBox.add(mPasswordField);
		
		mTopGrid.add(UsernameBox);
		mTopGrid.add(PasswordBox);
		
		mBottomGrid.add(mLoginButton);
		mBottomGrid.add(mBackButton);
	}
	private void setLayouts(){
		mTopGrid.setLayout(new GridLayout(2,1));
		mBottomGrid.setLayout(new GridLayout(1,2));
		setLayout(new BorderLayout());
	}
	private void createGUI(){
		add(mTopGrid,BorderLayout.CENTER);
		add(mBottomGrid,BorderLayout.SOUTH);
	}
	private void loginRequest(){
		String username = mUsernameField.getText();
		@SuppressWarnings("deprecation")
		String password = mPasswordField.getText();
		byte[] encryptedPassword = mMainClientWindow.encrypt(password);
		LoginMessage loginMessage = new LoginMessage();
		loginMessage.setUsername(username);
		loginMessage.setEncryptedPassword(encryptedPassword);
		//KEY
		//0 : no server online
		//1 : successful login
		//2 : incorrect username or password
		int LoginResult = mMainClientWindow.getConnector().transmitToServer(loginMessage);
		if(LoginResult==1){
			//System.out.println("YEAH SUCCESS");
			mMainClientWindow.clearBottomQuadrant();
			mMainClientWindow.addToCenter(new GameFinderGUI(mMainClientWindow, username));
		}else if(LoginResult==2){
			username = null;
			//userToken = 
			JOptionPane.showMessageDialog(null, "Username or Password is invalid","Login Failed", JOptionPane.ERROR_MESSAGE);
		}else{
			username = null;
			JOptionPane.showMessageDialog(null, "Server Can't be reached \n Offline mode initiated","Login Failed", JOptionPane.WARNING_MESSAGE);
		}
	}
	private void addActions(){
		mBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mMainClientWindow.backButton();
			}
		});
		mLoginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(checkFieldFormat()){
					loginRequest();
				}
			}
		});
	}
	private boolean checkFieldFormat(){
		String username = mUsernameField.getText();
		char[] password = mPasswordField.getPassword();
		
		if(username.isEmpty()||password.length==0)
		{
			JOptionPane.showMessageDialog(null, "One or more entry field is blank","Login Failed",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}
