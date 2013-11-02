package com.example.voicedemo;

import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MusicService extends Service {

	private MediaPlayer mediaPlayer=null;
	private AudioManager audioManager=null;
	private MyHandler myHandler;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.i("Service", "start");
		myHandler =new MyHandler();
		new AudioControl().start();
	}

	class MyHandler extends Handler{
		public MyHandler(){
		}
		public MyHandler(Looper L){
			super(L);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b=msg.getData();
			int volume=b.getInt("VL");
			String s=String.valueOf(volume);
			Log.i("AudioControl",s);
			switch(volume)
			{
				case 0:
				Log.i("AudioControl","here");
				audioManager=(AudioManager)getSystemService(Service.AUDIO_SERVICE);
				audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
				break;
				case 1:
				audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);	
				break;
			}
			stopSelf();
		}
	}
	
	
	public class AudioControl extends Thread {
	
			public void run(){
				       int i=1;
				       while(i==1){
						Log.i("AudioControl","get in the Loop");
						try{
							sleep(2000);
							
						}catch(InterruptedException e){
							e.printStackTrace();		
							}
						Message msg=new Message();
						Bundle b=new Bundle();
						b.putInt("VL", 0);
						msg.setData(b);
						myHandler.sendMessage(msg);
				       }
	            }
		}
	
}
