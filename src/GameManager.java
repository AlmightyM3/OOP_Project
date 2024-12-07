import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.vecmath.Vector2d;

public class GameManager {
	KeyChecker keyChecker;
	Player player;
	Asteroid[] asteroids; 
	Bullet[] bullets;
	boolean isRunning;
	long restartDelay;
	
	public GameManager() { // Set all default values, only one constructor since all should start with the same values each time.
		keyChecker = new KeyChecker();
		
		player = new Player();
		asteroids = new Asteroid[0]; 
		bullets = new Bullet[0];
		
		isRunning = false;
		restartDelay = 0;
	}
	
	KeyChecker getKeyChecker() {
		return keyChecker;
	}
	
	void handleInput(float dt) { // Checks what keys have been pressed and deals with it.		
		if (keyChecker.getUpPressed()) 
			player.setSpeed(20);
		else
			player.setSpeed(0);
		if (keyChecker.getLeftPressed()) 
			player.setDirection(player.getDirection()-(float)Math.PI*dt);
		if (keyChecker.getRightPressed()) 
			player.setDirection(player.getDirection()+(float)Math.PI*dt);
		if (keyChecker.getFirePressed()) {
			if (isRunning) { // if the game is running, shoot
				bullets = insert(bullets, new Bullet(player));
			} else if(restartDelay <= System.currentTimeMillis()){ // If suffishint time has passed reset the game.
				isRunning = true;
				player.setPosition(new Vector2d(100,100));
				bullets = new Bullet[0];
				asteroids = insert(new Asteroid[0], new Asteroid(new Vector2d(400,100), 0));
				asteroids = insert(asteroids, new Asteroid(new Vector2d(600,300), 0));
				asteroids = insert(asteroids, new Asteroid(new Vector2d(900,500), 0));
			}
			
			keyChecker.setFirePressed(false);
		}
	}
	
	void updateGame(float dt, int windowSizeX, int windowSizeY) {
		if (isRunning) {
			player.updatePhysics(dt, windowSizeX, windowSizeY);
			for (int i=0;i<bullets.length;i++) {
				bullets[i].updatePhysics(dt, windowSizeX, windowSizeY);
				if (System.currentTimeMillis()>=bullets[i].timeOfDestruction) 
					bullets = remove(bullets, i--);
			}
			for (int i=0;i<asteroids.length;i++) {
				asteroids[i].updatePhysics(dt, windowSizeX, windowSizeY);
				if (asteroids[i].isColliding(player, windowSizeX, windowSizeY)) {
					isRunning=false;
					restartDelay = System.currentTimeMillis()+500;
				}
				for (int b=0;b<bullets.length;b++) {
					if (asteroids[i].isColliding(bullets[b], windowSizeX, windowSizeY)) {
						if (asteroids[i].size<2) {
							asteroids = insert(asteroids, new Asteroid(new Vector2d(asteroids[i].position), asteroids[i].size+1));
							asteroids = insert(asteroids, new Asteroid(new Vector2d(asteroids[i].position), asteroids[i].size+1));
						}
						asteroids = remove(asteroids, i--);
						bullets = remove(bullets, b);
						b = bullets.length;
					}
				}
			}
			if (asteroids.length <1) {
				isRunning = false;
				restartDelay = System.currentTimeMillis()+500;
			}
		}
	}
	
	void renderScene(Graphics2D g2d, int windowSizeX, int windowSizeY) {
		g2d.setColor(Color.WHITE);
		for (Asteroid asteroid : asteroids) {
			asteroid.draw(g2d, windowSizeX, windowSizeY);
		}
		for (Bullet bullet : bullets) {
			bullet.draw(g2d, windowSizeX, windowSizeY);
		}
		
		player.draw(g2d, windowSizeX, windowSizeY);
		
		if (!isRunning) {
			g2d.setFont(new Font("Courier New", Font.PLAIN, 48));
			if (restartDelay==0) {
				g2d.setColor(Color.WHITE);
				g2d.drawString("Space to start!", windowSizeX/2-180, windowSizeY/2-18);
				g2d.setFont(new Font("Courier New", Font.PLAIN, 96));
				g2d.drawString("ASTEROIDS", windowSizeX/2-216, windowSizeY/2-100);
				g2d.setFont(new Font("Courier New", Font.PLAIN, 24));
				g2d.drawString("WASD or arrows to move.", windowSizeX/2-138, windowSizeY/2+24);
				g2d.drawString("Space will also Shoot.", windowSizeX/2-132, windowSizeY/2+48);
				g2d.drawString("All objects screen-wrap.", windowSizeX/2-144, windowSizeY/2+72);
				
				g2d.setFont(new Font("Courier New", Font.PLAIN, 16));
				g2d.drawString("<-You", 140, 104);
				g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
				g2d.drawString("Originally created by Lyle Rains and Ed Logg with hardware from Wendi Allen and published by Atari, Inc. in 1979.", windowSizeX/2-380, windowSizeY-16);
			} else if (asteroids.length <1) {
				g2d.setColor(Color.GREEN);
				g2d.drawString("You Win!", windowSizeX/2-96, windowSizeY/2-24);
			} else {
				g2d.setColor(Color.RED);
				g2d.drawString("You Lost.", windowSizeX/2-120, windowSizeY/2-24);
			}
		}
	}
	
	public Bullet[] remove(Bullet[] array, int index) {
		if (array.length <= 0) // If the array is empty, don't even try to remove anything.
			return array;
		
		Bullet[] output = new Bullet[array.length-1]; // Create a new array that can hold all of the old array - the value we remove.
		System.arraycopy(array, 0, output, 0, index); // Add the first part up to but not including the value to remove.
		System.arraycopy(array, index+1, output, index, array.length-index-1); // Add the second part from after the value to remove to the end.
		return output; // Return the updated array to the caller.
	}
	public Bullet[] insert(Bullet[] array, Bullet newValue) { 
		Bullet[] output = new Bullet[array.length+1]; // Create a new array that can hold all of the old array and one new value.
		System.arraycopy(array, 0, output, 0, array.length); // Add the old array.
		output[array.length] = newValue; // Add the new value.
		return output; // Return the updated array to the caller.
	}
	public Bullet[] getBullets() {
		return bullets;
	}
	
	public Asteroid[] remove(Asteroid[] array, int index) {
		if (array.length <= 0) // If the array is empty, don't even try to remove anything.
			return array;
		
		Asteroid[] output = new Asteroid[array.length-1]; // Create a new array that can hold all of the old array - the value we remove.
		System.arraycopy(array, 0, output, 0, index); // Add the first part up to but not including the value to remove.
		System.arraycopy(array, index+1, output, index, array.length-index-1); // Add the second part from after the value to remove to the end.
		return output; // Return the updated array to the caller.
	}
	public Asteroid[] insert(Asteroid[] array, Asteroid newValue) { 
		Asteroid[] output = new Asteroid[array.length+1]; // Create a new array that can hold all of the old array and one new value.
		System.arraycopy(array, 0, output, 0, array.length); // Add the old array.
		output[array.length] = newValue; // Add the new value.
		return output; // Return the updated array to the caller.
	}
	public Asteroid[] getAsteroids() {
		return asteroids;
	}
	
	public static void main(String[] args) {
		final int WINDOW_SIZE_X = 1080;
		final int WINDOW_SIZE_Y = 720;
		
		GameManager gameManager = new GameManager(); // Create ourselves a game manager.
		
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
		GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

		// Create off-screen drawing surface
		BufferedImage backBuffer = graphicsConfig.createCompatibleImage(WINDOW_SIZE_X, WINDOW_SIZE_Y);

		// Objects needed for rendering
		Graphics graphics = null;
		Graphics2D g2d = null;

		// Variables for calculating frames per seconds
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;

		while(true) {
			try {
				// Calculate delta time (dt) in seconds
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				float dt = (curTime - lastTime)/1000.0f;
				
				gameManager.handleInput(dt); // Check what keys have been pressed and deal with it.				
				gameManager.updateGame(dt, WINDOW_SIZE_X, WINDOW_SIZE_Y); // Move physics objects and resolve collisions.

				// Clear back buffer
				g2d = backBuffer.createGraphics();
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0,0, WINDOW_SIZE_X,WINDOW_SIZE_Y);

				gameManager.renderScene(g2d, WINDOW_SIZE_X, WINDOW_SIZE_Y); // Draw all physics objects and UI.

				// Display FPS
				g2d.setFont(new Font("Courier New", Font.PLAIN, 16));
				g2d.setColor(Color.GREEN);
				g2d.drawString(String.format("FPS: %s", Math.round(1/dt)), 20, 20);

				// Blit image and flip
				graphics = buffer.getDrawGraphics();
				graphics.drawImage(backBuffer, 0, 0, null);
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