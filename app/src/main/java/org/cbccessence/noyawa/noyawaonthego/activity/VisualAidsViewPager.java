package org.cbccessence.noyawa.noyawaonthego.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.VisualAidsBaseAdapter;
import org.cbccessence.noyawa.noyawaonthego.adapter.VisualAidsVideoBaseAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.application.Player;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.fragments.VisualAidsImagesFragment;
import org.cbccessence.noyawa.noyawaonthego.fragments.VisualAidsVideosFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class VisualAidsViewPager extends FragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

	private static Player player;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    setContentView(R.layout.visual_aids_gallery);

	    final ActionBar actionBar =getActionBar();
	    final PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.rgb(255, 149, 156));
        player=new Player(VisualAidsViewPager.this);
      //  actionBar.setTitle("Visual Aids");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager_visualAids);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
       
}
	 public class SectionsPagerAdapter extends FragmentPagerAdapter {

         public SectionsPagerAdapter(FragmentManager fm) {
                 super(fm);
         }

         @Override
         public Fragment getItem(int position) {
                 Fragment fragment = null;
                 if(position==0 ){
                        fragment= new VisualAidsImagesFragment();
                 }else if(position==1){
                	 fragment= new VisualAidsVideosFragment();
                 }else if(position ==2){

					 fragment= new VisualAidsVideosFragment();
				 }

                 return fragment;
         }

         @Override
         public int getCount() {
                 return 2;
         }

         @Override
         public CharSequence getPageTitle(int position) {
                 Locale l = Locale.getDefault();
                 switch (position) {
                         case 0:
                                 return "Images";
                         case 1:
                                 return "Videos";
                         
                 
                 }
                 return null;
         }
 }


}
