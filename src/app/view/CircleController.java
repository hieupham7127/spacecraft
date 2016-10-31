package app.view;

import library.Coordinate;
import library.IPhysics;
import library.IProvider;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import app.activities.GameActivity;

public class CircleController extends View {

	final Coordinate frame;
	final Coordinate center;

	private MapView map;
	private Paint paint;
	private IProvider provider;
	
	private float radius_circle;
	private float radius_touch;
	private int color_circle;
	private int color_touch;
	private Coordinate touch;
	
	public CircleController(Context context, AttributeSet atts) {
		super(context, atts);
		this.paint = new Paint();
		this.provider = new IProvider(context);
		final float edge = provider.CELL.x * 3 / 2;
		
		this.radius_circle = edge;
		this.radius_touch = edge / 2;
		this.frame = new Coordinate(edge * 5 / 2, edge * 5 / 2);
		this.center = Coordinate.divide(frame, 2);
		
		initCanvasState();
	}

	void initCanvasState() {
		color_circle = Color.TRANSPARENT;
		color_touch = Color.WHITE;
		touch = new Coordinate(center.x, center.y);
	}
	

	void setSpaceshipMotion(float vx, float vy, float acceleration) {
		map.getMode().getSpaceship().setVelocity(vx, vy);
		map.getMode().getSpaceship().setAcceleration(acceleration);
	}

	public void setMapView(MapView map) { 
		this.map = map; 
	}
	
	@Override 
	protected void onDraw(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2.0f);
		paint.setColor(color_circle);
		canvas.drawCircle(center.x, center.y, radius_circle, paint);
		
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(center.x, center.y, radius_circle, paint);
		
		paint.setStyle(Style.FILL);
		paint.setColor(color_touch);
		canvas.drawCircle(touch.x, touch.y, radius_touch, paint);

        float vx, vy, acceleration = 0;
        float dx = touch.x, dy = touch.y;
        vx = (dx < center.x ? -1 : 1) * Math.abs(dx - center.x) / radius_circle * provider.CELL.x * 3 / 8;
		vy = (dy < center.y ? -1 : 1) * Math.abs(dy - center.y) / radius_circle * provider.CELL.y * 3 / 8;
        setSpaceshipMotion(vx, vy, acceleration);
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
		switch (action) {
		    case MotionEvent.ACTION_DOWN: { 
		        // Finger presses
		    	break;
		    }
		    case MotionEvent.ACTION_MOVE: { 
		    	// Finger moves
		        final float dx = MotionEventCompat.getX(event, pointerIndex); 
		        final float dy = MotionEventCompat.getY(event, pointerIndex);
		    	color_circle = Color.parseColor("#DDDDDDDD");
		    	//color_touch = Color.parseColor("#");
		    	if (IPhysics.distance(dx, dy, center) > radius_circle) {
		    		
		    	}
		    	else touch.setCoordinate(dx, dy);
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
