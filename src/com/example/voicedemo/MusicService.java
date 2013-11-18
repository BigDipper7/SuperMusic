package com.example.voicedemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MusicService extends Service {

	//private boolean isRunning=false;
//    private boolean isRecording=false;
	private MediaPlayer mediaPlayer=null;
	private AudioManager audioManager=null;
	private MyHandler myHandler;
	private AudioControl audioControl=new AudioControl();
    private AudioCollect audioCollect=new AudioCollect();
    private boolean stop=false;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Log.d("AudioStop2","stop");
		    audioControl.stopRunning();
		    audioCollect.stopRecord();
	}
	@Override
	public boolean stopService(Intent name) {
		// TODO Auto-generated method stub
		Log.d("AudioStop","StopService");
		return super.stopService(name);

	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	//	Log.i("Service", "start");
		myHandler =new MyHandler();
//		audioControl.start();
		audioCollect.start();


	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

		   
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
			Double volume=b.getDouble("VL");
		//	String s=String.valueOf(volume);
		//	Log.i("AudioControl",s);
			audioManager=(AudioManager)getSystemService(Service.AUDIO_SERVICE);
			int current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
			Log.i("volume current",String.valueOf(current));	
			if(volume<30){
				while(current!=5){
					
					if(current<5) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
					if(current>5) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
					current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
				}
			}else
			if(volume<40){
				while(current!=6){
				
					if(current<6) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
					if(current>6) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
					current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
				}
			}else
				if(volume<50){
					while(current!=7){
					
						if(current<7) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
						if(current>7) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
						current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
					}
				}else
					if(volume<60){
						while(current!=8){
							
							if(current<8) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
							if(current>8) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
							current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
						}
					}else
						if(volume<70){
							while(current!=9){
								
								if(current<9) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
								if(current>9) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
								current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
							}
						}else
							if(volume<80){
								while(current!=10){
									
									if(current<10) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
									if(current>10) audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
									current = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
								}
							}
				
		//	switch(volume){
		//		case 0:
		//		Log.i("AudioControl","here");	
		//		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
		//		break;
		//		case 1:
		//		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);	
		//		break;
		//	}
		}
	}
	
	
	public  class AudioControl extends Thread {
	
			private   boolean isRunning;

			public void run(){
				       isRunning=true;
				       while(isRunning==true){
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
		  public void stopRunning(){
			  isRunning=false;
		  }
		}
	
   
      public class AudioCollect extends Thread{
    	  private  boolean isRecording;

		@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		super.run();
    		
    		  Log.i("AudioCollect","start");
    		  
    		 int frequency = 8000;

    	        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    	        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    	        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");
    	        
    	        // Delete any previous recording.
    	        if (file.exists())
    	          file.delete();
    	   
    	        // Create the new file.
    	        try {
    	          file.createNewFile();
    	        } catch (IOException e) {
    	          throw new IllegalStateException("Failed to create " + file.toString());
    	        }
    	        
    	        
    	        try {
    	          // Create a DataOuputStream to write the audio data into the saved file.
    	          OutputStream os = new FileOutputStream(file);
    	          BufferedOutputStream bos = new BufferedOutputStream(os);
    	          DataOutputStream dos = new DataOutputStream(bos);
    	         
    	          // Create a new AudioRecord object to record the audio.
    	          int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration,audioEncoding);
    	          AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
    	                                                    frequency, channelConfiguration,
    	                                                    audioEncoding, bufferSize);
    	          
    	          short[] buffer = new short[bufferSize];  
    	       //   Log.i("AudioCollect","prepare3");
    	          audioRecord.startRecording();
    	   
    	          isRecording = true ;
    	          while (isRecording) {
    	     //   	  Log.i("AudioCollect","prepare4");
    	        	  long v=0;
    	            int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
    	            for (int i = 0; i < bufferReadResult; i++)
    	            {
    	              dos.writeShort(buffer[i]);
    	              v+=buffer[i]*buffer[i];
    	            }
    	  //          Log.i("sp1",String.valueOf((v/(float)bufferReadResult)/1000)+"r="+String.valueOf(v));
    	            double dB = 10*Math.log10(v/(double)bufferReadResult);
    	            Log.i("DB",String.valueOf(dB));    //¼ÆËã·Ö±´
    	            Message msg=new Message();
					Bundle b=new Bundle();
					b.putDouble("VL",dB);
					msg.setData(b);
    	            myHandler.sendMessage(msg);
    	          }
    	   
    	   
    	          audioRecord.stop();
    	          dos.close();
    	        
    	        } catch (Throwable t) {
    	          Log.e("AudioRecord","Recording Failed");
    	        }
    	      }
		public void stopRecord(){
			isRecording=false;
		}
    	}
       
}
