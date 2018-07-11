package com.unmeshjoshi.inclass07;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity implements ShowAsyncTask.ShowList{

    ArrayList<Article> getListArticleArrayList = new ArrayList<>();
    static ProgressDialog progressDialogShow;
    public static String WEB_URL = "WEB_URL";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        progressDialogShow = new ProgressDialog(ShowActivity.this);

        String url = getIntent().getStringExtra(MainActivity.URL);

        new ShowAsyncTask(ShowActivity.this).execute(url);
    }

    @Override
    public void showSelected(ArrayList<Article> showSelected) {

        this.getListArticleArrayList=showSelected;
        ConstraintLayout constraintLayout = findViewById(R.id.list_show_id);

        final ShowAdapter adapter = new ShowAdapter(this, getListArticleArrayList);
// Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.listViewShow);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = adapter.getItem(position);
                String url = article.url;

                WebView webView = new WebView(view.getContext());
                setContentView(webView);

                webView.loadUrl(url);


            }
        });
    }
}
