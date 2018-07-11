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

public class GetListAsyncTask extends AsyncTask<String, Void, ArrayList<Source>> {

    SetData setData;


    public GetListAsyncTask(SetData mainActivity) {
        this.setData= mainActivity;
    }

    @Override
    protected void onPreExecute() {
        MainActivity.progressDialog.setMessage("Loading Sources");
        MainActivity.progressDialog.setCancelable(false);
        MainActivity.progressDialog.setMax(10000);
        MainActivity.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        MainActivity.progressDialog.show();
    }

    @Override
    protected ArrayList<Source> doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ArrayList<Source> listSources = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result = IOUtils.toString(httpURLConnection.getInputStream(), "UTF-8");

                JSONObject root = new JSONObject(result);
               // JSONObject object = root.getJSONObject("object");
                JSONArray itemList = root.getJSONArray("sources");
                for(int i=0;i<itemList.length();i++){
                    JSONObject itemObject = itemList.getJSONObject(i);

                    Source source = new Source();
                    source.name = itemObject.getString("name");
                    source.id = itemObject.getString("id");

                    listSources.add(source);


                }

        return  listSources;
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



        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<Source> strings) {

        if( MainActivity.progressDialog.isShowing()) {
            MainActivity.progressDialog.dismiss();
        }
            setData.getData(strings);
    }

   public interface SetData{
        public void getData(ArrayList<Source> data);
    }
}
