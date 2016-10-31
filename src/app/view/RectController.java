package app.view;

import library.Coordinate;
import library.IProvider;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import app.sprite.Spaceship;

public class RectController extends View {

	final Coordinate BASE = new Coordinate(3, 4);

	private MapView map;
	private Paint paint;
	private IProvider provider;

	final Coordinate frame;
	final Coordinate pos;
	private int color_screen;
	
	private Coordinate screen;
	private Coordinate touch;
	private Coordinate rect_touch;
	private int color_touch;
	
	public RectController(Context context, AttributeSet atts) {
		super(context, atts); 
		this.paint = new Paint();
		this.provider = new IProvider(context);
		this.screen = Coordinate.divide(Coordinate.substract(provider.targetScreen, provider.CELL), BASE);
		this.frame = Coordinate.multiply(screen, 1.5f);
		this.pos = Coordinate.multiply(screen, 0.25f);
		this.touch = new Coordinate(screen.x * 0.5f, screen.y * 0.5f);
    	rect_touch = Coordinate.multiply(provider.CELL, 0.5f);
		initCanvasState();
	}

	void initCanvasState() {
		color_screen = Color.TRANSPARENT;
		color_touch = Color.WHITE;
	}
	

	void setSpaceshipDisplacement(Coordinate pos) { map.getMode().getSpaceship().setPosition(pos.x, pos.y); }

	public void setMapView(MapView map) { 
		this.map = map;
		invalidate();
	}
	
	@Override 
	protected void onDraw(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(color_screen);
		canvas.drawRect(pos.x, pos.y, pos.x + screen.x, pos.y + screen.y, paint);
		
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2.0f);
		canvas.drawRect(pos.x, pos.y, pos.x + screen.x, pos.y + screen.y, paint);
		paint.setStyle(Style.FILL);
		paint.setColor(color_touch);
		canvas.drawRect(pos.x + touch.x - rect_touch.x, pos.y + touch.y - rect_touch.y, pos.x + touch.x + rect_touch.x, pos.y + touch.y + rect_touch.y, paint);

        setSpaceshipDisplacement(Coordinate.multiply(touch, BASE));
	}
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	//frame = new Coordinate(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension((int) frame.x, (int) frame.y);     
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = MotionEventCompat.getActionMasked(event); 
        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        float dx = MotionEventCompat.getX(event, pointerIndex) - pos.x; 
        float dy = MotionEventCompat.getY(event, pointerIndex) - pos.y; 
		switch (action) {
		    case MotionEvent.ACTION_DOWN: {
		        if (dx < touch.x - rect_touch.x || dx > touch.x + rect_touch.x 
		        		|| dy < touch.y - rect_touch.y || dy > touch.y + rect_touch.y) return false;
		        // Finger presses
		    	break;
		    }
		    case MotionEvent.ACTION_MOVE: { 
		    	// Finger moves
		    	color_screen = Color.parseColor("#DDDDDDDD");
		    	if (dx < 0) dx = 0;
		    	if (dy < 0) dy = 0;
		    	if (screen.x <= dx && screen.y > dy) touch.setYCoordinate(dy);
		    	else if (screen.x > dx && screen.y <= dy) touch.setXCoordinate(dx);
		    	else if (screen.x > dx && screen.y > dy) touch.setCoordinate(dx, dy);
		        invalidate();
		        break;
		    }
		    default: {
		    	initCanvasState();
		    	invalidate();
		    	return false;
		    }
		}
		return true;
	}

}
