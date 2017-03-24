package org.cbccessence.noyawa.noyawaonthego.fragments;

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
import org.cbccessence.noyawa.noyawaonthego.activity.VisualAidsGalleryView;
import org.cbccessence.noyawa.noyawaonthego.adapter.VisualAidsImagesAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.GridSpacesItemDecoration;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.model.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 17/02/2017.
 */

public class VisualAidsImagesFragment extends Fragment {

    View rootView;
    RecyclerView mRecycler;
    VisualAidsImagesAdapter mAdapter;

    //Same model can be used for images
    private List<Content> images;
    String TAG = VisualAidsImagesFragment.class.getSimpleName();






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.visual_aids_image_gallery, null, false);

        mRecycler = (RecyclerView) rootView.findViewById(R.id.va_images_recyclerView);
        images = new ArrayList<>();

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
        GridSpacesItemDecoration decoration = new GridSpacesItemDecoration(16);
        mRecycler.addItemDecoration(decoration);

        images = BaseActivity.getAssetFromLocation(Noyawa.VA_IMA_DIR);


        if (images != null){
            mAdapter = new VisualAidsImagesAdapter(getActivity().getApplicationContext(), images);
            mRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mAdapter.setOnItemClickListener(onItemClickListener);
        }

        else PlaceHolder.inflateNoContentEmptyView((AppCompatActivity) getActivity().getApplicationContext());







    }


    VisualAidsImagesAdapter.OnItemClickListener onItemClickListener = new VisualAidsImagesAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {


            //get section Id, start subsections activity with section id, get sub sections from db which has section_id = section_id you clicked :)
            Content doc = images.get(position);

            String file_name = doc.getDocName();
            String location = doc.getDocLoc();



            Log.i(TAG, "Absolute path is  " + location);

            Intent intent = new Intent(getActivity(), VisualAidsGalleryView.class);
            intent.putExtra("image_location", location);
            startActivity(intent);




        }
    };




}
