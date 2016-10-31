package app.sprite;

import library.IBitmap;
import library.Coordinate;
import library.ISprite;
import immortal.spacecraft.R;
import android.content.Context;
import android.graphics.Bitmap;

public class Bullet extends ISprite {
	private static Bitmap[] BITMAPS; 
	public float VELOCITY_STANDARD;
	
	public Bullet(Context context, String type) { super(context, type);	}
	
	protected void init() {
		VELOCITY_STANDARD = -provider.CELL.y / 3;
		size = Coordinate.divide(provider.CELL, 1.5f);
		velocity = new Coordinate(0, VELOCITY_STANDARD);
		
		if (BITMAPS == null)
		BITMAPS = new Bitmap[] {
			IBitmap.getBitmap(context, R.drawable.bullet_b)
		};
		
		if (type.equals("A")) {
			bitmap = BITMAPS[0];//IBitmap.getBitmap(context, R.drawable.bullet_b, (int) size.x, (int) size.y);
			life = 1;
		}
	}
	
	@Override 
	public int Collide(ISprite obj) { 
		if (obj instanceof Meteorite) {
			alive = false;
			return RAND.nextInt(10) + 25;
		}
		return 0;
	}

} // ends class

