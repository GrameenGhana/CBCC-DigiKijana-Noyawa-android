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
import org.cbccessence.noyawa.noyawaonthego.model.SubSection;

import java.util.List;

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
    List<SubSection> arrayList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radioseries_menu_activity);


/*
        listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
        String[] values={"Synopsis","Episodes"
                };

        int[] images={R.drawable.player_icon,
                R.drawable.player_icon};

        TrimesterListViewAdapter adapter=new TrimesterListViewAdapter(RadioSeriesActivity.this, values,images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);*/

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        String englishLocation;

        String eweLocation;
        String dagbaniLocation;
        String twiLocation;


    }

}
