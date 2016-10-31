package app.activities;

import immortal.spacecraft.R;
import library.IProvider;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import app.view.MainView;
import app.view.RandomShapeView;

public class MainActivity extends Activity {

	private IProvider provider;
	private RandomShapeView svMain;
	private Button btNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		provider = new IProvider(this);
		// Button
		btNext = (Button) findViewById(R.id.btNext);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) provider.CELL.x * 2, (int) provider.CELL.y);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		btNext.setLayoutParams(params);
		btNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), GameActivity.class));
			}
		});
		
		// MainView 
		svMain = (RandomShapeView) findViewById(R.id.svMain);
		
	}
	
	@Override
    protected void onPause() {
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }   
    
}
