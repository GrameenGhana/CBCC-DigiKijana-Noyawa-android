package org.cbccessence.noyawa.noyawaonthego.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.MenuBaseAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;

/**
 * Created by mac on 1/25/16.
 */

public class MenuActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView grid;

    Context context;
    SharedPreferences prefs;

    int[] images={
            R.drawable.youth,
            R.drawable.story_messages,
            R.drawable.radio_series,
            R.drawable.visual,
            R.drawable.preg,
            R.drawable.registration,
            R.drawable.meetings,
            R.drawable.settings
    };

    private String name;
    public static final long MILLISECONDS_PER_SECOND = 1000L;
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE *
                    MILLISECONDS_PER_SECOND;


    private DatabaseHandler db;

    private String username_value;

    private String username;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_menu);

        context = getApplicationContext();
        db = new DatabaseHandler(MenuActivity.this);
        grid=(GridView) findViewById(R.id.gridView1);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        TextView header = (TextView)findViewById(R.id.header);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-MediumItalic.ttf");
        header.setTypeface(custom_font);
        header.setTextSize(15);
        header.setTextColor(Color.rgb(238, 106, 80));

        //retrieve login details from the shared preferences to be used in the app
        name = "Welcome " + prefs.getString("username", "name");

        header.setText(name);
        MenuBaseAdapter adapter=new MenuBaseAdapter(MenuActivity.this, images);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);



        /*TextView languageselected = (TextView)findViewById(R.id.languageselected);
        languageselected.setText("Selected Language: " + getLanguage().toUpperCase());
        languageselected.setTextColor(Color.rgb(238, 106, 80));*/

    }

    public String getLanguage(){
        String language;
        language = prefs.getString("option", "English");
        return language;
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_icons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
               super.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent;
        switch (position){
            case 0:
                intent=new Intent(MenuActivity.this, YouthHealthMenuActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent=new Intent(MenuActivity.this, StoryMenuActivity.class);
                startActivity(intent);
                break;

            case 2:
                intent=new Intent(MenuActivity.this, RadioSeriesActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent=new Intent(MenuActivity.this, VisualAidsViewPager.class);
                startActivity(intent);
                break;
            case 4:
                intent=new Intent(MenuActivity.this, PregnancyMenuActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent=new Intent(MenuActivity.this, NewClientRegistrationActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent=new Intent(MenuActivity.this, MeetingSessionActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent=new Intent(MenuActivity.this, LanguageSettingsActivity.class);
                startActivity(intent);
                break;
        }

    }






    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
