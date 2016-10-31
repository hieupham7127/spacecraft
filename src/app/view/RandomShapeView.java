package app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RandomShapeView extends View {
	private Integer[] backgrounds = {
			
			};
	private Paint[] foregrounds = new Paint[] { 
				makePaint(Color.MAGENTA), makePaint(Color.BLUE), makePaint(Color.GREEN), makePaint(Color.RED), makePaint(Color.YELLOW)
			};
	private Bitmap[] bitmaps = {
			
			};
	private String message = "Spacecraft"; 

	public RandomShapeView(Context context) { super(context); }
	public RandomShapeView(Context context, AttributeSet attrs) { super(context, attrs); }
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//canvas.drawColor()
		int width = getWidth();
		int height = getHeight();
		int shape_width = width / 5;
		for (int i = 0; i < 20; i++) {
			drawRandomCircle(canvas, width, height, shape_width);
			drawRandomRect(canvas, width, height, shape_width);
			//drawRandomBitmap(canvas, width, height);
			drawRandomText(canvas, width, height, shape_width);
		}
	}

	private void drawRandomText(Canvas canvas, int width, int height, int shape_width) {
		// TODO Auto-generated method stub
		float x = (float) Math.random() * width;
		float y = (float) Math.random() * height;
		float textSize = (float) Math.random() * (shape_width + 1);
		Paint paint = foregrounds[(int) (Math.random() * foregrounds.length)];
		paint.setTextSize(textSize);
		canvas.drawText(message, x, y, paint);
	}

	private void drawRandomBitmap(Canvas canvas, int width, int height) {
		// TODO Auto-generated method stub
		float x = (float) Math.random() * width;
		float y = (float) Math.random() * height;
		Bitmap bitmap = bitmaps[(int) Math.random() * (bitmaps.length)];
		canvas.drawBitmap(bitmap, x, y, null);
	}

	private void drawRandomRect(Canvas canvas, int width, int height, int shape_width) {
		// TODO Auto-generated method stub
		float x = (float) Math.random() * width;
		float y = (float) Math.random() * height; 
		float x1 = x + (float) Math.random() * (shape_width + 1);
		float y1 = y + (float) Math.random() * (shape_width + 1);
		Paint paint = foregrounds[(int) (Math.random() * foregrounds.length)];
		canvas.drawRect(x, y, x1, y1, paint);
		
	}

	private void drawRandomCircle(Canvas canvas, int width, int height,	int shape_width) {
		// TODO Auto-generated method stub
		float x = (float) Math.random() * (width + 1);
		float y = (float) Math.random() * (height + 1); 
		float radius = (float) Math.random() * (shape_width / 2 + 1);
		Paint paint = foregrounds[(int) (Math.random() * foregrounds.length)];
		canvas.drawCircle(x, y, radius, paint);
	}

	private Bitmap makeBitmap(int drawable) {
		return (BitmapFactory.decodeResource(getResources(), drawable));
	}
	
	private Paint makePaint(int color) {
		Paint p = new Paint();
		p.setColor(color);
		return p;
	}
	
}
