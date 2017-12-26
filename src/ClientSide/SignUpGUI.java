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

import ClientToMainServerMessages.SignUpMessage;


public class SignUpGUI extends JPanel{
	private static final long serialVersionUID = 5714954049111829204L;
	private JLabel mUsernameLabel,mPasswordLabel,mRepeatLabel;
	private JTextField mUsernameField;
	private JPasswordField mPasswordField,mRepeatField;
	private JButton mSignUpButton,mBackButton;
	private JPanel mTopGrid, mBottomGrid;
	private MainClientGUI mMainClientWindow;
	
	public SignUpGUI(MainClientGUI mMainClientWindow){
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
		mRepeatLabel = new JLabel(" Repeat: ");
		mRepeatLabel.setHorizontalAlignment(WIDTH/2);
		mUsernameField = new JTextField();
		mPasswordField = new JPasswordField();
		mRepeatField = new JPasswordField();
		mTopGrid = new JPanel();
		mBottomGrid = new JPanel();
		mSignUpButton = new JButton("Sign Up");
		mBackButton = new JButton("Back");
		setLayouts();
		gridSetup();
	}
	private void gridSetup(){
		Box UsernameBox = Box.createHorizontalBox();
		Box PasswordBox = Box.createHorizontalBox();
		Box RepeatBox = Box.createHorizontalBox();

		UsernameBox.add(mUsernameLabel);
		UsernameBox.add(mUsernameField);
		PasswordBox.add(mPasswordLabel);
		PasswordBox.add(mPasswordField);
		RepeatBox.add(mRepeatLabel);
		RepeatBox.add(mRepeatField);
		
		mTopGrid.add(UsernameBox);
		mTopGrid.add(PasswordBox);
		mTopGrid.add(RepeatBox);
		
		mBottomGrid.add(mSignUpButton);
		mBottomGrid.add(mBackButton);
	}
	private void setLayouts(){
		mTopGrid.setLayout(new GridLayout(3,1));
		mBottomGrid.setLayout(new GridLayout(1,2));
		setLayout(new BorderLayout());
	}
	private void createGUI(){
		add(mTopGrid,BorderLayout.CENTER);
		add(mBottomGrid,BorderLayout.SOUTH);
	}
	private void signUpRequest(){
		String username = mUsernameField.getText();
		@SuppressWarnings("deprecation")
		String password = mPasswordField.getText();
		byte[] encryptedPassword = mMainClientWindow.encrypt(password);
		SignUpMessage signupMessage = new SignUpMessage();
		signupMessage.setUsername(username);
		signupMessage.setEncryptedPassword(encryptedPassword);
		//KEY
		//0 : no server online
		//1 : successful login
		//2 : incorrect username or password
		int SignUpResult = mMainClientWindow.getConnector().transmitToServer(signupMessage);
		if(SignUpResult==1){
			//System.out.println("YEAH SUCCESS");
			mMainClientWindow.clearBottomQuadrant();
			mMainClientWindow.addToCenter(new GameFinderGUI(mMainClientWindow, username));
		}else if(SignUpResult==2){
			username = null;
			JOptionPane.showMessageDialog(null, "Username is Already in Use","Sign-Up Failed", JOptionPane.ERROR_MESSAGE);
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
		mSignUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(checkFieldFormat()){
					signUpRequest();
				}
			}
		});
	}
	@SuppressWarnings("deprecation")
	private boolean checkFieldFormat(){
		String username = mUsernameField.getText();
		char[] passwordChar = mPasswordField.getPassword();
		String repeatPassword = mRepeatField.getText();
		String password = mPasswordField.getText();
		if(username.isEmpty()||password.isEmpty()||repeatPassword.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "One or more entry field is blank","Sign-Up Failed",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//validate password and repeat
		if(!password.equals(repeatPassword)){
			JOptionPane.showMessageDialog(null, "Repeat must match the Password","Sign-Up Failed",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		boolean hasNumber = false;
		boolean hasUpperCase = false;
		for(char c: passwordChar){
			if(Character.isUpperCase(c)) hasUpperCase = true;
			if(Character.isDigit(c)) hasNumber = true;
		}
		if(!hasNumber||!hasUpperCase){	
			JOptionPane.showMessageDialog(null, "Password must contain at least: \n 1-number 1-uppercase letter","Sign-Up Failed",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}