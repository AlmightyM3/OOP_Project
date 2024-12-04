import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.vecmath.Vector2d;

public class GameManager {
	KeyChecker keyChecker;
	public GameManager() {
		keyChecker = new KeyChecker();
	}
	
	KeyChecker getKeyChecker() {
		return keyChecker;
	}
	
	public static void main(String[] args) {
		final int WINDOW_SIZE_X = 1080;
		final int WINDOW_SIZE_Y = 720;
		
		GameManager gameManager = new GameManager();
		
		// Create a window
		JFrame app = new JFrame("Asteroids");
		app.setIgnoreRepaint(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create canvas for painting
		Canvas canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		canvas.addKeyListener(gameManager.getKeyChecker());

		// Add canvas to game window
		app.add(canvas);
		app.pack();
		app.setVisible(true);

		// Create BackBuffer
		canvas.createBufferStrategy(2);
		BufferStrategy buffer = canvas.getBufferStrategy();

		// Get graphics configuration
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		// Create off-screen drawing surface
		BufferedImage bi = gc.createCompatibleImage(WINDOW_SIZE_X, WINDOW_SIZE_Y);

		// Objects needed for rendering
		Graphics graphics = null;
		Graphics2D g2d = null;
		Color backgroundColor = Color.BLACK;

		// Variables for calculating frames per seconds
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;
		
		Player player = new Player();
		Asteroid[] asteroids = {new Asteroid(new Vector2d(400,100), 0), new Asteroid(new Vector2d(400,300), 1), new Asteroid(new Vector2d(400,500), 2)}; 
		boolean isRunning = true;

		while(true) {
			try {
				// Calculate delta time (dt) in seconds
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				float dt = (float)(curTime - lastTime)/1000.0f;
				
				if (gameManager.getKeyChecker().getUpPressed()) 
					player.setSpeed(20);
				else
					player.setSpeed(0);
				if (gameManager.getKeyChecker().getLeftPressed()) 
					player.setDirection(player.getDirection()-(float)Math.PI*dt);
				if (gameManager.getKeyChecker().getRightPressed()) 
					player.setDirection(player.getDirection()+(float)Math.PI*dt);
				if (gameManager.getKeyChecker().getFirePressed()) {
					if (!isRunning) {
						isRunning = true;
						player.setPosition(new Vector2d(100,100));
					}
					
					gameManager.getKeyChecker().setFirePressed(false);
				}
				
				if (isRunning) {
					player.updatePhysics(dt, WINDOW_SIZE_X, WINDOW_SIZE_Y);
					for (Asteroid asteroid : asteroids) {
						asteroid.updatePhysics(dt, WINDOW_SIZE_X, WINDOW_SIZE_Y);
						if (asteroid.isColliding(player)) 
							isRunning=false;
					}
				}

				// Clear back buffer
				g2d = bi.createGraphics();
				g2d.setColor(backgroundColor);
				g2d.fillRect(0,0, WINDOW_SIZE_X,WINDOW_SIZE_X);

				g2d.setColor(Color.WHITE);
				for (Asteroid asteroid : asteroids) {
					g2d.translate(asteroid.getPosition().x, asteroid.getPosition().y);
					g2d.draw(asteroid.getArt());
					g2d.translate(-asteroid.getPosition().x, -asteroid.getPosition().y);
				}
				
				g2d.translate(player.getPosition().x, player.getPosition().y);
				g2d.rotate(player.getDirection());
				g2d.draw(player.getArt());
				g2d.rotate(-player.getDirection());
				g2d.translate(-player.getPosition().x, -player.getPosition().y);

				// Display FPS
				g2d.setFont(new Font("Courier New", Font.PLAIN, 16));
				g2d.setColor(Color.GREEN);
				g2d.drawString(String.format( "FPS: %s", Math.round(1/dt)), 20, 20);

				// Blit image and flip
				graphics = buffer.getDrawGraphics();
				graphics.drawImage(bi, 0, 0, null);
				if(!buffer.contentsLost())
					buffer.show();

				// Let the OS have some time
				Thread.yield();
			} finally {
				// release resources
				if(graphics != null) 
					graphics.dispose();
				if(g2d != null) 
					g2d.dispose();
			}
		}
	}
}