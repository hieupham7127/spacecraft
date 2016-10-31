package app.view;

import library.IProvider;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import app.activities.GameActivity;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {
	protected GameActivity gameActivity;
	IProvider provider;
	
	public MainView(Context context, AttributeSet atts) {
		super(context, atts);
		this.gameActivity = (GameActivity) context;
		provider = new IProvider(this.gameActivity);
		getHolder().addCallback(this);
	}
	
	@Override public void surfaceCreated(SurfaceHolder holder) { }

	@Override public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) { }
	@Override public void surfaceDestroyed(SurfaceHolder holder) { }	
	public void surfacePaused() { }
	public void surfaceResumed() { }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				return true;
			case MotionEvent.ACTION_MOVE:
				return true;
			case MotionEvent.ACTION_UP:
				return true;
			default: break;
		}
		return super.onTouchEvent(event);
	}
}
