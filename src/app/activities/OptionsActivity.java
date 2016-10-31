package app.activities;

import immortal.spacecraft.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

public class OptionsActivity extends Activity {
	
	private Spinner spMode;
	private SeekBar sbMusic;
	private SeekBar sbSound;
	private Button btBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_options);
		// Spinner Mode 
		spMode = (Spinner) findViewById(R.id.spMode);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mode, R.layout.spinner_textview);  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spMode.setAdapter(adapter);
		spMode.setOnItemSelectedListener(null);
		
		// SeekBar Music and Sound
		sbMusic = (SeekBar) findViewById(R.id.sbMusic);
		sbSound = (SeekBar) findViewById(R.id.sbSound);
		
		// Button Back
		btBack = (Button) findViewById(R.id.btBack);
		btBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
			}
			
		});
	}
	
}
