import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.vecmath.Vector2d;

public class GameManager {
	public static void main(String[] args) {
		final int WINDOW_SIZE_X = 1080;
		final int WINDOW_SIZE_Y = 720;
		
		// Create a window
		JFrame app = new JFrame("Asteroids");
		app.setIgnoreRepaint(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create canvas for painting
		Canvas canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		canvas.addKeyListener(new KeyChecker());

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
		
		PhysicsObject test1 = new PhysicsObject(new Vector2d(0,100), new Vector2d(10,0), 50);
		PhysicsObject test2 = new PhysicsObject(new Vector2d(800,100), new Vector2d(-10,0), 50);

		while(true) {
			try {
				// Calculate delta time (dt) in seconds
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				float dt = (float)(curTime - lastTime)/1000.0f;
				
				

				// Clear back buffer
				g2d = bi.createGraphics();
				g2d.setColor(backgroundColor);
				g2d.fillRect(0,0, WINDOW_SIZE_X,WINDOW_SIZE_X);

				test1.updatePhysics(dt, WINDOW_SIZE_X,WINDOW_SIZE_Y);
				test2.updatePhysics(dt, WINDOW_SIZE_X,WINDOW_SIZE_Y);
				if (test1.isColliding(test2)) {
					test1.velocity.scale(-1);
					test2.velocity.scale(-1);
				}
				g2d.setColor(Color.GREEN);
				g2d.draw(new Ellipse2D.Double(test1.position.x-50, test1.position.y-50, 50*2,50*2));
				g2d.draw(new Ellipse2D.Double(test2.position.x-50, test2.position.y-50, 50*2,50*2));

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