package com.unmeshjoshi.class05;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowAsyncTask extends AsyncTask<String, Void, Void> {
    ImageView imageView;
    Bitmap bitmap = null;
    ProgressDialog progressDialog;
    MainActivity activity;

    public ShowAsyncTask(ImageView iv,MainActivity activity) {
        imageView = iv;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog.setMessage("Loading Photo");
        this.progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection connection = null;
        bitmap = null;

    for(int i =0;i<1000;i++){
        for(int j = 0 ; j<10000;j++){}

    }

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }
        }catch (MalformedURLException e) {
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
    protected void onPostExecute(Void aVoid) {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if (bitmap != null && imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
