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
	        case KeyEvent.VK_A :
	        	downPressed = true;
	            break;
	        case KeyEvent.VK_S :
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
        case KeyEvent.VK_A :
        	downPressed = false;
            break;
        case KeyEvent.VK_S :
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
}
