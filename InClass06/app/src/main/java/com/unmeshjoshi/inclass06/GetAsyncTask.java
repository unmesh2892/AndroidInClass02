package com.unmeshjoshi.inclass06;

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

public class GetAsyncTask extends AsyncTask<String, Void, ArrayList<Article>> {

    HandleData handleData;
    ProgressDialog asyncProgressDialog;


    public GetAsyncTask(MainActivity mainActivity) {
        this.handleData = mainActivity;
        this.asyncProgressDialog=new ProgressDialog(mainActivity);
    }

    @Override
    protected void onPreExecute() {
        asyncProgressDialog.setMessage("Loading News");
        asyncProgressDialog.setCancelable(false);
        asyncProgressDialog.setMax(10000);
        asyncProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncProgressDialog.show();
    }

    @Override
    protected ArrayList<Article> doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ArrayList<Article> resultArticle = new ArrayList<>();
        String result = "";
        try {
            URL url = new URL(strings[0]);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(httpURLConnection.getInputStream(), "UTF-8");

                JSONObject root = new JSONObject(result);
                JSONArray articlesJsonArray = root.getJSONArray("articles");


                for(int i=0;i<articlesJsonArray.length();i++){
                    JSONObject articleJson = articlesJsonArray.getJSONObject(i);
                    Article article = new Article();
                    article.author = articleJson.getString("author");
                    article.title = articleJson.getString("title");
                    if(articleJson.getString("description").length() == 0){
                        article.description = " ";
                    }else{
                        article.description = articleJson.getString("description");
                    }
                    article.url = articleJson.getString("url");
                    article.urlToImage = articleJson.getString("urlToImage");
                    article.publishedAt = articleJson.getString("publishedAt");

                    JSONObject sourceJson = articleJson.getJSONObject("source");
                    Source source = new Source();
                    source.id= sourceJson.getString("id");
                    source.name = sourceJson.getString("name");

                    article.source = source;

                    Log.d("article",""+article.toString());
                    resultArticle.add(article);

                }
           }

            return  resultArticle;
        } catch (MalformedURLException e) {
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
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<Article> strings) {
        if(asyncProgressDialog.isShowing()){
            asyncProgressDialog.dismiss();
        }

        handleData.getResult(strings);
    }

    public static interface HandleData{
        public void getResult(ArrayList<Article> articleResult);
    }
}
