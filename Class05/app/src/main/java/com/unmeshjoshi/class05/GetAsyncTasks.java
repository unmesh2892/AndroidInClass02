package com.unmeshjoshi.class05;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class GetAsyncTasks extends AsyncTask<String,Void,LinkedList<String>> {

    GetAsync getAsync;
    public GetAsyncTasks(GetAsync getAsync){
        this.getAsync = getAsync;
    }

    @Override
    protected LinkedList<String> doInBackground(String... params) {
        LinkedList<String> lists = new LinkedList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        String result = "";
        try {
            URL url = new URL(" http://dev.theappsdr.com/apis/photos/keywords.php");

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(httpURLConnection.getInputStream(), "UTF-8");

            }
            StringTokenizer stringTokenizer = new StringTokenizer(result,";");

            while (stringTokenizer.hasMoreTokens()){
                lists.add(stringTokenizer.nextToken());
            }

            Log.d("stringTokenizer", ""+lists);




            return  lists;
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
    protected void onPostExecute(LinkedList<String> strings) {
        getAsync.getData(strings);
    }

    public static interface GetAsync{
        public void getData(LinkedList<String> imageList);
    }
}
