package org.cbccessence.noyawa.noyawaonthego.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import org.cbccessence.noyawa.noyawaonthego.R;

import java.io.File;


/**
 * Created by mac on 1/25/16.
 */
public class Noyawa extends Application {

    public static final String TAG = Noyawa.class.getSimpleName();
    private static Context context;


    public static String ROOT_DIR = Environment
            .getExternalStorageDirectory() + File.separator + "Mlezi";

    public static final String VA_DOC_DIR = "Visual Aids/Documents";
    public static final String VA_IMA_DIR = "Visual Aids/Images";
    public static final String VA_VID_DIR = "Visual Aids/Videos";

    public static final String PM_DIR =  "Pregnancy Messages";

    public static final String PM_FIRST_TRE = PM_DIR + File.separator + "First Trimester";
    public static final String PM_SECOND_TRE = PM_DIR + File.separator + "Second Trimester";
    public static final String PM_THIRD_TRE = PM_DIR + File.separator + "Third Trimester";


    public static final String YSH_DIR =  "Youth Sexual Health";

    public static final String SM_DIR =  "Story Messages";
    public static final String RS_DIR =  "Radio Series";




    public static String SERVER_ADDRESS="";
    public static final String MOBIHEALTHSYNC="MobiHealthSync.php";
    public static final String SYNC_STATUS_NEW="new_record";
    public static final String SYNC_STATUS_OLD="updated";
    public static final String SMS_STATUS_SENT="sent";
    public static final String SMS_STATUS_NOT_SENT="not sent";
    public static final String AUDIO_STATUS_COMPLETED="completed";
    public static final String AUDIO_STATUS_NOT_COMPLETED="not completed";
    public static final String VISUAL_STATUS_COMPLETED="viewed";
    public static final String VISUAL_STATUS_NOT_COMPLETED="not viewed";
    public static final String MODULE_BABY_FIRST_YEAR="Baby's first year";
    public static final String MODULE_PREGNANCY_MESSAGES="Pregnancy messages";
    public static final String MODULE_YOUTH_SEXUAL_HEALTH="Youth Sexual Health";
    public static final String MODULE_STORY_MESSAGES="Story Messages";
    public static final String MODULE_RADIO_STORY_MESSAGES="Radio Story Messages";
    public static final String MODULE_VISUAL_AIDS ="Visual Aids";
    public static final String MODULE_REFERRAL_ALERTS="Referral Alerts System";
    public static final String EXTRAS_FIRST_TRIMESTER="	1st Trimester";
    public static final String EXTRAS_SECOND_TRIMESTER="2nd Trimester";
    public static final String EXTRAS_THIRD_TRIMESTER="3rd Trimester";
    public static SharedPreferences prefs;
    public static final String LANGUAGE_EWE="Ewe";
    public static final String LANGUAGE_ENGLISH="English";
    public final static String AUDIO_URL = "audio_url";
    public final static String IMG_URL = "image_url";
    public final static String MODULE = "module";
    public final static String SUB_MODULE = "submodule";
    public final static String TYPE = "type";
    public final static String EWE_AUDIO_LOCATION = "eweLocation";
    public final static String ENGLISH_AUDIO_LOCATION = "englishLocation";
    public final static String DAGBANI_AUDIO_LOCATION = "dangbaniLocation";
    public final static String DAGARE_AUDIO_LOCATION = "dagareLocation";
    public final static String DANGBE_AUDIO_LOCATION = "dangbeLocation";
    public final static String GONJA_AUDIO_LOCATION = "gonjaLocation";
    public final static String HAUSA_AUDIO_LOCATION = "hausaLocation";
    public final static String KASIM_AUDIO_LOCATION = "kasimLocation";
    public final static String TWI_AUDIO_LOCATION = "twiLocation";
    public final static String EXTRAS = "extras";
    public void onCreate(){
        super.onCreate();
        Noyawa.context = getApplicationContext();
        SERVER_ADDRESS=context.getResources().getString(R.string.prefServerDefault);
    }

    public static Context getAppContext() {
        return Noyawa.context;

    }

    public static String getUsername(){
        String username=null;
        prefs=context.getApplicationContext().getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
        username=prefs.getString("username", "name");
        return username;
    }


    public static String getLanguage(){
        String language=null;
        if(prefs==null){
            prefs=context.getApplicationContext().getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
            language=prefs.getString("option", LANGUAGE_ENGLISH);
        }
        return language;
    }
}
