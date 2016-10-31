package library;

public class Coordinate {
	public float x;
	public float y;
	
	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setCoordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setCoordinate(Coordinate point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public void setXCoordinate(float x) {
		this.x = x;
	}
	
	public float getXCoordinate() {
		return x;
	}

	public void setYCoordinate(float y) {
		this.y = y;
	}
	
	public float getYCoordinate() {
		return y;
	}
	public boolean checkCoordinate(Coordinate point) {
		if (x == point.x && y == point.y) return true;
		return false;
	}
	
	public Coordinate add(float value) {
		return add(new Coordinate(value, value));
	}
	
	public Coordinate add(Coordinate point) {
		this.setCoordinate(add(this, point));
		return this;
	}
	
	public Coordinate substract(Coordinate point) {
		this.setCoordinate(substract(this, point));
		return this;
	}

	public static Coordinate add(Coordinate p1, Coordinate p2) { return new Coordinate(p1.x + p2.x, p1.y + p2.y); }
	public static Coordinate add(Coordinate point, float value) { return new Coordinate(point.x + value, point.y + value); }
	
	public static Coordinate substract(Coordinate p1, Coordinate p2) { return new Coordinate(p1.x - p2.x, p1.y - p2.y); }
	public static Coordinate substract(Coordinate point, float value) { return new Coordinate(point.x - value, point.y - value); }
	
	public static Coordinate multiply(Coordinate p1, Coordinate p2) { return new Coordinate(p1.x * p2.x, p1.y * p2.y); }
	public static Coordinate multiply(Coordinate point, float value) { return new Coordinate(point.x * value, point.y * value); }
	
	public static Coordinate divide(Coordinate p1, Coordinate p2) { return new Coordinate(p1.x / p2.x, p1.y / p2.y); }
	public static Coordinate divide(Coordinate point, float value) { return new Coordinate(point.x / value, point.y / value); }
	
	public float distance() { return (float) Math.sqrt(x * x + y * y); }
	
	@Override
	public String toString() {
		return "[" + x + ":" + y + "]";
	}
	
	// ------- End -------
	
}
