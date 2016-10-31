package app.activities;

import immortal.spacecraft.R;
import library.Coordinate;
import library.IBitmap;
import library.IMusic;
import library.IProvider;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.sprite.Spaceship;
import app.view.CircleController;
import app.view.MapView;
import app.view.RectController;

public class GameActivity extends Activity {

	protected static final int GET_VALUES_REQUEST_ID = 1;
	private IProvider provider;
	private MapView mvMain;
	//private CircleController controller;
	private RectController controller;
	private ImageButton ibMusic;
	private IMusic music;
	private ImageButton ibSuspend;
	private TextView tvScore;
	private LinearLayout llLife;
	private ImageView ivLife;
	private TextView tvLife;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_offlinemode);
		provider = new IProvider(this);
		
		OptionsActivity options = new OptionsActivity();
		//  
		mvMain = (MapView) findViewById(R.id.GameView);
		//controller = (CircleController) findViewById(R.id.controller);
		controller = (RectController) findViewById(R.id.controller);
		controller.setMapView(mvMain);
		
		// TextView
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvScore.setTextSize((int) (provider.CELL.y / 4 * 0.95));
		tvLife = (TextView) findViewById(R.id.tvLife);
		tvLife.setTextSize((int) (provider.CELL.y / 4 * 0.95));
		
		// ImageView
		ivLife = (ImageView) findViewById(R.id.ivLife);
		ivLife.setBackgroundResource(R.drawable.spaceship1);
		ivLife.setLayoutParams(new LinearLayout.LayoutParams((int) (provider.CELL.x / 1.5f), (int) (provider.CELL.y / 1.5f)));
		
		// ImageButton
		ibMusic = (ImageButton) findViewById(R.id.ibMusic);
		ibMusic.setLayoutParams(new LinearLayout.LayoutParams((int) provider.CELL.x / 2, (int) provider.CELL.y / 2));
		ibMusic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				music.stopBackgroundMusic();
			}
		});
		
		ibSuspend = (ImageButton) findViewById(R.id.ibSuspend);
		ibSuspend.setBackgroundResource(R.drawable.options);
		ibSuspend.setLayoutParams(new LinearLayout.LayoutParams((int) provider.CELL.x / 2, (int) provider.CELL.y / 2));
		ibSuspend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mvMain.surfaceDestroyed(null);
				mvMain.surfaceCreated(null);
				/*if (mvMain.isOnPaused()) {
					mvMain.surfaceCreated(null);
					//mvMain.surfaceResumed();
					Spaceship spaceship = mvMain.getMode().getSpaceship();
					if (spaceship.getLife() < 1) spaceship.setLife(3);
				}
				else mvMain.surfacePaused();*/
			}
		});
		
		// ImageView
		ivLife = (ImageView) findViewById(R.id.ivLife);
		//ivLife.setImageBitmap(null);
		
	}
	
	public void setScore(CharSequence text) { tvScore.setText(text); }
	public void setLife(CharSequence text) { tvLife.setText(text); }
	
	@Override protected void onPause() { super.onPause(); }
    @Override protected void onResume() { super.onResume(); }   
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        	case GET_VALUES_REQUEST_ID: mvMain.surfaceResumed();
            break;
        }
    }*/

}
