package org.cbccessence.noyawa.noyawaonthego.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.cbccessence.noyawa.noyawaonthego.HttpHandler;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.application.JsonParser;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.database.DataClass;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHelper;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mac on 4/20/16.
 */
public class MeetingSessionActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private SimpleCursorAdapter dataAdapter;
    public boolean[] status = new boolean[100];

    @Bind(R.id.btn_start_meeting)
    Button btnStartMeeting;

    private JsonParser jsonParser;

    private static String meetingURL = "http://41.191.245.72/noyawagh/api/v2/endMeetingSession";
    private Boolean error = true;
    private String message = "";
    private String meeting_id = "";

    private SharedPreferences loginPref;
    private String name;

    // flag for Internet connection status
    public  Boolean isInternetPresent = false;

    // Connection detector class
    HttpHandler cd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_session);

        //create an instance of the database handler class
        db=new DatabaseHandler(MeetingSessionActivity.this);

        /** Restore from the previous state if exists */
        if(savedInstanceState!=null){
            status = savedInstanceState.getBooleanArray("status");
        }

        //db.deleteAllMeetingSessions();

        //Log.i("Meeting..", Arrays.toString(getMeetings1()) );


        //Generate ListView from SQLite Database
        displayListView();
        btnStartMeeting = (Button)findViewById(R.id.btn_start_meeting);
        btnStartMeeting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MeetingSessionActivity.this, MeetingsActivity.class);
                startActivity(intent);
                finish();


            }

        });


        jsonParser = new JsonParser();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        loginPref=MeetingSessionActivity.this.getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
        name=loginPref.getString("username", "name");

        // creating connection detector class instance
        cd = new HttpHandler(MeetingSessionActivity.this);

    }


    public String[] getMeetings1(){

        Cursor cursor =  db.fetchAllMeetingSessions();
        ArrayList<String> titles = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            titles.add(cursor.getString(cursor.getColumnIndex(DataClass.NoyawaDatabase.COL_MEETING_TITLE)) + " at " +cursor.getString(cursor.getColumnIndex(DataClass.NoyawaDatabase.COL_LOCATION)) +" on "+cursor.getString(cursor.getColumnIndex(DataClass.NoyawaDatabase.COL_START_TIME)));
            cursor.moveToNext();
        }
        cursor.close();
        return titles.toArray(new String[titles.size()]);

    }

    public String[] getMeetingsStatus(){

        Cursor cursor = db.fetchAllMeetingSessions();
        cursor.moveToFirst();
        ArrayList<String> statuses = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String in_session =  cursor.getString(cursor.getColumnIndex(DataClass.NoyawaDatabase.COL_IN_SESSION));

            statuses.add(in_session);
            cursor.moveToNext();
        }
        cursor.close();
        return statuses.toArray(new String[statuses.size()]);

    }

    public void updateMeeting(String meeting_id,String user_id){


        final ProgressDialog progressDialog = new ProgressDialog(MeetingSessionActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Processing...");
        progressDialog.show();


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Noyawa.SERVER_ADDRESS+"endMeetingSession");

        //getting current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd,HH mm ss");
        String currentDateandTime = sdf.format(new Date());


        List<NameValuePair> nameValuePairs = new ArrayList<>(3);
        nameValuePairs.add(new BasicNameValuePair("meeting_id",meeting_id));
        nameValuePairs.add(new BasicNameValuePair("end_time",currentDateandTime));
        nameValuePairs.add(new BasicNameValuePair("user_id",user_id));



        try {
            JSONObject json = jsonParser.getJSONFromUrl(meetingURL, nameValuePairs);
            error = json.getBoolean("error");
            message = json.getString("msg");
            meeting_id = json.getString("meeting_id");

            db.updateMeetingSessionStatus("Ended",meeting_id);

        } catch (Exception e) {
            e.printStackTrace();
        }


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (!error) {
                            onStartSuccess(message);
                        }else {
                            onStartFailed(message);
                        }

                        progressDialog.dismiss();


                    }
                }, 3000);
    }


    public void onStartSuccess(String message) {

        Toast.makeText(getBaseContext(),  message, Toast.LENGTH_LONG).show();


        finish();
    }

    public void onStartFailed(String message) {
        if(message.isEmpty()){
            message = "server problems, try again later.";
        }

        Toast.makeText(getBaseContext(), "Start meeting failed,"+message, Toast.LENGTH_LONG).show();

    }

    private void displayListView() {
        Cursor cursor = db.fetchAllMeetingSessions();




        ListView listView = (ListView) findViewById(R.id.lv_meetings);
        // Assign adapter to ListView
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);

        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lv, View item, int position, long id) {

                ListView lView = (ListView) lv;

                SimpleCursorAdapter adapter  = (SimpleCursorAdapter) lView.getAdapter();
                Cursor  c= (Cursor) adapter.getItem(position);
                String meeting_id = c.getString(c.getColumnIndex(DataClass.NoyawaDatabase.COL_MEETING_ID));
                String title = c.getString(c.getColumnIndex(DataClass.NoyawaDatabase.COL_MEETING_TITLE));
                String in_session = c.getString(c.getColumnIndex(DataClass.NoyawaDatabase.COL_IN_SESSION));


                /** The clicked Item in the ListView */
                RelativeLayout rLayout = (RelativeLayout) item;

                //TextView tv = (TextView) rLayout.getChildAt(position);

                /** Getting the toggle button corresponding to the clicked item */
                ToggleButton tgl = (ToggleButton) rLayout.getChildAt(3);

                String strStatus = "";


                if(tgl.isChecked()  ){
                    tgl.setText("Ended");
                    tgl.setChecked(false);

                    status[position] = false;
                    db.updateMeetingSessionStatus("Ended",meeting_id);
                    strStatus = " meeting session switched off";

                    // get Internet status
                    isInternetPresent = cd.checkInternetConnection();

                    // check for Internet status
                    if (isInternetPresent) {
                        // Internet Connection is Present
                        // make HTTP requests
                        updateMeeting(meeting_id, name);
                    } else {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        cd.showAlertDialog(MeetingSessionActivity.this, "No Internet Connection",
                                "You don't have internet connection.Please connect and try this again!");
                    }



                }else{
                    tgl.setText("In Session");
                    tgl.setChecked(true);
                    status[position] = true;
                    db.updateMeetingSessionStatus("In Session", meeting_id);
                    strStatus = " meeting session switched on";
                }
                Toast.makeText(getBaseContext(), title+ strStatus, Toast.LENGTH_SHORT).show();
            }
        };

        listView.setOnItemClickListener(itemClickListener);



        // The desired columns to be bound
        String[] columns = new String[] {
                DataClass.NoyawaDatabase.COL_MEETING_TITLE, DataClass.NoyawaDatabase.COL_LOCATION, DataClass.NoyawaDatabase.COL_START_TIME, DataClass.NoyawaDatabase.COL_IN_SESSION
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.textView1, R.id.textView2, R.id.textView3, R.id.tgl_status
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.meeting_session_info,
                cursor,
                columns,
                to,
                0);

        for (int i = 0; i < listView.getChildCount(); i++)
        {
            View v = listView.getChildAt(i);
            RelativeLayout rLayout = (RelativeLayout) v;
            ToggleButton tb = (ToggleButton) rLayout.getChildAt(3);

            Cursor  c= (Cursor) dataAdapter.getItem(i);
            String in_session = c.getString(c.getColumnIndex(DataClass.NoyawaDatabase.COL_IN_SESSION));
            Log.i("Meeting session .... " ,i+"->"+in_session);

            if(in_session.equals("In Session")){
                status[i] = true;
                tb.setChecked(true);
            }else{
                status[i] = false;
                tb.setChecked(false);
            }

        }


        listView.setAdapter(dataAdapter);






    }


    /** Saving the current state of the activity

     * for configuration changes [ Portrait <=> Landscape ]

     */

    @Override

    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBooleanArray("status", status);

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
                        MeetingSessionActivity.this);

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
                                Intent intent=new Intent(MeetingSessionActivity.this, WelcomeActivity.class);
                                startActivity(intent);
                                MeetingSessionActivity.this.finish();

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
               // goHome.setClass(MeetingSessionActivity.this, MenuActivity.class);
                goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goHome);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

