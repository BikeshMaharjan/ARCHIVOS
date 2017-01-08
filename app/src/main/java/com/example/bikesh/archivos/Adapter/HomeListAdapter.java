package com.example.bikesh.archivos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikesh.archivos.R;

import java.util.ArrayList;

/**
 * Created by bikesh on 1/4/17.
 */

public class HomeListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mDataSource;


    public HomeListAdapter(Context context, ArrayList<String> items) {
        this.mContext = context;
        this.mDataSource = items;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public String getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String rowData = getItem(i);

        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.list_view_home, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.rowTextView = (TextView) view.findViewById(R.id.textView_list);
            viewHolder.rowImage = (ImageView) view.findViewById(R.id.image_listView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TextView rowText = viewHolder.rowTextView;
        ImageView rowImage = viewHolder.rowImage;
        rowText.setText(rowData);
        rowImage.setImageResource(R.mipmap.ic_folder);
        return view;
    }

    public static class ViewHolder {
        TextView rowTextView;
        ImageView rowImage;
    }
}
