import java.awt.geom.GeneralPath;

import javax.vecmath.Vector2d;

public class Asteroid extends PhysicsObject {
	int size;
	
	public Asteroid(Vector2d startPosition, int startSize) {
		size = startSize;
		position = startPosition;
		double dir=Math.random()*Math.TAU;
		velocity = new Vector2d(Math.cos(dir)*(5*size+10),Math.sin(dir)*(5*size+10));
		
		collisionWidth = (int) (180*Math.pow(0.6, size));
		
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 12-size*2+1);
		int dist = (int)(Math.random()*collisionWidth/3+2.5*collisionWidth/3);
		art.moveTo(dist, 0);
		for (int i = 0; i < 12-size*2; i+=2) {
		    art.lineTo(Math.cos(i/(12.0-size)*Math.TAU)*dist, Math.sin(i/(12.0-size)*Math.TAU)*dist);
		    art.lineTo(Math.cos((i+1)/(12.0-size)*Math.TAU)*dist, Math.sin((i+1)/(12.0-size)*Math.TAU)*dist);
		    dist = (int)(Math.random()*collisionWidth);
		}
		art.closePath();
	}
	
	int getSize() {
		return size;
	}
	void setSize(int newValue) {
		size = newValue;
		
		collisionWidth = (int) (180*Math.pow(0.6, size));
		
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 12-size*2+1);
		int dist = (int)(Math.random()*collisionWidth/3+2.5*collisionWidth/3);
		art.moveTo(dist, 0);
		for (int i = 0; i < 12-size*2; i+=2) {
		    art.lineTo(Math.cos(i/(12.0-size)*Math.TAU)*dist, Math.sin(i/(12.0-size)*Math.TAU)*dist);
		    art.lineTo(Math.cos((i+1)/(12.0-size)*Math.TAU)*dist, Math.sin((i+1)/(12.0-size)*Math.TAU)*dist);
		    dist = (int)(Math.random()*collisionWidth);
		}
		art.closePath();
	}
}
