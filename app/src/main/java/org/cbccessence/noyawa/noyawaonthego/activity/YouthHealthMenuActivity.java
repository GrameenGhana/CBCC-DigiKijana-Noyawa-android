package org.cbccessence.noyawa.noyawaonthego.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.TrimesterListViewAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHelper;

import java.io.File;


public class YouthHealthMenuActivity extends AppCompatActivity implements OnItemClickListener {

	private ListView listView;
	private TextView header;
	private String type;
	private String submodule;
	private String module;
	private String extras;

	private ToggleButton myToggleButton;
	private TextView myToggleStatus;

	// toggle upper button (20 - 24yrs switch) checker
	private Boolean toggleUpper = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.submenu_activity);

		//get references to the text view and th toggle button
		myToggleStatus = (TextView) findViewById(R.id.toggleStatus);
		myToggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

		//attach onclick listener to the button
		myToggleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				switch (v.getId()) {
					case R.id.toggleButton1:

						//onclick check the current status of the toggle button and do necessary processing
						if(myToggleButton.isChecked()){
							myToggleStatus.setText("Currently on 20 - 24 yrs");
							toggleUpper = true;
						}
						else{
							myToggleStatus.setText("Currently on 15 - 19 yrs");
							toggleUpper = false;
						}
						break;

				}

			}
		});

		//display the current status of the button
		if(myToggleButton.isChecked()){
			myToggleStatus.setText("Currently on 20 - 24 yrs");
			toggleUpper = true;
		}
		else{
			myToggleStatus.setText("Currently on 15 - 19 yrs ");
			toggleUpper = false;
		}


		listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
	    String[] values={"STIs",
	    		"Pregnancy & Abortion",
	    		"Contraceptive",
				"Empowerment",
				"Anatomy & Physiology and WIS"};

	    int[] images={R.drawable.player_icon,
	    		R.drawable.audio_icon,
	    		R.drawable.player_icon,
				R.drawable.player_icon,
				R.drawable.player_icon};

	    header=(TextView) findViewById(R.id.textView1);
	    header.setText("Youth Sexual Health Messages");
	    Typeface custom_font = Typeface.createFromAsset(YouthHealthMenuActivity.this.getAssets(),

				"fonts/Roboto-MediumItalic.ttf");
	    header.setTypeface(custom_font);

	    TrimesterListViewAdapter adapter=new TrimesterListViewAdapter(YouthHealthMenuActivity.this, values, images);
	   listView.setAdapter(adapter);
	   listView.setOnItemClickListener(this);
	}

	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		String eweLocation;
		String englishLocation;
		String dagbaniLocation;
		String dagareLocation;
		String dangbeLocation;
		String gonjaLocation;
		String hausaLocation;
		String kasimLocation;
		String twiLocation;
		// check on upper or lower folder location
		String toggle;

		if(toggleUpper){
			toggle= "Upper";
		}else{
			toggle= "Lower";
		}


		switch (position){
		case 0:
			//intent=new Intent(YouthHealthMenuActivity.this, YouthHealthAbstinenceAudioGallery.class);
			intent=new Intent(YouthHealthMenuActivity.this, AudioGalleryActivity.class);
			type="Audio";
			submodule="STIs";
			module= Noyawa.MODULE_YOUTH_SEXUAL_HEALTH;
			extras=" ";
			 eweLocation="Noyawa/Voice Health Messages/EWE/STIs "+toggle;
			 englishLocation="Noyawa/Voice Health Messages/ENGLISH/STIs "+toggle;
			dagbaniLocation="Noyawa/Voice Health Messages/DAGBANI/STIs "+toggle;
			dagareLocation="Noyawa/Voice Health Messages/DAGARE/STIs "+toggle;
			dangbeLocation="Noyawa/Voice Health Messages/DANGBE/STIs "+toggle;
			gonjaLocation="Noyawa/Voice Health Messages/GONJA/STIs "+toggle;
			hausaLocation="Noyawa/Voice Health Messages/HAUSA/STIs "+toggle;
			kasimLocation="Noyawa/Voice Health Messages/KASIM/STIs "+toggle;
			twiLocation="Noyawa/Voice Health Messages/TWI/STIs "+toggle;


			 intent.putExtra(Noyawa.TYPE, type);
				intent.putExtra(Noyawa.SUB_MODULE, submodule);
				intent.putExtra(Noyawa.MODULE, module);
				intent.putExtra(Noyawa.EXTRAS,extras);
				intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);
				intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
			    intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
			intent.putExtra(Noyawa.DAGARE_AUDIO_LOCATION, dagareLocation);
			intent.putExtra(Noyawa.DANGBE_AUDIO_LOCATION, dangbeLocation);
			intent.putExtra(Noyawa.GONJA_AUDIO_LOCATION, gonjaLocation);
			intent.putExtra(Noyawa.HAUSA_AUDIO_LOCATION, hausaLocation);
			intent.putExtra(Noyawa.KASIM_AUDIO_LOCATION, kasimLocation);
			intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);

			startActivity(intent);
			break;

		case 1	:
			//intent=new Intent(YouthHealthMenuActivity.this, YouthHealthRapeAudioGallery.class);
			intent=new Intent(YouthHealthMenuActivity.this, AudioGalleryActivity.class);
			type="Audio";
			submodule="Pregnancy & Abortion";
			module=Noyawa.MODULE_YOUTH_SEXUAL_HEALTH;
			extras="";
			eweLocation="Noyawa/Voice Health Messages/EWE/Pregnancy&Abortion "+toggle;
			englishLocation="Noyawa/Voice Health Messages/ENGLISH/Pregnancy&Abortion "+toggle;
			dagbaniLocation="Noyawa/Voice Health Messages/DAGBANI/Pregnancy&Abortion "+toggle;
			dagareLocation="Noyawa/Voice Health Messages/DAGARE/Pregnancy&Abortion "+toggle;
			dangbeLocation="Noyawa/Voice Health Messages/DANGBE/Pregnancy&Abortion "+toggle;
			gonjaLocation="Noyawa/Voice Health Messages/GONJA/Pregnancy&Abortion "+toggle;
			hausaLocation="Noyawa/Voice Health Messages/HAUSA/Pregnancy&Abortion "+toggle;
			kasimLocation="Noyawa/Voice Health Messages/KASIM/Pregnancy&Abortion "+toggle;
			twiLocation="Noyawa/Voice Health Messages/TWI/Pregnancy&Abortion "+toggle;

			 intent.putExtra(Noyawa.TYPE, type);
				intent.putExtra(Noyawa.SUB_MODULE, submodule);
				intent.putExtra(Noyawa.MODULE, module);
				intent.putExtra(Noyawa.EXTRAS,extras);
			intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);
			intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
			intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
			intent.putExtra(Noyawa.DAGARE_AUDIO_LOCATION, dagareLocation);
			intent.putExtra(Noyawa.DANGBE_AUDIO_LOCATION, dangbeLocation);
			intent.putExtra(Noyawa.GONJA_AUDIO_LOCATION, gonjaLocation);
			intent.putExtra(Noyawa.HAUSA_AUDIO_LOCATION, hausaLocation);
			intent.putExtra(Noyawa.KASIM_AUDIO_LOCATION, kasimLocation);
			intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);

			startActivity(intent);
			break;

		case 2:
			//intent=new Intent(YouthHealthMenuActivity.this, YouthHealthTeenagePregnancyAudioGallery.class);
			intent=new Intent(YouthHealthMenuActivity.this, AudioGalleryActivity.class);
			type="Audio";
			submodule="Contraceptive";
			module=Noyawa.MODULE_YOUTH_SEXUAL_HEALTH;
			extras=" ";
			eweLocation="Noyawa/Voice Health Messages/EWE/Contraceptives "+toggle;
			englishLocation="Noyawa/Voice Health Messages/ENGLISH/Contraceptives "+toggle;
			dagbaniLocation="Noyawa/Voice Health Messages/DAGBANI/Contraceptives "+toggle;
			dagareLocation="Noyawa/Voice Health Messages/DAGARE/Contraceptives "+toggle;
			dangbeLocation="Noyawa/Voice Health Messages/DANGBE/Contraceptives "+toggle;
			gonjaLocation="Noyawa/Voice Health Messages/GONJA/Contraceptives "+toggle;
			hausaLocation="Noyawa/Voice Health Messages/HAUSA/Contraceptives "+toggle;
			kasimLocation="Noyawa/Voice Health Messages/KASIM/Contraceptives "+toggle;
			twiLocation="Noyawa/Voice Health Messages/TWI/Contraceptives "+toggle;

			 intent.putExtra(Noyawa.TYPE, type);
				intent.putExtra(Noyawa.SUB_MODULE, submodule);
				intent.putExtra(Noyawa.MODULE, module);
				intent.putExtra(Noyawa.EXTRAS,extras);
			intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);
			intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
			intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
			intent.putExtra(Noyawa.DAGARE_AUDIO_LOCATION, dagareLocation);
			intent.putExtra(Noyawa.DANGBE_AUDIO_LOCATION, dangbeLocation);
			intent.putExtra(Noyawa.GONJA_AUDIO_LOCATION, gonjaLocation);
			intent.putExtra(Noyawa.HAUSA_AUDIO_LOCATION, hausaLocation);
			intent.putExtra(Noyawa.KASIM_AUDIO_LOCATION, kasimLocation);
			intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);

			startActivity(intent);
			break;

		case 3:
				//intent=new Intent(YouthHealthMenuActivity.this, YouthHealthTeenagePregnancyAudioGallery.class);
				intent=new Intent(YouthHealthMenuActivity.this, AudioGalleryActivity.class);
				type="Audio";
				submodule="Empowerment";
				module=Noyawa.MODULE_YOUTH_SEXUAL_HEALTH;
				extras=" ";
				eweLocation="Noyawa/Voice Health Messages/EWE/Empowerment "+toggle;
				englishLocation="Noyawa/Voice Health Messages/ENGLISH/Empowerment "+toggle;
				dagbaniLocation="Noyawa/Voice Health Messages/DAGBANI/Empowerment "+toggle;
				dagareLocation="Noyawa/Voice Health Messages/DAGARE/Empowerment "+toggle;
				dangbeLocation="Noyawa/Voice Health Messages/DANGBE/Empowerment "+toggle;
				gonjaLocation="Noyawa/Voice Health Messages/GONJA/Empowerment "+toggle;
				hausaLocation="Noyawa/Voice Health Messages/HAUSA/Empowerment "+toggle;
				kasimLocation="Noyawa/Voice Health Messages/KASIM/Empowerment "+toggle;
				twiLocation="Noyawa/Voice Health Messages/TWI/Empowerment "+toggle;

				intent.putExtra(Noyawa.TYPE, type);
				intent.putExtra(Noyawa.SUB_MODULE, submodule);
				intent.putExtra(Noyawa.MODULE, module);
				intent.putExtra(Noyawa.EXTRAS,extras);
				intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);
				intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
				intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
				intent.putExtra(Noyawa.DAGARE_AUDIO_LOCATION, dagareLocation);
				intent.putExtra(Noyawa.DANGBE_AUDIO_LOCATION, dangbeLocation);
				intent.putExtra(Noyawa.GONJA_AUDIO_LOCATION, gonjaLocation);
				intent.putExtra(Noyawa.HAUSA_AUDIO_LOCATION, hausaLocation);
				intent.putExtra(Noyawa.KASIM_AUDIO_LOCATION, kasimLocation);
				intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);

				startActivity(intent);
				break;

		case 4:
				//intent=new Intent(YouthHealthMenuActivity.this, YouthHealthTeenagePregnancyAudioGallery.class);
				intent=new Intent(YouthHealthMenuActivity.this, AudioGalleryActivity.class);
				type="Audio";
				submodule="Anatomy & Physiology and WIS";
				module=Noyawa.MODULE_YOUTH_SEXUAL_HEALTH;
				extras=" ";
				eweLocation="Noyawa/Voice Health Messages/EWE/A&P and WIS "+toggle;
				englishLocation="Noyawa/Voice Health Messages/ENGLISH/A&P and WIS "+toggle;
				dagbaniLocation="Noyawa/Voice Health Messages/DAGBANI/A&P and WIS "+toggle;
				dagareLocation="Noyawa/Voice Health Messages/DAGARE/A&P and WIS "+toggle;
				dangbeLocation="Noyawa/Voice Health Messages/DANGBE/A&P and WIS "+toggle;
				gonjaLocation="Noyawa/Voice Health Messages/GONJA/A&P and WIS "+toggle;
				hausaLocation="Noyawa/Voice Health Messages/HAUSA/A&P and WIS "+toggle;
				kasimLocation="Noyawa/Voice Health Messages/KASIM/A&P and WIS "+toggle;
				twiLocation="Noyawa/Voice Health Messages/TWI/A&P and WIS "+toggle;

				intent.putExtra(Noyawa.TYPE, type);
				intent.putExtra(Noyawa.SUB_MODULE, submodule);
				intent.putExtra(Noyawa.MODULE, module);
				intent.putExtra(Noyawa.EXTRAS,extras);
				intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);
				intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
				intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
				intent.putExtra(Noyawa.DAGARE_AUDIO_LOCATION, dagareLocation);
				intent.putExtra(Noyawa.DANGBE_AUDIO_LOCATION, dangbeLocation);
				intent.putExtra(Noyawa.GONJA_AUDIO_LOCATION, gonjaLocation);
				intent.putExtra(Noyawa.HAUSA_AUDIO_LOCATION, hausaLocation);
				intent.putExtra(Noyawa.KASIM_AUDIO_LOCATION, kasimLocation);
				intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);

				startActivity(intent);
				break;
		}	
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
						YouthHealthMenuActivity.this);

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
								Intent intent=new Intent(YouthHealthMenuActivity.this, WelcomeActivity.class);
								startActivity(intent);
								YouthHealthMenuActivity.this.finish();

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
				goHome.setClass(YouthHealthMenuActivity.this, MenuActivity.class);
				goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(goHome);
				finish();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


}
