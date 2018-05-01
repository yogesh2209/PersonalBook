package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoteDetailsScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details_screen);
        View editBtn = (Button) findViewById(R.id.buttonNoteDetailEditScreen);
        showOrHideEditButton(editBtn, getStoredIndicator());
        updateUI();
    }

    public void showOrHideEditButton(View editBtn,String indicator){
        if (indicator.matches("2")){
            editBtn.setVisibility(View.VISIBLE);
        }
        else{
            editBtn.setVisibility(View.GONE);
        }
    }

    public void editNoteButtonClicked(View button) {
        //Take him to editing screen
    }

    //Calling all set methods here
    public void updateUI(){
        setChapterName(getChapterNameFromPreviousScreen());
        setChapterContent(getChapterContentFromPreviousScreen());
        setPasswordField(getPasswordFromPreviousScreen());
        setCurrentDate(getDateFromPreviousScreen());
        setNoteType(getNoteTypeFromPreviousScreen());
    }

    /* Set data to textviews on the screen */

    public void setChapterName(String chapterName){
        if (chapterName != null) {
            TextView chapterNameTextView = (TextView)findViewById(R.id.textViewChapterNameNoteDetaiScreen);
            chapterNameTextView.setText(chapterName);
        }
        else{
            TextView chapterNameTextView = (TextView)findViewById(R.id.textViewChapterNameNoteDetaiScreen);
            chapterNameTextView.setText("Chapter name here");
        }
    }
    public void setChapterContent(String chapterContent){
        if (chapterContent != null) {
            TextView chapterContentTextView = (TextView)findViewById(R.id.textViewChapterContentNoteDetaiScreen);
            chapterContentTextView.setText("Content: " + chapterContent);
        }
        else {
            TextView chapterContentTextView = (TextView)findViewById(R.id.textViewChapterContentNoteDetaiScreen);
            chapterContentTextView.setText("Content here");
        }
    }

    public void setPasswordField(String passwordField){
        if (passwordField != null) {
            TextView passwordFieldTextView = (TextView)findViewById(R.id.textViewPasswordenabledNoteDetailScreen);
            passwordFieldTextView.setText("Password: " + passwordField);
        }
        else{
            TextView passwordFieldTextView = (TextView)findViewById(R.id.textViewPasswordenabledNoteDetailScreen);
            passwordFieldTextView.setText("Not password protected");
        }
    }

    public void setCurrentDate(String currentDate){
        if (currentDate != null) {
            TextView passwordFieldTextView = (TextView)findViewById(R.id.textViewDateNoteDetailScreen);
            passwordFieldTextView.setText("Note date: " + currentDate);
        }
        else{
            TextView passwordFieldTextView = (TextView)findViewById(R.id.textViewDateNoteDetailScreen);
            passwordFieldTextView.setText("Date here");
        }
    }

    public void setNoteType(String noteType){
        if (noteType != null) {
            TextView noteTypeTextView = (TextView)findViewById(R.id.textViewNoteTypeNoteDetailScreen);
            noteTypeTextView.setText("Note type: " + noteType);
        }
        else{
            TextView noteTypeTextView = (TextView)findViewById(R.id.textViewNoteTypeNoteDetailScreen);
            noteTypeTextView.setText("Note type here");
        }
    }

    /* Get data from previous screen */

    //getting password from previous screen
    public String getPasswordFromPreviousScreen() {
        return getIntent().getStringExtra("password");
    }

    //getting noteId from previous screen
    public String getNoteIdFromPreviousScreen() {
        return getIntent().getStringExtra("noteId");
    }

    //getting Chapter Name from previous screen
    public String getChapterNameFromPreviousScreen() {
        return getIntent().getStringExtra("chapterName");
    }

    //getting Chapter content from previous screen
    public String getChapterContentFromPreviousScreen() {
        return getIntent().getStringExtra("content");
    }

    //getting Chapter content from previous screen
    public String getNoteTypeFromPreviousScreen() {
        return getIntent().getStringExtra("type");
    }

    //getting Chapter content from previous screen
    public String getDateFromPreviousScreen() {
        return getIntent().getStringExtra("currentDate");
    }

    //Getting value from shared preference for showing / hiding of edit button
    //If indicator = 1 ; viewing mode i.e hide edit button else show
    public String getStoredIndicator() {
        SharedPreferences prefs = getSharedPreferences("IndicatorData", MODE_PRIVATE);
        String indicator = prefs.getString("indicator", null);
        if (indicator != null) {
            return indicator;
        }
        return "";
    }
}
