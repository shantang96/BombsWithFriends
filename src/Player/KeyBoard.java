package Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;


public class KeyBoard implements KeyListener, Serializable {
	
	private static final long serialVersionUID = 1L;
	public boolean up, down, left, right, space, x, z;
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			up = true;
		}	else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			down = true;
		}	else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			left = true;
		}	else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			space = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_X)
		{
			x = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			z = true;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			up = false;
		}	else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			down = false;
		}	else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			left = false;
		}	else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			space = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_X)
		{
			x = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_Y)
		{
			z = false;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

}