package com.unmeshjoshi.midterm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetAsynTask extends AsyncTask<String, Void, ArrayList<App>> {

    HandleData handleData;

    public GetAsynTask(HandleData handleData){
        this.handleData = handleData;
    }

    @Override
    protected void onPreExecute() {
        Apps.progressDialog.setMessage("Loading Apps");
        Apps.progressDialog.setCancelable(false);
        Apps.progressDialog.setMax(10000);
        Apps.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Apps.progressDialog.show();
    }

    @Override
    protected ArrayList<App> doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ArrayList<App> resultApp = new ArrayList<>();
       // String result = "";
        try {
            URL url = new URL(strings[0]);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               String result = IOUtils.toString(httpURLConnection.getInputStream(), "UTF-8");

                JSONObject root = new JSONObject(result);
                JSONObject feed = root.getJSONObject("feed");
                App.copyright = feed.getString("copyright");
                JSONArray newsitems = feed.getJSONArray("results");
                for (int i=0;i<newsitems.length();i++) {
                    JSONObject newsItemJson = newsitems.getJSONObject(i);

                    App appItem = new App();

                    appItem.copyright = feed.getString("copyright");
                    appItem.AppName = newsItemJson.getString("name");
                    appItem.ArtistName = newsItemJson.getString("artistName");
                    appItem.urlToImage=newsItemJson.getString("artworkUrl100");
                    appItem.releaseDate=newsItemJson.getString("releaseDate");

                    JSONArray gener =  newsItemJson.getJSONArray("genres");
                   appItem.geners = new ArrayList<>();
                    for(int j = 0;j<gener.length();j++){
                        JSONObject generJson = gener.getJSONObject(j);
                        Generes generes = new Generes();
                        generes.genreId = generJson.getInt("genreId");
                        generes.name = generJson.getString("name");
                        generes.url = generJson.getString("url");

                        appItem.geners.add(generes);
                    }
                    resultApp.add(appItem);
                }
                Log.d("result app size",""+resultApp.size());
        return resultApp;
          }


        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }

            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultApp;
    }


    @Override
    protected void onPostExecute(ArrayList<App> strings) {

        if( Apps.progressDialog.isShowing()) {
            Apps.progressDialog.dismiss();
        }
        if(strings.size() > 0){
            Log.d("size",""+strings.size());
            handleData.getData(strings);
        }


    }

    public interface HandleData{
            public void getData(ArrayList<App> data);
    }

}
