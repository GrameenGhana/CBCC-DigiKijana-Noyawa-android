package org.cbccessence.noyawa.noyawaonthego.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.SettingMenuBaseAdapter;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHelper;

import java.io.File;

public class LanguageSettingsActivity extends AppCompatActivity implements OnItemClickListener {

	private RadioButton languageEnglishButton;
	private RadioButton languageEweButton;
	private RadioGroup languageOptions;
	private ListView listView;
	private ImageButton dialogButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings_menu);
	    listView=(ListView) findViewById(R.id.listView1);
	    listView.setOnItemClickListener(this);
	    String[] values={"Language options","Register Client"};
	    int[] images={R.drawable.language,
	    			 R.drawable.ic_people};
	    SettingMenuBaseAdapter adapter=new SettingMenuBaseAdapter(LanguageSettingsActivity.this,values,images);
	    listView.setAdapter(adapter);
	}

	
	
	public void Dialog(){
		final Dialog dialog = new Dialog(LanguageSettingsActivity.this);
		dialog.setContentView(R.layout.language_settings_activity);
		dialog.setTitle("Select a language");

	    
	    languageOptions=(RadioGroup) dialog.findViewById(R.id.language_options);
	    languageEnglishButton=(RadioButton) dialog.findViewById(R.id.language_english);
	    languageEweButton=(RadioButton) dialog.findViewById(R.id.language_ewe);
        
		dialogButton =(ImageButton) dialog.findViewById(R.id.imageButton1);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if(languageOptions.getCheckedRadioButtonId()==R.id.language_english){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "English");
					prefsEditor.commit();
					 LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_ewe){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Ewe");
					prefsEditor.commit();
					 LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_dagaare){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Dagaare");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_dagbani){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Dagbani");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_gonja){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Gonja");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_dangme){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Dangme");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_kassem){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Kasim");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_hausa){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Hausa");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}else if(languageOptions.getCheckedRadioButtonId()==R.id.language_twi){
					SharedPreferences myPrefs = LanguageSettingsActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putString("option", "Twi");
					prefsEditor.commit();
					LanguageSettingsActivity.this.finish();
				}

			}
		});
				dialog.show();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_all, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.action_logout:
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LanguageSettingsActivity.this);

				// set title
				alertDialogBuilder.setTitle("Logout");

				// set dialog message
				alertDialogBuilder
						.setMessage("You will be logged out.Continue?")
						.setCancelable(false)
						.setIcon(R.drawable.ic_warning)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								getBaseContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
								String filePath = getApplicationContext().getFilesDir().getPath()+"/"+"shared_prefs/loginPrefs.xml";
								//File deletePrefFile = new File(filePath );
								//deletePrefFile.delete();
								dialog.cancel();
								Intent intent=new Intent(LanguageSettingsActivity.this, WelcomeActivity.class);
								startActivity(intent);
								LanguageSettingsActivity.this.finish();

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

				return true;

			case R.id.action_home:
				Intent goHome = new Intent(Intent.ACTION_MAIN);
				goHome.setClass(LanguageSettingsActivity.this, MenuActivity.class);
				goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(goHome);
				finish();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch(position){
		case 0:
			Dialog();
			break;
			
		case 1:
			Intent mainIntent = new Intent(LanguageSettingsActivity.this,NewClientRegistrationActivity.class);
			LanguageSettingsActivity.this.startActivity(mainIntent);
			LanguageSettingsActivity.this.finish();

			 
			break;
		case 2:
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					LanguageSettingsActivity.this);

			// set title
			alertDialogBuilder.setTitle("Logout");

			// set dialog message
			alertDialogBuilder
					.setMessage("You will be logged out.Continue?")
					.setCancelable(false)
					.setIcon(R.drawable.ic_warning)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							getBaseContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
							String filePath = getApplicationContext().getFilesDir().getPath()+"/"+"shared_prefs/loginPrefs.xml";
							File deletePrefFile = new File(filePath );
							//deletePrefFile.delete();
							dialog.cancel();
							Intent intent=new Intent(LanguageSettingsActivity.this, WelcomeActivity.class);
							startActivity(intent);
							LanguageSettingsActivity.this.finish();

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


			break;
		}
		
	}
	

}
