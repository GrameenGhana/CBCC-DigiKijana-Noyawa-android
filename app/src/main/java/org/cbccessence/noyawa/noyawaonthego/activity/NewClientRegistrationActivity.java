package org.cbccessence.noyawa.noyawaonthego.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.cbccessence.noyawa.noyawaonthego.HttpHandler;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.application.JsonParser;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHelper;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewClientRegistrationActivity extends AppCompatActivity {


    @Bind(R.id.editText_phoneNumber) EditText phone_number;
    @Bind(R.id.editText_age) EditText age;
    @Bind(R.id.editText_address) EditText address;

    @Bind(R.id.radioGroup_sex) RadioGroup gender;
    @Bind(R.id.radioGroup_location_status) RadioGroup location_status;

    @Bind(R.id.spinner_education) Spinner education;
    @Bind(R.id.spinner_channel) Spinner channel;
    @Bind(R.id.spinner_language) Spinner language;
    @Bind(R.id.spinner_region) Spinner region;

    @Bind(R.id.button_submit) Button submit;


	private String selected_gender;
    private String selected_education;
	private String selected_region;
    private String selected_channel;
    private String selected_language;
    private String selected_location_status;

    private  final String TAG = this.getClass().getSimpleName();

    //server responses
    private Boolean error = true;
    private String message = "";

    private JsonParser jsonParser;

    private static String registerURL = "http://41.191.245.72/noyawagh/api/v2/register";

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    HttpHandler cd;

    private SharedPreferences loginPref;

    private String name;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_client_registration);
        ButterKnife.bind(this);

        selected_gender = ((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();

		populateEducationSpinner();
		education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selected_education = education.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selected_education = "None";
            }

        });

        populateChannelSpinner();
        channel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selected_channel = channel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selected_channel = "SMS";
            }

        });

        populateLanguageSpinner();
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selected_language = language.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selected_language = "English";
            }

        });

         selected_location_status = ((RadioButton)findViewById(location_status.getCheckedRadioButtonId())).getText().toString();

	    	populateRegionSpinner();
	    	region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    selected_region = region.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }

            });

        // creating connection detector class instance
        cd = new HttpHandler(NewClientRegistrationActivity.this);

        submit=(Button) findViewById(R.id.button_submit);
	    	submit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {

                    // get Internet status
                    isInternetPresent = cd.checkInternetConnection();

                    // check for Internet status
                    if (isInternetPresent) {
                        // Internet Connection is Present
                        // make HTTP requests
                        postRegister(phone_number.getText().toString(), selected_gender, age.getText().toString(), selected_education, selected_channel, selected_language, selected_location_status, address.getText().toString(), selected_region);

                    } else {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        cd.showAlertDialog(NewClientRegistrationActivity.this, "No Internet Connection",
                                "You don't have internet connection.Please connect and try this again!");
                    }


				}
	    		
	    	});

        jsonParser = new JsonParser();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        loginPref=NewClientRegistrationActivity.this.getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
        name=loginPref.getString("username", "name");


	    	
	}


	public void populateRegionSpinner(){
	String[] regions={"Greater Accra Region",
						"Central Region",
						"Western Region",
						"Eastern Region",
						"Northern Region",
						"Upper East Region",
						"Upper West Region",
						"Brong Ahafo Region",
						"Volta Region",
						"Ashanti Region"};

	ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewClientRegistrationActivity.this,android.R.layout.simple_list_item_1,regions);
	region.setAdapter(adapter);	
	}

	public void populateEducationSpinner(){
		String[] educations={"SHS",
				"JHS",
				"Tertiary",
				"None"};
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewClientRegistrationActivity.this,android.R.layout.simple_list_item_1,educations);
		education.setAdapter(adapter);
	}

    public void populateChannelSpinner(){
        String[] channels={"SMS",
                "Voice"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewClientRegistrationActivity.this,android.R.layout.simple_list_item_1,channels);
        channel.setAdapter(adapter);
    }


    public void populateLanguageSpinner(){
        String[] languages={"English",
                "Dagaare",
                "Dagbani",
                "Dangme",
                "Ewe",
                "Gonja",
                "Hausa",
                "Kasim",
                "Twi"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewClientRegistrationActivity.this,android.R.layout.simple_list_item_1,languages);
        language.setAdapter(adapter);
    }

    public void postRegister(String phoneNumber,String gender,String age,String education,String channel,String language,String location_status,String location,String region){
        Log.d(TAG, "New client registration....");

        if (!validate()) {
            onRegisterFailed("check inputs and try again!");
            return;
        }

        submit.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(NewClientRegistrationActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Processing...");
        progressDialog.show();



            List<NameValuePair> nameValuePairs = new ArrayList<>(9);
            nameValuePairs.add(new BasicNameValuePair("msisdn",phoneNumber));
            nameValuePairs.add(new BasicNameValuePair("gender",gender));
            nameValuePairs.add(new BasicNameValuePair("age",age));
            nameValuePairs.add(new BasicNameValuePair("education_level",education));
            nameValuePairs.add(new BasicNameValuePair("channel", channel));
            nameValuePairs.add(new BasicNameValuePair("language", language));
            nameValuePairs.add(new BasicNameValuePair("location_status", location_status));
            nameValuePairs.add(new BasicNameValuePair("location", location));
            nameValuePairs.add(new BasicNameValuePair("region", region));
            nameValuePairs.add(new BasicNameValuePair("username", name));



            try {
                JSONObject json = jsonParser.getJSONFromUrl(registerURL, nameValuePairs);
                error = json.getBoolean("error");
                message = json.getString("msg");

                Log.i(TAG, "Message -> " + message);

            } catch (Exception e) {
                e.printStackTrace();
            }




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (!error) {
                            onRegisterSuccess(message);
                        } else {
                            onRegisterFailed(message);
                        }

                        progressDialog.dismiss();


                    }
                }, 3000);
    }


    public void onRegisterSuccess(String message) {
        submit.setEnabled(true);

        Toast.makeText(getBaseContext(),  message, Toast.LENGTH_LONG).show();


        finish();
    }

    public void onRegisterFailed(String message) {
        if(message.isEmpty()){
            message = "server problems, try again later.";
        }
        Toast.makeText(getBaseContext(), "New client registration failed," + message, Toast.LENGTH_LONG).show();

        submit.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String phoneNumber = phone_number.getText().toString();
        String ag = age.getText().toString();

        if (phoneNumber.isEmpty() ) {
            phone_number.setError("enter a valid phone number ");
            valid = false;
        } else {
            phone_number.setError(null);
        }


        if (ag.isEmpty() || Integer.parseInt(ag) <15 ||  Integer.parseInt(ag) >24 ) {
            age.setError("enter a valid age, 15 to 24yrs");
            valid = false;
        } else {
            age.setError(null);
        }

        if ( phoneNumber.length() < 10 ) {
            phone_number.setError("equal to 10 numeric characters eg. 026xxxxxxx");
            valid = false;
        } else {
            phone_number.setError(null);
        }

        return valid;
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
                        NewClientRegistrationActivity.this);

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
                                Intent intent=new Intent(NewClientRegistrationActivity.this, WelcomeActivity.class);
                                startActivity(intent);
                                NewClientRegistrationActivity.this.finish();

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
                goHome.setClass(NewClientRegistrationActivity.this, MenuActivity_Updated.class);
                goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goHome);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
