package com.example.luthf.projectqroctowallet.history;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luthf.projectqroctowallet.PromoActivity;
import com.example.luthf.projectqroctowallet.R;

import java.util.List;

/**
 * Created by luthf on 9/29/2015.
 */
public class    EventAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> movieItems;

    public EventAdapter(Activity activity, List<Event> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.event_row, null);





        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView letter = (ImageView) convertView.findViewById(R.id.letter);

        // getting movie data for the row
        Event m = movieItems.get(position);


        // title
        title.setText(m.getTitle());

        // rating
        desc.setText(m.getDesc());

        // release year
        date.setText(m.getDate());

        name.setText(m.getThumbnailUrl());



        return convertView;
    }

}
