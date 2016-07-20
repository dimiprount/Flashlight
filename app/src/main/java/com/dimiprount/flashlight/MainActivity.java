package com.dimiprount.flashlight;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity implements OnClickListener {

	Button bSwitch_On, bSwitch_Off;
	public Camera cam;
	public Parameters params;
	SoundPool sp;
	int sound = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		bSwitch_On = (Button) findViewById(R.id.bswitchon);
		bSwitch_Off = (Button) findViewById(R.id.bswitchoff);

		bSwitch_On.setOnClickListener(this);
		bSwitch_Off.setOnClickListener(this);

		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // For short clips

		sound = sp.load(this, R.raw.sound_on_off, 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Start the camera pushing the icon button
	@Override
	public void onAttachedToWindow() {

		try {
			cam = Camera.open();
			params = cam.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			cam.setParameters(params);
			cam.startPreview();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//  Stop the camera pushing the back button
	@Override
	public void onBackPressed() { // If not, camera will close a few seconds after pushing the back button
		closeApp();
	}

	// Stop the camera pushing any button to leave this class
	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
		super.onUserLeaveHint();
		closeApp();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
			case R.id.bswitchon:

				if (cam == null || params == null) {
					try {
						cam = Camera.open();
						params = cam.getParameters();
						params.setFlashMode(Parameters.FLASH_MODE_TORCH);
						cam.setParameters(params);
						cam.startPreview();
						if (sound != 0)
							sp.play(sound, 1, 1, 0, 0, 1);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;

			case R.id.bswitchoff:
				if (cam != null && params != null) {
					try {
						params = cam.getParameters();
						params.setFlashMode(Parameters.FLASH_MODE_OFF);
						cam.setParameters(params);
						cam.stopPreview();
						cam.release();
						cam = null;
						if (sound != 0)
							sp.play(sound, 1, 1, 0, 0, 1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case android.R.id.home:
				closeApp();
		}
		return super.onOptionsItemSelected(item);
	}

	private void closeApp() {
		// TODO Auto-generated method stub
		try {
			params = cam.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			cam.setParameters(params);
			cam.stopPreview();
			cam.release();
			cam = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.finish();
	}

}