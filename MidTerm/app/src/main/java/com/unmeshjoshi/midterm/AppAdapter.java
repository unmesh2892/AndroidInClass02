package com.unmeshjoshi.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppAdapter extends ArrayAdapter<App> {
    public AppAdapter(Context context, int resource, ArrayList<App> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        App app = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_display, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.textViewAppName);
        TextView date = (TextView) convertView.findViewById(R.id.textViewReleaseDate);
        TextView artistName = (TextView) convertView.findViewById(R.id.textViewArtistName);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
        TextView tvGen = (TextView) convertView.findViewById(R.id.textViewGenre);
        title.setText(app.AppName);
        date.setText(app.releaseDate);
        artistName.setText(app.ArtistName);

        if (app.urlToImage != null && !app.urlToImage.isEmpty()) {
            Picasso.with(convertView.getContext()).load(app.urlToImage.toString()).into(image);
        } else {
            Picasso.with(convertView.getContext()).load(R.drawable.not_found);
        }
        //set the data from the news object
        StringBuilder sb = new StringBuilder();
        for (Generes gen : app.geners
                ) {
            sb.append(gen.name + ",");
        }

        tvGen.setText(sb.toString());
        return convertView;
    }


}
