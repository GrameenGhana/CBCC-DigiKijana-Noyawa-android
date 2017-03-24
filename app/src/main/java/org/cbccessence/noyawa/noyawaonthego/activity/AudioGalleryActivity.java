package org.cbccessence.noyawa.noyawaonthego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;


import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.FirstTrimesterAudioBaseAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.application.Player;
import org.cbccessence.noyawa.noyawaonthego.application.URLMediaPlayerActivity;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.model.Content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioGalleryActivity extends BaseActivity implements OnItemClickListener {

	private ListView audioGrid;
	private Player player;
	private String path;
	private DatabaseHandler db;
	private String submodule;
	private String module;
	private String extras;
	private Intent intent;
	RelativeLayout emptyView;

	String subSecName = null;
	String dir = null;
	String type = null;

	List<Content> audioList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();

		subSecName = intent.getStringExtra("subSecName");
		dir = intent.getStringExtra("directory");
		type = intent.getStringExtra("type");


		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.first_trimester_gallery);

		audioList = new ArrayList<>();

		audioGrid=(ListView) findViewById(R.id.first_trimester_audio_gridview);

		emptyView = (RelativeLayout) findViewById(R.id.emptyView);
		if (emptyView.getVisibility() == View.VISIBLE) emptyView.setVisibility(View.GONE);

		player = new Player(AudioGalleryActivity.this);

		preparePlayer();
	}

	public void preparePlayer(){
		try{

			audioList =  getAudioList(dir, subSecName);
			if(audioList != null && audioList.size() > 0) {

				FirstTrimesterAudioBaseAdapter adapter = new FirstTrimesterAudioBaseAdapter(AudioGalleryActivity.this,  audioList);
				audioGrid.setAdapter(adapter);
				audioGrid.setOnItemClickListener(this);

			}else

			{
				emptyView.setVisibility(View.VISIBLE);

			}
		}catch(Exception e){
			e.printStackTrace();

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

		Intent intent=new Intent(AudioGalleryActivity.this, URLMediaPlayerActivity.class);
		intent.putExtra("fileName", audioList.get(position).getDocName());
		intent.putExtra(Noyawa.AUDIO_URL, audioList.get(position).getDocLoc());
		intent.putExtra(Noyawa.TYPE, type);
		intent.putExtra(Noyawa.SUB_MODULE, submodule);
		intent.putExtra(Noyawa.MODULE, module);
		intent.putExtra(Noyawa.EXTRAS,extras);
		startActivity(intent);
	}

}
