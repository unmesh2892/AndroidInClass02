package com.unmeshjoshi.inclass04;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Handler handler;

    ExecutorService threadPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.buttonAsyncTask).setOnClickListener(new View.OnClickListener() {

     @Override
            public void onClick(View v) {

                new WorkAsync((ImageView) findViewById(R.id.imageView)).execute(" https://cdn.pixabay.com/photo/2014/12/16/22/25/youth-570881_960_720.jpg");
            }
        });


        threadPool = Executors.newFixedThreadPool(4);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);

        findViewById(R.id.buttonThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {

                       ImageView myImageView =(ImageView) findViewById(R.id.imageView);


                        switch (msg.what){
                            case ThreadLoad.STATUS_START:
                                Log.d("demo","message started");
                                progressBar.setProgress(0);


                                break;
                            case ThreadLoad.STATUS_PROGRESS:


                                progressBar.setProgress(msg.getData().getInt(ThreadLoad.PROGRESS_KEY));
                                progressBar.isShown();
                                Log.d("demo","message progressed"+msg.getData().getInt(ThreadLoad.PROGRESS_KEY));

                                break;

                           case ThreadLoad.STATUS_STOP:

                               myImageView.setImageBitmap((Bitmap) msg.obj);
                                Log.d("demo","message stopped");
                                progressBar.setProgress(0);

                                break;
                        }



                        return false;
                    }
                });

                threadPool.execute(new ThreadLoad());
            }
        });



    }
    class ThreadLoad implements  Runnable{

        static final int STATUS_START=0X00;
        static final int STATUS_PROGRESS=0X01;
        static final int STATUS_STOP=0X02;
        static final String PROGRESS_KEY = "progress";
        @Override
        public void run() {

            Message startMessage = new Message();
            startMessage.what=STATUS_START;
            handler.sendMessage(startMessage);
            try {

                Message stopMessage = new Message();
               // message.what = STATUS_PROGRESS;


                URL url = new URL("https://cdn.pixabay.com/photo/2017/12/31/06/16/boats-3051610_960_720.jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                stopMessage.obj = myBitmap;

                for(int i = 0;i<100;i++){
                    for(int j = 0;j<10000000;j++){

                    }
                    Message message = new Message();
                    message.what=STATUS_PROGRESS;
                    message.obj = (Integer)i;

                    Bundle bundle = new Bundle();
                    bundle.putInt(PROGRESS_KEY,(Integer)i+1);

                    message.setData(bundle);
                    handler.sendMessage(message);
                }

                stopMessage.what = STATUS_STOP;
                handler.sendMessage(stopMessage);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



    class WorkAsync extends AsyncTask<String, Integer,Bitmap>{
        ImageView myImageView;

        public WorkAsync(ImageView myImageView) {
          this.myImageView = myImageView;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            //progressBar.("Updating progress");
            progressBar.setMax(100);
            progressBar.isShown();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            myImageView.setImageBitmap(bitmap);
            progressBar.setProgress(0);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //progressBar.setProgress();
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            double sum=0;
            double count = 0;
            Random random = new Random();

            for(int i=0;i<100;i++ ){
                for(int j = 0 ;j<1000000;j++){
                    count++;

                }
                publishProgress(i+1);
            }
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}
