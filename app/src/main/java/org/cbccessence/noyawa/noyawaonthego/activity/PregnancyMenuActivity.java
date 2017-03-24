package org.cbccessence.noyawa.noyawaonthego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.adapter.NewListViewBaseAdapter;
import org.cbccessence.noyawa.noyawaonthego.application.BaseActivity;

public class PregnancyMenuActivity extends BaseActivity implements OnItemClickListener{

	private ListView listView;

	int[] images={
			R.drawable.first,
			R.drawable.second,
			R.drawable.third
	};

	private TextView header;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pregnancy_menu_activity);


		header=(TextView) findViewById(R.id.textView1);

		listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
		String[] values={"1st Trimester","2nd Trimester","3rd Trimester"};
		NewListViewBaseAdapter adapter=new NewListViewBaseAdapter(PregnancyMenuActivity.this,values,images);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		Intent intent;
		switch (position){
			case 0:
				intent=new Intent(PregnancyMenuActivity.this, FirstTrimesterActivity.class);
				startActivity(intent);
				break;
			case 1	:
				intent=new Intent(PregnancyMenuActivity.this, SecondTrimesterActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent=new Intent(PregnancyMenuActivity.this, ThirdTrimesterActivity.class);
				startActivity(intent);
				break;
		}

	}





}
