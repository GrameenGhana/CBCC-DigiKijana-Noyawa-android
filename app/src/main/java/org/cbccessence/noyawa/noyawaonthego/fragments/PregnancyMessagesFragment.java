package org.cbccessence.noyawa.noyawaonthego.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.FirstTrimesterActivity;
import org.cbccessence.noyawa.noyawaonthego.activity.SecondTrimesterActivity;
import org.cbccessence.noyawa.noyawaonthego.activity.ThirdTrimesterActivity;
import org.cbccessence.noyawa.noyawaonthego.adapter.NewListViewBaseAdapter;

/**
 * Created by aangjnr on 06/02/2017.
 */
public class PregnancyMessagesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;

    int[] images={
            R.drawable.first_trimester,
            R.drawable.second_trimester,
            R.drawable.third_trimester
    };



    public PregnancyMessagesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PregnancyMessagesFragment newInstance(String param1, String param2) {
        PregnancyMessagesFragment fragment = new PregnancyMessagesFragment();

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
        return inflater.inflate(R.layout.pregnancy_messages_fragment_layout, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);
        listView=(ListView) getActivity().findViewById(R.id.pregnancy_menu_listView);

        String[] values={"1st Trimester","2nd Trimester","3rd Trimester"};

        NewListViewBaseAdapter adapter = new NewListViewBaseAdapter(getActivity(),values,images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }








    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent;
        switch (position){
            case 0:
                intent=new Intent(getActivity(), FirstTrimesterActivity.class);
                startActivity(intent);
                break;
            case 1	:
                intent=new Intent(getActivity(), SecondTrimesterActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent=new Intent(getActivity(), ThirdTrimesterActivity.class);
                startActivity(intent);
                break;
        }

    }



}