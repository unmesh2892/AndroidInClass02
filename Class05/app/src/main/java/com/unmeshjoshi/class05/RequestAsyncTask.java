package com.unmeshjoshi.class05;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RequestAsyncTask extends AsyncTask<String,Void,LinkedList<String>> {

    RequestAsync requestAsync;
    MainActivity activity;
    ProgressDialog progressDialog;

    public RequestAsyncTask(RequestAsync requestAsync,MainActivity activity){
        this.requestAsync = requestAsync;
        this.activity = (MainActivity) requestAsync;
        progressDialog = new ProgressDialog(this.activity);
    }

    @Override
    protected void onPreExecute() {
            this.progressDialog.setMessage("Loading Dictionary...");
            this.progressDialog.show();
    }

    @Override
    protected LinkedList<String> doInBackground(String... params) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        LinkedList<String> urlList =  new LinkedList<>();
        for(int i =0;i<10000;i++){
            for(int j = 0 ; j<10000;j++){}

        }

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";

            while((line = bufferedReader.readLine()) != null){

                urlList.add(line);

            }
            Log.d("the list is",""+urlList);
          return urlList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }


        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
        requestAsync.getURL(strings);

      if(progressDialog.isShowing()){
          progressDialog.dismiss();
      }

    }

    public static interface RequestAsync{
        public void getURL(LinkedList<String> urlList);
    }

}
