package com.example.jamal.backgroundexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button handlerExample,asynckExample;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handlerExample=(Button) findViewById(R.id.handlerExample);
        asynckExample=(Button) findViewById(R.id.asynckExample);
        handlerExample.setOnClickListener(this);
        asynckExample.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.handlerExample :
                Intent handlerIntent = new Intent(this,HandlerExample.class);
                startActivity(handlerIntent);
                break;
            case R.id.asynckExample :
                Intent asynckIntent = new Intent(this,AsynckExample.class);
                startActivity(asynckIntent);
                break;
        }
    }
}
