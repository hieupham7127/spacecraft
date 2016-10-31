package app.sprite;

import java.util.ArrayList;

import library.IAnimation;
import library.IBitmap;
import library.Coordinate;
import library.ISprite;
import immortal.spacecraft.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Spaceship extends ISprite {
	final int MAX_AMMUNITION = 5;
	public static final int UP = -1;
	public static final int DOWN = 1;
	public static final int LEFT = -1;
	public static final int STAY = 0;
	public static final int RIGHT = 1;
	
	private ArrayList<Bullet> bullets;
	private ArrayList<Bitmap> bullets_bitmap;
	
	public Spaceship(Context context, Coordinate pos, String type, int life) { super(context, pos, type, life); }

	protected void init() {
		size = Coordinate.multiply(provider.CELL, 2);
		
		bullets = new ArrayList<Bullet>();
		bullets_bitmap = new ArrayList<Bitmap>();
		upgrade();
		
		if (type.equals("normal")) bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship1);
	}
	
	public void upgrade() {
		if (bullets.size() >= MAX_AMMUNITION) return;
		Bullet bullet = new Bullet(context, "A"); 
		bullets.add(bullet);
		bullets_bitmap.add(bullet.getBitmap());
	}
	
	public void degrade() {
		for (int i = 0; i < 2; i++) 
			if (bullets.size() > 1) {
				bullets.remove(bullets.size() - 1);
				bullets_bitmap.remove(bullets_bitmap.size() - 1);
			}
	}

	public ArrayList<Bullet> getBullets() {
		float degree = (bullets.size() - 1) * 10f;
		float displacement = pos.x;
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			displacement += size.x / (bullets.size() + 1);
			bullet.setPosition(displacement - bullet.getSize().x * 0.5f, 
					pos.y - bullet.getSize().y - 1);
			bullet.setVelocity(bullet.VELOCITY_STANDARD * (float) Math.sin(Math.toRadians(degree)), bullet.VELOCITY_STANDARD * (float) Math.cos(Math.toRadians(degree)));
			bullet.setBitmap(IBitmap.rotate(bullets_bitmap.get(i), -degree));
			degree -= 20f;
		}
		return bullets;
	}

	@Override
	public void moving() {
		if (!isDestinationXValid()) velocity.x = 0;
		if (!isDestinationYValid()) velocity.y = 0;
		pos.add(velocity);
		velocity.add(acceleration);
	}
	
	@Override 
	public int Collide(ISprite obj) { 
		if (obj instanceof Meteorite) {
			life--;
			degrade();
			//IAnimation.rotation(this, "spaceship", 30, 1000);
			IAnimation.fadeInOut(this, "spaceship", 1000);			
		}
		if (obj instanceof Bonus) {
			if (obj.getType().equals("life")) life++;
			if (obj.getType().equals("upgrade")) upgrade();
			if (obj.getType().equals("equipment"));
			return RAND.nextInt(25) + 50;
		}
		return 0;
	}
	
}

