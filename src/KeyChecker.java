import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyChecker extends KeyAdapter {
	boolean upPressed, downPressed, leftPressed, rightPressed, firePressed;
	
	public KeyChecker() {
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	    switch (e.getKeyCode()){
	        case KeyEvent.VK_W :
	        	upPressed = true;
	            break;
	        case KeyEvent.VK_S :
	        	downPressed = true;
	            break;
	        case KeyEvent.VK_A :
	        	leftPressed = true;
	            break;
	        case KeyEvent.VK_D :
	        	rightPressed = true;
	            break;
	    }
	}
	@Override
	public void keyReleased(KeyEvent e) {
	    switch (e.getKeyCode()){
	    case KeyEvent.VK_W :
        	upPressed = false;
            break;
        case KeyEvent.VK_S :
        	downPressed = false;
            break;
        case KeyEvent.VK_A :
        	leftPressed = false;
            break;
        case KeyEvent.VK_D :
        	rightPressed = false;
            break;
	    }
	}
	@Override
	public void keyTyped(KeyEvent e) {
	    if (e.getKeyChar() == ' '){
	    	firePressed = true;
	    }
	}
	
	boolean getUpPressed() {
		return upPressed;
	}
	void setUpPressed(boolean newValue) {
		upPressed = newValue;
	}
	boolean getDownPressed() {
		return downPressed;
	}
	void setDownPressed(boolean newValue) {
		downPressed = newValue;
	}
	boolean getLeftPressed() {
		return leftPressed;
	}
	void setLeftPressed(boolean newValue) {
		leftPressed = newValue;
	}
	boolean getRightPressed() {
		return rightPressed;
	}
	void setRightPressed(boolean newValue) {
		rightPressed = newValue;
	}
	boolean getFirePressed() {
		return firePressed;
	}
	void setFirePressed(boolean newValue) {
		firePressed = newValue;
	}
}
