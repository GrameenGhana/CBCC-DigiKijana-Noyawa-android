package org.cbccessence.noyawa.noyawaonthego.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.HttpHandler;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.application.JsonParser;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.tasks.LoginTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_FORGOT_PASSWORD = 0;

    @Bind(R.id.email_layout)
    TextInputLayout _usernameText;
    @Bind(R.id.password_layout)
    TextInputLayout _passwordText;
    @Bind(R.id.login_fab)
    FloatingActionButton _loginButton;
    @Bind(R.id.forgot_password ) TextView _forgotPasswordLink;


    //server responses
    private Boolean error = true;
    private String message = "";

    private DatabaseHandler db;

    private String current_time;
    private String current_date;
    String username;
    String password;

    private JsonParser jsonParser;

    //private static String loginURL = "http://41.191.245.72/noyawagh/api/v2/login";

    private static String loginURL = "http://188.166.30.140/gfcare/login";

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    HttpHandler cd;

    static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }




        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null)getSupportActionBar().hide();


        ButterKnife.bind(this);

        //create an instance of the database handler class
        db=new DatabaseHandler(LoginActivity.this);
        // creating connection detector class instance
        cd = new HttpHandler(LoginActivity.this);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get Internet status
                isInternetPresent = cd.checkInternetConnection();

                // check for Internet status
                if (isInternetPresent) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        launchMultiplePermissions(LoginActivity.this);
                    else login();


                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    cd.showAlertDialog(LoginActivity.this, "No Internet Connection",
                            "You don't have internet connection. Please connect and try again!");
                }


            }
        });


        _passwordText.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.this.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                isInternetPresent = cd.checkInternetConnection();

                // check for Internet status
                if (isInternetPresent) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        launchMultiplePermissions(LoginActivity.this);
                    else login();

                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    cd.showAlertDialog(LoginActivity.this, "No Internet Connection",
                            "You don't have internet connection. Please connect and try again!");
                }

                return true;

            }
        });


        _forgotPasswordLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Forgot Password activity
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivityForResult(intent, REQUEST_FORGOT_PASSWORD);
            }
        });
    }

    public void login() {

        username = _usernameText.getEditText().getText().toString();
        password = _passwordText.getEditText().getText().toString();

        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        _loginButton.setEnabled(false);
        LoginTask loginTask = new LoginTask(this);
        loginTask.execute(username, password);
        _loginButton.setEnabled(true);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FORGOT_PASSWORD) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here


                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }



    public boolean validate() {
        boolean valid = true;

        if (username.isEmpty() ) {
            _usernameText.setError("enter a valid username ");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);



        if(grantResults[0] != PackageManager.PERMISSION_GRANTED ) {


            //httpHandler.showAlertDialog(LoginActivity.this, "Provide permissions", "Digitunza requires all permissions to work effectively");
            AlertDialog.Builder alertDialog = new  AlertDialog.Builder(this, R.style.AppAlertDialog)
                    .setTitle("Permissions")
                    .setMessage("DigiKijana requires all permissions to work effectively")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            login();
                        }
                    }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                            supportFinishAfterTransition();

                        }
                    });

            AlertDialog alert = alertDialog.create();
            alert.show();
        }else {

            // login
            login();


        }
    }

}

