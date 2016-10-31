package library;

import immortal.spacecraft.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;

public class IBitmap {
	
	public static void recyclingBitmap(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled()) return;
		bitmap.recycle();
		bitmap = null;
	}
	
	public static Bitmap crop(Bitmap bitmap) {
		int minWidth = bitmap.getWidth();
		int minHeight = bitmap.getHeight();
		int maxWidth = 0;
		int maxHeight = 0;
		int j;
		for (int i = 0; i < bitmap.getWidth(); i++) 
			for (j = 0; j < bitmap.getHeight(); j++)
				if (bitmap.getPixel(i, j) != Color.TRANSPARENT) {
					minWidth = Math.min(minWidth, i);
					maxWidth = Math.max(maxWidth, i);
					minHeight = Math.min(minHeight, j);
					maxHeight = Math.max(maxHeight, j); 
				}
			
		return Bitmap.createBitmap(bitmap, minWidth, minHeight, maxWidth - minWidth, maxHeight - minHeight);
	}
	
	public static Bitmap rotate(Bitmap bitmap, float degree) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		/*
		final double R = Math.sqrt(Math.pow(bitmap.getWidth() / 2, 2) + Math.pow(bitmap.getHeight() / 2, 2));
		final double x = bitmap.getWidth();
		final double y = bitmap.getHeight();
		double x1 = x * Math.cos(Math.toRadians(degree)) + y * Math.sin(Math.toRadians(degree));
		double y1 = y * Math.cos(Math.toRadians(degree)) + x * Math.sin(Math.toRadians(degree));
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) x, (int) y, matrix, true);
		return Bitmap.createScaledBitmap(bitmap, (int) Math.abs(x1), (int) Math.abs(y1), false);*/
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	public static Bitmap getBitmap(Context context, int drawable) {
		return BitmapFactory.decodeResource(context.getResources(), drawable);
	}

	public static Bitmap getBitmap(Context context, int drawable, int width, int height) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
		return Bitmap.createScaledBitmap(bitmap, width, height, false);
	}
	
}
