package app.sprite;

import java.util.ArrayList;

import library.Coordinate;
import library.ISprite;
import immortal.spacecraft.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BackGround extends ISprite { // square shape
	private ArrayList<Bitmap> list; // Frames List
	
	public BackGround(Context context) { super(context); }
	
	@Override
	protected void init() {
		size = panel;
		list = new ArrayList<Bitmap>();
		velocity = new Coordinate(0, provider.scaleHeight(1));
		list.add(bitmapTackling(R.drawable.galaxy8));
		list.add(bitmapTackling(R.drawable.galaxy8));
	}
	
	public Bitmap bitmapTackling(int drawable) {
		bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
		return Bitmap.createScaledBitmap(bitmap, (int) size.x, (int) Math.max(size.y, bitmap.getHeight() / bitmap.getWidth() * size.x), false);
	}
		
	@Override
	public void draw(Canvas canvas) {
		bitmap = Bitmap.createBitmap(list.get(0), (int) pos.x, (int) pos.y, (int) size.x, (int) Math.min(size.y, list.get(0).getHeight() - pos.y));
		canvas.drawBitmap(bitmap, 0f, 0f, null);
		if (size.y <= bitmap.getHeight()) return; // second background
		Bitmap bitmap_next = Bitmap.createBitmap(list.get(1), 0, 0, (int) size.x, (int) size.y - bitmap.getHeight());
		canvas.drawBitmap(bitmap_next, 0f, bitmap.getHeight(), null);
	}

	@Override
	public void moving() {
		pos.y = (pos.y + velocity.y < list.get(0).getHeight()) ? pos.y + velocity.y : 0;
		if (pos.y == 0) {
			list.add(list.get(0));
			list.remove(0);
			return;
		}
	}

}
