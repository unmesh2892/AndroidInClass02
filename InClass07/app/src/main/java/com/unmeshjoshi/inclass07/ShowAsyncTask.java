package com.unmeshjoshi.inclass07;

import android.app.ProgressDialog;
import android.os.AsyncTask;

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

import javax.crypto.AEADBadTagException;

public class ShowAsyncTask  extends AsyncTask<String, Void, ArrayList<Article>> {

        ShowList showList;
        ArrayList<Article> articleArrayList = new ArrayList<>();


    public ShowAsyncTask(ShowList showList) {

        this.showList=showList;

    }

    @Override
    protected void onPreExecute() {
        ShowActivity.progressDialogShow.setMessage("Loading Stories");
        ShowActivity.progressDialogShow.setCancelable(false);
        ShowActivity.progressDialogShow.setMax(10000);
        ShowActivity.progressDialogShow.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ShowActivity.progressDialogShow.show();
    }

    @Override
    protected ArrayList<Article> doInBackground(String... strings) {

        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(strings[0]);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result = IOUtils.toString(httpURLConnection.getInputStream(), "UTF-8");

                JSONObject root= new JSONObject(result);
                JSONArray arrayArticle = root.getJSONArray("articles");
                for(int i =0;i<arrayArticle.length();i++){
                    JSONObject articleObject = arrayArticle.getJSONObject(i);

                    Article article = new Article();
                    if(articleObject.getString("author").isEmpty() || articleObject.getString("author") == null){
                        article.author = " ";
                    }else {
                        article.author = articleObject.getString("author");
                    }

                    if(articleObject.getString("title").isEmpty() || articleObject.getString("title") == null){
                        article.title = "";
                    }else{
                        article.title = articleObject.getString("title");

                    }

                    if(articleObject.getString("publishedAt").isEmpty() || articleObject.getString("publishedAt") == null){
                        article.publishedAt = "";
                    }else{
                        article.publishedAt = articleObject.getString("publishedAt");

                    }

                    if(articleObject.getString("urlToImage") == null || articleObject.getString("urlToImage").isEmpty()){
                        article.urlToImage="";
                    }else{
                        article.urlToImage = articleObject.getString("urlToImage");
                    }


                    if(articleObject.getString("url").isEmpty() || articleObject.getString("url") == null){
                        article.url = "";
                    }else{
                        article.url = articleObject.getString("url");

                    }

                    articleArrayList.add(article);

                }


            }

            return articleArrayList;
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


        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        if(ShowActivity.progressDialogShow.isShowing()){
            ShowActivity.progressDialogShow.dismiss();
        }
        showList.showSelected(articles);

    }

    public interface ShowList{
        public void showSelected(ArrayList<Article> showSelected);
    }
}
