package org.cbccessence.noyawa.noyawaonthego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.cbccessence.noyawa.noyawaonthego.R;

/**
 * Created by mac on 1/25/16.
 */
public class MenuBaseAdapter extends BaseAdapter {

    private Context mContext;
    private final int[] Imageid;

    public MenuBaseAdapter(Context c,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;

    }
    @Override
    public int getCount() {

        return Imageid.length;
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
        View grid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

        } else {
            grid = (View) convertView;
        }
        ImageView imageView = (ImageView)grid.findViewById(R.id.new_grid_imageview);
        imageView.setImageResource(Imageid[position]);
        imageView.setMaxHeight(250);
        imageView.setBackgroundResource(R.color.trans);
        return grid;
    }

}