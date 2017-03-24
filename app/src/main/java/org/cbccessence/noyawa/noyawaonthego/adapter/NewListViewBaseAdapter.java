package org.cbccessence.noyawa.noyawaonthego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.R;


public class NewListViewBaseAdapter extends BaseAdapter{
	private Context mContext;
	private final String[] category;
	private final int[] Imageid;

	public NewListViewBaseAdapter(Context c,String[] category,int[] Imageid ) {
		mContext = c;
		this.Imageid = Imageid;
		this.category = category;
	}
	@Override
	public int getCount() {

		return category.length;
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			list = new View(mContext);
			list = inflater.inflate(R.layout.menu_listview_single, null);

		} else {
			list = (View) convertView;
		}
		TextView textView2 = (TextView) list.findViewById(R.id.item_category);
		ImageView imageView = (ImageView)list.findViewById(R.id.imageView1);

		imageView.setImageResource(Imageid[position]);
		textView2.setText(category[position]);

		return list;
	}

}


