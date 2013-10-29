package com.example.voicedemo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
	private Button btnUpper=null;
	private Button btnLower=null;
	private ToggleButton tbMute=null;
	private MediaPlayer mediaPlayer=null;
	private AudioManager audioManager=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		audioManager=(AudioManager)getSystemService(Service.AUDIO_SERVICE);
		btnPlay=(Button)findViewById(R.id.button1);
		btnUpper=(Button) findViewById(R.id.button2);
		btnLower=(Button) findViewById(R.id.button3);
		tbMute=(ToggleButton)findViewById(R.id.toggleButton1);
		btnPlay.setOnClickListener(listener);
		btnUpper.setOnClickListener(listener);
		btnLower.setOnClickListener(listener);
		tbMute.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC,!isChecked);	
			}
		});
	}
	View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			Button btn=(Button)v;
			switch(v.getId()){
			case R.id.button1:
				mediaPlayer=MediaPlayer.create(AudioActivity.this, R.raw.music);
				mediaPlayer.setLooping(true);
				mediaPlayer.start();
				break;
			case R.id.button2:
				audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
				break;
			case R.id.button3:
			    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
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
