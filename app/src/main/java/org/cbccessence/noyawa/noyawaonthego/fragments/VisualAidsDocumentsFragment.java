package org.cbccessence.noyawa.noyawaonthego.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.cbccessence.noyawa.noyawaonthego.PlaceHolder;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.activity.VisualAidsPdfView;
import org.cbccessence.noyawa.noyawaonthego.adapter.VisualAidsDocumentAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.GridSpacesItemDecoration;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.model.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 17/02/2017.
 */

public class VisualAidsDocumentsFragment extends Fragment {

    View rootView;
    RecyclerView mRecycler;
     private List<Content> documents;
    static VisualAidsDocumentAdapter adapter;
    Integer pageNumber = 0;

     String TAG = "Visual Aids Doc Frag";


    View view;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.visual_aids_documents_layout, null, false);

         mRecycler = (RecyclerView) rootView.findViewById(R.id.va_recyclerView);

        documents = new ArrayList<>();


        return rootView;

    }





    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);


        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(mStaggeredLayoutManager);
        mRecycler.setHasFixedSize(true);
        GridSpacesItemDecoration decoration = new GridSpacesItemDecoration(24);
        mRecycler.addItemDecoration(decoration);

        documents = BaseActivity.getAssetFromLocation(Noyawa.VA_DOC_DIR);


        if (documents != null){
            adapter = new VisualAidsDocumentAdapter(getActivity(), documents);
        mRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(onItemClickListener);
        }

        else inflateNoContentEmptyView();

    }







    VisualAidsDocumentAdapter.OnItemClickListener onItemClickListener = new VisualAidsDocumentAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {


            //get section Id, start subsections activity with section id, get sub sections from db which has section_id = section_id you clicked :)
            Content doc = documents.get(position);

            String file_name = doc.getDocName();
            String location = doc.getDocLoc();



            Log.i(TAG, "Absolute path is  " + location);

            Intent intent = new Intent(getActivity(), VisualAidsPdfView.class);
            intent.putExtra("pdf_location", location);
            startActivity(intent);




        }
    };





    public void inflateNoContentEmptyView() {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.empty_view_no_content, null);
            getActivity().getWindow().addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            view.findViewById(R.id.login_button).setVisibility(View.GONE);

        }
    }


    @Override
    public void onStop() {
        super.onStop();

        if (view != null) {
            try {
                ((ViewGroup) view.getParent()).removeView(view);
            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
            }
        }
    }



}






