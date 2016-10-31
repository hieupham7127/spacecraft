package app.sprite;

import immortal.spacecraft.R;
import library.Coordinate;
import library.IBitmap;
import library.ISprite;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

public class Bonus extends ISprite { // square shape
	private static String[] NAME;
	private static Bitmap[] BITMAPS;

	public Bonus(Context context) { super(context); }
	public Bonus(Context context, Coordinate pos) { super(context, pos); }	
	public Bonus(Context context, int drawable) { super(context, drawable); }
	public Bonus(Context context, String type) { super(context, type); }
	
	@Override
	public void init() {
		size = Coordinate.divide(provider.CELL, 1.5f);
		int sign = (RAND.nextInt(2) == 0) ? -1 : 1;
		try {
			if (sign > 0)
				pos = new Coordinate(RAND.nextFloat() * (panel.x / 2), 0);
			else pos = new Coordinate(RAND.nextFloat() * (panel.x / 2) - 1 + panel.x / 2, 0);
			setMotion(sign * (RAND.nextInt(5)), RAND.nextInt(7) + provider.CELL.y * 0.1f);
		} catch(Exception e) { }
		
		if (type == null) type = String.valueOf(RAND.nextInt(3));
		//bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ammunition);
		NAME = new String[]{"life", "upgrade", "equiment"};
		
		if (BITMAPS == null)
		BITMAPS = new Bitmap[] {  
				IBitmap.getBitmap(context, R.drawable.life),
				IBitmap.getBitmap(context, R.drawable.ammunition),
				IBitmap.getBitmap(context, R.drawable.bonus)
		};
		bitmap = getTypeImage();
	}
	
	@Override 
	public String getType() {
		return NAME[Integer.parseInt(type)];
	}
	
	public Bitmap getTypeImage() {
		return BITMAPS[Integer.parseInt(type)];
	}
	
	@Override 
	public int Collide(ISprite obj) { 
		alive = !(obj instanceof Spaceship);
		return 0;
	}
	
}
