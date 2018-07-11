package com.unmeshjoshi.inclass07;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowAdapter extends ArrayAdapter<Article> {
    public ShowAdapter(@NonNull Context context, ArrayList<Article> resources) {
        super(context, 0,resources);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_show, parent, false);

        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewAuthor = convertView.findViewById(R.id.textViewAuthor);
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        textViewTitle.setText(article.title);
        textViewAuthor.setText(article.author);
        textViewDate.setText(article.publishedAt);

        if (article.urlToImage != null && !article.urlToImage.isEmpty()) {
            Picasso.with(convertView.getContext()).load(article.urlToImage.toString()).into(imageView);
        } else {
            Picasso.with(convertView.getContext()).load(R.drawable.not_found).into(imageView);
        }





        return convertView;
    }
}
