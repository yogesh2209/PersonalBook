package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Get Started Button Action
    public void getStartedButtonClicked(View button) {
        Intent i = new Intent(this, ThingsToDoActivity.class);
        startActivity(i);
    }
}
