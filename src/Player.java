/* This program implements a asteroids player with rotation.
 * Author: Matthew Moulton
 * Date: 11/27/2024 to 12/9/2024
 */

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import javax.vecmath.Vector2d;

public class Player extends PhysicsObject {
	float direction, speed;
	
	public Player() {
		position = new Vector2d(100,100);
		velocity = new Vector2d(0,0);
		
		Vector2d[] points = {new Vector2d(30,0),new Vector2d(-30,22.5),new Vector2d(-15,0),new Vector2d(-30,-22.5)};
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, points.length);
		art.moveTo (points[0].x, points[0].y);
		for (int i = 1; i < points.length; i++) {
		         art.lineTo(points[i].x, points[i].y);
		}
		art.closePath();
		
		collisionWidth = 20;
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
	
	void draw(Graphics2D g2d, int windowSizeX, int windowSizeY) {
		g2d.translate(position.x, position.y);
		g2d.rotate(direction);
		g2d.draw(art);
		g2d.rotate(-direction);
		
		g2d.translate(windowSizeX, 0);
		g2d.rotate(direction);
		g2d.draw(art);
		g2d.rotate(-direction);
		g2d.translate(-2*windowSizeX, 0);
		g2d.rotate(direction);
		g2d.draw(art);
		g2d.rotate(-direction);
		g2d.translate(windowSizeX, windowSizeY);
		g2d.rotate(direction);
		g2d.draw(art);
		g2d.rotate(-direction);
		g2d.translate(0, -2*windowSizeY);
		g2d.rotate(direction);
		g2d.draw(art);
		g2d.rotate(-direction);
		
		g2d.translate(-position.x, windowSizeY-position.y);
	}
}
