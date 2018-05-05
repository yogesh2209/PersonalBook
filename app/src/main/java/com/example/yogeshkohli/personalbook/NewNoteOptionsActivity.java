package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewNoteOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note_options);
    }

    //Note type text Button Action
    public void noteOptionTextButtonClicked(View button) {
        Intent i = new Intent(this, NoteTypeTextActivity.class);
        startActivity(i);
    }

    //Note type Draw Button Action
    public void noteOptionDrawButtonClicked(View button) {
        Intent i = new Intent(this, NoteTypeDrawActivity.class);
        startActivity(i);
    }
}

