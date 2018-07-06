package com.unmeshjoshi.midterm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Apps extends AppCompatActivity implements GetAsynTask.HandleData{

    String arr[]= {"occasion","festival","the diwali"};
    String category="";
    static ProgressDialog progressDialog ;
    public static String KEY_DATA = "temApp";
    AlertDialog dialog;
    ArrayList<App> appItem = new ArrayList<>();
    ArrayList<App> otherItem = new ArrayList<>();
    ArrayList<String> generList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    final    TextView textViewSet = (TextView) findViewById(R.id.textViewSet);
        if(isConnected()){
            String url = " https://rss.itunes.apple.com/api/v1/us/ios-apps/top-free/all/50/explicit.json";
           progressDialog = new ProgressDialog(this);
           textViewSet.setText("All");
            new GetAsynTask(Apps.this).execute(url);
        }else{
            Toast.makeText(Apps.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

        findViewById(R.id. buttonGo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Apps.this).setTitle("Choose Genre");
                final String[] genres=generList.toArray(new String[generList.size()]);
                builder.setItems(genres, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.dismiss();
                        dialog.cancel();
                        dialog.setTitle("Choose Genre");
                        textViewSet.setText(genres[i]);

                        final ArrayList<App> data = filterGenre(genres[i]);

                        if (genres[i] == "All") {
                            AppAdapter adapter = new AppAdapter(Apps.this, R.layout.app_display, appItem);
                            ListView listView = (ListView) findViewById(R.id.ListView);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    App temApp = appItem.get(position);
                                    Intent intent = new Intent(Apps.this, DetailActivity.class);
                                    intent.putExtra(KEY_DATA, temApp);
                                    startActivity(intent);

                                }
                            });
                        } else {

                            AppAdapter adapter = new AppAdapter(Apps.this, R.layout.app_display, data);
                            ListView listView = (ListView) findViewById(R.id.ListView);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    App temApp = data.get(position);
                                    Intent intent = new Intent(Apps.this, DetailActivity.class);
                                    intent.putExtra(KEY_DATA, temApp);
                                    startActivity(intent);

                                }
                            });

                        }
                    }
                });
                dialog = builder.create();
                dialog.show();
            }

        });



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



    public ArrayList<App> filterGenre(String genre){
        Generes generes = new Generes();
        generes.name = genre;
        otherItem.clear();
        for(App app:appItem){

            for(Generes generes1:app.geners){
                if(generes1.name.contains(genre)){
                    otherItem.add(app);
                }

            }

        }

        return  otherItem;
    }



    @Override
    public void getData(ArrayList<App> data) {

        appItem.addAll(data);
       // otherItem.addAll(data);

        for (App app :
                appItem) {
            for (Generes gen :
                    app.geners) {
                if (!generList.contains(gen.name)){
                    generList.add(gen.name);
                }
            }
        }

        Collections.sort(generList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        generList.add(0,"All");
        ListView listView = (ListView)findViewById(R.id.ListView);
        AppAdapter adapter = new AppAdapter(this, R.layout.app_display, appItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App temApp =  appItem.get(position);
                Intent intent = new Intent(Apps.this,DetailActivity.class);
                intent.putExtra(KEY_DATA,temApp);
                startActivity(intent);
                finish();
         }
        });




    }
}
