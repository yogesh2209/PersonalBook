package com.example.yogeshkohli.personalbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoteDetailsScreenActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details_screen);
        View editBtn = (Button) findViewById(R.id.buttonNoteDetailEditScreen);
        View deleteBtn = (Button) findViewById(R.id.buttonDelete);
        showOrHideEditDeleteButton(editBtn, deleteBtn, getStoredIndicator());
        updateUI();
        setupFirebase();
    }

    //toggle edit & delete button button - if view mode - hide and vice versa
    public void showOrHideEditDeleteButton(View editBtn, View deleteBtn, String indicator){
        if (indicator.matches("2")){
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }
        else{
            editBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }
    }

    //edit button action here
    public void editNoteButtonClicked(View button) {
        //Take him to editing screen
       fireIntent();
    }

    public void setupFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Boolean isNoteDeleted(){
        //Get datasnapshot at your "users" root node
        DatabaseReference ref = mDatabase.child("notes");
        if (getNoteIdFromPreviousScreen() != null){
            ref.child(getNoteIdFromPreviousScreen()).removeValue();
            return true;
        }
        return false;
    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //delete button action here
    public void deleteNoteButtonClicked(View button) {
        //if note id is not null, then delete the data from firebase and take him to home screen
        AlertDialog alertDialog = new AlertDialog.Builder(NoteDetailsScreenActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Do you want to delete this note?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //delete
                        if (isNoteDeleted()) {
                            //take him back to home
                            backToHomeScreen();
                        }
                        else{
                            //show toast of error
                            showToast(Constants.SOMETHING_WRONG_MESSAGE);
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //Intent method - take him to editing screen
    public void fireIntent() {
        Intent i = new Intent(this, NoteEditScreenActivity.class);
        i.putExtra("password", getPasswordFromPreviousScreen());
        i.putExtra("noteId", getNoteIdFromPreviousScreen());
        i.putExtra("chapterName", getChapterNameFromPreviousScreen());
        i.putExtra("content", getChapterContentFromPreviousScreen());
        i.putExtra("type", getNoteTypeFromPreviousScreen());
        startActivity(i);
    }

    //fire intent - take him back to home without creating new instance of it
    public void backToHomeScreen(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
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

    //setting chapter name
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

    //setting chapter content
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

    //setting password
    public void setPasswordField(String passwordField){
        if (!passwordField.matches("")) {
            TextView passwordFieldTextView = (TextView)findViewById(R.id.textViewPasswordenabledNoteDetailScreen);
            passwordFieldTextView.setText("Password: " + passwordField);
        }
        else{
            TextView passwordFieldTextView = (TextView)findViewById(R.id.textViewPasswordenabledNoteDetailScreen);
            passwordFieldTextView.setText("Not password protected");
        }
    }

    //setting date of the note
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

    //setting note type - text / draw
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
