package com.unmeshjoshi.class05;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements GetAsyncTasks.GetAsync, RequestAsyncTask.RequestAsync {
    LinkedList<String> data = new LinkedList<>();
    LinkedList<String> dataURL = new LinkedList<>();
    StringBuilder stringBuilder;
    Spinner spinner;
    String url = "";
    ImageView imageView;
    static int counter = 0;
    ImageView next;
    ImageView prev;
    static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (ImageView) findViewById(R.id.Next);
        prev = (ImageView) findViewById(R.id.Prev);
        if (isConnected()) {

            Toast.makeText(MainActivity.this, "Internet Connection", Toast.LENGTH_SHORT).show();
            new GetAsyncTasks(MainActivity.this).execute();

        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {

                    Toast.makeText(MainActivity.this, "Internet Connection", Toast.LENGTH_SHORT).show();

                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    String text = spinner.getSelectedItem().toString();
                    String selectedClass = spinner.getSelectedItem().toString();
                    Log.d("selected class", "" + selectedClass);
                    url = "http://dev.theappsdr.com/apis/photos/index.php?keyword=" + selectedClass;
                    Log.d("url", "" + url);

                    try {

                        Object result =   new RequestAsyncTask(MainActivity.this,MainActivity.this).execute(url).get();
                        dataURL = (LinkedList<String>) result;
                        validateButtons(counter, dataURL.size());


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Log.d("counter vlaue", "" + counter + "   " + dataURL.size());



            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter++;
                        validateButtons(counter, dataURL.size());
                        Log.d("in counter", "" + counter);


                    if(counter == dataURL.size()){
                        counter =0;
                    }
                        imageView = (ImageView) findViewById(R.id.imageView);
                        new ShowAsyncTask(imageView,MainActivity.this).execute(dataURL.get(counter));

                    }

            });



            findViewById(R.id.Prev).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    validateButtons(counter, dataURL.size());

                    if(counter == 0){
                        counter = dataURL.size();
                    }
                    --counter;
                        imageView = (ImageView) findViewById(R.id.imageView);

                        new ShowAsyncTask(imageView,MainActivity.this).execute(dataURL.get(counter));

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

    @Override
    public void getData(LinkedList<String> data) {
        this.data = data;
        Log.d("data in spinner", "" + data);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


    }


    @Override
    public void getURL(LinkedList<String> urlList) {
        this.dataURL = urlList;
        counter = 0;
        if(dataURL.size() == 0){
            Toast.makeText(MainActivity.this, "No Images Found", Toast.LENGTH_SHORT).show();
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setVisibility(View.INVISIBLE);
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        }else {

            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setVisibility(View.VISIBLE);
            new ShowAsyncTask(imageView, MainActivity.this).execute(dataURL.get(counter));
        }
}

public void validateButtons(int currentCounter, int listSize) {
    Log.d("dataurl", "" + dataURL);

    if (listSize == 1) {
        next.setVisibility(View.INVISIBLE);
        prev.setVisibility(View.INVISIBLE);
    }
    if (counter > 0) {
        prev.setVisibility(View.VISIBLE);
    }
    if (counter == 0) {
        next.setVisibility(View.VISIBLE);
    }
}

}
