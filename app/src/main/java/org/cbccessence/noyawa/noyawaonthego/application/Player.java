package org.cbccessence.noyawa.noyawaonthego.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;


import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.MenuActivity_Updated;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;

import java.io.File;
import java.util.ArrayList;

public class Player{

	MediaPlayer mediaPlayer;
	private boolean isPaused = false;
	SeekBar seek_bar;
	Handler seekHandler;
	Context ctx;
	private DatabaseHandler db;
	SharedPreferences prefs;
	
	public Player(Context context, MediaPlayer mediaplayer)
	{
		mediaplayer=new MediaPlayer();
		this.mediaPlayer=mediaplayer;
		//this.mediaPlayer.setWakeMode(ctx, PowerManager.FULL_WAKE_LOCK);

		this.ctx = context;
		db=new DatabaseHandler(ctx);
	}
	public Player(Context context)
	{
		this.ctx = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

	}
	

     public boolean isPlaying(MediaPlayer mediaplayer) {
       return mediaPlayer.isPlaying();
     }

     public void populateImages(int[] imageid,String[] list){
		 imageid=new int[list.length];  
		 
		 for(int i=0;i<list.length;i++){
			 imageid[i]= R.drawable.treble;
		 }
	   }
	   
	   public void populateSongNames(String[] songname,String[] list){
			 songname=new String[list.length];
			 
			 for(int i=0;i<list.length;i++){
				 int increment =1+i;
				 songname[i]="Message "+increment;
			 }
		   }
		public String mediaPlayerDuration(TextView audio_duration,MediaPlayer mediaPlayer){
			final int HOUR = 60*60*1000;
		    final int MINUTE = 60*1000;
		    final int SECOND = 1000;
		    String duration;
		    int durationInMillis = mediaPlayer.getDuration();
		    int durationHour = durationInMillis/HOUR;
		    int durationMint = (durationInMillis%HOUR)/MINUTE;
		    int durationSec = (durationInMillis%MINUTE)/SECOND;
		    if(durationHour>0){
				audio_duration.setText(String.format("%02d:%02d:%02d",
						durationHour, durationMint, durationSec));
				duration= String.format("%02d:%02d:%02d",
						durationHour, durationMint, durationSec);
		    }else{
		    	audio_duration.setText(String.format("%02d:%02d:%02d",
						durationHour, durationMint, durationSec));
		    	duration= String.format("%02d:%02d:%02d",
						durationHour, durationMint, durationSec);
		    
		}
		    return duration;
		}
		
		public String mediaPlayerCurrentDuration(MediaPlayer mediaPlayer){
			String duration_played;
			final int HOUR = 60*60*1000;
		    final int MINUTE = 60*1000;
		    final int SECOND = 1000;
		    int durationInMillis=mediaPlayer.getCurrentPosition();
		    int durationHour = durationInMillis/HOUR;
		    int durationMint = (durationInMillis%HOUR)/MINUTE;
		    int durationSec = (durationInMillis%MINUTE)/SECOND;
		    if(durationHour>0){
				duration_played= String.format("%02d:%02d:%02d",
						durationHour, durationMint, durationSec);
		    }else{
		    	duration_played= String.format("%02d:%02d:%02d",
						durationHour, durationMint, durationSec);
		    
		}
		    return duration_played;
		}
		
		public String messageStatus(MediaPlayer mediaPlayer){
			String message_status = null;
			int durationDifference=mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition();
			
			if(durationDifference>0){
				message_status=Noyawa.AUDIO_STATUS_NOT_COMPLETED;
			}else {
				message_status=Noyawa.AUDIO_STATUS_COMPLETED;
			}
			return message_status;
			
		} 






 }
