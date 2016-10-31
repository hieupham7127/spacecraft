package library;

public class IPhysics {

	public static float distance(Coordinate p1, Coordinate p2) { return distance(p1.x, p1.y, p2.x, p2.y); }
	public static float distance(Coordinate p1, float x2, float y2) { return distance(p1.x, p1.y,x2, y2); }
	public static float distance(float x1, float y1, Coordinate p2) { return distance(x1, y1, p2.x, p2.y); }
	public static float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
	}
	
	
	
}
