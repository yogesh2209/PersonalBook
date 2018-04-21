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

    public void setAboutText() {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.textViewAboutDetails);
        myAwesomeTextView.setText("This application is developed as a final project for Android development Class (Computer Science Department) which comes under Extended Studies - San Diego State University.\n\nThis app aims to provide a seamless experience in which user can create his/her personal notes.\n\nFEATURES:\n\n1. Create customised notes.\n2. Make them password protected.\n3. Edit / Update / Delete Notes.\n\n");
    }
}
