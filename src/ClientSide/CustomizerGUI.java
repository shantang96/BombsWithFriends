package ClientSide;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class CustomizerGUI extends JPanel{
        private static final long serialVersionUID = -6005389666322029366L;
        private Box mMainBox;
        private CustomPanel mBodyPanel;
        private JButton mConfirmButton, mLeftButton,mRightButton;
        private MainClientGUI mMainClientGUI = null;
        private JPanel mainPanel;
        private Vector<String> spriteBodyFiles = new Vector<>();
        private int vectorIndex = 0;
		private String username;
		
		
		private int spriteNumber;
		private String currentSprite;

        public CustomizerGUI(MainClientGUI mMainClientGUI,String username, int spriteNumber) {
        		this.username = username;
                this.mMainClientGUI = mMainClientGUI;
                this.spriteNumber = spriteNumber;
                setLayout(new BorderLayout());
                populateBodyCombo();
                initialize();
                createGUI();
                addActions();
        }

        private void initialize(){
                mBodyPanel = new CustomPanel();
                mConfirmButton = new JButton("Confirm");
                mLeftButton = new JButton("<-");
                mRightButton = new JButton("->");
                mainPanel = new JPanel(new BorderLayout());
                mMainBox = Box.createHorizontalBox();

        }
        private void populateBodyCombo(){
                spriteBodyFiles.add("resources/CustomizerBodies/White.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Blue.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Pink.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Red.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Ghost.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Black.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Yellow.png");
                spriteBodyFiles.add("resources/CustomizerBodies/Green.png");


        }

        private void createGUI(){
                mMainBox.add(mLeftButton);
                mMainBox.add(mBodyPanel);
                mMainBox.add(mRightButton);
                mainPanel.add(mMainBox,BorderLayout.CENTER);
                mainPanel.add(mConfirmButton,BorderLayout.SOUTH);
                mainPanel.setPreferredSize(new Dimension(400,275));
                mainPanel.setMinimumSize(new Dimension(400,275));
                mainPanel.setSize(new Dimension(400,275));
                mainPanel.setMaximumSize(new Dimension(400,275));
                add(mainPanel);
        }


        private void addActions(){
                mLeftButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        		vectorIndex--;
                        		if(vectorIndex == -1)
                        		{
                        			vectorIndex = spriteBodyFiles.size()-1;
                        		}
                        	
                                mBodyPanel.paintWithNewFile(vectorIndex);
                                revalidate();
                                repaint();
                        }
                });
                mRightButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
//                                if(vectorIndex==0)vectorIndex = 1;
//                                else if(vectorIndex==1)vectorIndex = 2;
//                                else if(vectorIndex==2)vectorIndex = 0;
                        		vectorIndex++;
                        		if(vectorIndex == spriteBodyFiles.size())
                        		{
                        			vectorIndex = 0;
                        		}
                                mBodyPanel.paintWithNewFile(vectorIndex);
                                revalidate();
                                repaint();
                        }
                });
                mConfirmButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	currentSprite = spriteBodyFiles.elementAt(vectorIndex);
                        	if(currentSprite.equals("resources/CustomizerBodies/Blue.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetBlue.png";
                        		spriteNumber = 1;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/Pink.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetPink.png";
                        		spriteNumber = 2;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/White.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetWhite.png";
                        		spriteNumber = 0;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/Red.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetRed.png";
                        		spriteNumber = 3;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/Ghost.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetGhost.png";
                        		spriteNumber = 4;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/Black.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetBlack.png";
                        		spriteNumber = 5;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/Yellow.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetYellow.png";
                        		spriteNumber = 6;
                        	}
                        	else if(currentSprite.equals("resources/CustomizerBodies/Green.png")){
                        		currentSprite = "./resources/spritesheets/BasicPlayerSheetGreen.png";
                        		spriteNumber = 7;
                        	}
//                        	System.out.println(currentSprite);
                        	GameFinderGUI newGFGUI = new GameFinderGUI(mMainClientGUI,username);
                			newGFGUI.changeSpriteSheet(currentSprite);
                			newGFGUI.changeCurrentSprite(spriteNumber);
                            mMainClientGUI.addToCenter(newGFGUI);
                        }
                });
        }
        class CustomPanel extends JPanel{
                private static final long serialVersionUID = 1L;
                private String spriteFilename = spriteBodyFiles.elementAt(spriteNumber);
                public CustomPanel(){
                        super();
                }
                public void paintWithNewFile(int vectorIndex){

                        this.spriteFilename = spriteBodyFiles.elementAt(vectorIndex);
                        revalidate();
                        repaint();
                }
                public void paintComponent(Graphics g)
                 {
                        Image img = new ImageIcon( spriteFilename).getImage();
                        g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
                }
        }
}