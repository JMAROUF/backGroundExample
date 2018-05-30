package com.example.jamal.backgroundexample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HandlerExample extends AppCompatActivity {

    private static final int DIALOG_ID=1;
    private static final int MAX_PROGRESS=100;
    private int progression = 0;
    private Button demarrer;
    private TextView textProgression;
    private ProgressBar mProgressBar;
    private Thread mProgress;

    // on crée un Handler dans le Thread principale
    final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            int valeur=msg.what;
            mProgressBar.setProgress(valeur);
            textProgression.setText(String.valueOf(valeur)+" ...");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        mProgressBar=(ProgressBar) findViewById(R.id.progressBar);
        demarrer=(Button) findViewById(R.id.demarrer);
        textProgression=(TextView) findViewById(R.id.progressionText);
        // on crée un nouveau Thread pour le travail en arriére plan
        mProgress=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while(progression<MAX_PROGRESS){
                        progression++;

                        Thread.sleep(500);

                        mProgressBar.setProgress(progression);


                        // on envoie un message au Thread principale à travers un Handler pour changer le graphisme
                        // puisqu'on ne doit pas changer changer le graphisme à travers un autre Thread
                        Message msg = mHandler.obtainMessage(progression);
                        mHandler.sendMessage(msg);
                    }
                    if(progression>=MAX_PROGRESS){
                        // cette methode sert à executer un traitement dans le Thread principale
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HandlerExample.this, "TELECHARGEMENT TERMINE ", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });

                    }

                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        demarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.start();
            }
        });

    }

 /*   @Override
    protected Object onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        return  this.mProgressBar;
    }*/
}
