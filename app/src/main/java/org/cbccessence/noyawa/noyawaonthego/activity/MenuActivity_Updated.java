package org.cbccessence.noyawa.noyawaonthego.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.fragments.PregnancyMessagesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.RadioSeriesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.RegistrationFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.StoryMessagesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.VisualAidsFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.YouthSexualHealthFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by aangjnr on 06/02/2017.
 */

public class MenuActivity_Updated extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout navDrawer;
    static SharedPreferences prefs;
    List<String> languages;

    static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_2);
        setContentView(R.layout.activity_menu_updated);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        launchMultiplePermissions(this);

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


        String username = prefs.getString("first_name", "User") + " " + prefs.getString("last_name", "User");


         navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) navDrawer.findViewById(R.id.navigation_view);

        TextView user_name = (TextView) navView.getHeaderView(0).findViewById(R.id.user);
        user_name.setText(username);
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
                 syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
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
        }, 2000);



        languages = new ArrayList<>();
        languages = getLanguages();




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






                if(languages != null) {

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

                                    PreferenceManager.getDefaultSharedPreferences(MenuActivity_Updated.this).edit().putString("language", _language).apply();

                                    Toast.makeText(MenuActivity_Updated.this, "You have selected " + _language, Toast.LENGTH_SHORT).show();


                                    Noyawa.ROOT_DIR = Noyawa.ROOT + File.separator + PreferenceManager.getDefaultSharedPreferences(MenuActivity_Updated.this).getString("language", _language);

                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(new Intent(MenuActivity_Updated.this, MenuActivity_Updated.class));


                                }
                            });

                    builder.create().show();

                } else {
                    Toast.makeText(this, "No languages found! Default language is set to english", Toast.LENGTH_SHORT).show();

                }




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




    public static boolean launchMultiplePermissions(Activity context) {
        Boolean hasPermissions = null;

        for(String permission : PERMISSIONS){
            if(!hasPermission(context, permission)){


                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    ActivityCompat.requestPermissions(context, PERMISSIONS, 30);
                } else {
                    ActivityCompat.requestPermissions(context, PERMISSIONS, 30);
                }

                return false;
            }


        }

        return true;
    }


    public static boolean hasPermission(Context context, String PERMISSION) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {

            if (ActivityCompat.checkSelfPermission(context, PERMISSION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSION)) {


                }
                return false;
            }

        }
        return true;
    }





    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);



        if(grantResults[0] != PackageManager.PERMISSION_GRANTED ) {


            //httpHandler.showAlertDialog(LoginActivity.this, "Provide permissions", "Digitunza requires all permissions to work effectively");
            android.app.AlertDialog.Builder alertDialog = new  android.app.AlertDialog.Builder(this, R.style.AppAlertDialog)
                    .setTitle("Permissions")
                    .setMessage("DigiKijana requires all permissions to work effectively")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            launchMultiplePermissions(MenuActivity_Updated.this);
                        }
                    }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                            supportFinishAfterTransition();

                        }
                    });

            android.app.AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }

}
