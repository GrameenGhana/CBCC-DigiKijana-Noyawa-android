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


			   
			   public String[] loadAssets(String Englishlocation,String Ewelocation,
					   				  String Ewepath,String EnglishPath){
				    String[] fileslist = null;
				    ArrayList<String> f = new ArrayList<String>();
				    AssetManager assetManager = ctx.getAssets();
				    try {
				    	if(MenuActivity_Updated.getLanguage().equalsIgnoreCase("Ewe")){
				    		fileslist = assetManager.list(Ewelocation);
				    		Ewepath=Ewelocation+"/";
			        for(int i=0;i<fileslist.length;i++){
			        	f.add(fileslist[i]);
			        }
				    	}else if(MenuActivity_Updated.getLanguage().equalsIgnoreCase("English")){
				    		fileslist = assetManager.list(Englishlocation);
				    		EnglishPath=Englishlocation+"/";
				        for(int i=0;i<fileslist.length;i++){
				        	f.add(fileslist[i]);
				        }
				    	}
				    }
				    catch (Exception e) {
				        e.printStackTrace();
				    }
				    return fileslist;
			   }

	public  String[] loadFilesFromPhone(String Englishlocation,String Ewelocation,String DagbaniLocation,String DagareLocation,String DangbeLocation,String GonjaLocation,String HausaLocation,String KasimLocation,String TwiLocation){
		String[] fileslist = null;
		ArrayList<String> f = new ArrayList<String>();
		File files = null;
		String language = MenuActivity_Updated.getLanguage();
		Log.i("Audio Player ", "Language Selected is " + language);


		try {
			if (language.equalsIgnoreCase("Ewe")) {
				files = new File(Environment.getExternalStorageDirectory(), Ewelocation);
				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), Ewelocation);
				}


			} else if (language.equalsIgnoreCase("English")) {
				files = new File(Environment.getExternalStorageDirectory(), Englishlocation);
				Log.i("Audio Player ", "Files location is " + files.toString());
				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), Englishlocation);
				}


			} else if (language.equalsIgnoreCase("Dagbani")) {
				files = new File(Environment.getExternalStorageDirectory(), DagbaniLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());
				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), DagbaniLocation);
				}


			} else if (language.equalsIgnoreCase("Dagare")) {
				files = new File(Environment.getExternalStorageDirectory(), DagareLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());

				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), DagareLocation);
				}


			} else if (language.equalsIgnoreCase("Dangbe")) {
				files = new File(Environment.getExternalStorageDirectory(), DangbeLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());

				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), DangbeLocation);
				}


			} else if (language.equalsIgnoreCase("Gonja")) {
				files = new File(Environment.getExternalStorageDirectory(), GonjaLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());

				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), GonjaLocation);
				}


			} else if (language.equalsIgnoreCase("Hausa")) {
				files = new File(Environment.getExternalStorageDirectory(), HausaLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());

				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), HausaLocation);
				}


			} else if (language.equalsIgnoreCase("Kasim")) {
				files = new File(Environment.getExternalStorageDirectory(), KasimLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());

				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), KasimLocation);
				}


			} else if (language.equalsIgnoreCase("Twi")) {
				files = new File(Environment.getExternalStorageDirectory(), TwiLocation);
				Log.i("Audio Player ", "Files location is " + files.toString());

				if (!files.exists()) {
					files = new File(Environment
							.getExternalStorageDirectory(), TwiLocation);
				}


			}



				fileslist = files.list();

				for (int i = 0; i < fileslist.length; i++) {


					f.add(fileslist[i]);

					Log.i("Audio Player ", "File " + i + " added with name " + fileslist[i]);

				}


			}
			catch(Exception e){
				e.printStackTrace();
			}

		return fileslist;

	}



			   public String getFilePath(String Ewelocation,String Englishlocation,String DagbaniLocation,String DagareLocation,String DangbeLocation,String GonjaLocation,String HausaLocation,String KasimLocation,String TwiLocation){
				   String path=null;
					String lang = MenuActivity_Updated.getLanguage();

				   System.out.println("Language :" + lang);

				 	if(lang.equalsIgnoreCase("Ewe")){
				 		path=Ewelocation + File.separator;
			    	}else if(lang.equalsIgnoreCase("English")){

			    		path=Englishlocation+ File.separator;

			    	}else if(lang.equalsIgnoreCase("Dagbani")){

						path=DagbaniLocation+ File.separator;

					}else if(lang.equalsIgnoreCase("Dagare")){

						path=DagareLocation+ File.separator;

					}else if(lang.equalsIgnoreCase("Dangbe")){

						path=DangbeLocation+ File.separator;

					}else if(lang.equalsIgnoreCase("Gonja")){

						path=GonjaLocation+ File.separator;

					}else if(lang.equalsIgnoreCase("Hausa")){

						path=HausaLocation+ File.separator;

					}else if(lang.equalsIgnoreCase("Kasim")){

						path=KasimLocation+ File.separator;

					}else if(lang.equalsIgnoreCase("Twi")){

						path=TwiLocation+ File.separator;

					}


				   System.out.println("Path :" + path);
				   
				   
				   return path;
			   }
}
