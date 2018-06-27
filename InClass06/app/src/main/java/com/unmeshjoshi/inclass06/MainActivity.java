package com.unmeshjoshi.inclass06;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAsyncTask.HandleData {

    ImageView next;
    ImageView prev;
    Spinner spinner;
    ImageView images;
    TextView newsTitle;
    TextView publishedAt;
    TextView description;
    TextView outOf;
    TextView number;
    TextView total;
    static ProgressDialog progressDialog;

    int count=0;
    String url="";
    static ArrayList<Article> mainArticleList = new ArrayList<>();

    String arr[] = {"Business","Entertainment","General","Health","Science"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.imageViewNext);
        prev = findViewById(R.id.imageViewPrev);
        images = findViewById(R.id.imageViewNews);
        newsTitle = findViewById(R.id.textViewTitle);
        publishedAt = findViewById(R.id.textViewPublishedAt);
        description = findViewById(R.id.textViewDescription);
        outOf = findViewById(R.id.textViewOutOf);
        number = findViewById(R.id.textViewNumber);
        total = findViewById(R.id.textViewTotal);


        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {

                    Toast.makeText(MainActivity.this, "Internet Connection", Toast.LENGTH_SHORT).show();

                    spinner = findViewById(R.id.spinner);

                    getSpinnerValues();

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String spinnerText = spinner.getSelectedItem().toString();
                            url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=6251d48fcf914a19ada6a160f449ca0e&category="+spinnerText;
                            Log.d("url" ," "+url);
                            new GetAsyncTask(MainActivity.this).execute(url);
                            count=0;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count<=mainArticleList.size()-1  ){
                    newsTitle.setText(mainArticleList.get(count).getTitle());
                    outOf.setVisibility(View.VISIBLE);
                    outOf.setEnabled(true);
                    publishedAt.setText(mainArticleList.get(count).getPublishedAt());
                    Picasso.with(MainActivity.this).load(mainArticleList.get(count).getUrlToImage()).into(images);
                    images.setVisibility(View.VISIBLE);
                    description.setText(mainArticleList.get(count).getDescription());
                    number.setText(""+(String.valueOf(count+1)));
                    total.setText(String.valueOf(mainArticleList.size()));
                    count++;
                }else{
                    count=0;
                    newsTitle.setText(mainArticleList.get(count).getTitle());
                    outOf.setVisibility(View.VISIBLE);
                    outOf.setEnabled(true);
                    publishedAt.setText(mainArticleList.get(count).getPublishedAt());
                    Picasso.with(MainActivity.this).load(mainArticleList.get(count).getUrlToImage()).into(images);
                    images.setVisibility(View.VISIBLE);
                    description.setText(mainArticleList.get(count).getDescription());
                    number.setText(""+(String.valueOf(count+1)));
                    total.setText(String.valueOf(mainArticleList.size()));
                    count++;

                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count >=0){
                    newsTitle.setText(mainArticleList.get(count).getTitle());
                    outOf.setVisibility(View.VISIBLE);
                    outOf.setEnabled(true);
                    publishedAt.setText(mainArticleList.get(count).getPublishedAt());
                    Picasso.with(MainActivity.this).load(mainArticleList.get(count).getUrlToImage()).into(images);
                    images.setVisibility(View.VISIBLE);
                    description.setText(mainArticleList.get(count).getDescription());
                    number.setText(""+(String.valueOf(count+1)));
                    total.setText(String.valueOf(mainArticleList.size()));
                    count--;
                }else{
                    count = mainArticleList.size()-1;
                    outOf.setVisibility(View.VISIBLE);
                    newsTitle.setText(mainArticleList.get(count).getTitle());
                    outOf.setEnabled(true);
                    publishedAt.setText(mainArticleList.get(count).getPublishedAt());
                    Picasso.with(MainActivity.this).load(mainArticleList.get(count).getUrlToImage()).into(images);
                    images.setVisibility(View.VISIBLE);
                    description.setText(mainArticleList.get(count).getDescription());
                    number.setText(""+(String.valueOf(count+1)));
                    total.setText(String.valueOf(mainArticleList.size()));
                    count--;
                }
            }
        });

    }








    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {

            return false;
        }

        return true;
    }

    public void getSpinnerValues(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    @Override
    public void getResult(ArrayList<Article> articleResult) {
        if(articleResult.size() < 2){
            next.setEnabled(false);
            prev.setEnabled(false);
        }else{
            next.setEnabled(true);

            prev.setEnabled(true);
        }
        if(articleResult.size() != 0){
            mainArticleList = articleResult;
            newsTitle.setText(mainArticleList.get(count).getTitle());
            outOf.setVisibility(View.VISIBLE);
            publishedAt.setText(mainArticleList.get(count).getPublishedAt());
            Picasso.with(MainActivity.this).load(mainArticleList.get(count).getUrlToImage()).into(images);
            images.setVisibility(View.VISIBLE);
            description.setText(mainArticleList.get(count).getDescription());
            number.setText(""+(String.valueOf(count+1)));
            total.setText(String.valueOf(mainArticleList.size()));
            count++;
        }
        else{
            Toast.makeText(this, "No News Found", Toast.LENGTH_SHORT).show();
        }


    }
}
