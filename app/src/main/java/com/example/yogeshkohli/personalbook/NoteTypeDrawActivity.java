package com.example.yogeshkohli.personalbook;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NoteTypeDrawActivity extends AppCompatActivity {

    int startHour, startMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_type_draw);
        setFocusOnTitleEditText();
    }

     /* -------- GET EDIT TEXT VALUE METHODS -------------*/

    public String getEditTextTitle(){
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitle);
        String  titleString = titleEditText.getText().toString().trim();
        return titleString;
    }
    public String getEditTextNoteContent(){
        EditText noteContentEditText = (EditText) findViewById(R.id.editTextContentChapter);
        String  noteContentString = noteContentEditText.getText().toString().trim();
        return noteContentString;
    }
    public String getEditTextPassword(){
        EditText pswdText = (EditText) findViewById(R.id.editTextPassword);
        String  pswdString = pswdText.getText().toString().trim();
        return pswdString;
    }

    //Setting the focus to first name edit text
    public void setFocusOnTitleEditText() {
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitle);
        titleEditText.requestFocus();
    }

    //get time
    public String getTime(int hour, int min){
        return (String.valueOf(hour) + String.valueOf(min));
    }
    //display time
    public String displayTime(int hour, int min){
        return (String.valueOf(hour) + " : " +  String.valueOf(min));
    }

    //Getting current time
    public Calendar getCurrentTime(){
        return Calendar.getInstance();
    }

    //Getting current hour
    public int getCurrentHour(){
        return getCurrentTime().get(Calendar.HOUR_OF_DAY);
    }

    //Getting current minute
    public int getCurrentMinute(){
        return getCurrentTime().get(Calendar.MINUTE);
    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public Date getCurrentDateTime() {
        return Calendar.getInstance().getTime();
    }

    //set reminder Button Action
    public void setReminderButtonClicked(View button) {
        if (getEditTextTitle().length() == 0 || getEditTextNoteContent().length() == 0) {
            showToast(Constants.EMPTY_EDIT_TEXT);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getCurrentDateTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,getCurrentDateTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.Events.TITLE, getEditTextTitle());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, getEditTextNoteContent());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        startActivity(intent);
    }
    public void drawButtonClicked(View button) {
        Intent i = new Intent(this, DrawingActivity.class);
        startActivity(i);

    }
    public void saveNoteDrawTypeButtonClicked(View button) {

    }
}
