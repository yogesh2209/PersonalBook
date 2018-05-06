package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class PasswordEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_enter);
        setFocusOnPasswordEditText();
    }

    //Get Started Button Action
    public void passwordDoneButtonClicked(View button) {

        if (getEditTextPassword().length() == 0){
            showToast(Constants.EMPTY_PASSWORD);
            return;
        }

        if (!isPasswordCorrect()) {
            showToast(Constants.PASSWORD_NOT_MATCHED_ERROR);
            return;
        }
        //Take him to next screen i.e note detail one
        Intent i = new Intent(this, NoteDetailsScreenActivity.class);
        i.putExtra("password", getPasswordFromPreviousScreen());
        i.putExtra("noteId", getNoteIdFromPreviousScreen());
        i.putExtra("chapterName", getChapterNameFromPreviousScreen());
        i.putExtra("content", getChapterContentFromPreviousScreen());
        i.putExtra("currentDate", getDateFromPreviousScreen());
        i.putExtra("type", getNoteTypePreviousScreen());
        startActivity(i);
    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String getEditTextPassword(){
        EditText pswdText = (EditText) findViewById(R.id.editTextPasswordToUnlock);
        String  pswdString = pswdText.getText().toString().trim();
        return pswdString;
    }

    public Boolean isPasswordCorrect(){
        if (getEditTextPassword().matches(getPasswordFromPreviousScreen()) && getEditTextPassword().length() != 0) {
            return true;
        }
        return false;
    }

    //Setting the focus to first name edit text
    public void setFocusOnPasswordEditText() {
        EditText titleEditText = (EditText) findViewById(R.id.editTextPasswordToUnlock);
        titleEditText.requestFocus();
    }

    /* Getting all the data from previous screen */

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
    public String getDateFromPreviousScreen() {
        return getIntent().getStringExtra("currentDate");
    }

    //getting Chapter content from previous screen
    public String getNoteTypePreviousScreen() {
        return getIntent().getStringExtra("type");
    }
}
