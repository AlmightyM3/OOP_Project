import java.awt.geom.GeneralPath;
import java.util.Date;
import javax.vecmath.Vector2d;

public class Bullet extends PhysicsObject {
	long timeOfDestruction;
	
	public Bullet(Player player) {
		position = new Vector2d(player.position);
		double dir=Math.random()*Math.TAU;
		velocity = new Vector2d(Math.cos(player.direction)*30, Math.sin(player.direction)*30);
		
		collisionWidth = 5;
		
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 5);
		art.moveTo(collisionWidth, 0);
		for (int i = 1; i < 5; i++) {
		    art.lineTo(Math.cos(i/5.0*Math.TAU)*collisionWidth, Math.sin(i/5.0*Math.TAU)*collisionWidth);
		}
		art.closePath();
		
		timeOfDestruction = System.currentTimeMillis()+2000;
	}
	
	void setTimeOfDestruction(long newValue) {
		timeOfDestruction = newValue;
	}
	
	long getTimeOfDestruction() {
		return timeOfDestruction;
	}
}
