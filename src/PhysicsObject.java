import java.awt.geom.GeneralPath;
import javax.vecmath.Vector2d;

public class PhysicsObject {
	Vector2d position, velocity;
	GeneralPath art;
	int collisionWidth;
	
	public PhysicsObject() {
		position = new Vector2d(0,0);
		velocity = new Vector2d(0,0);
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 0);
		collisionWidth = 0;
	}
	public PhysicsObject(Vector2d startPosition) {
		position = startPosition;
		velocity = new Vector2d(0,0);
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 0);
		collisionWidth = 0;
	}
	public PhysicsObject(GeneralPath artPath) {
		position = new Vector2d(0,0);
		velocity = new Vector2d(0,0);
		art = artPath;
		collisionWidth = 0;
	}
	public PhysicsObject(Vector2d startPosition, Vector2d startVelocity) {
		position = startPosition;
		velocity = startVelocity;
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 0);
		collisionWidth = 0;
	}
	public PhysicsObject(GeneralPath artPath, Vector2d startPosition) {
		position = startPosition;
		velocity = new Vector2d(0,0);
		art = artPath;
		collisionWidth = 0;
	}
	public PhysicsObject(GeneralPath artPath, Vector2d startPosition, Vector2d startVelocity) {
		position = startPosition;
		velocity = startVelocity;
		art = artPath;
		collisionWidth = 0;
	}
	public PhysicsObject(Vector2d startPosition, int startCollisionWidth) {
		position = startPosition;
		velocity = new Vector2d(0,0);
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 0);
		collisionWidth = startCollisionWidth;
	}
	public PhysicsObject(GeneralPath artPath, int startCollisionWidth) {
		position = new Vector2d(0,0);
		velocity = new Vector2d(0,0);
		art = artPath;
		collisionWidth = startCollisionWidth;
	}
	public PhysicsObject(Vector2d startPosition, Vector2d startVelocity, int startCollisionWidth) {
		position = startPosition;
		velocity = startVelocity;
		art = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 0);
		collisionWidth = startCollisionWidth;
	}
	public PhysicsObject(GeneralPath artPath, Vector2d startPosition, int startCollisionWidth) {
		position = startPosition;
		velocity = new Vector2d(0,0);
		art = artPath;
		collisionWidth = startCollisionWidth;
	}
	public PhysicsObject(GeneralPath artPath, Vector2d startPosition, Vector2d startVelocity, int startCollisionWidth) {
		position = startPosition;
		velocity = startVelocity;
		art = artPath;
		collisionWidth = startCollisionWidth;
	}
	
	
	Vector2d getPosition() {
		return position;
	}
	void setPosition(Vector2d newValue){
		position = newValue;
	}
	Vector2d getVelocity() {
		return velocity;
	}
	void setVelocity(Vector2d newValue){
		velocity = newValue;
	}
	GeneralPath getArt() {
		return art;
	}
	void setArt(GeneralPath newValue){
		art = newValue;
	}
	int getCollisionWidth(){
		return collisionWidth;
	}
	void setCollisionWidth(int newValue){
		collisionWidth = newValue;
	}

	void updatePhysics(float dt, int windowSizeX, int windowSizeY) {
		Vector2d temp = new Vector2d(velocity);
		temp.scale(dt*velocity.length());
		position.add(temp);
		position.x +=windowSizeX;
		position.y +=windowSizeY;
		position.x %=windowSizeX;
		position.y %=windowSizeY;
	}
	boolean isColliding(PhysicsObject other) {
		return (this.position.x-other.position.x)*(this.position.x-other.position.x) + (this.position.y-other.position.y)*(this.position.y-other.position.y) <= (this.collisionWidth+other.collisionWidth)*(this.collisionWidth+other.collisionWidth);
	}
}
