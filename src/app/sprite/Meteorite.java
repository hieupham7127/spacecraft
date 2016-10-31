package app.sprite;

import immortal.spacecraft.R;
import library.Coordinate;
import library.IBitmap;
import library.ISprite;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Meteorite extends ISprite { // square shape
	private static Bitmap[] BITMAPS; 

	public Meteorite(Context context, String type) { super(context, type); }
	
	void createObject() {
		if (BITMAPS == null)
		BITMAPS = new Bitmap[] {
				IBitmap.getBitmap(context, R.drawable.flaming_meteorite),
				IBitmap.getBitmap(context, R.drawable.meteorite)
			};
		if (type.equals("big")) {
			bitmap = BITMAPS[0];
			size = provider.CELL;
			life = 2;
		}
		else if (type.equals("small")){
			bitmap = BITMAPS[1];;
			size = Coordinate.divide(provider.CELL, 1.5f);
			life = 1;
		}
		
	}
	
	protected void init() {
		createObject();
		int sign = (RAND.nextInt(2) == 0) ? -1 : 1;
		try {
			if (sign > 0)
				pos = new Coordinate(RAND.nextFloat() * (panel.x / 2), 0);
			else pos = new Coordinate(RAND.nextFloat() * (panel.x / 2) - 1 + panel.x / 2, 0);
			setMotion(sign * (RAND.nextInt(5)), RAND.nextInt(7) + provider.CELL.y * 0.1f);

		} catch(Exception e) { }
	}
	
	@Override 
	public int Collide(ISprite obj) { 
		if (obj instanceof Bullet || obj instanceof Spaceship) {
			if (obj instanceof Bullet) life -= obj.getLife();
			if (obj instanceof Spaceship) life = 0;
			if (life < 1) alive = false;
			else if (type.equals("big")) {
				type = "small";
				createObject();
			}
		}
		return 0;
	}

}
