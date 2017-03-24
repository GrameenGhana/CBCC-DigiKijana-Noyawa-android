package org.cbccessence.noyawa.noyawaonthego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.PlaceHolder;
import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.TrimesterListViewAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;
import org.cbccessence.noyawa.noyawaonthego.application.Noyawa;
import org.cbccessence.noyawa.noyawaonthego.database.DatabaseHandler;
import org.cbccessence.noyawa.noyawaonthego.model.SubSection;

import java.util.ArrayList;
import java.util.List;

import static org.cbccessence.noyawa.noyawaonthego.application.Noyawa.*;


public class SecondTrimesterActivity extends BaseActivity implements OnItemClickListener{
	private ListView listView;
	private TextView header;
	private String type;
	private String submodule;
	private String module;
	private String extras;
	List<SubSection> subSecs;
	String TAG = SecondTrimesterActivity.class.getSimpleName();
	String dir = PM_SECOND_TRE;

	 DatabaseHandler dbh;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		getSubSectionNamesFromLocation(this, dir, TAG);
		dbh = new  DatabaseHandler(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trimester_menu_activity);

		if(getSupportActionBar() != null) getSupportActionBar().setSubtitle("Second Trimester");

		subSecs = new ArrayList<>();


		listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
		header=(TextView) findViewById(R.id.textView1);

		subSecs = dbh.getSubSections(TAG, dir);


		int[] images={
				R.drawable.birth_preparedness,
				R.drawable.warning_signs,
				R.drawable.pregnancymalaria,
				R.drawable.nutrition,
				R.drawable.handwashing
		};


		if(subSecs != null && subSecs.size() > 0) {
			header = (TextView) findViewById(R.id.textView1);

			TrimesterListViewAdapter adapter = new TrimesterListViewAdapter(SecondTrimesterActivity.this, subSecs, images);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);

		}else{
			PlaceHolder.inflateNoContentEmptyView(this);
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

		Intent intent = new Intent( this, AudioGalleryActivity.class );
		intent.putExtra("subSecName", subSecs.get(position).getSubSectionName());
		intent.putExtra("directory", dir);
		intent.putExtra("type", "Audio");
		startActivity(intent);

	}

}
