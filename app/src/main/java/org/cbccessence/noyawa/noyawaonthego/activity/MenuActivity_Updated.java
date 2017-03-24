package org.cbccessence.noyawa.noyawaonthego.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.fragments.PregnancyMessagesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.RadioSeriesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.RegistrationFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.StoryMessagesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.VisualAidsFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.YouthSexualHealthFragment;

import java.util.ArrayList;

/**
 * Created by aangjnr on 06/02/2017.
 */

public class MenuActivity_Updated extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout navDrawer;
    static SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_2);
        setContentView(R.layout.activity_menu_updated);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = YouthSexualHealthFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();


        }




         navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                navDrawer,
                toolbar,
                R.string.open,
                R.string.close
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        navDrawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.youth_sexual_health);
        getSupportActionBar().setSubtitle("Youth and Sexual Health");


        View hView = navigationView.getHeaderView(0);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                navDrawer.openDrawer(GravityCompat.START);
                // do stuff
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                navDrawer.closeDrawer(GravityCompat.START);

                // do stuff
            }
        }, 5000);



    }


    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);

            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else { Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_SHORT).show(); }

            mBackPressed = System.currentTimeMillis();


        }else
            super.onBackPressed();
        }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Boolean logOut = false;

        String subTitleName = "";

        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;


        switch (id){

            case R.id.youth_sexual_health:
                fragmentClass = YouthSexualHealthFragment.class;
                subTitleName = "Youth and Sexual Health";
                break;

            case R.id.story_messages:
                fragmentClass = StoryMessagesFragment.class;
                subTitleName = "Story Messages";
                break;

            case R.id.radio_series:
                fragmentClass = RadioSeriesFragment.class;
                subTitleName = "Radio Series";
                break;
            case R.id.visual_aids:
                fragmentClass = VisualAidsFragment.class;

                subTitleName = "Visual Aids";
                break;
            case R.id.pregnancy_messages:
                fragmentClass = PregnancyMessagesFragment.class;
                subTitleName = "Pregnancy Messages";
                break;
            case R.id.registration:
                fragmentClass = RegistrationFragment.class;

                subTitleName = "Registration";
                break;
            case R.id.language:
                //Show dialog with languages

                logOut = null;

                final ArrayList<String> languages = new ArrayList<>();
                languages.add("Japanese");
                languages.add("Hindi");
                languages.add("Chinese");
                languages.add("Alien");
                languages.add("English");




                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        R.layout.select_dialog, R.id.select_item,
                        languages);

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppDialog);
                builder.setTitle("Please select language")
                        .setCancelable(true)
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                String _language = languages.get(i);
                                dialogInterface.dismiss();
                                Toast.makeText(MenuActivity_Updated.this, "You have selected " + _language, Toast.LENGTH_SHORT).show();

                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MenuActivity_Updated.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("language", _language).apply();

                            }
                        });

                builder.create().show();





                break;

            case R.id.log_out:
                logOut = true;
                break;

        }







        if(logOut != null && !logOut) {

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
            if ( getSupportActionBar() != null )getSupportActionBar().setSubtitle(subTitleName);

            navDrawer.closeDrawer(GravityCompat.START);

        }else if (logOut != null){

            navDrawer.closeDrawers();
            final AlertDialog.Builder logout_alert_builder = new AlertDialog.Builder(MenuActivity_Updated.this, R.style.AppDialog)
                    .setTitle("Logging you out!")
                    .setMessage("Are you sure?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            try {

                                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(MenuActivity_Updated.this);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();


                                startActivity(new Intent(MenuActivity_Updated.this, LoginActivity.class));
                                finish();
                            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }})
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });
            AlertDialog logout_dialog = logout_alert_builder.create();
            logout_dialog.show();

        }


        return true;
    }



    public static String getUserName(){
        String userName;
        userName = prefs.getString("username", "name");
        return userName;
    }

    public static String getLanguage(){

        return  prefs.getString("language", "English");
    }






}
