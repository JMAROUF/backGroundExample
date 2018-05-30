package com.example.jamal.backgroundexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class AsynckExample extends AppCompatActivity {



    TextView txtString,timeText;

    private  String url= "http://192.168.43.30/tdi/tdiPage.php";
    //String url="https://api.myjson.com/bins/134856";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynck_example);
        txtString= (TextView)findViewById(R.id.txtString);
        timeText=(TextView) findViewById(R.id.timeText);
        timeText.setText("demarrer l'asyncktask");


// si vous enlevez ces commentaires vous allez génerer l'erreur NetworkOnMainThreadException
        //puisqu'on ne peut pas ouvrir une connexion internet au sein du Thread principale
        // alors on le fait dans une AsynckTask

        /*
        OkHttpClient client = new OkHttpClient();
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        okHttpHandler.execute(url);
        */

        OkHttpAsynck okHttpAsynck= new OkHttpAsynck();
        okHttpAsynck.execute(url);
    }

    public class OkHttpAsynck extends AsyncTask<String,Integer,String> {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = new OkHttpClient();
        ArrayList<Protocol> protocols = new ArrayList<Protocol>();

        // juste avant l'execution
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            protocols.add(Protocol.HTTP_1_1);
            protocols.add(Protocol.HTTP_2);
            builder.protocols(protocols);
            client = builder.build();
            timeText.setText("Debut ");
        }

        //cette methode s'execute dans un autre Thread
        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                int temps= (client.readTimeoutMillis()/1000);
                for(int i=0;i<temps;i++){
                    Thread.sleep(1000);
                    publishProgress(i);
                }
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            timeText.setText("temps écoulé en secondes : "+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtString.setText(s);
        }


    }
}
