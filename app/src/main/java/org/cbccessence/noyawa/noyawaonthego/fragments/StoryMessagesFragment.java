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
public class StoryMessagesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private TextView header;
    private String type;
    private String submodule;
    private String module;
    private String extras;


    public StoryMessagesFragment() {
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
    public static StoryMessagesFragment newInstance(String param1, String param2) {
        StoryMessagesFragment fragment = new StoryMessagesFragment();

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
        return inflater.inflate(R.layout.story_messages_fragment_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);

        header=(TextView) getActivity().findViewById(R.id.textView1);
        header.setText("Story Messages");


        listView=(ListView) getActivity().findViewById(R.id.pregnancy_menu_listView);
        String[] values={"General",
                "Abstinence",
                "Teenage Pregnancy",
                "Rape"};

        int[] images={R.drawable.player_icon,
                R.drawable.abstinence,
                R.drawable.teenage_pregnancy,
                R.drawable.rape};

        TrimesterListViewAdapter adapter=new TrimesterListViewAdapter(getActivity(),values,images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }






    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        String eweLocation;
        String englishLocation;
        String dagbaniLocation;
        String dagareLocation;
        String dangbeLocation;
        String gonjaLocation;
        String hausaLocation;
        String kasimLocation;
        String twiLocation;


        switch (position){
            case 0:
                intent=new Intent(getActivity(), AudioGalleryActivity.class);
                type="Audio";
                submodule="General";
                module= Noyawa.MODULE_STORY_MESSAGES;
                extras=" ";
                eweLocation="Noyawa/Voice Story Messages/EWE";
                englishLocation="Noyawa/Voice Story Messages/ENGLISH";
                dagbaniLocation="Noyawa/Voice Story Messages/DAGBANI";
                dagareLocation="Noyawa/Voice Story Messages/DAGARE";
                dangbeLocation="Noyawa/Voice Story Messages/DANGBE";
                gonjaLocation="Noyawa/Voice Story Messages/GONJA";
                hausaLocation="Noyawa/Voice Story Messages/HAUSA";
                kasimLocation="Noyawa/Voice Story Messages/KASIM";
                twiLocation="Noyawa/Voice Story Messages/TWI";


                intent.putExtra(Noyawa.TYPE, type);
                intent.putExtra(Noyawa.SUB_MODULE, submodule);
                intent.putExtra(Noyawa.MODULE, module);
                intent.putExtra(Noyawa.EXTRAS,extras);
                intent.putExtra(Noyawa.EWE_AUDIO_LOCATION, eweLocation);
                intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);
                intent.putExtra(Noyawa.DAGBANI_AUDIO_LOCATION, dagbaniLocation);
                intent.putExtra(Noyawa.DAGARE_AUDIO_LOCATION, dagareLocation);
                intent.putExtra(Noyawa.DANGBE_AUDIO_LOCATION, dangbeLocation);
                intent.putExtra(Noyawa.GONJA_AUDIO_LOCATION, gonjaLocation);
                intent.putExtra(Noyawa.HAUSA_AUDIO_LOCATION, hausaLocation);
                intent.putExtra(Noyawa.KASIM_AUDIO_LOCATION, kasimLocation);
                intent.putExtra(Noyawa.TWI_AUDIO_LOCATION, twiLocation);

                startActivity(intent);
                break;

            case 1	:
                intent=new Intent(getActivity(), AudioGalleryActivity.class);
                type="Audio";
                submodule="Abstinence";
                module=Noyawa.MODULE_STORY_MESSAGES;
                extras="";
                englishLocation="Noyawa/Voice Story Messages/Abstinence";

                intent.putExtra(Noyawa.TYPE, type);
                intent.putExtra(Noyawa.SUB_MODULE, submodule);
                intent.putExtra(Noyawa.MODULE, module);
                intent.putExtra(Noyawa.EXTRAS,extras);
                intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);

                startActivity(intent);
                break;

            case 2:
                intent=new Intent(getActivity(), AudioGalleryActivity.class);
                type="Audio";
                submodule="Teenage Pregnancy";
                module=Noyawa.MODULE_STORY_MESSAGES;
                extras=" ";
                englishLocation="Noyawa/Voice Story Messages/Teenage Pregnancy";

                intent.putExtra(Noyawa.TYPE, type);
                intent.putExtra(Noyawa.SUB_MODULE, submodule);
                intent.putExtra(Noyawa.MODULE, module);
                intent.putExtra(Noyawa.EXTRAS,extras);
                intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);

                startActivity(intent);
                break;

            case 3:
                intent=new Intent(getActivity(), AudioGalleryActivity.class);
                type="Audio";
                submodule="Rape";
                module=Noyawa.MODULE_STORY_MESSAGES;
                extras=" ";
                englishLocation="Noyawa/Voice Story Messages/Rape";

                intent.putExtra(Noyawa.TYPE, type);
                intent.putExtra(Noyawa.SUB_MODULE, submodule);
                intent.putExtra(Noyawa.MODULE, module);
                intent.putExtra(Noyawa.EXTRAS,extras);
                intent.putExtra(Noyawa.ENGLISH_AUDIO_LOCATION, englishLocation);

                startActivity(intent);
                break;


        }
    }

}

