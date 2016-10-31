package app.view;

import library.IProvider;
import library.IThread;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import app.activities.GameActivity;
import app.mode.OfflineMode;

public class MapView extends MainView {
	private OfflineMode offlineMode;
	
	public MapView(Context context, AttributeSet atts) {
		super(context, atts);
		//offlineMode = new OfflineMode(this, 1, provider.targetScreen);
	}

	@Override public void surfaceCreated(SurfaceHolder holder) { offlineMode = new OfflineMode(this, 1, provider.targetScreen); }
	@Override public void surfaceDestroyed(SurfaceHolder holder) { offlineMode.onStop(); }	
	@Override public void surfacePaused() {	offlineMode.onPause(); }
	@Override public void surfaceResumed() { offlineMode.onResume(); }
	
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
	
	public OfflineMode getMode() { return offlineMode; }
	public boolean isOnPaused() { return offlineMode.isOnPause(); }
	
	public void updateScore() {
		gameActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String s = "" + offlineMode.score;
				while (s.length() < 10) s = "0" + s;
				gameActivity.setScore(s);
				gameActivity.setLife("" + offlineMode.getSpaceship().getLife());
			}
		});
	}
}
