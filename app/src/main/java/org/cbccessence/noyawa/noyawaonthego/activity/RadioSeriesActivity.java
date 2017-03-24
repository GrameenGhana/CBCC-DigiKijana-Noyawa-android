package org.cbccessence.noyawa.noyawaonthego.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.TrimesterListViewAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;

/**
 * Created by mac on 1/26/16.
 */
public class RadioSeriesActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener  {



    private ListView listView;
    private TextView header;
    private String type;
    private String submodule;
    private String module;
    private String extras;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radioseries_menu_activity);
        header=(TextView) findViewById(R.id.textView1);
        header.setText("Radio Series");
        Typeface custom_font = Typeface.createFromAsset(RadioSeriesActivity.this.getAssets(),
                "fonts/Roboto-MediumItalic.ttf");
        header.setTypeface(custom_font);

        listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
        String[] values={"Synopsis","Episodes"
                };

        int[] images={R.drawable.player_icon,
                R.drawable.player_icon};

        TrimesterListViewAdapter adapter=new TrimesterListViewAdapter(RadioSeriesActivity.this,values,images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        String englishLocation;

        String eweLocation;
        String dagbaniLocation;
        String twiLocation;


        switch (position){

            case 0:
                intent=new Intent(RadioSeriesActivity.this, AudioGalleryActivity.class);
                type="Audio";
                submodule="Synopsis";
                module=Noyawa.MODULE_RADIO_STORY_MESSAGES;
                extras=" ";
                englishLocation="Noyawa/Radio Story Messages/ENGLISH SYNOPSIS";
                eweLocation="Noyawa/Radio Story Messages/EWE SYNOPSIS/";
                dagbaniLocation="Noyawa/Radio Story Messages/DAGBANI SYNOPSIS/";
                twiLocation="Noyawa/Radio Story Messages/TWI SYNOPSIS/";

                intent.putExtra(Noyawa.TYPE, type);
                intent.putExtra(Noyawa.SUB_MODULE, submodule);
                intent.putExtra(Noyawa.MODULE, module);
                intent.putExtra(Noyawa.EXTRAS,extras);
                intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
                intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
                intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);
                intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);

                startActivity(intent);
                break;

            case 1	:
                intent=new Intent(RadioSeriesActivity.this, AudioGalleryActivity.class);
                type="Audio";
                submodule="Episodes";
                module=Noyawa.MODULE_RADIO_STORY_MESSAGES;
                extras="";
                englishLocation="Noyawa/Radio Story Messages/ENGLISH/";
                eweLocation="Noyawa/Radio Story Messages/EWE/";
                dagbaniLocation="Noyawa/Radio Story Messages/DAGBANI/";
                twiLocation="Noyawa/Radio Story Messages/TWI/";

                intent.putExtra(Noyawa.TYPE, type);
                intent.putExtra(Noyawa.SUB_MODULE, submodule);
                intent.putExtra(Noyawa.MODULE, module);
                intent.putExtra(Noyawa.EXTRAS,extras);
                intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
                intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
                intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);
                intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);

                startActivity(intent);
                break;





        }
    }

}
