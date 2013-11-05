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

	private boolean isRecording=false;
	private MediaPlayer mediaPlayer=null;
	private AudioManager audioManager=null;
	private MyHandler myHandler;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
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
	
    public void play() {
        // Get the file we want to playback.
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");
        // Get the length of the audio stored in the file (16 bit so 2 bytes per short)
        // and create a short array to store the recorded audio.
        int musicLength = (int)(file.length()/2);
        short[] music = new short[musicLength];
   
   
        try {
          // Create a DataInputStream to read the audio data back from the saved file.
          InputStream is = new FileInputStream(file);
          BufferedInputStream bis = new BufferedInputStream(is);
          DataInputStream dis = new DataInputStream(bis);
           
          // Read the file into the music array.
          int i = 0;
          while (dis.available() > 0) {
            music[i] = dis.readShort();
            i++;
          }
   
   
          // Close the input streams.
          dis.close();    
   
   
          // Create a new AudioTrack object using the same parameters as the AudioRecord
          // object used to create the file.
          AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                                 11025,
                                                 AudioFormat.CHANNEL_CONFIGURATION_MONO,
                                                 AudioFormat.ENCODING_PCM_16BIT,
                                                 musicLength*2,
                                                 AudioTrack.MODE_STREAM);
          // Start playback
          audioTrack.play();
       
          // Write the music buffer to the AudioTrack object
          audioTrack.write(music, 0, musicLength);
   
          audioTrack.stop() ;
   
        } catch (Throwable t) {
          Log.e("AudioTrack","Playback Failed");
        }
      }
   
      public void record() {
        int frequency = 11025;
        int v=0;
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
          int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration,  audioEncoding);
          AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                                                    frequency, channelConfiguration,
                                                    audioEncoding, bufferSize);
        
          short[] buffer = new short[bufferSize];  
          audioRecord.startRecording();
   
          isRecording = true ;
          while (isRecording) {
            int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
            for (int i = 0; i < bufferReadResult; i++)
            {
              dos.writeShort(buffer[i]);
              v+=buffer[i]*buffer[i];
            }
            Log.i("sp1",String.valueOf(v/(float)r));
          }
   
   
          audioRecord.stop();
          dos.close();
        
        } catch (Throwable t) {
          Log.e("AudioRecord","Recording Failed");
        }
      }
}
