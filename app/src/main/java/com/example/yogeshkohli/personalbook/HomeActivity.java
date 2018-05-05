package com.example.yogeshkohli.personalbook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class HomeActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupGridView();
    }

    //setting up grid view
    public void setupGridView(){
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        //Set Click Event
        setClickEvent(mainGrid);
    }

    //Handle click on grid cells
    private void setClickEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch(finalI){

                        case 0:
                            //New note
                            fireIntent(new NewNoteOptionsActivity());
                            break;

                        case 1:
                            //Viewing Mode
                            storeIndicatorInSharedPreferences("1");
                            fireIntent(new NotesListActivity());
                            break;

                        case 2:
                            //Editing Mode
                            storeIndicatorInSharedPreferences("2");
                            fireIntent(new NotesListActivity());
                            break;

                        case 3:
                            //About app
                            fireIntent(new AboutAppActivity());
                            break;

                        case 4:
                            //contact developer - about developer also
                            fireIntent(new ContactDeveloperActivity());
                            break;

                        case 5:
                            //help - email developer if you have any queries
                            fireIntent(new HelpActivity());
                            break;
                    }
                }
            });
        }
    }

    //Fire Intent to different Activities according to requirement
    public void fireIntent(Activity page){
        Intent intent = new Intent(HomeActivity.this,page.getClass());
        startActivity(intent);
    }

    //storing data in shared preferences
    //if indicator = 1 => view note mode
    //if indicator = 2 => edit note mode
    public void storeIndicatorInSharedPreferences(String indicator) {
        Intent myIntent = getIntent();
        SharedPreferences.Editor editor = getSharedPreferences("IndicatorData", MODE_PRIVATE).edit();
        editor.putString("indicator",indicator);
        editor.apply();
    }
}
