package com.example.yogeshkohli.personalbook;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NoteTypeTextActivity extends AppCompatActivity {

    int startHour, startMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_type_text);
        setFocusOnTitleEditText();
    }

    //Save note Button Action
    public void saveNoteTextTypeButtonClicked(View button) {
        validateForm();
    }

    public void validateForm(){
        if (getEditTextTitle().length() == 0 || getEditTextNoteContent().length() == 0){
            showToast(Constants.EMPTY_EDIT_TEXT);
        }

        //Everything is fine, now save the data
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

    //set reminder Button Action
    public void setReminderButtonClicked(View button) {
      //Open time dialog picker
        TimePickerDialog timePickerDialog = new TimePickerDialog(NoteTypeTextActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startHour = hourOfDay;
                startMinute = minute;
                //change title of button
                Button buttonReminderTime = (Button) findViewById(R.id.buttonSetReminder);
                buttonReminderTime.setText("Set time " + displayTime(startHour, startMinute));
            }
        } , getCurrentHour(), getCurrentMinute(), true);
        timePickerDialog.show();
    }
}
