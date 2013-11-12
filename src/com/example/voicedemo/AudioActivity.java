package com.example.voicedemo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class AudioActivity extends Activity {

	protected static final Context AudioActivity = null;
	private Button btnPlay=null;
	private Button btnStop=null;
	private Button btnStopService=null;
	private ToggleButton tbMute=null;
	private MediaPlayer mediaPlayer=null;
	private AudioManager audioManager=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		audioManager=(AudioManager)getSystemService(Service.AUDIO_SERVICE);
		btnPlay=(Button)findViewById(R.id.button1);
		btnStop=(Button) findViewById(R.id.button4);
		btnStopService=(Button) findViewById(R.id.button5);
		tbMute=(ToggleButton)findViewById(R.id.toggleButton1);
		btnPlay.setOnClickListener(listener);
		btnStop.setOnClickListener(listener);
		btnStopService.setOnClickListener(listener);
		tbMute.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC,!isChecked);	//¿ØÖÆÊÇ·ñÎª¾²Òô
			}
		});
	}
	View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			Button btn=(Button)v;
			Intent intent=new Intent(AudioActivity.this,MusicService.class);
			switch(v.getId()){
			case R.id.button1:				
				intent.putExtra("key", 0);
				Log.i("ServiceActivity", "start");
				startService(intent);
				mediaPlayer=MediaPlayer.create(AudioActivity.this, R.raw.music);
				mediaPlayer.setLooping(true);
				mediaPlayer.start();
				break;
			case R.id.button5:			
				Log.i("ServiceActivity", "stopSercice");
			//	intent.putExtra("key", 1);
				stopService(intent);
				break;
			case R.id.button4:	
				Log.i("ServiceActivity", "stopMusic");
				mediaPlayer.stop();
				break;
					}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
