package org.cbccessence.noyawa.noyawaonthego.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.database.DataClass.NoyawaDatabase;
import org.cbccessence.noyawa.noyawaonthego.model.Attendee;
import org.cbccessence.noyawa.noyawaonthego.model.GroupMeeting;
import org.cbccessence.noyawa.noyawaonthego.model.SubSection;
import org.cbccessence.noyawa.noyawaonthego.model.UsageTracking;
import org.cbccessence.noyawa.noyawaonthego.model.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mac on 1/25/16.
 */
public class DatabaseHandler {


    Context context;
DatabaseHelper mDbHelper = new DatabaseHelper(context);
private SQLiteDatabase database;

public DatabaseHandler(Context context) {
        mDbHelper = new DatabaseHelper(context);

        }








        public boolean insertMeeting (String topic, String start_dateTime, String end_dateTime, String location){


                try {

                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NoyawaDatabase.GROUP_MEETING_TOPIC, topic);
                        values.put(NoyawaDatabase.GROUP_MEETING_START_DATE_TIME, start_dateTime);
                        values.put(NoyawaDatabase.GROUP_MEETING_END_DATE_TIME, end_dateTime);
                        values.put(NoyawaDatabase.GROUP_MEETING_LOCATION, location);
                        db.insert(NoyawaDatabase.GROUP_MEETINGS_TABLE, null, values);

                        Log.i("DATABASE", "New meeting inserted with details : " + "Topic " + topic + " StartDateTime " + start_dateTime
                                + " EndDateTime " + end_dateTime + " Location " + location );
                        return true;
                }catch(Exception e)
                {
                        e.printStackTrace();

                        return false;
                }
        }


        public boolean insertSubSection (String subSec_name,  String activity_name){


                try {

                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NoyawaDatabase.SUBSECTION_NAME, subSec_name);
                        values.put(NoyawaDatabase.SUBSECTION_ACTIVITY_NAME, activity_name);
                        db.insert(NoyawaDatabase.ALL_SUBSECTION_NAMES_TABLE, null, values);

                        Log.i("DATABASE", "New subsection inserted with details : " + "Name " + subSec_name + " from activity  " + activity_name );
                        return true;
                }catch(Exception e)
                {
                        e.printStackTrace();

                        return false;
                }
        }



        public Integer removeSubSection (String subSec_name,  String activity_name) {

                try {
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();
                        return db.delete(NoyawaDatabase.ALL_SUBSECTION_NAMES_TABLE,
                                NoyawaDatabase.SUBSECTION_NAME + " = ? AND " + NoyawaDatabase.SUBSECTION_ACTIVITY_NAME + " = ?",
                                new String[]{subSec_name, activity_name});
                }catch(Exception e){

                        e.printStackTrace();
                        return 0;
                }

        }

        public Boolean doesSubSecNameExist(String name, String activity_name) {
                try {
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();
                        return DatabaseUtils.queryNumEntries(db, NoyawaDatabase.ALL_SUBSECTION_NAMES_TABLE,
                                NoyawaDatabase.SUBSECTION_NAME + " = ? AND " + NoyawaDatabase.SUBSECTION_ACTIVITY_NAME + " = ?",
                                new String[]{name, activity_name}) > 0;
                }catch(Exception e){
                        e.printStackTrace();
                        return false;

                }
        }




        public List<SubSection> getSubSections(String activity_name, String subDir){
                List<SubSection> subSections = new ArrayList<>();

                String query = "SELECT * FROM " + NoyawaDatabase.ALL_SUBSECTION_NAMES_TABLE + " WHERE " +
                        NoyawaDatabase.SUBSECTION_ACTIVITY_NAME + " ='" + activity_name + "'";

                Log.i("QUERY", query);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(query, null);

                try{
                        if(cursor.moveToFirst() && cursor.getCount() > 0){

                                do{
                                        String name = cursor.getString(cursor.getColumnIndex(NoyawaDatabase.SUBSECTION_NAME));
                                        String activity = cursor.getString(cursor.getColumnIndex(NoyawaDatabase.SUBSECTION_ACTIVITY_NAME));


                                        if(BaseActivity.doesSubSecNameExistInFolder(context, subDir, name)) {
                                                SubSection subSection = new SubSection(name, activity);
                                                subSections.add(subSection);

                                                Log.i("DATABASE", "Sub Section added with name  " +  name
                                                        +  " belonging to activity  " + activity);

                                        }else {

                                                if(removeSubSection(name, activity_name) > 0)
                                                        Log.i("DATABASE", "REMOVED! No Sub Section content found with name " + name + "belonging to activity " + activity);
                                        }






                                }while(cursor.moveToNext());
                        }
                }catch(Exception e){
                        e.printStackTrace();
                }finally{
                        cursor.close();

                }
                return subSections;
        }



        public Integer removeMeeting (String topic, String meeting_uid) {

                try {
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();
                        return db.delete(NoyawaDatabase.GROUP_MEETINGS_TABLE,
                                NoyawaDatabase.GROUP_MEETING_TOPIC + " = ? AND " + NoyawaDatabase.GROUP_MEETING_START_DATE_TIME + " = ?",
                                new String[]{topic, meeting_uid});
                }catch(Exception e){
                        e.printStackTrace();
                        return 0;
                }

        }


        public boolean insertMeetingAttendee (String attendeeName, String meetingUid){


                try {

                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(NoyawaDatabase.GROUP_MEETING_ATTENDEE_NAME, attendeeName);
                        values.put(NoyawaDatabase.GROUP_MEETING_UID, meetingUid);
                        db.insert(NoyawaDatabase.GROUP_MEETING_ATTENDEES_TABLE, null, values);

                        Log.i("DATABASE", "New attendee inserted with details : " + "Name " + attendeeName + " StartDateTime " + meetingUid);
                        return true;
                }catch(Exception e)
                {
                        e.printStackTrace();

                        return false;
                }
        }


        public Integer removeMeetingAttendee(String attendee_name, String meeting_uid) {

                try {
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();
                        return db.delete(NoyawaDatabase.GROUP_MEETING_ATTENDEES_TABLE,
                                NoyawaDatabase.GROUP_MEETING_ATTENDEE_NAME + " = ? AND " + NoyawaDatabase.GROUP_MEETING_UID + " = ?",
                                new String[]{attendee_name, meeting_uid});
                }catch(Exception e){
                        e.printStackTrace();
                        return 0;
                }

        }



        public Boolean doesAttendeeExist(String attendee_name, String meeting_uid) {
                try {
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();
                        return DatabaseUtils.queryNumEntries(db, NoyawaDatabase.GROUP_MEETING_ATTENDEES_TABLE,
                                NoyawaDatabase.GROUP_MEETING_ATTENDEE_NAME + " = ? AND " + NoyawaDatabase.GROUP_MEETING_UID + " = ?",
                                new String[]{attendee_name, meeting_uid}) > 0;
                }catch(Exception e){
                        e.printStackTrace();
                        return false;

                }
        }





        public List<GroupMeeting> getAllMeetings(){
                List<GroupMeeting> meetings = new ArrayList<>();

                String query = "SELECT * FROM " + NoyawaDatabase.GROUP_MEETINGS_TABLE;
                Log.i("QUERY", query);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(query, null);

                try{
                        if(cursor.moveToFirst() && cursor.getCount() > 0){

                                do{
                                        GroupMeeting meeting = new GroupMeeting(
                                                cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_TOPIC)),
                                                cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_START_DATE_TIME)),
                                                cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_END_DATE_TIME)),
                                                cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_LOCATION))
                                        );

                                        meetings.add(meeting);
                                        Log.i("DATABASE", "Group meeting added with topic  " +  cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_TOPIC)));


                                }while(cursor.moveToNext());
                        }
                }catch(Exception e){
                        e.printStackTrace();
                }finally{
                        cursor.close();

                }
                return meetings;
        }





        public List<Attendee> getAttendees(String meeting_uid){
                List<Attendee> attendees = new ArrayList<>();

                String query = "SELECT * FROM " + NoyawaDatabase.GROUP_MEETING_ATTENDEES_TABLE + " WHERE " + NoyawaDatabase.GROUP_MEETING_UID
                        + " ='" + meeting_uid + "'";
                Log.i("QUERY", query);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(query, null);

                try{
                        if(cursor.moveToFirst() && cursor.getCount() > 0){

                                do{
                                        Attendee attendee = new Attendee(
                                                cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_ATTENDEE_NAME))
                                        );

                                        attendees.add(attendee);
                                        Log.i("DATABASE", "Attendee added with name  " +  cursor.getString(cursor.getColumnIndex(NoyawaDatabase.GROUP_MEETING_ATTENDEE_NAME)));


                                }while(cursor.moveToNext());
                        }
                }catch(Exception e){
                        e.printStackTrace();
                }finally{
                        cursor.close();

                }
                return attendees;
        }




















        public void insertUserTable(String volunteer_name,String username,String password,String chps_zone,String community_resident){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoyawaDatabase.COL_LOGIN_NAME_OF_USER,volunteer_name);
        values.put(NoyawaDatabase.COL_USERNAME,username);
        values.put(NoyawaDatabase.COL_PASSWORD,password);

        long newRowId;
        newRowId = db.insert(
        NoyawaDatabase.LOGIN_TABLE_NAME, null, values);
        }

public void insertLoginActivity(String date,String time,String username,String status,String update_status){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoyawaDatabase.COL_DATE_LOGGED_IN,date);
        values.put(NoyawaDatabase.COL_TIME_LOGGED_IN,time);
        values.put(NoyawaDatabase.COL_USERNAME,username);
        values.put(NoyawaDatabase.COL_LOGIN_STATUS,status);
        values.put(NoyawaDatabase.COL_LOGIN_UPDATE_STATUS,update_status);

        long newRowId;
        newRowId = db.insert(
        NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME, null, values);
        }

        public void insertMeetingSession(String meeting_id,String meeting_title,String start_time,String male_attendence,String female_attendence,String location,String region,String comments){
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(NoyawaDatabase.COL_MEETING_TITLE,meeting_title);
                values.put(NoyawaDatabase.COL_MEETING_ID,meeting_id);
                values.put(NoyawaDatabase.COL_START_TIME,start_time);
                values.put(NoyawaDatabase.COL_MALE_ATTENDENCE,male_attendence);
                values.put(NoyawaDatabase.COL_FEMALE_ATTENDENCE,female_attendence);
                values.put(NoyawaDatabase.COL_LOCATION,location);
                values.put(NoyawaDatabase.COL_REGION,region);
                values.put(NoyawaDatabase.COL_COMMENTS,comments);
                values.put(NoyawaDatabase.COL_IN_SESSION,"In Session");

                long newRowId;
                newRowId = db.insert(
                        NoyawaDatabase.MEETING_SESSION_TABLE_NAME, null, values);

                Log.i("DatabaseHandler","Saving ... Id -->" +newRowId);
        }

        public boolean deleteAllMeetingSessions() {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                int doneDelete = 0;
                doneDelete = db.delete(NoyawaDatabase.MEETING_SESSION_TABLE_NAME, null , null);

                return doneDelete > 0;

        }

        public Cursor fetchMeetingSessionByName(String inputText) throws SQLException {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor mCursor = null;
                if (inputText == null  ||  inputText.length () == 0)  {
                        mCursor = db.query(NoyawaDatabase.MEETING_SESSION_TABLE_NAME, new String[] {NoyawaDatabase.COL_MEETING_TITLE,
                                        NoyawaDatabase.COL_START_TIME, NoyawaDatabase.COL_MALE_ATTENDENCE, NoyawaDatabase.COL_FEMALE_ATTENDENCE, NoyawaDatabase.COL_LOCATION},
                                null, null, null, null, null);

                }
                else {
                        mCursor = db.query(true, NoyawaDatabase.MEETING_SESSION_TABLE_NAME, new String[] {NoyawaDatabase.COL_MEETING_TITLE,
                                        NoyawaDatabase.COL_START_TIME, NoyawaDatabase.COL_MALE_ATTENDENCE, NoyawaDatabase.COL_FEMALE_ATTENDENCE, NoyawaDatabase.COL_LOCATION},
                                NoyawaDatabase.COL_MEETING_TITLE + " like '%" + inputText + "%'", null,
                                null, null, null, null);
                }
                if (mCursor != null) {
                        mCursor.moveToFirst();
                }
                return mCursor;

        }

        public Cursor fetchAllMeetingSessions() {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor mCursor = db.query(NoyawaDatabase.MEETING_SESSION_TABLE_NAME, new String[]{NoyawaDatabase._ID, NoyawaDatabase.COL_MEETING_TITLE,
                                NoyawaDatabase.COL_START_TIME, NoyawaDatabase.COL_LOCATION, NoyawaDatabase.COL_IN_SESSION, NoyawaDatabase.COL_MEETING_ID},
                        null, null, null, null, null);

                if (mCursor != null) {
                        mCursor.moveToFirst();
                }
                return mCursor;
        }

        public void updateMeetingSessionStatus(String status,String id){
                SQLiteDatabase database = mDbHelper.getWritableDatabase();
                String updateQuery = "Update "+ NoyawaDatabase.MEETING_SESSION_TABLE_NAME+ " set "+
                        NoyawaDatabase.COL_IN_SESSION+" = '"+ status +"' where "+ NoyawaDatabase.COL_MEETING_ID+" = '"+id+"' ";
                Log.d("query", updateQuery);
                database.execSQL(updateQuery);
                database.close();
        }

public void insertUsageActivity(String username,String module,String submodule, String type,String action_date,String duration,String duration_played,String status,String extras,String update_status){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoyawaDatabase.COL_USERNAME_USAGE_TRACKING,username);
        values.put(NoyawaDatabase.COL_MODULE,module);
        values.put(NoyawaDatabase.COL_SUBMODULE,submodule);
        values.put(NoyawaDatabase.COL_TYPE,type);
        values.put(NoyawaDatabase.COL_ACTION_DATE,action_date);
        values.put(NoyawaDatabase.COL_DURATION,duration);
        values.put(NoyawaDatabase.COL_DURATION_PLAYED,duration_played);
        values.put(NoyawaDatabase.COL_STATUS,status);
        values.put(NoyawaDatabase.COL_EXTRAS, extras);
        values.put(NoyawaDatabase.COL_UPDATE_STATUS_TRACKING, update_status);


        long newRowId;
        newRowId = db.insert(
        NoyawaDatabase.USAGE_ACTIVITY_TABLE_NAME, null, values);
        }

public ArrayList<String> verifyLogin(String username, String password){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<String> list=new ArrayList<String>();
        String strQuery="select "+ NoyawaDatabase._ID
        +","+ NoyawaDatabase.COL_USERNAME
        +"," + NoyawaDatabase.COL_PASSWORD
        +","+ NoyawaDatabase.COL_LOGIN_NAME_OF_USER
        +" from "+ NoyawaDatabase.LOGIN_TABLE_NAME
        +" where "+ NoyawaDatabase.COL_USERNAME+" = \""+username+"\""
        + " and "+ NoyawaDatabase.COL_PASSWORD+ " = \""+password+"\"";

        System.out.println(strQuery);
        Cursor c=db.rawQuery(strQuery, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
        list.add(c.getString(c.getColumnIndex(NoyawaDatabase.COL_USERNAME)));
        list.add(c.getString(c.getColumnIndex(NoyawaDatabase.COL_PASSWORD)));
        list.add(c.getString(c.getColumnIndex(NoyawaDatabase.COL_LOGIN_NAME_OF_USER)));
        c.moveToNext();
        }
        c.close();
        return list;
        }



public int getAllLoginActivity(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int count=0;
        String strQuery="select * "
        +" from "+ NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME
        +" where "+ NoyawaDatabase.COL_LOGIN_UPDATE_STATUS
        +" = '"+"new_record"+"'";
        System.out.println(strQuery);
        Cursor c=db.rawQuery(strQuery, null);
        count=c.getCount();
        c.close();
        return count;

        }
public ArrayList<User> getLoginActivity(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<User> list=new ArrayList<User>();
        String strQuery="select * "
        +" from "+ NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME
        +" where "+ NoyawaDatabase.COL_LOGIN_UPDATE_STATUS
        +" = '"+ Noyawa.SYNC_STATUS_NEW+"'";
        System.out.println(strQuery);
        Cursor c=db.rawQuery(strQuery, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){

        User u = new User();
        u.setUserId(c.getString(c.getColumnIndex(NoyawaDatabase._ID)));
        u.setUsername(c.getString(c.getColumnIndex(NoyawaDatabase.COL_USERNAME_LOGIN_ACTIVITY)));
        u.setLoginDate(c.getString(c.getColumnIndex(NoyawaDatabase.COL_DATE_LOGGED_IN)));
        u.setLoginTime(c.getString(c.getColumnIndex(NoyawaDatabase.COL_TIME_LOGGED_IN)));
        u.setStatus(c.getString(c.getColumnIndex(NoyawaDatabase.COL_STATUS)));
        list.add(u);

        c.moveToNext();
        }
        c.close();
        return list;

        }
public ArrayList<UsageTracking> getUsageActivity(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<UsageTracking> list=new ArrayList<UsageTracking>();
        String strQuery="select *"
        +" from "+ NoyawaDatabase.USAGE_ACTIVITY_TABLE_NAME
        +" where "+ NoyawaDatabase.COL_UPDATE_STATUS_TRACKING
        +" = '"+Noyawa.SYNC_STATUS_NEW+"'";
        System.out.println(strQuery);
        Cursor c=db.rawQuery(strQuery, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
        UsageTracking usage = new UsageTracking();
        usage.setUsageId(c.getString(c.getColumnIndex(NoyawaDatabase._ID)));
        usage.setUsername(c.getString(c.getColumnIndex(NoyawaDatabase.COL_USERNAME_USAGE_TRACKING)));
        usage.setDuration(c.getString(c.getColumnIndex(NoyawaDatabase.COL_DURATION)));
        usage.setDurationPlayed(c.getString(c.getColumnIndex(NoyawaDatabase.COL_DURATION_PLAYED)));
        usage.setActionDate(c.getString(c.getColumnIndex(NoyawaDatabase.COL_ACTION_DATE)));
        usage.setExtras(c.getString(c.getColumnIndex(NoyawaDatabase.COL_EXTRAS)));
        usage.setModule(c.getString(c.getColumnIndex(NoyawaDatabase.COL_MODULE)));
        usage.setSubModule(c.getString(c.getColumnIndex(NoyawaDatabase.COL_SUBMODULE)));
        usage.setType(c.getString(c.getColumnIndex(NoyawaDatabase.COL_TYPE)));
        usage.setStatus(c.getString(c.getColumnIndex(NoyawaDatabase.COL_STATUS)));
        list.add(usage);
        c.moveToNext();
        }
        c.close();
        return list;

        }


public int getRowLoginCount() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + NoyawaDatabase.LOGIN_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
        }
public int getRowLoginActivityCount() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
        }

public int getRowUsageActivityCount() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + NoyawaDatabase.USAGE_ACTIVITY_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
        }

public int LoginActivitySyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM "+ NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME+" where "
        + NoyawaDatabase.COL_LOGIN_UPDATE_STATUS+ " = '"+"new_record"+"'";
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
        }

public void updateLoginActivitySyncStatus(String status){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String updateQuery = "Update "+ NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME+ " set "+
        NoyawaDatabase.COL_LOGIN_UPDATE_STATUS+" = '"+ status +"'";
        Log.d("query", updateQuery);
        database.execSQL(updateQuery);
        database.close();
        }


public void updateUsageActivitySyncStatus(String status,int id){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String updateQuery = "Update "+ NoyawaDatabase.USAGE_ACTIVITY_TABLE_NAME+ " set "+
        NoyawaDatabase.COL_UPDATE_STATUS_TRACKING+" = '"+ status +"'"+
        " where "+ NoyawaDatabase._ID+" = "+id;
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
        }


public ArrayList<User> getLoginActivityUpdatedStatus(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<User> list=new ArrayList<User>();
        String selectQuery = "SELECT  "+	NoyawaDatabase.COL_LOGIN_UPDATE_STATUS+
        " FROM "+ NoyawaDatabase.LOGIN_ACTIVITY_TABLE_NAME+" where "
        + NoyawaDatabase.COL_LOGIN_UPDATE_STATUS+ " = '"+Noyawa.SYNC_STATUS_NEW+"'";
        Cursor c=db.rawQuery(selectQuery, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
        User u = new User();
        u.setUserId(c.getString(c.getColumnIndex(NoyawaDatabase._ID)));
        u.setLoginDate(c.getString(c.getColumnIndex(NoyawaDatabase.COL_DATE_LOGGED_IN)));
        u.setLoginTime(c.getString(c.getColumnIndex(NoyawaDatabase.COL_TIME_LOGGED_IN)));
        u.setStatus(c.getString(c.getColumnIndex(NoyawaDatabase.COL_STATUS)));
        u.setUsername(c.getString(c.getColumnIndex(NoyawaDatabase.COL_USERNAME_LOGIN_ACTIVITY)));
        list.add(u);

        c.moveToNext();
        }
        c.close();
        return list;

        }


public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
        "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
        }
        }
