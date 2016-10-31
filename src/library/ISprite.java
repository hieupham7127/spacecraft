package library;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.widget.ImageView;

public abstract class ISprite extends ImageView {

	protected final Random RAND = new Random();
	protected IProvider provider;
	
	protected Context context;
	protected Bitmap bitmap;
	protected Coordinate panel;
	protected Coordinate size;
	protected Coordinate pos = new Coordinate(0f, 0f);
	protected Coordinate velocity = new Coordinate(0f, 0f);
	protected float acceleration = 0;
	
	protected String name;
	protected String type; 
	protected int life;
	protected boolean alive = true;
	
	public ISprite(Context context) {
		super(context);
		create(context);
	}
	
	public ISprite(Context context, int drawable) {
		super(context);
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
		create(context); // needs to be below all other variables because init() is called inside this method
	}
	
	public ISprite(Context context, Coordinate pos) {
		super(context);
		this.pos = pos;
		create(context);
	}

	public ISprite(Context context, String type) {
		super(context);
		this.type = type;
		create(context);
	}
	
	public ISprite(Context context, Coordinate pos, String type) {
		super(context);
		this.pos = pos;
		this.type = type;
		create(context);
	}
	
	public ISprite(Context context, Coordinate pos, String type, int life) {
		super(context);
		this.pos = pos;
		this.type = type;
		this.life = life;
		create(context);
	}
	
	void create(Context context) {
		this.context = context;
		provider = new IProvider(context);
		panel = provider.targetScreen;
		init();
	}
	
	abstract protected void init();
	
	public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
	public Bitmap getBitmap() { return bitmap; }

	public void draw(Canvas canvas) {
		if (!valid()) return;
		if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0) return;
		bitmap = Bitmap.createScaledBitmap(bitmap, (int) size.x, (int) size.y, false);
		canvas.drawBitmap(bitmap, pos.x, pos.y, null);
	}

	public void setLife(int life) { this.life = life; }
	public int getLife() { return life; }
	
	public boolean isAlive() { return alive; }
	
	public Coordinate getPanel() { return panel; }
	
	public Coordinate getSize() { return size; }

	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setType(String type) { this.type = type; }
	public String getType() { return type; }

	public void setMotion(float vx, float vy) { velocity = new Coordinate(vx, vy); }
	public void setVelocity(Coordinate velocity) { this.velocity = velocity; }
	public void setVelocity(float vx, float vy) { velocity = new Coordinate(vx, vy); }
	public void setVelocityX(float vx) { this.velocity.x = vx; }
	public void setVelocityY(float vy) { this.velocity.y = vy; }
	public Coordinate getVelocity() { return velocity; }
	public float getVelocityX() { return velocity.x; }
	public float getVelocityY() { return velocity.y; }
	
	public void setAcceleration(float acceleration) {	this.acceleration = acceleration; }
	public float getAcceleration() { return acceleration; }

	public void setPosition(Coordinate pos) { this.pos = pos; }
	public void setPosition(float x, float y) { this.pos = new Coordinate(x, y); }
	public Coordinate getPosition() { return pos; }
	public float getX() { return pos.x; }
	public float getY() { return pos.y; }
	
	public float getXCoordinate() { return (velocity.x < 0) ? pos.x :  pos.x + size.x; }
	public float getYCoordinate() { return (velocity.y < 0) ? pos.y :  pos.y + size.y; }
	
	public boolean checkX(float x) { return !(x < 0 || x >= panel.x); }
	public boolean checkY(float y) { return !(y < 0 || y >= panel.y); }
	
	public boolean check(float x, float y) { return (checkX(x) && checkY(y)); }
	public boolean valid() { return check(getXCoordinate(), getYCoordinate()); }
	public boolean isDestinationXValid() { return checkX(velocity.x + getXCoordinate()); }
	public boolean isDestinationYValid() { return checkY(velocity.y + getYCoordinate()); }
	public boolean isDestinationValid() { return check(velocity.x + getXCoordinate(), velocity.y + getYCoordinate()); }
	
	static boolean isOverlapped(Coordinate p1, Coordinate size, Coordinate p2) {
		return (p1.x <= p2.x && p2.x <= p1.x + size.x) 
				&& (p1.y <= p2.y && p2.y <= p1.y + size.y);
	}

	public boolean isOverlapped(ISprite obj) {
		return (isOverlapped(pos, size, obj.getPosition()) 
				|| isOverlapped(pos, size, new Coordinate(obj.getX() + obj.getSize().x, obj.getY() + obj.getSize().y))
				|| isOverlapped(obj.getPosition(), obj.getSize(), new Coordinate(pos.x, pos.y + size.y))
				|| isOverlapped(obj.getPosition(), obj.getSize(), new Coordinate(pos.x + size.x, pos.y)));
	}

	public int Collide(ISprite obj) { return 0; }
	
	public void moving() {
		if (!isDestinationValid()) return;
		pos.add(velocity);
		velocity.add(acceleration);
	}
	
	@Override	
	public String toString() {
		return String.format("%s\nSize:%s\nVelocity:%s\n", 
				pos.toString() + "[" + (pos.x + size.x) + ":" + (pos.y + size.y) + "]",
				size.toString(),
				velocity.toString());
	}	

}
