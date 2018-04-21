package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ThingsToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list2);
    }


    /* Button Actions */

    //Contact button action
    public void contactButtonClicked(View button) {
        Intent i = new Intent(this, ContactDeveloperActivity.class);
        startActivity(i);
    }

    //Help Button Action
    public void helpButtonClicked(View button) {
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
    }

    //About App Button Action
    public void aboutButtonClicked(View button) {
        Intent i = new Intent(this, AboutAppActivity.class);
        startActivity(i);
    }

    //View Saved Button Action
    public void viewSavedNotesButtonClicked(View button) {

    }

    //New Button Action
    public void newButtonClicked(View button) {

    }
}
