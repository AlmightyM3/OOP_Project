import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.JFrame;

//@SuppressWarnings("serial")
//public class GraphicsTest extends JPanel {
//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Java 2D Graphics Test ");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.add(new GraphicsTest());
//        frame.setVisible(true);
//    }
//    
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//
//        g2d.draw(new Rectangle2D.Double(30,50,  100,200));
//        
//        g2d.setPaint(Color.blue);
//        g2d.fill(new Ellipse2D.Double(150,200,  300,100));
//    }
//}

public class GraphicsTest {
	public static void main(String[] args) {
		final int WINDOW_SIZE_X = 1080;
		final int WINDOW_SIZE_Y = 720;
		// Create a window
		JFrame app = new JFrame();
		app.setIgnoreRepaint(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create canvas for painting
		Canvas canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);

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

		while(true) {
			try {
				// Calculate delta time (dt) in seconds
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				float dt = (float)(curTime - lastTime)/1000.0f;

				// Clear back buffer
				g2d = bi.createGraphics();
				g2d.setColor(backgroundColor);
				g2d.fillRect(0,0, WINDOW_SIZE_X-1,WINDOW_SIZE_X-1);

				g2d.setPaint(Color.blue);
		        g2d.fill(new Rectangle2D.Double(500+Math.sin(0.002*curTime)*400,200,  100,300));
		        g2d.setPaint(Color.red);
		        g2d.fill(new Ellipse2D.Double(700,300+Math.cos(0.002*curTime)*200,  100,150));

				// Display FPS
				g2d.setFont(new Font("Courier New", Font.PLAIN, 16));
				g2d.setColor(Color.GREEN);
				g2d.drawString(String.format( "FPS: %s", Math.round(1.0/dt)), 20, 20);

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