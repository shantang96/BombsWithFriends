package ClientSide;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class MainClientGUI extends JFrame{
	public static final long serialVersionUID = 832423423;
	private static final String mKey = "GoTrojansBeatCal";
	private Key mAESKey;
	private MainClientGUI mMainClientGUI;
	private Cipher mCipher = null;
	private JPanel mLogo, mCenter, mSubCenter, mGuestSignUpGrid;
	private JButton mLoginButton, mSignUpButton, mGuestButton;
	private LoginGUI mLoginGUI = null;
	private SignUpGUI mSignUpGUI = null;
	private ClientToMainServerConnector mClientToMainServerConnector;
	
	public MainClientGUI(){
		super("BomberMan Client");
		try{ UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
		catch(Exception e){ System.out.println("Warning! Cross-platform L&F not used!"); }
		mClientToMainServerConnector = new ClientToMainServerConnector();
		mMainClientGUI = this;
		GenerateAESKey();
		instantiateComponents();
		createGUI();
		addActions();
		setVisible(true);
	}
	private void instantiateComponents(){
		mCenter = new JPanel();
		mLogo = new JPanel(new FlowLayout()) {
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        try {
					g.drawImage(ImageIO.read(new File("./resources/bwf.png")), 0, 0, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		};
		mSubCenter = new JPanel();
		mGuestSignUpGrid = new JPanel();
		mLoginButton = new JButton("Login");
		mSignUpButton = new JButton("Sign Up");
		mGuestButton = new JButton("Guest");
		setLayouts();
	}
	private void setLayouts(){
		mCenter.setLayout(new BorderLayout());
		mSubCenter.setLayout(new GridLayout(2,1));
		mGuestSignUpGrid.setLayout(new GridLayout(1,2));
		
	}
	public void backButton(){
		if(mLoginGUI!=null) remove(mLoginGUI);
		if(mSignUpGUI!=null) remove(mSignUpGUI);
		add(mSubCenter,BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
	private void createGUI(){
		setSize(500,300);
		setLocation(600,50);
		mGuestSignUpGrid.add(mGuestButton);
		mGuestSignUpGrid.add(mSignUpButton);
		mSubCenter.add(mGuestSignUpGrid);
		mSubCenter.add(mLoginButton);
		add(mLogo);
		add(mSubCenter,BorderLayout.SOUTH);
		add(mLogo);
		//add(mCenter);
	}
	public void addToCenter(JPanel centerPanel){
		if(mCenter!=null) remove(mCenter);
		if(mLogo!=null) remove(mLogo);
		mCenter.removeAll();
		mCenter.add(((JPanel)centerPanel),BorderLayout.CENTER);
		add(mCenter);
		revalidate();
		repaint();
	}
	public ClientToMainServerConnector getConnector(){
		return mClientToMainServerConnector;
	}
	public void clearBottomQuadrant(){
		if(mLoginGUI!=null) remove(mLoginGUI);
		if(mSignUpGUI!=null) remove(mSignUpGUI);
		revalidate();
		repaint();
	}
	private void addActions(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mLoginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				remove(mSubCenter);
				mLoginGUI = new LoginGUI(mMainClientGUI);
				add(mLoginGUI,BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
		});
		mSignUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				remove(mSubCenter);
				mSignUpGUI = new SignUpGUI(mMainClientGUI);
				add(mSignUpGUI,BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
		});
		mGuestButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				remove(mSubCenter);
				
				addToCenter(new GuestGameFinderGUI(mMainClientGUI));
				revalidate();
				repaint();
			}
		});
	}
	private void GenerateAESKey(){
		Key tempKey = null;
		try{
		tempKey = new SecretKeySpec(mKey.getBytes(),"AES");
		} catch(Exception nsae){
			System.out.println("Exception in GenerateAESKey method: " + nsae);
		}
		mAESKey = tempKey;
	}
	public byte[] encrypt(String password){
		byte[] encryptedString = null;
		try{
			if(mCipher == null){
				mCipher = Cipher.getInstance("AES");
			}
			mCipher.init(Cipher.ENCRYPT_MODE, mAESKey);
			encryptedString = mCipher.doFinal(password.getBytes());	
		}catch(Exception e){
			System.out.println(e);
		}
		return encryptedString;
	}

}
