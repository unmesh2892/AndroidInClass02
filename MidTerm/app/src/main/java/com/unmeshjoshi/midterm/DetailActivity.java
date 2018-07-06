package com.unmeshjoshi.midterm;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        App getAppData = (App) getIntent().getExtras().getSerializable(Apps.KEY_DATA);

        TextView textName = (TextView) findViewById(R.id.textViewName);
        TextView textDate = (TextView)findViewById(R.id.textViewDate);
        TextView textGenre = (TextView)findViewById(R.id.textViewGenres);
        TextView textViewArtist =(TextView)findViewById(R.id.textViewArtist);
        TextView textCopy = (TextView)findViewById(R.id.textViewCopy);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        textName.setText(getAppData.AppName);
        textDate.setText(getAppData.releaseDate);
        textViewArtist.setText(getAppData.ArtistName);
        textCopy.setText(App.copyright);
        StringBuilder stringBuilder = new StringBuilder();
        for(Generes generes:getAppData.geners){
            stringBuilder.append(generes.name+",");
        }
        textGenre.setText(stringBuilder.toString());

        if (getAppData.urlToImage != null && !getAppData.urlToImage.isEmpty()) {
            Picasso.with(DetailActivity.this).load(getAppData.urlToImage.toString()).into(imageView);
        } else {
            Picasso.with(DetailActivity.this).load(R.drawable.not_found);
        }


    }
}
