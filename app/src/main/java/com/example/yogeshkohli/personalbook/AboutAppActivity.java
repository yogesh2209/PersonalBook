package com.example.yogeshkohli.personalbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        setAboutText();
    }

    //setting about text function here
    public void setAboutText() {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.textViewAboutDetails);
        myAwesomeTextView.setText(R.string.aboutAppMessage);
    }
}
