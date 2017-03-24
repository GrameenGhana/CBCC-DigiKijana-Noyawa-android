package org.cbccessence.noyawa.noyawaonthego.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mac on 1/25/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Noyawa.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataClass.GROUP_MEETINGS_CREATE_TABLE);
        db.execSQL(DataClass.GROUP_MEETING_ATTENDEES_CREATE_TABLE);
        db.execSQL(DataClass.ALL_SUB_SECTION_NAMES_CREATE_TABLE);

        db.execSQL(DataClass.LOGIN_ACTIVITY_CREATE_TABLE);
        db.execSQL(DataClass.LOGIN_CREATE_TABLE);
        db.execSQL(DataClass.USAGE_ACTIVITY_CREATE_TABLE);
       db.execSQL(DataClass.MEETING_SESSION_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataClass.GROUP_MEETING_DELETE_TABLE);
        db.execSQL(DataClass.GROUP_MEETINGS_ATTENDEES_DELETE_TABLE);
        db.execSQL(DataClass.ALL_SUBSECTION_NAMES_DELETE_TABLE);

        db.execSQL(DataClass.LOGIN_ACTIVITY_DELETE_TABLE);
        db.execSQL(DataClass.LOGIN_DELETE_TABLE);
        db.execSQL(DataClass.USAGE_ACTIVITY_DELETE_TABLE);
        db.execSQL(DataClass.MEETING_SESSION_DELETE_TABLE);

        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}