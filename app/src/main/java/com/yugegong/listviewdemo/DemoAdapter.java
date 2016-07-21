package com.yugegong.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple adapter to provide data for {@link android.widget.ListView}
 * @author Yuge Gong
 * @since 2016-07-09
 */
public class DemoAdapter extends ArrayAdapter<DemoData> {

    private LayoutInflater mInflater;
    class ViewHolder {
        TextView textViewPosition;
        ViewHolder(View view) {
            textViewPosition = (TextView) view.findViewById(R.id.list_number);
        }
    }
    public DemoAdapter(Context context, int resource, CachedList objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(getContext());
        objects.setAdapter(this);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        DemoData data = getItem(position);
        if (data == null) {
            holder.textViewPosition.setText("Loading Data...");
        } else {
            holder.textViewPosition.setText(data.getDataString());
        }
        return  convertView;
    }

}
