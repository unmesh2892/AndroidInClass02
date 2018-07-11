package com.unmeshjoshi.inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetListAsyncTask.SetData {


    static ProgressDialog progressDialog;

    ArrayList<Source> getList = new ArrayList<>();
    ArrayList<String> getListNames = new ArrayList<>();

    public static String URL="URL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            if(isConnected()){
                progressDialog = new ProgressDialog(this);



                String url = "https://newsapi.org/v2/sources?apiKey=6251d48fcf914a19ada6a160f449ca0e";

                new GetListAsyncTask(MainActivity.this).execute(url);



            }else{

                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

    }




    private boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwi = cm.getActiveNetworkInfo();

        if(nwi == null || !nwi.isConnected() ||
                ((nwi.getType() != ConnectivityManager.TYPE_WIFI) &&
                        nwi.getType() != ConnectivityManager.TYPE_MOBILE))
        {
            Toast.makeText(this, "No internet access", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void getData(ArrayList<Source> data) {


        ListView listView = findViewById(R.id.listView);
        this.getList=data;
        for(Source source:getList){
            getListNames.add(source.name);
            Log.d("names" , ""+source.name);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,getListNames);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = adapter.getItem(position);
                String url = "https://newsapi.org/v2/everything?apiKey=6251d48fcf914a19ada6a160f449ca0e&sources="+item;
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra(URL,url);
                startActivity(intent);



            }
        });
 }


}
