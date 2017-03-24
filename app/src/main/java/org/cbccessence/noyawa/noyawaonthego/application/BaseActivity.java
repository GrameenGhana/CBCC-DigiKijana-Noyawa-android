package org.cbccessence.noyawa.noyawaonthego.application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.MenuActivity_Updated;
import org.cbccessence.noyawa.noyawaonthego.activity.WelcomeActivity;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHelper;
import org.cbccessence.noyawa.noyawaonthego.fragments.YouthSexualHealthFragment;
import org.cbccessence.noyawa.noyawaonthego.model.Content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
	Context mContext;


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_all, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	 Intent goHome = new Intent(Intent.ACTION_MAIN);
	 	          goHome.setClass(BaseActivity.this, MenuActivity_Updated.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        	
        case R.id.action_logout:
        	logout();
        	
        	return true;

			case android.R.id.home:
				this.finish();

				return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	
	
	}
	
	public void logout() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				BaseActivity.this, R.style.AppDialog);
 
			// set title
			alertDialogBuilder.setTitle("Logout");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You will be logged out.Continue?")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						 getBaseContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
						 String filePath = getApplicationContext().getFilesDir().getPath()+"/"+"shared_prefs/loginPrefs.xml";
						 File deletePrefFile = new File(filePath );
						  deletePrefFile.delete();
						  dialog.cancel();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
						prefs.edit().clear().apply();
						 Intent intent=new Intent(BaseActivity.this, WelcomeActivity.class);
							startActivity(intent);
							BaseActivity.this.finish();
						
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						  dialog.cancel();
						
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			
	}



	public static List<Content> getAssetFromLocation(String sub_dir) {

		List<Content> docs = new ArrayList<>();


		File folder = new File(Noyawa.ROOT_DIR + File.separator + sub_dir + File.separator);

		Log.i("Does folder file exist", "File you are looking for is in " + folder);

		if (!folder.exists()) {
			Boolean status = folder.mkdirs();


			Log.i("Directory", "created?  " + status);
			return null;


		} else { //folder already exist. search for file, if file, open else download

			String filename;
			String location;

			File[] listOfFiles = folder.listFiles();


			for (File listOfFile : listOfFiles) {

				if (!listOfFile.isDirectory()) {
					Content _doc = new Content();

					filename = listOfFile.getName();
					location = listOfFile.getAbsolutePath();

					Log.i("Files in Directory", folder + "\tName\t" + filename + "\tAbsolute path\t" + location);

					_doc.setDocName(filename);
					_doc.setDocLoc(location);

					docs.add(_doc);
				}
			}

			return docs;
		}
	}


	public void getSubSectionNamesFromLocation(Context c, String sub_dir, String activity_name) {

		 DatabaseHandler dbh = new DatabaseHandler(c);


		File folder = new File(Noyawa.ROOT_DIR + File.separator + sub_dir + File.separator);

		Log.i("Does folder file exist", "File you are looking for is in " + folder);

		if (!folder.exists()) {
			Boolean status = folder.mkdirs();


			Log.i("Directory", "created?  " + status);


		} else { //folder already exist. search for file, if file, open else download

			String filename, secName = "";

			File[] listOfFiles = folder.listFiles();


			for (File listOfFile : listOfFiles) {

				if (!listOfFile.isDirectory()) {

					filename = listOfFile.getName();
					int pos = filename.lastIndexOf("_");

					if (pos != -1) secName = filename.substring(0, pos);

					Log.i("Files in Directory", folder + "\tName\t" + secName + "\tActivity name\t" + activity_name);


					if (!dbh.doesSubSecNameExist(secName, activity_name))
						dbh.insertSubSection(secName, activity_name);
					else  Log.i("SubSecName", "A file with the subsec name " + secName + " already exist! skipping ...");


				}
			}


		}
	}


	public static boolean doesSubSecNameExistInFolder(Context c, String sub_dir, String sub_secName) {

		Boolean status = null;
		 DatabaseHandler dbh = new  DatabaseHandler(c);


		File folder = new File(Noyawa.ROOT_DIR + File.separator + sub_dir + File.separator);

		Log.i("Does folder file exist", "File you are looking for is in " + folder);

		if (folder.exists()) { //folder already exist. search for file, if file, open else download

			String filename;
			File[] listOfFiles = folder.listFiles();
			for (File listOfFile : listOfFiles) {

				if (!listOfFile.isDirectory()) {

					filename = listOfFile.getName();

					if (filename.contains(sub_secName)) {
						Log.i("SubSectionChecker", "TRUE");
						return true;

					}
				}
			}
		} else return false;

		return false;
	}


	public static List<Content> getAudioList(String sub_dir, String subSecName) {

		List<Content> docs = new ArrayList<>();


		File folder = new File(Noyawa.ROOT_DIR + File.separator + sub_dir + File.separator);

		Log.i("Does folder file exist", "File you are looking for is in " + folder);

		if (!folder.exists()) {
			Boolean status = folder.mkdirs();


			Log.i("Directory", "created?  " + status);
			return null;


		} else { //folder already exist. search for file, if file, open else download

			String filename;
			String _name = "";
			String location;

			File[] listOfFiles = folder.listFiles();


			for (File listOfFile : listOfFiles) {

				if (!listOfFile.isDirectory()) {
					Content _doc = new Content();

					filename = listOfFile.getName();
					location = listOfFile.getAbsolutePath();

					if(filename.contains(subSecName)) {
						int pos = filename.lastIndexOf("_");

						if (pos != -1) _name = filename.substring(pos + 1);
						else _name = filename;


						Log.i("Files in Directory", folder + "\tName\t" + _name + "\tAbsolute path\t" + location);

						_doc.setDocName(_name);
						_doc.setDocLoc(location);

						docs.add(_doc);

					}else Log.i("Get audio", "File wasn't found!");
				}
			}

			return docs;
		}
	}

	public static void getSubSectionNamesFromLocation(YouthSexualHealthFragment youthSexualHealthFragment, String dir, String tag) {

	}
}
