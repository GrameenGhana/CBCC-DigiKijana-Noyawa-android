package org.cbccessence.noyawa.noyawaonthego.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.cbccessence.noyawa.noyawaonthego.PlaceHolder;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.AudioGalleryActivity;
import org.cbccessence.noyawa.noyawaonthego.adapter.TrimesterListViewAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.model.SubSection;

import java.util.ArrayList;
import java.util.List;

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
    List<SubSection> subSecs;
    String dir = Noyawa.SM_DIR;
    View rootView;
    View view;


    String TAG = StoryMessagesFragment.class.getSimpleName();
      DatabaseHandler dbh;
    private RelativeLayout emptyView;

    public StoryMessagesFragment() {
        // Required empty public constructor
    }


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

        rootView = inflater.inflate(R.layout.story_messages_fragment_layout, container, false);

         dbh = new  DatabaseHandler(getActivity());


        emptyView = (RelativeLayout) rootView.findViewById(R.id.empty_view_placeHolder);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);



        listView=(ListView) getActivity().findViewById(R.id.pregnancy_menu_listView);
        subSecs = new ArrayList<>();
        subSecs = dbh.getSubSections(TAG, dir);



        int[] images={R.drawable.player_icon,
                R.drawable.abstinence,
                R.drawable.teenage_pregnancy,
                R.drawable.rape};

        if(subSecs != null && subSecs.size() > 0) {

            TrimesterListViewAdapter adapter = new TrimesterListViewAdapter(getActivity(), subSecs, images);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            emptyView.setVisibility(View.GONE);
        }else
            emptyView.setVisibility(View.VISIBLE);

    }






    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Intent intent = new Intent( getActivity(), AudioGalleryActivity.class );
        intent.putExtra("subSecName", subSecs.get(position).getSubSectionName());
        intent.putExtra("directory", dir);
        intent.putExtra("type", "Audio");
        startActivity(intent);
    }


}

