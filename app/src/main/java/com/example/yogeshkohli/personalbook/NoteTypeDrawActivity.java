package com.example.yogeshkohli.personalbook;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NoteTypeDrawActivity extends AppCompatActivity {

    int startHour, startMinute;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    ArrayList<String> chapterNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_type_draw);
        setFocusOnTitleEditText();

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
    }

     /* -------- GET EDIT TEXT VALUE METHODS -------------*/

    public String getEditTextTitle(){
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitleDraw);
        String  titleString = titleEditText.getText().toString().trim();
        return titleString;
    }
    public String getEditTextPassword(){
        EditText pswdText = (EditText) findViewById(R.id.editTextPasswordDraw);
        String  pswdString = pswdText.getText().toString().trim();
        return pswdString;
    }

    //Setting the focus to first name edit text
    public void setFocusOnTitleEditText() {
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitleDraw);
        titleEditText.requestFocus();
    }

    //Getting current time
    public Calendar getCurrentTime(){
        return Calendar.getInstance();
    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //get current date time
    public String getCurrentDateTime() {
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        LocalDateTime localTime= LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return localTime.format(formatter);
    }

    //set reminder Button Action
    public void setReminderButtonClicked(View button) {
        if (getEditTextTitle().length() == 0) {
            showToast(Constants.EMPTY_EDIT_TEXT);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getCurrentDateTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,getCurrentDateTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.Events.TITLE, getEditTextTitle());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Your description here");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        startActivity(intent);
    }
    //draw button action
    public void drawButtonClicked(View button) {
        Intent i = new Intent(this, DrawingActivity.class);
        startActivity(i);

    }
    //save button action
    public void saveNoteDrawTypeButtonClicked(View button) {
        validateForm();
    }
    //validating form
    public void validateForm(){
        if (getEditTextTitle().length() == 0){
            showToast(Constants.EMPTY_EDIT_TEXT);
            return;
        }

        //if it is true then return and show him already exist chapter
        if (isChapterNameExist(getEditTextTitle(), getChapterList())) {
            showToast(Constants.CHAPTER_ALREADY_EXIST);
            return;
        }

        chapterNames.add(getEditTextTitle());
        storeDataInSharedPreferences(buildArrayString(chapterNames));
        writeNewNote(getEditTextTitle(), "", "Draw", getNoteId(), getEditTextPassword(), getCurrentDateTime());
    }

    //generate random note id
    protected String getNoteId() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    //take back to home without creating new instance of it
    public void fireIntent(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    //writing new data to firebase
    public void writeNewNote(String chapterName, String noteContent, String noteType, String noteID, String password, String currentDate) {
        Note note = new Note(chapterName, noteContent, noteType, noteID, password, currentDate);
        mDatabase.child("notes").child(noteID).setValue(note);
        showToast(Constants.DATA_SAVED);
        fireIntent();
    }

    //storing data in shared preferences
    public void storeDataInSharedPreferences(StringBuilder strBuilder) {
        Intent myIntent = getIntent();
        SharedPreferences.Editor editor = getSharedPreferences("NotesData", MODE_PRIVATE).edit();
        if (chapterNames.size() != 0) {
            editor.putString("chaptersList", strBuilder.toString());
        }
        editor.apply();
    }

    //building arrayString with comma separated.
    public StringBuilder buildArrayString(ArrayList<String> chapters) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String chapter : chapters) {
            stringBuilder.append(chapter);
            stringBuilder.append(",");
        }
        return stringBuilder;
    }

    //getting stored datafrom shared preferences and showing on UI
    public Boolean isChapterNameExist(String currentChapterName, ArrayList<String> chapterList) {

        if (chapterList == null) {
            return false;
        }

        if (chapterList.size() != 0) {
            for (int i = 0; i < chapterList.size(); i++){
                if (currentChapterName.matches(chapterList.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    //return array of chapters
    public ArrayList<String> getChapterList() {
        SharedPreferences prefs = getSharedPreferences("NotesData", MODE_PRIVATE);
        String chapterString = prefs.getString("chaptersList", null);
        if (chapterString != null) {
            ArrayList<String> chapterList = new ArrayList<String>(Arrays.asList(chapterString.split(",")));
            return chapterList;
        }
        return null;
    }
}
