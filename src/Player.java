import java.awt.geom.GeneralPath;
import javax.vecmath.Vector2d;

public class Player extends PhysicsObject {
	float direction, speed;
	
	public Player() {
		position = new Vector2d(100,100);
		velocity = new Vector2d(0,0);
		
		Vector2d[] points = {new Vector2d(40,0),new Vector2d(-40,30),new Vector2d(-20,0),new Vector2d(-40,-30)};
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, points.length);
		art.moveTo (points[0].x, points[0].y);
		for (int i = 1; i < points.length; i++) {
		         art.lineTo(points[i].x, points[i].y);
		}
		art.closePath();
		
		collisionWidth = 30;
		direction = 0;
		speed = 20;
	}
	void setDirection(float newValue) {
		direction = newValue;
	}
	float getDirection() {
		return direction;
	}
	void setSpeed(float newValue) {
		speed = newValue;
	}
	float getSpeed() {
		return speed;
	}
	
	void updatePhysics(float dt, int windowSizeX, int windowSizeY) {
		velocity = new Vector2d(Math.cos(direction)*speed, Math.sin(direction)*speed);
		super.updatePhysics(dt, windowSizeX, windowSizeY);
	}
}
