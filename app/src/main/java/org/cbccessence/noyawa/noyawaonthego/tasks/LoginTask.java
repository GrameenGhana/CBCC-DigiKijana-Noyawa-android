package org.cbccessence.noyawa.noyawaonthego.tasks;/*
 * This file is part of OppiaMobile - http://oppia-mobile.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import org.cbccessence.noyawa.noyawaonthego.HttpHandler;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.MenuActivity_Updated;
import org.cbccessence.noyawa.noyawaonthego.adapter.ProjectsListAdapter;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHelper;
import org.cbccessence.noyawa.noyawaonthego.model.Projects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginTask extends AsyncTask<String, String, String> {

    public static final String TAG = LoginTask.class.getSimpleName();
    private DatabaseHelper dbh;
    private AppCompatActivity ctx;
    private SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    ProgressDialog progress;
    HttpHandler post;
    List<Projects> projects = new ArrayList<Projects>();
    AlertDialog dialog;
    Integer uid;


    public LoginTask(AppCompatActivity c) {
        this.ctx = c;
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        dbh = new DatabaseHelper(ctx);
        progress = new ProgressDialog(ctx);
        progress.setTitle("Logging in");

    }

    @Override
    protected void onPreExecute() {

        progress.setMessage("Authenticating...");
        progress.show();

        post = new HttpHandler(ctx);


    }

    @Override
    protected String doInBackground(String... strings) {


        String response = null;

        String url = "http://188.166.30.140/gfcare/api/users/login";
        JSONObject json = new JSONObject();

        String email = strings[0];
        String password = strings[1];
        String module = "gfcare-module-3";

        try {


            Log.d(TAG, "logging in user *** " + email);


            json.put("email", email);
            json.put("password", password);
            json.put("module", module);


            String responseJsonString = post.makePostRequest(url, json.toString());

            Log.i("Login Task", responseJsonString);

            switch (responseJsonString) {
                case "no access": // unauthorised

                    response = "no access";


                    break;
                case "invalid": // user not found
                    response = "invalid";

                    break;
                case "null":

                    //Check connection

                    response = "null";
                    Log.d(TAG, responseJsonString);


                    break;
                default: // logged in


                    JSONObject jsonResp = new JSONObject(responseJsonString);
                    String token = jsonResp.getString("token");
                    JSONObject jsonUserDetails = jsonResp.getJSONObject("user");
                    JSONArray projectsArray = jsonUserDetails.getJSONArray("projects");

                    uid = jsonUserDetails.getInt("id");


                    Log.d(TAG, token);
                    Log.d(TAG, jsonUserDetails.toString());

                    for (int i = 0; i < projectsArray.length(); i++) {
                        JSONObject project = projectsArray.getJSONObject(i);
                        JSONObject pivotObject = project.getJSONObject("pivot");


                        Integer id = project.getInt("id");
                        Integer owner_id = project.getInt("owner_id");
                        String name = project.getString("name");
                        String date_created = project.getString("created_at");
                        String date_updated = project.getString("updated_at");

                        int tid = pivotObject.getInt("team_id");


                        Projects _project = new Projects();
                        _project.setProjectId(id);
                        _project.setProjectOwnerId(owner_id);
                        _project.setProjectName(name);
                        _project.setDateUpdated(date_updated);
                        _project.setgetDateCreated(date_created);
                        _project.setTeamId(tid);

                        projects.add(_project);

                        Log.d(TAG, i + "\t" + _project.getProjectName());


                    }


                    //User's parameters
                    String name[] = jsonUserDetails.getString("name").split("\\s+");

                    //Get current users Projects, Display in a custom dialog with a list of users projects, User makes a selection then app starts
                    //with the current projects data! after making a post with the project id


                    String firstName = name[0].trim();
                    String lastName = name[1].trim();


                    //User has access to This module, Save name in shared prefs/database and Sign him in
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    prefsEditor.putString("token", token);
                    prefsEditor.putInt("uid", uid);

                    prefsEditor.putString("email", email);
                    prefsEditor.putString("first_name", firstName);
                    prefsEditor.putString("last_name", lastName);

                    prefsEditor.apply();

                    Log.d(TAG, token);
                    Log.d(TAG, firstName);
                    Log.d(TAG, lastName);


            }


        } catch (JSONException e) {

            e.printStackTrace();

        }
        return response;

    }


    @Override
    protected void onPostExecute(String response) {

        if (progress != null) progress.hide();

        if (response != null) {

            switch (response) {
                case "no access":

                    post.showAlertDialog(ctx, "Login failed!", "You do not have access to this module.");


                    break;

                case "invalid":

                    post.showAlertDialog(ctx, "Login failed!", "Please check your email and password.");

                    break;

                case "null":

                    post.showAlertDialog(ctx, "Login failed!", "Server is under maintenance, please try again later");

                    break;

            }
        } else {
            //logged In start activity


            // test
            Projects _project = new Projects();
            _project.setProjectId(5);
            _project.setTeamId(60);
            _project.setProjectOwnerId(7);
            _project.setProjectName("Camara's Test Project");
            _project.setDateUpdated("01/02/02");
            _project.setgetDateCreated("01/02/02");
            projects.add(_project);


            if (projects != null) {
                Log.i(TAG, "There are " + projects.size() + " project(s) ");

                Log.i(TAG, "0 " + projects.get(0));

                Log.i(TAG, "1 " + projects.get(1));


                if (projects.size() == 0) {
                    post.showAlertDialog(ctx, "No Projects!", "You have no current projects");

                    //If no projects, display a dialog. Close app since there are no projects
                } else if (projects.size() == 1) { //Else log the user in.


                    sendPostLoginRequest(uid, projects.get(0).getTeamId(), projects.get(0).getProjectId());


                } else if (projects.size() > 1) {
                    LayoutInflater inflater = LayoutInflater.from(ctx);
                    View view = inflater.inflate(R.layout.select_project_dialog, null, false);
                    RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
                    recycler.setLayoutManager(layoutManager);
                    recycler.hasFixedSize();
                    ProjectsListAdapter adapter = new ProjectsListAdapter(ctx, projects);
                    recycler.setAdapter(adapter);
                    adapter.setOnItemClickListener(onItemClickListener);


                    //Show a dialog with a list of current projects if the projects obtained are more than one.

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx, R.style.AppDialog);
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            post.showAlertDialog(ctx, null, "You did not make any selection");
                        }
                    }).setView(view);
                    dialog = alertDialog.create();
                    dialog.show();
                }

            } else {
                post.showAlertDialog(ctx, "Server error", "Couldn't load app projects! Something went wrong :(");
            }

        }


    }

    ProjectsListAdapter.OnItemClickListener onItemClickListener = new ProjectsListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {


            Projects _project = projects.get(position);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("project_name", _project.getProjectName());
            editor.putInt("project_id", _project.getProjectId());
            editor.putInt("project_owner_id", _project.getProjectOwnerId());
            editor.putString("project_date_created", _project.getDateCreated());
            editor.putString("project_date_updated", _project.getDateUpdated());
            editor.apply();

            Log.i(TAG, _project.getProjectName() + " was clicked!");
            sendPostLoginRequest(uid, projects.get(position).getTeamId(), projects.get(position).getProjectId());


        }
    };


    public void sendPostLoginRequest(int uid, int tid, int mid) {

        Intent intent = new Intent(ctx, MenuActivity_Updated.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);




        progress.setTitle("Please wait");
        progress.setMessage("Doing more stuff...");

        String postProjectSelectedUrl = "http://188.166.30.140/gfcare/api/users/context/" + uid + "/" + tid + "/" + mid;
        String responsePostJsonString = post.sendPostProjectSelection(postProjectSelectedUrl);


        Log.i(TAG, "Post url is " + postProjectSelectedUrl);
        Log.i(TAG, "Post url response is " + responsePostJsonString);

        //test
        responsePostJsonString = "OK";
        prefsEditor = prefs.edit();


        switch (responsePostJsonString) {

            case "OK":
                //log the user in
                prefsEditor.putBoolean("isSignedIn", true);
                prefsEditor.putBoolean("isFirstSignIn", false);
                prefsEditor.apply();
                ctx.startActivity(intent);
                ctx.finish();
                break;

            default:
                //Something happened, check if the user has signed in before

                if (prefs.getBoolean("isFirstSignIn", true)) {//first time ever user signed in, let user try again later
                    post.showAlertDialog(ctx, "Uh-oh! Something happened!", "Couldn't get modules. Please try again later");

                } else { //user has signed in before, just sign in anyway :)

                    prefsEditor.putBoolean("isSignedIn", true);
                    prefsEditor.putBoolean("isFirstSignIn", false);
                    prefsEditor.apply();
                    ctx.startActivity(intent);
                    ctx.finish();



                }
        }
    }
}