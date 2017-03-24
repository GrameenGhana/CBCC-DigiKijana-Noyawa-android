package org.cbccessence.noyawa.noyawaonthego.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cbccessence.noyawa.noyawaonthego.R;
import org.cbccessence.noyawa.noyawaonthego.model.SubSection;

import java.util.List;


public class TrimesterListViewAdapter extends BaseAdapter {
    private final List<SubSection> category;
    private final int[] Imageid;
    private Context mContext;

    public TrimesterListViewAdapter(Context c, List<SubSection> category, @Nullable  int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.category = category;
    }

    @Override
    public int getCount() {

        return category.size();
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

        View list = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list = new View(mContext);
            list = inflater.inflate(R.layout.trimester_listview_single, null);
        } else {
            list = (View) convertView;
        }


        TextView textView2 = (TextView) list.findViewById(R.id.textView1);
        ImageView imageView = (ImageView) list.findViewById(R.id.imageView1);


        textView2.setText(category.get(position).getSubSectionName());

        return list;
    }

}


