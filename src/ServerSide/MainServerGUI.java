package ServerSide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MainServerGUI extends JFrame{
	public static final long serialVersionUID = 1;
	private JTextArea textArea;
	private JButton mStartButton;
	private JButton mStopButton;
	private JScrollPane mScrollPane;
	private MainServerGUI mMainServerGUI;
	public MServer mMainServer;
	private static int mPort;
	public MainServerGUI(){	
		super("Server");
		mMainServerGUI = this;
		instantiateComponents();
		createGUI();
		addActions();
		this.setVisible(true);
	}
	private void instantiateComponents(){
		textArea = new JTextArea();
		mScrollPane = new JScrollPane(textArea);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mStartButton = new JButton("Start");
		mStopButton = new JButton("Stop");
		mMainServer = null;
	}

	private void createGUI(){
		setSize(500,300);
		setLocation(50,50);
		setBackground(Color.darkGray);
		add(mScrollPane,BorderLayout.CENTER);
		add(mStartButton,BorderLayout.SOUTH);
	}
	public void writeToScrean(String text){
		textArea.append(text);
		textArea.append("\n");
	}
	public void startToStop(){
		remove(mStartButton);
		add(mStopButton,BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
	public void stopToStart(){
		remove(mStopButton);
		add(mStartButton,BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
	private static int getPort(){
		int portNumber = 7000; // if the file is not read properly the port will be set to the default
		String line = null;
		try {
			File portFile = new File("resources/configs/portNumberConfig.txt");
			FileReader portFileReader = new FileReader(portFile);
		    BufferedReader in = new BufferedReader(portFileReader);	    
		    while ((line = in.readLine()) != null)
		        portNumber = Integer.parseInt(line);
		    in.close();
		} catch (IOException e) {
		}
		return portNumber;
	}
	private void addActions(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mStartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mPort = getPort();
				mMainServer = new MServer(mPort, mMainServerGUI);
			}
		});
		mStopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mMainServer.shutDown();
			}
		});
	}
}