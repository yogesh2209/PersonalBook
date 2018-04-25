package com.example.yogeshkohli.personalbook;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NoteTypeDrawActivity extends AppCompatActivity {

    int startHour, startMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_type_draw);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(NoteTypeDrawActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    //set reminder Button Action
    public void drawButtonClicked(View button) {
        Intent i = new Intent(this, DrawingActivity.class);
        startActivity(i);

    }

    //set reminder Button Action
    public void saveNoteDrawTypeButtonClicked(View button) {

    }
}
