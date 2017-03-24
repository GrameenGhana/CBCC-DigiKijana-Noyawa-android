package org.cbccessence.noyawa.noyawaonthego.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.AudioGalleryActivity;
import org.cbccessence.noyawa.noyawaonthego.adapter.TrimesterListViewAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;

/**
 * Created by aangjnr on 06/02/2017.
 */
public class RadioSeriesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private TextView header;
    private String type;
    private String submodule;
    private String module;
    private String extras;


    public RadioSeriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static RadioSeriesFragment newInstance(String param1, String param2) {
        RadioSeriesFragment fragment = new RadioSeriesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.radio_series_fragment_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);


        listView=(ListView) getActivity().findViewById(R.id.pregnancy_menu_listView);
        String[] values={"Synopsis","Episodes"
        };

        int[] images={R.drawable.ic_audiotrack_black_24dp,
                R.drawable.ic_audiotrack_black_24dp};

        TrimesterListViewAdapter adapter=new TrimesterListViewAdapter(getActivity(),values,images);
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
                intent=new Intent(getActivity(), AudioGalleryActivity.class);
                type="Audio";
                submodule="Synopsis";
                module= Noyawa.MODULE_RADIO_STORY_MESSAGES;
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
                intent=new Intent(getActivity(), AudioGalleryActivity.class);
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