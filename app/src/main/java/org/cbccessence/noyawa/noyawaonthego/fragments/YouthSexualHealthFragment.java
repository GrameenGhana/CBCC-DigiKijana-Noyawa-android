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
import org.cbccessence.noyawa.noyawaonthego.model.Content;
import org.cbccessence.noyawa.noyawaonthego.model.SubSection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 06/02/2017.



**/

public class YouthSexualHealthFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TextView header;
    private String type;
    private String submodule;
    private String module;
    private String extras;
    View rootView;

    View view;

    List<SubSection> subSecs;
    String TAG = YouthSexualHealthFragment.class.getSimpleName();
    String dir = Noyawa.YSH_DIR;

    DatabaseHandler dbh;
    private RelativeLayout  emptyView;


    public YouthSexualHealthFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static YouthSexualHealthFragment newInstance(String param1, String param2) {
        YouthSexualHealthFragment fragment = new YouthSexualHealthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSubSectionNamesFromLocation(getActivity(), dir, TAG);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.youth_sexual_health_fragment, container, false);

         dbh = new  DatabaseHandler(getActivity());
        emptyView = (RelativeLayout) rootView.findViewById(R.id.empty_view_placeHolder);


        listView = (ListView) rootView.findViewById(R.id.pregnancy_menu_listView);



        return rootView;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);


        subSecs = new ArrayList<>();
        subSecs = dbh.getSubSections(TAG, dir);




        int[] images={R.drawable.abstinence,
                R.drawable.rape,
                R.drawable.teenage_pregnancy};
        if(subSecs != null && subSecs.size() > 0) {

            TrimesterListViewAdapter adapter = new TrimesterListViewAdapter(getActivity(), subSecs, images);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            emptyView.setVisibility(View.GONE);
        }else
            emptyView.setVisibility(View.VISIBLE);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


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




    public void getSubSectionNamesFromLocation(Context c, String sub_dir, String activity_name) {

        DatabaseHandler dbh = new DatabaseHandler(c);


        File folder = new File(Noyawa.ROOT_DIR + File.separator + sub_dir + File.separator);

        Log.i("Does folder file exist", "File you are looking for is in " + folder);

        if (!folder.exists()) {
            Boolean status = folder.mkdirs();


            Log.i("Directory", "created?  " + status);


        } else { //folder already exist. search for file, if file, open else download

            String filename, secName = "";

            File[] listOfFiles = folder.listFiles();


            for (File listOfFile : listOfFiles) {

                if (!listOfFile.isDirectory() && !listOfFile.getName().startsWith(".") && !listOfFile.getName().startsWith("_")) {

                    filename = listOfFile.getName();
                    int pos = filename.lastIndexOf("_");

                    if (pos != -1) secName = filename.substring(0, pos);

                    Log.i("Files in Directory", folder + "\tName\t" + secName + "\tActivity name\t" + activity_name);


                    if (!dbh.doesSubSecNameExist(secName, activity_name))
                        dbh.insertSubSection(secName, activity_name);
                    else  Log.i("SubSecName", "A file with the subsec name " + secName + " already exist! skipping ...");


                }
            }


        }
    }



}


