package com.example.voicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AudioControl extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyThread extends Thread {
		public void run(){
			
		}
	}
}
